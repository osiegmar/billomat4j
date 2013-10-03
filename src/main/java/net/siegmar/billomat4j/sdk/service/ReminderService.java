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
package net.siegmar.billomat4j.sdk.service;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.reminder.Reminder;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderFilter;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderItem;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderPdf;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderTag;

/**
 * @see http://www.billomat.com/api/mahnungen/
 * @see http://www.billomat.com/api/mahnungen/positionen/
 * @see http://www.billomat.com/api/mahnungen/schlagworte/
 */
public interface ReminderService extends
    AbstractCustomFieldService,
    AbstractTagService<ReminderTag>,
    AbstractItemService<ReminderItem> {

    /**
     * @param reminderFilter
     *            reminder filter, may be {@code null} to find unfiltered
     * @return reminders found by filter criteria or an empty list if no reminders were found - never
     *         {@code null}
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Reminder> findReminders(ReminderFilter reminderFilter);

    /**
     * Gets a reminder by its id.
     *
     * @param reminderId
     *            the reminder's id
     * @return the reminder or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Reminder getReminderById(int reminderId);

    /**
     * @param reminder
     *            the reminder to create, must not be {@code null}
     * @throws NullPointerException
     *             if reminder is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createReminder(Reminder reminder);

    /**
     * @param reminder
     *            the reminder to update, must not be {@code null}
     * @throws NullPointerException
     *             if reminder is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateReminder(Reminder reminder);

    /**
     * @param reminderId
     *            the id of the reminder to be deleted
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteReminder(int reminderId);

    /**
     * @param reminderId
     *            the id of the reminder to get the PDF for
     * @return the reminder PDF or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    ReminderPdf getReminderPdf(int reminderId);

    /**
     * @param reminderId
     *            the id of the reminder to get the signed PDF for
     * @return the signed reminder PDF or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     * @see #uploadReminderSignedPdf(int, byte[])
     * @see #getReminderPdf(int)
     */
    ReminderPdf getReminderSignedPdf(int reminderId);

    /**
     * Sets the reminder status to {@link net.siegmar.billomat4j.sdk.domain.reminder.ReminderStatus#COMPLETED}.
     *
     * @param reminderId
     *            the id of the reminder to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void completeReminder(int reminderId, Integer templateId);

    /**
     * @param reminderId
     *            the id of the reminder to upload the signed PDF for
     * @param pdf
     *            the signed PDF as binary data (must not be {@code null})
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     * @throws NullPointerException
     *             if pdf is null
     * @see #getReminderSignedPdf(int)
     */
    void uploadReminderSignedPdf(int reminderId, byte[] pdf);

    /**
     * @param reminderId
     *            the id of the reminder to send an email for
     * @param reminderEmail
     *            the email configuration
     * @throws NullPointerException
     *             if reminderEmail is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void sendReminderViaEmail(int reminderId, Email reminderEmail);

    /**
     * Sets the reminder status to {@link net.siegmar.billomat4j.sdk.domain.reminder.ReminderStatus#CANCELED}.
     *
     * @param reminderId
     *            the id of the reminder to update
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void cancelReminder(int reminderId);

}
