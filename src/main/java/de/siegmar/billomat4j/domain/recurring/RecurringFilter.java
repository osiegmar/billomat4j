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
package de.siegmar.billomat4j.domain.recurring;

import de.siegmar.billomat4j.domain.AbstractFilter;
import de.siegmar.billomat4j.domain.types.PaymentType;

public class RecurringFilter extends AbstractFilter<RecurringFilter> {

    public RecurringFilter byClientId(final int clientId) {
        return add("client_id", clientId);
    }

    public RecurringFilter byName(final String name) {
        return add("name", name);
    }

    public RecurringFilter byPaymentType(final PaymentType... paymentTypes) {
        return add("payment_type", paymentTypes);
    }

    public RecurringFilter byCycle(final RecurringCycle... cycles) {
        return add("cycle", cycles);
    }

    public RecurringFilter byLabel(final String label) {
        return add("label", label);
    }

    public RecurringFilter byIntro(final String intro) {
        return add("intro", intro);
    }

    public RecurringFilter byNote(final String note) {
        return add("note", note);
    }

}
