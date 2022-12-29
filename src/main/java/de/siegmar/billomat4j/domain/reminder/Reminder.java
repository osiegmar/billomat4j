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

package de.siegmar.billomat4j.domain.reminder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import de.siegmar.billomat4j.domain.AbstractMeta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonRootName("reminder")
public class Reminder extends AbstractMeta {

    private Integer invoiceId;
    private ReminderStatus status;
    private Integer reminderTextId;
    private Integer reminderLevel;
    private String reminderLevelName;

    @JsonInclude(Include.NON_NULL)
    private LocalDate date;

    private String label;
    private String subject;
    private String intro;
    private String note;
    private LocalDate dueDate;
    private BigDecimal totalGross;

    @JsonProperty("reminder-items")
    private ReminderItems reminderItems;

    @JsonProperty("customerportal_url")
    private String customerPortalUrl;

    private Integer templateId;

    @JsonProperty("is_old")
    private Boolean old;

    @Setter(AccessLevel.NONE)
    private String clientId;
    private String contactId;
    private Integer dueDays;
    private String address;
    private Currency currencyCode;

    @Setter(AccessLevel.NONE)
    private BigDecimal paidAmount;

    @Setter(AccessLevel.NONE)
    private BigDecimal openAmount;

    public void addReminderItem(final ReminderItem reminderItem) {
        if (reminderItems == null) {
            reminderItems = new ReminderItems();
        }

        reminderItems.getReminderItems().add(reminderItem);
    }

}
