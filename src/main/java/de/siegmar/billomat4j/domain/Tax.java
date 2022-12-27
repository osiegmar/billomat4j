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

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonRootName("tax")
public class Tax {

    private String name;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal amountPlain;
    private BigDecimal amountRounded;
    private BigDecimal amountNet;
    private BigDecimal amountNetPlain;
    private BigDecimal amountNetRounded;
    private BigDecimal amountGross;
    private BigDecimal amountGrossPlain;
    private BigDecimal amountGrossRounded;

}
