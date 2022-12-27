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

package de.siegmar.billomat4j.service;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.siegmar.billomat4j.RequestHelper;
import de.siegmar.billomat4j.domain.ByteString;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.json.ByteStringDeserializer;
import de.siegmar.billomat4j.json.ByteStringSerializer;
import de.siegmar.billomat4j.json.CustomBooleanDeserializer;
import de.siegmar.billomat4j.json.PaymentTypesDeserializer;
import de.siegmar.billomat4j.json.PaymentTypesSerializer;
import de.siegmar.billomat4j.json.Views;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("checkstyle:classdataabstractioncoupling")
public class BillomatConfiguration {

    private static final String GROUP_ID = "de.siegmar";
    private static final String ARTIFACT_ID = "billomat4j";

    private String billomatId;
    private String apiKey;
    private String appId;
    private String appSecret;
    private boolean secure = true;
    private boolean ignoreUnknownProperties = true;
    private RequestHelper requestHelper;
    private ObjectReader objectReader;
    private ObjectWriter objectWriter;

    RequestHelper getRequestHelper() {
        return requestHelper;
    }

    ObjectReader getObjectReader() {
        return objectReader;
    }

    ObjectWriter getObjectWriter() {
        return objectWriter;
    }

    synchronized void init() {
        if (requestHelper != null) {
            return;
        }

        Objects.requireNonNull(billomatId, "billomatId not configured");
        Objects.requireNonNull(apiKey, "apiKey not configured");

        requestHelper = new RequestHelper(this);

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.registerModule(
            new SimpleModule("CustomBooleanDeserializer",
                new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID))
                .addDeserializer(Boolean.class, new CustomBooleanDeserializer()));

        objectMapper.registerModule(
            new SimpleModule("PaymentTypesDeserializer",
                new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID))
                .addDeserializer(PaymentType[].class, new PaymentTypesDeserializer()));

        objectMapper.registerModule(
            new SimpleModule("PaymentTypesSerializer",
                new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID))
                .addSerializer(PaymentType[].class, new PaymentTypesSerializer()));

        objectMapper.registerModule(
            new SimpleModule("ByteStringDeserializer",
                new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID))
                .addDeserializer(ByteString.class, new ByteStringDeserializer()));

        objectMapper.registerModule(
            new SimpleModule("ByteStringSerializer",
                new Version(1, 0, 0, null, GROUP_ID, ARTIFACT_ID))
                .addSerializer(ByteString.class, new ByteStringSerializer()));

        objectReader = objectMapper.reader()
            .with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .with(DeserializationFeature.UNWRAP_ROOT_VALUE);

        if (isIgnoreUnknownProperties()) {
            objectReader = objectReader.without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }

        objectWriter = objectMapper.writer()
            .withView(Views.Default.class)
            .with(SerializationFeature.WRAP_ROOT_VALUE)
            .without(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)
            .without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
