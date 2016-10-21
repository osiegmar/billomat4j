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
package de.siegmar.billomat4j.domain.client;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.json.Views;

@JsonRootName("client")
public class Client extends AbstractMeta {

    @JsonView(Views.NonSerialize.class)
    private String clientNumber;

    private Integer number;
    private String numberPre;
    private Integer numberLength;
    private String name;
    private String salutation;
    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String state;
    private String countryCode;
    private String phone;
    private String fax;
    private String mobile;
    private String email;
    private String www;
    private String taxNumber;
    private String vatNumber;
    private String bankAccountOwner;
    private String bankNumber;
    private String bankName;
    private String bankAccountNumber;
    private String bankSwift;
    private String bankIban;
    private TaxRule taxRule;
    private SettingsType discountRateType;
    private BigDecimal discountRate;
    private SettingsType discountDaysType;
    private Integer discountDays;
    private SettingsType dueDaysType;
    private Integer dueDays;
    private SettingsType reminderDueDaysType;
    private Integer reminderDueDays;
    private SettingsType offerValidityDaysType;
    private Integer offerValidityDays;
    private Integer priceGroup;
    private String note;
    private Boolean archived;
    private String customerportalUrl;

    @JsonView(Views.NonSerialize.class)
    private BigDecimal revenueGross;

    @JsonView(Views.NonSerialize.class)
    private BigDecimal revenueNet;

    public String getClientNumber() {
        return clientNumber;
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

    public Integer getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(final Integer numberLength) {
        this.numberLength = numberLength;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(final String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getWww() {
        return www;
    }

    public void setWww(final String www) {
        this.www = www;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(final String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(final String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getBankAccountOwner() {
        return bankAccountOwner;
    }

    public void setBankAccountOwner(final String bankAccountOwner) {
        this.bankAccountOwner = bankAccountOwner;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(final String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(final String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankSwift() {
        return bankSwift;
    }

    public void setBankSwift(final String bankSwift) {
        this.bankSwift = bankSwift;
    }

    public String getBankIban() {
        return bankIban;
    }

    public void setBankIban(final String bankIban) {
        this.bankIban = bankIban;
    }

    public TaxRule getTaxRule() {
        return taxRule;
    }

    public void setTaxRule(final TaxRule taxRule) {
        this.taxRule = taxRule;
    }

    public SettingsType getDiscountRateType() {
        return discountRateType;
    }

    public void setDiscountRateType(final SettingsType discountRateType) {
        this.discountRateType = discountRateType;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(final BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public SettingsType getDiscountDaysType() {
        return discountDaysType;
    }

    public void setDiscountDaysType(final SettingsType discountDaysType) {
        this.discountDaysType = discountDaysType;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(final Integer discountDays) {
        this.discountDays = discountDays;
    }

    public SettingsType getDueDaysType() {
        return dueDaysType;
    }

    public void setDueDaysType(final SettingsType dueDaysType) {
        this.dueDaysType = dueDaysType;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(final Integer dueDays) {
        this.dueDays = dueDays;
    }

    public SettingsType getReminderDueDaysType() {
        return reminderDueDaysType;
    }

    public void setReminderDueDaysType(final SettingsType reminderDueDaysType) {
        this.reminderDueDaysType = reminderDueDaysType;
    }

    public Integer getReminderDueDays() {
        return reminderDueDays;
    }

    public void setReminderDueDays(final Integer reminderDueDays) {
        this.reminderDueDays = reminderDueDays;
    }

    public SettingsType getOfferValidityDaysType() {
        return offerValidityDaysType;
    }

    public void setOfferValidityDaysType(final SettingsType offerValidityDaysType) {
        this.offerValidityDaysType = offerValidityDaysType;
    }

    public Integer getOfferValidityDays() {
        return offerValidityDays;
    }

    public void setOfferValidityDays(final Integer offerValidityDays) {
        this.offerValidityDays = offerValidityDays;
    }

    public Integer getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(final Integer priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public BigDecimal getRevenueGross() {
        return revenueGross;
    }

    public BigDecimal getRevenueNet() {
        return revenueNet;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(final Boolean archived) {
        this.archived = archived;
    }

    public String getCustomerportalUrl() {
        return customerportalUrl;
    }

    public void setCustomerportalUrl(final String customerportalUrl) {
        this.customerportalUrl = customerportalUrl;
    }
}
