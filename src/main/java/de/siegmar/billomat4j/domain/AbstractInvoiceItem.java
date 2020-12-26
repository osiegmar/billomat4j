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

package de.siegmar.billomat4j.domain;

import java.math.BigDecimal;

public abstract class AbstractInvoiceItem extends AbstractItem {

    private String taxName;
    private BigDecimal taxRate;
    private String reduction;
    private BigDecimal totalGross;
    private BigDecimal totalNet;
    private BigDecimal totalGrossUnreduced;
    private BigDecimal totalNetUnreduced;

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(final String taxName) {
        this.taxName = taxName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(final BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(final String reduction) {
        this.reduction = reduction;
    }

    public BigDecimal getTotalGross() {
        return totalGross;
    }

    public void setTotalGross(final BigDecimal totalGross) {
        this.totalGross = totalGross;
    }

    public BigDecimal getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(final BigDecimal totalNet) {
        this.totalNet = totalNet;
    }

    public BigDecimal getTotalGrossUnreduced() {
        return totalGrossUnreduced;
    }

    public void setTotalGrossUnreduced(final BigDecimal totalGrossUnreduced) {
        this.totalGrossUnreduced = totalGrossUnreduced;
    }

    public BigDecimal getTotalNetUnreduced() {
        return totalNetUnreduced;
    }

    public void setTotalNetUnreduced(final BigDecimal totalNetUnreduced) {
        this.totalNetUnreduced = totalNetUnreduced;
    }

}
