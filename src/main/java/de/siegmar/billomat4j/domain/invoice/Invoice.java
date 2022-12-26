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

@JsonRootName("invoice")
public class Invoice extends AbstractMeta {

    private Integer clientId;

    @JsonView(Views.NonSerialize.class)
    private String invoiceNumber;

    private Integer number;
    private String numberPre;
    private InvoiceStatus status;
    private LocalDate date;
    private String supplyDate;
    private SupplyDateType supplyDateType;
    private LocalDate dueDate;

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
    private String customerportalUrl;
    private Integer templateId;

    @JsonView(Views.NonSerialize.class)
    private BigDecimal paidAmount;

    @JsonView(Views.NonSerialize.class)
    private BigDecimal openAmount;

    @JsonProperty("invoice-items")
    private InvoiceItems invoiceItems;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(final Integer clientId) {
        this.clientId = clientId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public String getNumberPre() {
        return numberPre;
    }

    public void setNumberPre(final String numberPre) {
        this.numberPre = numberPre;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(final InvoiceStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(final String supplyDate) {
        this.supplyDate = supplyDate;
    }

    public SupplyDateType getSupplyDateType() {
        return supplyDateType;
    }

    public void setSupplyDateType(final SupplyDateType supplyDateType) {
        this.supplyDateType = supplyDateType;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(final LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(final BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public LocalDate getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(final LocalDate discountDate) {
        this.discountDate = discountDate;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(final Integer discountDays) {
        this.discountDays = discountDays;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(final BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(final String intro) {
        this.intro = intro;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
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

    public String getReduction() {
        return reduction;
    }

    public void setReduction(final String reduction) {
        this.reduction = reduction;
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

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(final BigDecimal quote) {
        this.quote = quote;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(final Taxes taxes) {
        this.taxes = taxes;
    }

    public PaymentType[] getPaymentTypes() {
        return paymentTypes != null ? paymentTypes.clone() : null;
    }

    public void setPaymentTypes(final PaymentType... paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(final Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(final Integer confirmationId) {
        this.confirmationId = confirmationId;
    }

    public Integer getRecurringId() {
        return recurringId;
    }

    public void setRecurringId(final Integer recurringId) {
        this.recurringId = recurringId;
    }

    public InvoiceItems getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(final InvoiceItems invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItem(final InvoiceItem invoiceItem) {
        if (invoiceItems == null) {
            invoiceItems = new InvoiceItems();
        }
        invoiceItems.getInvoiceItems().add(invoiceItem);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getOpenAmount() {
        return openAmount;
    }

    public String getCustomerportalUrl() {
        return customerportalUrl;
    }

    public void setCustomerportalUrl(final String customerportalUrl) {
        this.customerportalUrl = customerportalUrl;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final Integer templateId) {
        this.templateId = templateId;
    }

}
