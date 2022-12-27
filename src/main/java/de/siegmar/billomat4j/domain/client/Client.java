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

package de.siegmar.billomat4j.domain.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.json.PaymentTypesDeserializer;
import de.siegmar.billomat4j.json.PaymentTypesSerializer;
import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("client")
public class Client extends AbstractMeta {

    @Setter(AccessLevel.NONE)
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
    private String sepaMandate;
    private LocalDate sepaMandateDate;
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

    @JsonProperty("customerportal_url")
    private String customerPortalUrl;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private BigDecimal revenueGross;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private BigDecimal revenueNet;

    private Boolean digExclude; // TODO undocumented feature - clarify with support
    private String facturxIdentifier; // TODO undocumented feature - clarify with support

    @Setter(AccessLevel.NONE)
    private String address;

    private Currency currencyCode;

    @JsonProperty("enable_customerportal")
    private Boolean enableCustomerPortal;

    private String sellerIdentifier; // TODO undocumented feature - clarify with support

    @JsonSerialize(using = PaymentTypesSerializer.class)
    @JsonDeserialize(using = PaymentTypesDeserializer.class)
    private Set<PaymentType> defaultPaymentTypes;

    private String locale;
    private String netGross;
    private Integer debitorAccountNumber;
    private BigDecimal reduction;
    private Boolean dunningRun;

    @JsonProperty("client-property-values")
    private String clientPropertyValues; // TODO undocumented feature - clarify with support

    private String plan; // TODO undocumented feature - clarify with support
    private ObjectNode quotas; // TODO undocumented feature - clarify with support
    private String taxAuthorityNumber; // TODO undocumented feature - clarify with support
    private Boolean facturxFieldsEnabled; // TODO undocumented feature - clarify with support

}
