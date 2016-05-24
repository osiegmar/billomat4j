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
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.siegmar.billomat4j.sdk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RequestHelper {

    private static final int FIRST_HTTP_ERROR_CODE = 400;
    private static final String CONTENT_TYPE = "application/json";
    private static final int BUF_SIZE = 2048;
    private static final int HTTP_NOT_FOUND = 404;
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_PUT = "PUT";
    private static final String HTTP_DELETE = "DELETE";
    private static final String ENCODING = "UTF-8";
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 60000;
    private static final String USER_AGENT = "Billomat4J/" + Billomat4JSettings.getVersion();

    private static final Logger LOG = LoggerFactory.getLogger(RequestHelper.class);

    private final BillomatConfiguration billomatConfiguration;

    public RequestHelper(final BillomatConfiguration billomatConfiguration) {
        this.billomatConfiguration = billomatConfiguration;
    }

    public byte[] get(final String resource, final String id, final String method, final Map<String, String> filter)
        throws IOException {

        final URL url = buildUrl(resource, id, method, filter);
        final HttpURLConnection connection = prepareConnection(url, HTTP_GET);

        LOG.debug("Service status response: {} {}", connection.getResponseCode(), connection.getResponseMessage());

        if (connection.getResponseCode() == HTTP_NOT_FOUND) {
            return null;
        }

        if (isError(connection)) {
            try (InputStream inputStream = connection.getErrorStream()) {
                final byte[] result = readToByteArray(inputStream);
                throw new ServiceException("Service error response: code=" + connection.getResponseCode() + ", data=" +
                        new String(result, ENCODING));
            }
        }

        try (InputStream inputStream = connection.getInputStream()) {
            final byte[] data = readToByteArray(inputStream);
            if (LOG.isDebugEnabled()) {
                final String msg;
                if (!isBinaryContent(connection)) {
                    msg = new String(data, ENCODING);
                } else {
                    msg = "[binary]";
                }
                LOG.debug("Service response: {}", msg);
            }
            return data;
        }
    }

    private boolean isError(final HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() >= FIRST_HTTP_ERROR_CODE;
    }

    private boolean isBinaryContent(final HttpURLConnection connection) {
        return connection.getContentType().startsWith("image/");
    }

    public byte[] post(final String resource, final String method, final byte[] data) throws IOException {
        return post(resource, null, method, data);
    }

    public byte[] post(final String resource, final String id, final String method, final byte[] data)
        throws IOException {

        return sendAndReceive(resource, method, id, data, HTTP_POST);
    }

    public byte[] put(final String resource, final String method, final String id, final byte[] data)
        throws IOException {

        return sendAndReceive(resource, method, id, data, HTTP_PUT);
    }

    private byte[] sendAndReceive(final String resource, final String method, final String id, final byte[] data,
            final String type) throws IOException {

        final URL url = buildUrl(resource, id, method, null);
        final HttpURLConnection connection = prepareConnection(url, type);
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setDoOutput(true);

        if (data != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Service request: {}", new String(data, ENCODING));
            }

            connection.setRequestProperty("Content-Length", "" + data.length);

            try (final OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(data);
            }
        }

        LOG.debug("Service status response: {} {}", connection.getResponseCode(), connection.getResponseMessage());

        if (isError(connection)) {
            try (InputStream inputStream = connection.getErrorStream()) {
                final byte[] result = readToByteArray(inputStream);
                if (result.length > 0) {
                    throw new ServiceException("Service error response: code=" + connection.getResponseCode() +
                        ", data=" + new String(result, ENCODING));
                }
            }
        }

        try (InputStream inputStream = connection.getInputStream()) {
            final byte[] result = readToByteArray(inputStream);
            if (result.length > 0 && LOG.isDebugEnabled()) {
                LOG.debug("Service response: {}", new String(result, ENCODING));
            }
            return result;
        }
    }

    private static byte[] readToByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        final byte[] buf = new byte[BUF_SIZE];
        int cnt;
        while ((cnt = inputStream.read(buf)) != -1) {
            bos.write(buf, 0, cnt);
        }

        return bos.toByteArray();
    }

    public void delete(final String resource, final String id) throws IOException {
        final URL url = buildUrl(resource, id, null, null);
        final HttpURLConnection connection = prepareConnection(url, HTTP_DELETE);

        LOG.debug("Service status response: {} {}", connection.getResponseCode(), connection.getResponseMessage());

        if (isError(connection)) {
            try (InputStream inputStream = connection.getErrorStream()) {
                final byte[] result = readToByteArray(inputStream);
                throw new ServiceException("Service error response: " + new String(result, ENCODING));
            }
        }
    }

    private HttpURLConnection prepareConnection(final URL url, final String type) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestProperty("X-BillomatApiKey", billomatConfiguration.getApiKey());
        if (billomatConfiguration.getAppId() != null) {
            connection.setRequestProperty("X-AppId", billomatConfiguration.getAppId());
        }
        if (billomatConfiguration.getAppSecret() != null) {
            connection.setRequestProperty("X-AppSecret", billomatConfiguration.getAppSecret());
        }
        connection.setRequestProperty("Accept", CONTENT_TYPE);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestMethod(type);

        LOG.debug("HTTP request: {} {}", type, url);

        return connection;
    }

    private URL buildUrl(final String resource, final String id, final String method,
            final Map<String, String> filter) {

        Validate.notEmpty(resource, "resource required");

        final StringBuilder sb = new StringBuilder();
        if (billomatConfiguration.isSecure()) {
            sb.append("https");
        } else {
            sb.append("http");
        }
        sb.append("://");
        sb.append(billomatConfiguration.getBillomatId());
        sb.append(".billomat.net/api/");
        sb.append(resource);

        if (id != null) {
            sb.append("/");
            sb.append(id);
        }

        if (method != null) {
            sb.append("/");
            sb.append(method);
        }

        if (filter != null && !filter.isEmpty()) {
            sb.append("?");
            for (final Iterator<Entry<String, String>> iterator = filter.entrySet().iterator(); iterator.hasNext();) {
                final Entry<String, String> entry = iterator.next();
                sb.append(entry.getKey());
                if (entry.getValue() != null) {
                    sb.append("=");
                    sb.append(encodeValue(entry));
                }

                if (iterator.hasNext()) {
                    sb.append("&");
                }
            }
        }

        try {
            return new URL(sb.toString());
        } catch (final MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    private String encodeValue(final Entry<String, String> entry) {
        try {
            return URLEncoder.encode(entry.getValue(), ENCODING);
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
