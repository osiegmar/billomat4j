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
package net.siegmar.billomat4j.sdk.domain.invoice;

import java.math.BigDecimal;
import java.util.Date;

import net.siegmar.billomat4j.sdk.domain.AbstractIdentifiable;
import net.siegmar.billomat4j.sdk.json.MyDateSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName("invoice-group")
public class InvoiceGroup extends AbstractIdentifiable {

    private BigDecimal totalGross;
    private BigDecimal totalNet;
    private Integer clientId;
    private InvoiceStatus status;

    @JsonSerialize(using = MyDateSerializer.class)
    @JsonInclude(Include.NON_NULL)
    private Date day;

    private String week;
    private String month;
    private Integer year;

    @JsonProperty("invoice-params")
    private InvoiceParams invoiceParams;

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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(final Integer clientId) {
        this.clientId = clientId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(final InvoiceStatus status) {
        this.status = status;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(final Date day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(final String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(final String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public InvoiceParams getInvoiceParams() {
        return invoiceParams;
    }

    public void setInvoiceParams(final InvoiceParams invoiceParams) {
        this.invoiceParams = invoiceParams;
    }

}
