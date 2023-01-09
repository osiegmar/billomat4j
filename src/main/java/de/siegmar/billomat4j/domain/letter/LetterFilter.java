/*
 * Copyright 2023 Oliver Siegmar
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

package de.siegmar.billomat4j.domain.letter;

import java.time.LocalDate;

import de.siegmar.billomat4j.domain.AbstractFilter;
import lombok.ToString;

@ToString(callSuper = true)
public class LetterFilter extends AbstractFilter<LetterFilter> {

    public LetterFilter byClientId(final int clientId) {
        return add("client_id", clientId);
    }

    public LetterFilter byContactId(final int contactId) {
        return add("contact_id", contactId);
    }

    public LetterFilter bySupplierId(final int supplierId) {
        return add("supplier_id", supplierId);
    }

    public LetterFilter byStatus(final LetterStatus status) {
        return add("status", status);
    }

    public LetterFilter byFrom(final LocalDate from) {
        return add("from", from);
    }

    public LetterFilter byTo(final LocalDate to) {
        return add("to", to);
    }

    public LetterFilter byLabel(final String label) {
        return add("label", label);
    }

    public LetterFilter byIntro(final String intro) {
        return add("intro", intro);
    }

    public LetterFilter byTags(final String... tags) {
        return add("tags", tags);
    }

}
