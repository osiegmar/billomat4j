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

package de.siegmar.billomat4j.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.reminder.Reminder;
import de.siegmar.billomat4j.domain.reminder.ReminderFilter;
import de.siegmar.billomat4j.domain.reminder.ReminderItem;
import de.siegmar.billomat4j.domain.reminder.ReminderItems;
import de.siegmar.billomat4j.domain.reminder.ReminderPdf;
import de.siegmar.billomat4j.domain.reminder.ReminderTag;
import de.siegmar.billomat4j.domain.reminder.ReminderTags;
import de.siegmar.billomat4j.domain.reminder.Reminders;
import de.siegmar.billomat4j.service.GenericCustomFieldService;
import de.siegmar.billomat4j.service.GenericItemService;
import de.siegmar.billomat4j.service.GenericTagService;

public class ReminderService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<ReminderTag>,
    GenericItemService<ReminderItem> {

    private static final String RESOURCE = "reminders";
    private static final String RESOURCE_ITEMS = "reminder-items";
    private static final String RESOURCE_TAGS = "reminder-tags";

    public ReminderService(final BillomatConfiguration billomatConfiguration) {
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

    /**
     * @param reminderFilter reminder filter, may be {@code null} to find unfiltered
     * @return reminders found by filter criteria or an empty list if no reminders were found - never
     * {@code null}
     * @throws ServiceException if an error occured while accessing the web service
     */
    public List<Reminder> findReminders(final ReminderFilter reminderFilter) {
        return getAllPagesFromResource(RESOURCE, Reminders.class, reminderFilter);
    }

    /**
     * Gets a reminder by its id.
     *
     * @param reminderId the reminder's id
     * @return the reminder or {@code null} if not found
     * @throws ServiceException if an error occured while accessing the web service
     */
    public Reminder getReminderById(final int reminderId) {
        return getById(RESOURCE, Reminder.class, reminderId);
    }

    /**
     * @param reminder the reminder to create, must not be {@code null}
     * @throws NullPointerException if reminder is null
     * @throws ServiceException     if an error occured while accessing the web service
     */
    public void createReminder(final Reminder reminder) {
        create(RESOURCE, Validate.notNull(reminder));
    }

    /**
     * @param reminder the reminder to update, must not be {@code null}
     * @throws NullPointerException if reminder is null
     * @throws ServiceException     if an error occured while accessing the web service
     */
    public void updateReminder(final Reminder reminder) {
        update(RESOURCE, Validate.notNull(reminder));
    }

    /**
     * @param reminderId the id of the reminder to be deleted
     * @throws ServiceException if an error occured while accessing the web service
     */
    public void deleteReminder(final int reminderId) {
        delete(RESOURCE, reminderId);
    }

    /**
     * @param reminderId the id of the reminder to get the PDF for
     * @return the reminder PDF or {@code null} if not found
     * @throws ServiceException if an error occured while accessing the web service
     */
    public ReminderPdf getReminderPdf(final int reminderId) {
        return getReminderPdf(reminderId, null);
    }

    private ReminderPdf getReminderPdf(final int reminderId, final Map<String, String> filter) {
        return getPdf(RESOURCE, ReminderPdf.class, reminderId, filter);
    }

    /**
     * @param reminderId the id of the reminder to get the signed PDF for
     * @return the signed reminder PDF or {@code null} if not found
     * @throws ServiceException if an error occured while accessing the web service
     * @see #uploadReminderSignedPdf(int, byte[])
     * @see #getReminderPdf(int)
     */
    public ReminderPdf getReminderSignedPdf(final int reminderId) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getReminderPdf(reminderId, filter);
    }

    /**
     * Sets the reminder status to {@link de.siegmar.billomat4j.domain.reminder.ReminderStatus#OPEN}
     * or {@link de.siegmar.billomat4j.domain.reminder.ReminderStatus#OVERDUE}.
     *
     * @param reminderId the id of the reminder to update
     * @param templateId the id of the template to use for the resulting document or {@code null}
     *                   if no template should be used
     * @throws ServiceException if an error occured while accessing the web service
     */
    public void completeReminder(final int reminderId, final Integer templateId) {
        completeDocument(RESOURCE, reminderId, templateId);
    }

    /**
     * @param reminderId the id of the reminder to upload the signed PDF for
     * @param pdf        the signed PDF as binary data (must not be {@code null})
     * @throws ServiceException     if an error occured while accessing the web service
     * @throws NullPointerException if pdf is null
     * @see #getReminderSignedPdf(int)
     */
    public void uploadReminderSignedPdf(final int reminderId, final byte[] pdf) {
        uploadSignedPdf(RESOURCE, reminderId, Validate.notNull(pdf));
    }

    /**
     * @param reminderId    the id of the reminder to send an email for
     * @param reminderEmail the email configuration
     * @throws NullPointerException if reminderEmail is null
     * @throws ServiceException     if an error occured while accessing the web service
     */
    public void sendReminderViaEmail(final int reminderId, final Email reminderEmail) {
        sendEmail(RESOURCE, reminderId, reminderEmail);
    }

    /**
     * Sets the reminder status to {@link de.siegmar.billomat4j.domain.reminder.ReminderStatus#CANCELED}.
     *
     * @param reminderId the id of the reminder to update
     * @throws ServiceException if an error occured while accessing the web service
     */
    public void cancelReminder(final int reminderId) {
        transit(RESOURCE, "cancel", reminderId);
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
