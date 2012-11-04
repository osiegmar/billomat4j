/*
 * Copyright 2012 Oliver Siegmar <oliver@siegmar.net>
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
package net.siegmar.billomat4j.sdk.json;

import java.io.IOException;
import java.util.List;

import net.siegmar.billomat4j.sdk.domain.EmailRecipients;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomEmailRecipientsSerializer extends JsonSerializer<EmailRecipients> {

    @Override
    public void serialize(final EmailRecipients value, final JsonGenerator jgen, final SerializerProvider provider)
        throws IOException {

        jgen.writeStartObject();
        serialize(value.getToRecipients(), "to", jgen);
        serialize(value.getCcRecipients(), "cc", jgen);
        serialize(value.getBccRecipients(), "bcc", jgen);
        jgen.writeEndObject();
    }

    private void serialize(final List<String> recipients, final String name, final JsonGenerator jgen)
        throws IOException {

        if (recipients != null) {
            for (final String recipient : recipients) {
                jgen.writeStringField(name, recipient);
            }
        }
    }

}
