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
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import de.siegmar.billomat4j.domain.types.PaymentType;

public class PaymentTypesDeserializer extends JsonDeserializer<Set<PaymentType>> {

    @Override
    public Set<PaymentType> deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final String text = jp.getText();

        final Set<PaymentType> paymentTypes = new HashSet<>();
        if (text != null && !text.isEmpty()) {
            final String[] tokens = text.split(",");
            for (final String token : tokens) {
                paymentTypes.add(PaymentType.valueOf(token));
            }
        }

        return paymentTypes;
    }

}
