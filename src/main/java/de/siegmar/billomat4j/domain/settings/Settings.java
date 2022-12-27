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

package de.siegmar.billomat4j.domain.settings;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String articleNumberNext;

    private String clientNumberPre;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String clientNumberNext;

    private String invoiceNumberPre;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String invoiceNumberNext;

    private String offerNumberPre;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String offerNumberNext;

    private String confirmationNumberPre;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String confirmationNumberNext;

    private String deliveryNoteNumberPre;

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private String deliveryNoteNumberNext;

    private String creditNoteNumberPre;

    @Setter(AccessLevel.NONE)
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

    @JsonProperty("bgcolor")
    private String bgColor;

    private String color1;
    private String color2;
    private String color3;
    private String defaultEmailSender;
    private Boolean printVersion;

    @Getter
    @JsonView(Views.NonSerialize.class)
    private ZonedDateTime created;

    @Getter
    @JsonView(Views.NonSerialize.class)
    private ZonedDateTime updated;

    private Locale locale;
    private String netGross;
    private String sepaCreditorId;
    private String priceGroup2;
    private String priceGroup3;
    private String priceGroup4;
    private String priceGroup5;
    private String invoiceFilename;
    private String invoiceLabel;
    private String offerFilename;
    private String offerLabel;
    private String confirmationFilename;
    private String confirmationLabel;
    private String creditNoteFilename;
    private String creditNoteLabel;
    private String deliveryNoteFilename;
    private String deliveryNoteLabel;
    private String reminderFilename;
    private Integer reminderDueDays;
    private String letterLabel;
    private String letterFilename;
    private String letterIntro;
    private String templateEngine;
    private String taxation;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
