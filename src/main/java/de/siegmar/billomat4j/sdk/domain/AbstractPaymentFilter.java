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
package de.siegmar.billomat4j.sdk.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.time.DateFormatUtils;

import de.siegmar.billomat4j.sdk.domain.types.PaymentType;

public abstract class AbstractPaymentFilter<T extends AbstractPaymentFilter<?>> extends AbstractFilter<T> {

    public T byFrom(final LocalDate from) {
        return add("from", DateFormatUtils.ISO_DATE_FORMAT.format(from));
    }

    public T byTo(final LocalDate to) {
        return add("to", DateFormatUtils.ISO_DATE_FORMAT.format(to));
    }

    public T byType(final PaymentType... paymentTypes) {
        return add("type", paymentTypes);
    }

    public T byUserId(final int userId) {
        return add("user_id", userId);
    }

}
