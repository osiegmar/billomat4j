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

package de.siegmar.billomat4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRecipients {

    @JsonProperty("to")
    private List<String> toRecipients;

    @JsonProperty("cc")
    private List<String> ccRecipients;

    @JsonProperty("bcc")
    private List<String> bccRecipients;

    public void addTo(final String to) {
        if (this.toRecipients == null) {
            this.toRecipients = new ArrayList<>();
        }
        this.toRecipients.add(to);
    }

    public void addCc(final String cc) {
        if (this.ccRecipients == null) {
            this.ccRecipients = new ArrayList<>();
        }
        this.ccRecipients.add(cc);
    }

    public void addBcc(final String bcc) {
        if (this.bccRecipients == null) {
            this.bccRecipients = new ArrayList<>();
        }
        this.bccRecipients.add(bcc);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
