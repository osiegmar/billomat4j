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

package de.siegmar.billomat4j.json;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import de.siegmar.billomat4j.domain.types.PaymentType;

public class PaymentTypesDeserializer extends JsonDeserializer<PaymentType[]> {

    @Override
    public PaymentType[] deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final String[] tokens = StringUtils.split(jp.getText(), ',');
        if (tokens == null || tokens.length == 0) {
            return null;
        }

        final PaymentType[] paymentTypes = new PaymentType[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            paymentTypes[i] = PaymentType.valueOf(tokens[i]);
        }

        return paymentTypes;
    }

}
