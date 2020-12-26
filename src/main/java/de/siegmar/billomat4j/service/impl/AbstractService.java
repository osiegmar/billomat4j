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

package de.siegmar.billomat4j.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.siegmar.billomat4j.domain.AbstractDocumentPdf;
import de.siegmar.billomat4j.domain.DocumentComplete;
import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.Identifiable;
import de.siegmar.billomat4j.domain.Pageable;
import de.siegmar.billomat4j.domain.WrappedRecord;

// CSOFF: ClassFanOutComplexity
abstract class AbstractService {

    protected final RequestHelper requestHelper;
    protected final ObjectReader objectReader;
    protected final ObjectWriter objectWriter;

    protected AbstractService(final BillomatConfiguration billomatConfiguration) {
        billomatConfiguration.init();

        this.requestHelper = billomatConfiguration.getRequestHelper();
        this.objectReader = billomatConfiguration.getObjectReader();
        this.objectWriter = billomatConfiguration.getObjectWriter();
    }

    protected <T extends Pageable<E>, E> List<E> getAllPagesFromResource(final String resource,
                                                                         final Class<T> wrapperClass,
                                                                         final Filter filter) {

        final Pager<T, E> pager = new Pager<>(wrapperClass, requestHelper, objectReader);
        return pager.getAll(resource, filter);
    }

    protected <T extends WrappedRecord<E>, E> List<E> getAllFromResource(final String resource,
            final Class<T> wrapperClass, final Filter filter) {

        final Map<String, String> params = new HashMap<>();
        if (filter != null) {
            params.putAll(filter.toMap());
        }

        try {
            final byte[] data = requestHelper.get(resource, null, null, params);
            final WrappedRecord<E> wrapper = objectReader.forType(wrapperClass).readValue(data);
            return wrapper.getEntries();
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected <T> T getMySelf(final String resource, final Class<T> wrapperClass) {
        return getById(resource, wrapperClass, "myself");
    }

    protected <T> T getById(final String resource, final Class<T> wrapperClass, final Object id) {
        try {
            final byte[] data = requestHelper.get(resource, id.toString(), null, null);
            if (data == null) {
                return null;
            }
            return objectReader.forType(wrapperClass).readValue(data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void create(final String resource, final Object object) {
        try {
            final byte[] requestData = objectWriter.writeValueAsBytes(object);
            final byte[] responseData = requestHelper.post(resource, null, requestData);
            objectReader.withValueToUpdate(object).readValue(responseData);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void update(final String resource, final Identifiable object) {
        try {
            final byte[] requestData = objectWriter.writeValueAsBytes(object);
            final byte[] responseData = requestHelper.put(resource, null, object.getId().toString(), requestData);
            objectReader.withValueToUpdate(object).readValue(responseData);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void delete(final String resource, final int id) {
        try {
            requestHelper.delete(resource, Integer.toString(id));
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void transit(final String resource, final String method, final int id) {
        try {
            requestHelper.put(resource, method, Integer.toString(id), null);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void sendEmail(final String resource, final int id, final Email email) {
        Validate.notNull(email);
        Validate.notNull(email.getRecipients());
        Validate.notEmpty(email.getRecipients().getToRecipients());

        try {
            final byte[] data = objectWriter.writeValueAsBytes(email);
            requestHelper.post(resource, Integer.toString(id), "email", data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void completeDocument(final String resource, final int id, final Integer templateId) {
        final DocumentComplete confirmationComplete = new DocumentComplete();
        confirmationComplete.setTemplateId(templateId);
        try {
            final byte[] data = objectWriter.writeValueAsBytes(confirmationComplete);
            requestHelper.put(resource, "complete", Integer.toString(id), data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected <T extends AbstractDocumentPdf> T getPdf(final String resource, final Class<T> clazz, final int id,
                                                       final Map<String, String> filter) {

        try {
            final byte[] data = requestHelper.get(resource, Integer.toString(id), "pdf", filter);
            return objectReader.forType(clazz).readValue(data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void uploadSignedPdf(final String resource, final int id, final byte[] pdf) {
        final ObjectMapper om = new ObjectMapper();
        final ObjectNode rootNode = om.createObjectNode();
        final ObjectNode signature = rootNode.putObject("signature");
        signature.put("base64file", pdf);

        try {
            final byte[] data = om.writeValueAsBytes(rootNode);

            requestHelper.put(resource, "upload-signature", Integer.toString(id), data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected String getCustomField(final String resource, final int id) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final byte[] data = requestHelper.get(resource, Integer.toString(id), "customfield", null);
            final JsonNode jsonNode = mapper.readValue(data, JsonNode.class);
            return jsonNode.findValue("customfield").asText();
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected void updateCustomField(final String resource, final int id, final String rootName, final String value) {
        final ObjectMapper om = new ObjectMapper();
        final ObjectNode rootNode = om.createObjectNode();
        final ObjectNode firstNode = rootNode.putObject(rootName);
        firstNode.put("customfield", value);

        try {
            final byte[] data = om.writeValueAsBytes(rootNode);

            requestHelper.put(resource, "customfield", Integer.toString(id), data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    protected static <T> T single(final List<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new IllegalArgumentException("list must not contain > 1 elements");
        }

        return list.get(0);
    }

}
