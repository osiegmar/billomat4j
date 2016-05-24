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
package de.siegmar.billomat4j.sdk.domain.creditnote;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import de.siegmar.billomat4j.sdk.domain.WrappedRecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("credit-note-groups")
public class CreditNoteGroups implements WrappedRecord<CreditNoteGroup> {

    @JsonProperty("credit-note-group")
    private List<CreditNoteGroup> creditNoteGroups = new ArrayList<>();

    @JsonProperty("@currency_code")
    private Currency currencyCode;

    public List<CreditNoteGroup> getCreditNoteGroups() {
        return creditNoteGroups;
    }

    public void setCreditNoteGroups(final List<CreditNoteGroup> creditNoteGroups) {
        this.creditNoteGroups = creditNoteGroups;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonIgnore
    @Override
    public List<CreditNoteGroup> getEntries() {
        return creditNoteGroups;
    }

}
