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

package de.siegmar.billomat4j.domain.deliverynote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.Taxes;
import de.siegmar.billomat4j.json.Views;

@JsonRootName("delivery-note")
public class DeliveryNote extends AbstractMeta {

    private Integer clientId;

    @JsonView(Views.NonSerialize.class)
    private String deliveryNoteNumber;

    private Integer number;
    private String numberPre;
    private DeliveryNoteStatus status;

    @JsonInclude(Include.NON_NULL)
    private LocalDate date;

    private String address;
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
    private Integer offerId;
    private Integer confirmationId;
    private Integer invoiceId;
    private DeliveryNoteItems deliveryNoteItems;
    private Integer contactId;
    private String customerportalUrl;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(final Integer clientId) {
        this.clientId = clientId;
    }

    public String getDeliveryNoteNumber() {
        return deliveryNoteNumber;
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

    public DeliveryNoteStatus getStatus() {
        return status;
    }

    public void setStatus(final DeliveryNoteStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
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

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public DeliveryNoteItems getDeliveryNoteItems() {
        return deliveryNoteItems;
    }

    public void setDeliveryNoteItems(final DeliveryNoteItems deliveryNoteItems) {
        this.deliveryNoteItems = deliveryNoteItems;
    }

    public void addDeliveryNoteItem(final DeliveryNoteItem deliveryNoteItem) {
        if (deliveryNoteItems == null) {
            deliveryNoteItems = new DeliveryNoteItems();
        }

        deliveryNoteItems.getDeliveryNoteItems().add(deliveryNoteItem);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }

    public String getCustomerportalUrl() {
        return customerportalUrl;
    }

    public void setCustomerportalUrl(final String customerportalUrl) {
        this.customerportalUrl = customerportalUrl;
    }
}
