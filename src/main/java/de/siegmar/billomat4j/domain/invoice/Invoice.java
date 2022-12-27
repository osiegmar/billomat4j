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

package de.siegmar.billomat4j.domain.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.Taxes;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.domain.types.SupplyDateType;
import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("invoice")
public class Invoice extends AbstractMeta {

    private Integer clientId;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String invoiceNumber;

    private Integer number;
    private String numberPre;
    private InvoiceStatus status;
    private LocalDate date;
    private String supplyDate;
    private SupplyDateType supplyDateType;
    private LocalDate dueDate;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private Integer dueDays;

    private String address;
    private BigDecimal discountRate;
    private LocalDate discountDate;
    private Integer discountDays;
    private BigDecimal discountAmount;
    private String label;
    private String intro;
    private String note;
    private BigDecimal totalGross;
    private BigDecimal totalNet;
    private String reduction;
    private BigDecimal totalGrossUnreduced;
    private BigDecimal totalNetUnreduced;
    private Currency currencyCode;
    private BigDecimal quote;
    private Taxes taxes;
    private PaymentType[] paymentTypes;
    private Integer offerId;
    private Integer confirmationId;
    private Integer recurringId;
    private Integer contactId;

    @JsonProperty("customerportal_url")
    private String customerPortalUrl;

    private Integer templateId;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private BigDecimal paidAmount;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private BigDecimal openAmount;

    @JsonProperty("invoice-items")
    private InvoiceItems invoiceItems;

    private Integer numberLength;
    private String title;
    private BigDecimal totalReduction; // TODO undocumented feature - clarify with support
    private String netGross;
    private Integer invoiceId;
    private Boolean digProceeded; // TODO undocumented feature - clarify with support

    public void addInvoiceItem(final InvoiceItem invoiceItem) {
        if (invoiceItems == null) {
            invoiceItems = new InvoiceItems();
        }
        invoiceItems.getInvoiceItems().add(invoiceItem);
    }

    public PaymentType[] getPaymentTypes() {
        return paymentTypes != null ? paymentTypes.clone() : null;
    }

    public void setPaymentTypes(final PaymentType... paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

}
