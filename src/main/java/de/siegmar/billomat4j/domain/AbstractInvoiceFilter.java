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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.ToString;

@ToString(callSuper = true)
public abstract class AbstractInvoiceFilter<T extends AbstractInvoiceFilter<T>> extends AbstractFilter<T> {

    public T byClientId(final int clientId) {
        return add("client_id", clientId);
    }

    public T byContactId(final int contactId) {
        return add("contact_id", contactId);
    }

    public T byFrom(final LocalDate from) {
        return add("from", DateTimeFormatter.ISO_DATE.format(from));
    }

    public T byTo(final LocalDate to) {
        return add("to", DateTimeFormatter.ISO_DATE.format(to));
    }

    public T byLabel(final String label) {
        return add("label", label);
    }

    public T byIntro(final String intro) {
        return add("intro", intro);
    }

    public T byNote(final String note) {
        return add("note", note);
    }

    public T byArticleId(final int articleId) {
        return add("article_id", articleId);
    }

    public T byTags(final String... tags) {
        return add("tags", tags);
    }

}
