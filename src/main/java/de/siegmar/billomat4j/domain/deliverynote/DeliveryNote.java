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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.Taxes;
import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonRootName("delivery-note")
public class DeliveryNote extends AbstractMeta {

    private Integer clientId;

    @Setter(AccessLevel.NONE)
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

    @JsonProperty("delivery-note-items")
    private DeliveryNoteItems deliveryNoteItems;

    private Integer contactId;

    @JsonProperty("customerportal_url")
    private String customerPortalUrl;

    private Integer freeTextId;
    private Integer templateId;
    private Integer numberLength;
    private String title;

    public void addDeliveryNoteItem(final DeliveryNoteItem deliveryNoteItem) {
        if (deliveryNoteItems == null) {
            deliveryNoteItems = new DeliveryNoteItems();
        }

        deliveryNoteItems.getDeliveryNoteItems().add(deliveryNoteItem);
    }

}
