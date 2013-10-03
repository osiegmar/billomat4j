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
package net.siegmar.billomat4j.sdk.domain.recurring;

import net.siegmar.billomat4j.sdk.domain.AbstractIdentifiable;
import net.siegmar.billomat4j.sdk.domain.types.RecipientType;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("recurring-email-receiver")
public class RecurringEmailReceiver extends AbstractIdentifiable {

    private Integer recurringId;
    private RecipientType type;
    private String address;

    public Integer getRecurringId() {
        return recurringId;
    }

    public void setRecurringId(final Integer recurringId) {
        this.recurringId = recurringId;
    }

    public RecipientType getType() {
        return type;
    }

    public void setType(final RecipientType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

}
