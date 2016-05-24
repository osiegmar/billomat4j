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
package de.siegmar.billomat4j.sdk.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.siegmar.billomat4j.sdk.domain.reminder.ReminderFilter;
import de.siegmar.billomat4j.sdk.domain.reminder.ReminderTags;
import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.reminder.Reminder;
import de.siegmar.billomat4j.sdk.domain.reminder.ReminderItem;
import de.siegmar.billomat4j.sdk.domain.reminder.ReminderItems;
import de.siegmar.billomat4j.sdk.domain.reminder.ReminderPdf;
import de.siegmar.billomat4j.sdk.domain.reminder.ReminderTag;
import de.siegmar.billomat4j.sdk.domain.reminder.Reminders;
import de.siegmar.billomat4j.sdk.service.ReminderService;

import org.apache.commons.lang3.Validate;

public class ReminderServiceImpl extends AbstractService implements ReminderService {

    private static final String RESOURCE = "reminders";
    private static final String RESOURCE_ITEMS = "reminder-items";
    private static final String RESOURCE_TAGS = "reminder-tags";

    public ReminderServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Reminder

    @Override
    public String getCustomFieldValue(final int reminderId) {
        return getCustomField(RESOURCE, reminderId);
    }

    @Override
    public void setCustomFieldValue(final int reminderId, final String value) {
        updateCustomField(RESOURCE, reminderId, "reminder", value);
    }

    @Override
    public List<Reminder> findReminders(final ReminderFilter reminderFilter) {
        return getAllPagesFromResource(RESOURCE, Reminders.class, reminderFilter);
    }

    @Override
    public Reminder getReminderById(final int id) {
        return getById(RESOURCE, Reminder.class, id);
    }

    @Override
    public void createReminder(final Reminder reminder) {
        create(RESOURCE, Validate.notNull(reminder));
    }

    @Override
    public void updateReminder(final Reminder reminder) {
        update(RESOURCE, Validate.notNull(reminder));
    }

    @Override
    public void deleteReminder(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public ReminderPdf getReminderPdf(final int id) {
        return getReminderPdf(id, null);
    }

    @Override
    public ReminderPdf getReminderSignedPdf(final int id) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getReminderPdf(id, filter);
    }

    private ReminderPdf getReminderPdf(final int id, final Map<String, String> filter) {
        return getPdf(RESOURCE, ReminderPdf.class, id, filter);
    }

    @Override
    public void completeReminder(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void uploadReminderSignedPdf(final int reminderId, final byte[] pdf) {
        uploadSignedPdf(RESOURCE, reminderId, Validate.notNull(pdf));
    }

    @Override
    public void sendReminderViaEmail(final int reminderId, final Email reminderEmail) {
        sendEmail(RESOURCE, reminderId, reminderEmail);
    }

    @Override
    public void cancelReminder(final int id) {
        transit(RESOURCE, "cancel", id);
    }

    // ReminderItem

    @Override
    public List<ReminderItem> getItems(final int reminderId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, ReminderItems.class, reminderIdFilter(reminderId));
    }

    private GenericFilter reminderIdFilter(final Integer reminderId) {
        return reminderId == null ? null : new GenericFilter("reminder_id", reminderId);
    }

    @Override
    public ReminderItem getItemById(final int reminderItemId) {
        return getById(RESOURCE_ITEMS, ReminderItem.class, reminderItemId);
    }

    @Override
    public void createItem(final ReminderItem reminderItem) {
        create(RESOURCE_ITEMS, Validate.notNull(reminderItem));
    }

    @Override
    public void updateItem(final ReminderItem reminderItem) {
        update(RESOURCE_ITEMS, Validate.notNull(reminderItem));
    }

    @Override
    public void deleteItem(final int reminderItemId) {
        delete(RESOURCE_ITEMS, reminderItemId);
    }

    // ReminderTag

    @Override
    public List<ReminderTag> getTags(final Integer reminderId) {
        return getAllPagesFromResource(RESOURCE_TAGS, ReminderTags.class, reminderIdFilter(reminderId));
    }

    @Override
    public ReminderTag getTagById(final int reminderTagId) {
        return getById(RESOURCE_TAGS, ReminderTag.class, reminderTagId);
    }

    @Override
    public void createTag(final ReminderTag reminderTag) {
        create(RESOURCE_TAGS, Validate.notNull(reminderTag));
    }

    @Override
    public void deleteTag(final int reminderTagId) {
        delete(RESOURCE_TAGS, reminderTagId);
    }

}
