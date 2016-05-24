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
package de.siegmar.billomat4j.sdk.domain.confirmation;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import de.siegmar.billomat4j.sdk.domain.Taxes;
import de.siegmar.billomat4j.sdk.json.MyDateSerializer;
import de.siegmar.billomat4j.sdk.domain.AbstractMeta;
import de.siegmar.billomat4j.sdk.json.Views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName("confirmation")
public class Confirmation extends AbstractMeta {

    private Integer clientId;

    @JsonView(Views.NonSerialize.class)
    private String confirmationNumber;

    private Integer number;
    private String numberPre;
    private ConfirmationStatus status;

    @JsonSerialize(using = MyDateSerializer.class)
    @JsonInclude(Include.NON_NULL)
    private Date date;

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

    @JsonSerialize(using = MyDateSerializer.class)
    @JsonInclude(Include.NON_NULL)
    private Date validityDate;

    private Integer offerId;
    private ConfirmationItems confirmationItems;
    private Integer contactId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(final Integer clientId) {
        this.clientId = clientId;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
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

    public ConfirmationStatus getStatus() {
        return status;
    }

    public void setStatus(final ConfirmationStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
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

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(final Date validityDate) {
        this.validityDate = validityDate;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(final Integer offerId) {
        this.offerId = offerId;
    }

    public ConfirmationItems getConfirmationItems() {
        return confirmationItems;
    }

    public void setConfirmationItems(final ConfirmationItems confirmationItems) {
        this.confirmationItems = confirmationItems;
    }

    public void addConfirmationItem(final ConfirmationItem confirmationItem) {
        if (confirmationItems == null) {
            confirmationItems = new ConfirmationItems();
        }

        confirmationItems.getConfirmationItems().add(confirmationItem);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }

}
