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
package net.siegmar.billomat4j.sdk.domain.settings;

import java.math.BigDecimal;
import java.util.Currency;

import net.siegmar.billomat4j.sdk.json.Views;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

@JsonRootName("settings")
public class Settings {

    private String invoiceIntro;
    private String invoiceNote;
    private String offerIntro;
    private String offerNote;
    private String confirmationIntro;
    private String confirmationNote;
    private String deliveryNoteIntro;
    private String deliveryNoteNote;
    private String creditNoteIntro;
    private String creditNoteNote;
    private String invoiceEmailSubject;
    private String invoiceEmailBody;
    private String offerEmailSubject;
    private String offerEmailBody;
    private String confirmationEmailSubject;
    private String confirmationEmailBody;
    private String deliveryNoteEmailSubject;
    private String deliveryNoteEmailBody;
    private String creditNoteEmailSubject;
    private String creditNoteEmailBody;
    private String reminderEmailSubject;
    private String reminderEmailBody;
    private String articleNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String articleNumberNext;

    private String clientNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String clientNumberNext;

    private String invoiceNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String invoiceNumberNext;

    private String offerNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String offerNumberNext;

    private String confirmationNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String confirmationNumberNext;

    private String deliveryNoteNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String deliveryNoteNumberNext;

    private String creditNoteNumberPre;

    @JsonView(Views.NonSerialize.class)
    private String creditNoteNumberNext;

    private Integer offerNumberLength;
    private Integer invoiceNumberLength;
    private Integer confirmationNumberLength;
    private Integer deliveryNoteNumberLength;
    private Integer creditNoteNumberLength;
    private NumberRangeMode numberRangeMode;
    private Integer articleNumberLength;
    private Integer clientNumberLength;
    private Currency currencyCode;
    private BigDecimal discountRate;
    private Integer discountDays;
    private Integer dueDays;
    private Integer offerValidityDays;
    private BccAddresses bccAddresses;
    private String bgcolor;
    private String color1;
    private String color2;
    private String color3;
    private String defaultEmailSender;
    private Boolean printVersion;

    public String getInvoiceIntro() {
        return invoiceIntro;
    }

    public void setInvoiceIntro(final String invoiceIntro) {
        this.invoiceIntro = invoiceIntro;
    }

    public String getInvoiceNote() {
        return invoiceNote;
    }

    public void setInvoiceNote(final String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }

    public String getOfferIntro() {
        return offerIntro;
    }

    public void setOfferIntro(final String offerIntro) {
        this.offerIntro = offerIntro;
    }

    public String getOfferNote() {
        return offerNote;
    }

    public void setOfferNote(final String offerNote) {
        this.offerNote = offerNote;
    }

    public String getConfirmationIntro() {
        return confirmationIntro;
    }

    public void setConfirmationIntro(final String confirmationIntro) {
        this.confirmationIntro = confirmationIntro;
    }

    public String getConfirmationNote() {
        return confirmationNote;
    }

    public void setConfirmationNote(final String confirmationNote) {
        this.confirmationNote = confirmationNote;
    }

    public String getDeliveryNoteIntro() {
        return deliveryNoteIntro;
    }

    public void setDeliveryNoteIntro(final String deliveryNoteIntro) {
        this.deliveryNoteIntro = deliveryNoteIntro;
    }

    public String getDeliveryNoteNote() {
        return deliveryNoteNote;
    }

    public void setDeliveryNoteNote(final String deliveryNoteNote) {
        this.deliveryNoteNote = deliveryNoteNote;
    }

    public String getCreditNoteIntro() {
        return creditNoteIntro;
    }

    public void setCreditNoteIntro(final String creditNoteIntro) {
        this.creditNoteIntro = creditNoteIntro;
    }

    public String getCreditNoteNote() {
        return creditNoteNote;
    }

    public void setCreditNoteNote(final String creditNoteNote) {
        this.creditNoteNote = creditNoteNote;
    }

    public String getInvoiceEmailSubject() {
        return invoiceEmailSubject;
    }

    public void setInvoiceEmailSubject(final String invoiceEmailSubject) {
        this.invoiceEmailSubject = invoiceEmailSubject;
    }

    public String getInvoiceEmailBody() {
        return invoiceEmailBody;
    }

    public void setInvoiceEmailBody(final String invoiceEmailBody) {
        this.invoiceEmailBody = invoiceEmailBody;
    }

    public String getOfferEmailSubject() {
        return offerEmailSubject;
    }

    public void setOfferEmailSubject(final String offerEmailSubject) {
        this.offerEmailSubject = offerEmailSubject;
    }

    public String getOfferEmailBody() {
        return offerEmailBody;
    }

    public void setOfferEmailBody(final String offerEmailBody) {
        this.offerEmailBody = offerEmailBody;
    }

    public String getConfirmationEmailSubject() {
        return confirmationEmailSubject;
    }

    public void setConfirmationEmailSubject(final String confirmationEmailSubject) {
        this.confirmationEmailSubject = confirmationEmailSubject;
    }

    public String getConfirmationEmailBody() {
        return confirmationEmailBody;
    }

    public void setConfirmationEmailBody(final String confirmationEmailBody) {
        this.confirmationEmailBody = confirmationEmailBody;
    }

    public String getDeliveryNoteEmailSubject() {
        return deliveryNoteEmailSubject;
    }

    public void setDeliveryNoteEmailSubject(final String deliveryNoteEmailSubject) {
        this.deliveryNoteEmailSubject = deliveryNoteEmailSubject;
    }

    public String getDeliveryNoteEmailBody() {
        return deliveryNoteEmailBody;
    }

    public void setDeliveryNoteEmailBody(final String deliveryNoteEmailBody) {
        this.deliveryNoteEmailBody = deliveryNoteEmailBody;
    }

    public String getCreditNoteEmailSubject() {
        return creditNoteEmailSubject;
    }

    public void setCreditNoteEmailSubject(final String creditNoteEmailSubject) {
        this.creditNoteEmailSubject = creditNoteEmailSubject;
    }

    public String getCreditNoteEmailBody() {
        return creditNoteEmailBody;
    }

    public void setCreditNoteEmailBody(final String creditNoteEmailBody) {
        this.creditNoteEmailBody = creditNoteEmailBody;
    }

    public String getReminderEmailSubject() {
        return reminderEmailSubject;
    }

    public void setReminderEmailSubject(final String reminderEmailSubject) {
        this.reminderEmailSubject = reminderEmailSubject;
    }

    public String getReminderEmailBody() {
        return reminderEmailBody;
    }

    public void setReminderEmailBody(final String reminderEmailBody) {
        this.reminderEmailBody = reminderEmailBody;
    }

    public String getArticleNumberPre() {
        return articleNumberPre;
    }

    public void setArticleNumberPre(final String articleNumberPre) {
        this.articleNumberPre = articleNumberPre;
    }

    public String getArticleNumberNext() {
        return articleNumberNext;
    }

    public String getClientNumberPre() {
        return clientNumberPre;
    }

    public void setClientNumberPre(final String clientNumberPre) {
        this.clientNumberPre = clientNumberPre;
    }

    public String getClientNumberNext() {
        return clientNumberNext;
    }

    public String getInvoiceNumberPre() {
        return invoiceNumberPre;
    }

    public void setInvoiceNumberPre(final String invoiceNumberPre) {
        this.invoiceNumberPre = invoiceNumberPre;
    }

    public String getInvoiceNumberNext() {
        return invoiceNumberNext;
    }

    public String getOfferNumberPre() {
        return offerNumberPre;
    }

    public void setOfferNumberPre(final String offerNumberPre) {
        this.offerNumberPre = offerNumberPre;
    }

    public String getOfferNumberNext() {
        return offerNumberNext;
    }

    public String getConfirmationNumberPre() {
        return confirmationNumberPre;
    }

    public void setConfirmationNumberPre(final String confirmationNumberPre) {
        this.confirmationNumberPre = confirmationNumberPre;
    }

    public String getConfirmationNumberNext() {
        return confirmationNumberNext;
    }

    public String getDeliveryNoteNumberPre() {
        return deliveryNoteNumberPre;
    }

    public void setDeliveryNoteNumberPre(final String deliveryNoteNumberPre) {
        this.deliveryNoteNumberPre = deliveryNoteNumberPre;
    }

    public String getDeliveryNoteNumberNext() {
        return deliveryNoteNumberNext;
    }

    public String getCreditNoteNumberPre() {
        return creditNoteNumberPre;
    }

    public void setCreditNoteNumberPre(final String creditNoteNumberPre) {
        this.creditNoteNumberPre = creditNoteNumberPre;
    }

    public String getCreditNoteNumberNext() {
        return creditNoteNumberNext;
    }

    public Integer getOfferNumberLength() {
        return offerNumberLength;
    }

    public void setOfferNumberLength(final Integer offerNumberLength) {
        this.offerNumberLength = offerNumberLength;
    }

    public Integer getInvoiceNumberLength() {
        return invoiceNumberLength;
    }

    public void setInvoiceNumberLength(final Integer invoiceNumberLength) {
        this.invoiceNumberLength = invoiceNumberLength;
    }

    public Integer getConfirmationNumberLength() {
        return confirmationNumberLength;
    }

    public void setConfirmationNumberLength(final Integer confirmationNumberLength) {
        this.confirmationNumberLength = confirmationNumberLength;
    }

    public Integer getDeliveryNoteNumberLength() {
        return deliveryNoteNumberLength;
    }

    public void setDeliveryNoteNumberLength(final Integer deliveryNoteNumberLength) {
        this.deliveryNoteNumberLength = deliveryNoteNumberLength;
    }

    public Integer getCreditNoteNumberLength() {
        return creditNoteNumberLength;
    }

    public void setCreditNoteNumberLength(final Integer creditNoteNumberLength) {
        this.creditNoteNumberLength = creditNoteNumberLength;
    }

    public NumberRangeMode getNumberRangeMode() {
        return numberRangeMode;
    }

    public void setNumberRangeMode(final NumberRangeMode numberRangeMode) {
        this.numberRangeMode = numberRangeMode;
    }

    public Integer getArticleNumberLength() {
        return articleNumberLength;
    }

    public void setArticleNumberLength(final Integer articleNumberLength) {
        this.articleNumberLength = articleNumberLength;
    }

    public Integer getClientNumberLength() {
        return clientNumberLength;
    }

    public void setClientNumberLength(final Integer clientNumberLength) {
        this.clientNumberLength = clientNumberLength;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(final BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(final Integer discountDays) {
        this.discountDays = discountDays;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(final Integer dueDays) {
        this.dueDays = dueDays;
    }

    public Integer getOfferValidityDays() {
        return offerValidityDays;
    }

    public void setOfferValidityDays(final Integer offerValidityDays) {
        this.offerValidityDays = offerValidityDays;
    }

    public BccAddresses getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(final BccAddresses bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(final String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(final String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(final String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(final String color3) {
        this.color3 = color3;
    }

    public String getDefaultEmailSender() {
        return defaultEmailSender;
    }

    public void setDefaultEmailSender(final String defaultEmailSender) {
        this.defaultEmailSender = defaultEmailSender;
    }

    public Boolean getPrintVersion() {
        return printVersion;
    }

    public void setPrintVersion(final Boolean printVersion) {
        this.printVersion = printVersion;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
