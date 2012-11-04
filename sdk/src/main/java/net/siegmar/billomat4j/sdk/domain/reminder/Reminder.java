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
package net.siegmar.billomat4j.sdk.domain.reminder;

import java.math.BigDecimal;
import java.util.Date;

import net.siegmar.billomat4j.sdk.domain.AbstractMeta;
import net.siegmar.billomat4j.sdk.json.MyDateSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName("reminder")
public class Reminder extends AbstractMeta {

    private Integer invoiceId;
    private ReminderStatus status;
    private Integer reminderTextId;
    private Integer reminderLevel;
    private String reminderLevelName;

    @JsonSerialize(using = MyDateSerializer.class)
    @JsonInclude(Include.NON_NULL)
    private Date date;

    private String label;
    private String subject;
    private String intro;
    private String note;
    private Date dueDate;
    private BigDecimal totalGross;
    private ReminderItems reminderItems;

    @JsonProperty("is_old")
    private Boolean old;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ReminderStatus getStatus() {
        return status;
    }

    public void setStatus(final ReminderStatus status) {
        this.status = status;
    }

    public Integer getReminderTextId() {
        return reminderTextId;
    }

    public void setReminderTextId(final Integer reminderTextId) {
        this.reminderTextId = reminderTextId;
    }

    public Integer getReminderLevel() {
        return reminderLevel;
    }

    public void setReminderLevel(final Integer reminderLevel) {
        this.reminderLevel = reminderLevel;
    }

    public String getReminderLevelName() {
        return reminderLevelName;
    }

    public void setReminderLevelName(final String reminderLevelName) {
        this.reminderLevelName = reminderLevelName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(final Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalGross() {
        return totalGross;
    }

    public void setTotalGross(final BigDecimal totalGross) {
        this.totalGross = totalGross;
    }

    public Boolean getOld() {
        return old;
    }

    public void setOld(final Boolean old) {
        this.old = old;
    }

    public ReminderItems getReminderItems() {
        return reminderItems;
    }

    public void setReminderItems(final ReminderItems reminderItems) {
        this.reminderItems = reminderItems;
    }

    public void addReminderItem(final ReminderItem reminderItem) {
        if (reminderItems == null) {
            reminderItems = new ReminderItems();
        }

        reminderItems.getReminderItems().add(reminderItem);
    }

}
