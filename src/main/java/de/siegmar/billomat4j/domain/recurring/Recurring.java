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
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.domain.types.SupplyDateType;
import de.siegmar.billomat4j.json.PaymentTypesDeserializer;
import de.siegmar.billomat4j.json.PaymentTypesSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
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

    @JsonSerialize(using = PaymentTypesSerializer.class)
    @JsonDeserialize(using = PaymentTypesDeserializer.class)
    private Set<PaymentType> paymentTypes;

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

    private String title;
    private BigDecimal totalReduction; // TODO undocumented feature - clarify with support
    private String netGross;
    private BigDecimal discountAmount; // TODO undocumented feature - clarify with support
    private String status; // TODO undocumented feature - clarify with support
    private Boolean inProgress; // TODO undocumented feature - clarify with support
    private Boolean emailBcc;
    private Boolean letterColor;
    private Boolean letterDuplex;
    private Integer letterPaperWeight;
    private Integer emailTemplateId;

    public void addRecurringItem(final RecurringItem recurringItem) {
        if (recurringItems == null) {
            recurringItems = new RecurringItems();
        }
        recurringItems.getRecurringItems().add(recurringItem);
    }

}
