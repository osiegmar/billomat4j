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
package de.siegmar.billomat4j.sdk.domain.settings;

import java.math.BigDecimal;

import de.siegmar.billomat4j.sdk.domain.AbstractIdentifiable;
import de.siegmar.billomat4j.sdk.json.Views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

@JsonRootName("tax")
public class Tax extends AbstractIdentifiable {

    @JsonView(Views.NonSerialize.class)
    private Integer accountId;

    private String name;
    private BigDecimal rate;

    @JsonProperty("is_default")
    private Boolean defaultTax;

    public Integer getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(final BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean getDefaultTax() {
        return defaultTax;
    }

    public void setDefaultTax(final Boolean defaultTax) {
        this.defaultTax = defaultTax;
    }

}
