/*
 * Copyright 2012 Oliver Siegmar
 *
 * This file is part of Billomat4J.
 *
 * Billomat4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Billomat4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.siegmar.billomat4j.service.Billomat4JSettings;
import de.siegmar.billomat4j.service.BillomatConfiguration;
import de.siegmar.billomat4j.service.ServiceException;

public class RequestHelper {

    private static final String CONTENT_TYPE = "application/json";
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_PUT = "PUT";
    private static final String HTTP_DELETE = "DELETE";
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 60000;
    private static final String USER_AGENT = "Billomat4J/" + Billomat4JSettings.getVersion();
    private static final int SC_CLIENT_ERROR = 400;
    private static final int SC_NOT_FOUND = 404;
    private static final int SC_SERVER_ERROR = 500;
    private static final Logger LOG = LoggerFactory.getLogger(RequestHelper.class);

    private final BillomatConfiguration billomatConfiguration;
    private final HttpClient httpClient;

    public RequestHelper(final BillomatConfiguration billomatConfiguration) {
        this.billomatConfiguration = billomatConfiguration;
        httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
            .build();
    }

    public byte[] get(final String resource, final String id, final String method, final Map<String, String> filter)
        throws IOException {

        final HttpResponse<byte[]> res = sendAndReceive(resource, method, id, null, HTTP_GET, filter);

        if (isClientError(res.statusCode())) {
            if (res.statusCode() == SC_NOT_FOUND) {
                return null;
            }

            throw new ServiceException("Service error response: code=" + res.statusCode()
                + ", data=" + new String(res.body(), StandardCharsets.UTF_8));
        }

        return res.body();
    }

    private boolean isClientError(final int statusCode) {
        return statusCode >= SC_CLIENT_ERROR && statusCode < SC_SERVER_ERROR;
    }

    private boolean isServerError(final int statusCode) {
        return statusCode >= SC_SERVER_ERROR;
    }

    private boolean isBinaryContent(final HttpResponse<byte[]> connection) {
        final Optional<String> contentType = connection.headers().firstValue("Content-Type");
        return contentType.isPresent() && contentType.get().startsWith("image/");
    }

    public byte[] post(final String resource, final String method, final byte[] data) throws IOException {
        return post(resource, null, method, data);
    }

    public byte[] post(final String resource, final String id, final String method, final byte[] data)
        throws IOException {

        return sendAndReceive(resource, method, id, data, HTTP_POST, null).body();
    }

    public byte[] put(final String resource, final String method, final String id, final byte[] data)
        throws IOException {

        return sendAndReceive(resource, method, id, data, HTTP_PUT, null).body();
    }

    private HttpResponse<byte[]> sendAndReceive(final String resource, final String method, final String id,
                                                final byte[] data, final String type, final Map<String, String> filter)
        throws IOException {

        // build request
        final URI uri = buildUrl(resource, id, method, filter);
        final HttpRequest request = prepareRequest(data, type, uri);

        LOG.debug("HTTP request: {}", request);
        if (data != null && data.length > 0 && LOG.isDebugEnabled()) {
            LOG.debug("Service request: {}", new String(data, StandardCharsets.UTF_8));
        }

        // send request
        final HttpResponse<byte[]> res;
        try {
            res = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }

        // handle response
        LOG.debug("Service status response: {}", res.statusCode());
        if (isServerError(res.statusCode())) {
            throw new ServiceException("Service error response: code=" + res.statusCode()
                + ", data=" + new String(res.body(), StandardCharsets.UTF_8));
        }

        if (res.body() != null && res.body().length > 0 && LOG.isDebugEnabled()) {
            LOG.debug("Service response: {}",
                isBinaryContent(res) ? "[binary]" : new String(res.body(), StandardCharsets.UTF_8));
        }

        return res;
    }

    private HttpRequest prepareRequest(final byte[] data, final String type, final URI uri) {
        if (data != null) {
            return prepareConnection(uri)
                .setHeader("Content-Type", CONTENT_TYPE)
                .method(type, HttpRequest.BodyPublishers.ofByteArray(data))
                .build();
        }

        return prepareConnection(uri)
            .method(type, HttpRequest.BodyPublishers.noBody())
            .build();
    }

    public void delete(final String resource, final String id) throws IOException {
        sendAndReceive(resource, null, id, null, HTTP_DELETE, null);
    }

    private HttpRequest.Builder prepareConnection(final URI uri) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
            .timeout(Duration.ofMillis(READ_TIMEOUT))
            .setHeader("Accept", CONTENT_TYPE)
            .setHeader("User-Agent", USER_AGENT)
            .setHeader("X-BillomatApiKey", billomatConfiguration.getApiKey());

        if (billomatConfiguration.getAppId() != null) {
            builder.setHeader("X-AppId", billomatConfiguration.getAppId());
        }
        if (billomatConfiguration.getAppSecret() != null) {
            builder.setHeader("X-AppSecret", billomatConfiguration.getAppSecret());
        }

        return builder;
    }

    URI buildUrl(final String resource, final String id, final String method,
                 final Map<String, String> filter) {

        if (resource == null || resource.isEmpty()) {
            throw new IllegalArgumentException("resource required");
        }

        final StringBuilder sb = new StringBuilder();

        sb.append(billomatConfiguration.isSecure() ? "https" : "http");
        sb.append("://");
        sb.append(billomatConfiguration.getBillomatId()).append(".billomat.net");
        sb.append(buildPath(resource, id, method));

        if (filter != null) {
            sb.append('?');
            sb.append(buildQS(filter));
        }

        return URI.create(sb.toString());
    }

    private String buildPath(final String resource, final String id, final String method) {
        final StringBuilder sb = new StringBuilder();

        sb.append("/api/");
        sb.append(resource);

        if (id != null) {
            sb.append('/');
            sb.append(id);
        }

        if (method != null) {
            sb.append('/');
            sb.append(method);
        }

        return sb.toString();
    }

    private String buildQS(final Map<String, String> filter) {
        return filter.entrySet().stream()
            .map(e -> e.getKey() + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));
    }

    private String encode(final String entry) {
        return URLEncoder.encode(entry, StandardCharsets.UTF_8);
    }

}
