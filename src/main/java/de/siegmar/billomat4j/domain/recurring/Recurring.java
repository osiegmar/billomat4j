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

package de.siegmar.billomat4j.domain.recurring;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.domain.types.SupplyDateType;

@JsonRootName("recurring")
public class Recurring extends AbstractMeta {

    private Integer clientId;
    private Integer templateId;
    private String address;
    private String supplyDate;
    private SupplyDateType supplyDateType;
    private Integer dueDays;
    private Integer discountRate;
    private Integer discountDays;
    private String name;
    private String label;
    private String intro;
    private String note;
    private Currency currencyCode;
    private String reduction;
    private BigDecimal quote;
    private Integer ultimo;
    private PaymentType[] paymentTypes;
    private RecurringAction action;
    private RecurringCycle cycle;
    private Integer hour;
    private Integer cycleNumber;
    private Integer contactId;

    @JsonInclude(Include.NON_NULL)
    private LocalDate startDate;

    @JsonInclude(Include.NON_NULL)
    private LocalDate endDate;

    @JsonInclude(Include.NON_NULL)
    private LocalDate lastCreationDate;

    private LocalDate nextCreationDate;

    private Integer iterations;
    private Integer counter;

    private BigDecimal totalGross;
    private BigDecimal totalNet;
    private BigDecimal totalGrossUnreduced;
    private BigDecimal totalNetUnreduced;

    private String emailSender;
    private String emailSubject;
    private String emailMessage;
    private String emailFilename;

    private Integer offerId;
    private Integer confirmationId;

    @JsonProperty("recurring-items")
    private RecurringItems recurringItems;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(final Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final Integer templateId) {
        this.templateId = templateId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
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

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(final Integer dueDays) {
        this.dueDays = dueDays;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(final Integer discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(final Integer discountDays) {
        this.discountDays = discountDays;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(final String reduction) {
        this.reduction = reduction;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(final BigDecimal quote) {
        this.quote = quote;
    }

    public Integer getUltimo() {
        return ultimo;
    }

    public void setUltimo(final Integer ultimo) {
        this.ultimo = ultimo;
    }

    public PaymentType[] getPaymentTypes() {
        return paymentTypes != null ? paymentTypes.clone() : null;
    }

    public void setPaymentTypes(final PaymentType... paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public RecurringAction getAction() {
        return action;
    }

    public void setAction(final RecurringAction action) {
        this.action = action;
    }

    public RecurringCycle getCycle() {
        return cycle;
    }

    public void setCycle(final RecurringCycle cycle) {
        this.cycle = cycle;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(final Integer hour) {
        this.hour = hour;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getLastCreationDate() {
        return lastCreationDate;
    }

    public void setLastCreationDate(final LocalDate lastCreationDate) {
        this.lastCreationDate = lastCreationDate;
    }

    public LocalDate getNextCreationDate() {
        return nextCreationDate;
    }

    public void setNextCreationDate(final LocalDate nextCreationDate) {
        this.nextCreationDate = nextCreationDate;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(final Integer iterations) {
        this.iterations = iterations;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(final Integer counter) {
        this.counter = counter;
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

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(final String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(final String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(final String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getEmailFilename() {
        return emailFilename;
    }

    public void setEmailFilename(final String emailFilename) {
        this.emailFilename = emailFilename;
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

    public RecurringItems getRecurringItems() {
        return recurringItems;
    }

    public void setRecurringItems(final RecurringItems recurringItems) {
        this.recurringItems = recurringItems;
    }

    public void addRecurringItem(final RecurringItem recurringItem) {
        if (recurringItems == null) {
            recurringItems = new RecurringItems();
        }
        recurringItems.getRecurringItems().add(recurringItem);
    }

    public Integer getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(final Integer cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }

}
