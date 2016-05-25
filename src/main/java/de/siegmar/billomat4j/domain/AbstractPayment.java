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
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.json.Views;

public abstract class AbstractPayment extends AbstractMeta {

    @JsonView(Views.NonSerialize.class)
    private Integer userId;

    @JsonInclude(Include.NON_NULL)
    private LocalDate date;

    private BigDecimal amount;
    private String comment;

    @JsonProperty("type")
    private PaymentType paymentType;

    private Boolean markInvoiceAsPaid;

    public Integer getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getMarkInvoiceAsPaid() {
        return markInvoiceAsPaid;
    }

    public void setMarkInvoiceAsPaid(final Boolean markInvoiceAsPaid) {
        this.markInvoiceAsPaid = markInvoiceAsPaid;
    }

}
