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
package net.siegmar.billomat4j.sdk.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class EmailRecipients {

    private List<String> toRecipients;
    private List<String> ccRecipients;
    private List<String> bccRecipients;

    public List<String> getToRecipients() {
        return toRecipients;
    }

    public void setToRecipients(final List<String> toRecipients) {
        this.toRecipients = toRecipients;
    }

    public void addTo(final String to) {
        if (this.toRecipients == null) {
            this.toRecipients = new ArrayList<>();
        }
        this.toRecipients.add(to);
    }

    public List<String> getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(final List<String> ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public void addCc(final String cc) {
        if (this.ccRecipients == null) {
            this.ccRecipients = new ArrayList<>();
        }
        this.ccRecipients.add(cc);
    }

    public List<String> getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(final List<String> bccRecipients) {
        this.bccRecipients = bccRecipients;
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
