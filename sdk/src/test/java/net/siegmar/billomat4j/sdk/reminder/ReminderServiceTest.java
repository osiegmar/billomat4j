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
package net.siegmar.billomat4j.sdk.reminder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.EmailRecipients;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.reminder.Reminder;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderFilter;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderItem;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderPdf;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class ReminderServiceTest extends AbstractServiceTest {

    // Reminder

    @After
    public void cleanup() {
        // Clean up all reminders
        List<Reminder> reminders = reminderService.findReminders(null);
        if (!reminders.isEmpty()) {
            for (final Reminder reminder : reminders) {
                final Invoice invoice = invoiceService.getInvoiceById(reminder.getInvoiceId());
                final int clientId = invoice.getClientId();
                reminderService.deleteReminder(reminder.getId());
                invoiceService.deleteInvoice(invoice.getId());
                clientService.deleteClient(clientId);
            }

            reminders = reminderService.findReminders(null);
            assertTrue(reminders.isEmpty());
        }
    }

    @Test
    public void findAll() {
        List<Reminder> reminders = reminderService.findReminders(null);
        assertTrue(reminders.isEmpty());

        final Reminder reminder1 = createReminder(1);
        final Reminder reminder2 = createReminder(2);

        reminders = reminderService.findReminders(null);
        assertEquals(2, reminders.size());
        assertEquals(reminder1.getId(), reminders.get(0).getId());
        assertEquals(reminder2.getId(), reminders.get(1).getId());
    }

    @Test
    public void findFiltered() {
        assertTrue(reminderService.findReminders(null).isEmpty());

        final Reminder reminder1 = createReminder(1);
        createReminder(2);

        final List<Reminder> reminders = reminderService.findReminders(new ReminderFilter().byInvoiceNumber("1"));
        assertEquals(1, reminders.size());
        assertEquals(reminder1.getId(), reminders.get(0).getId());
    }

    @Test
    public void create() {
        final Reminder reminder = createReminder(1);
        assertNotNull(reminder.getId());
    }

    @Test
    public void getById() {
        final Reminder reminder = createReminder(1);
        assertEquals(reminder.getId(), reminderService.getReminderById(reminder.getId()).getId());
    }

    @Test
    public void update() {
        final Reminder reminder = createReminder(1);
        reminder.setLabel("Test Label");
        reminderService.updateReminder(reminder);
        assertEquals("Test Label", reminder.getLabel());
        assertEquals("Test Label", reminderService.getReminderById(reminder.getId()).getLabel());
    }

    @Test
    public void delete() {
        final Reminder reminder = createReminder(1);

        final Invoice invoice = invoiceService.getInvoiceById(reminder.getInvoiceId());
        final int clientId = invoice.getClientId();
        reminderService.deleteReminder(reminder.getId());
        invoiceService.deleteInvoice(invoice.getId());
        clientService.deleteClient(clientId);

        assertNull(reminderService.getReminderById(reminder.getId()));
    }

    @Test
    public void complete() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        assertEquals(ReminderStatus.OPEN, reminderService.getReminderById(reminder.getId()).getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Reminder reminder = createReminder(1);
            reminderService.completeReminder(reminder.getId(), null);
            assertEquals(ReminderStatus.OPEN, reminderService.getReminderById(reminder.getId()).getStatus());
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.REMINDER);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void uploadSignedPdf() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        reminderService.uploadReminderSignedPdf(reminder.getId(), "dummy".getBytes());

        assertArrayEquals("dummy".getBytes(), reminderService.getReminderSignedPdf(reminder.getId()).getBase64file());
    }

    @Test
    public void getReminderPdf() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        final ReminderPdf reminderPdf = reminderService.getReminderPdf(reminder.getId());
        assertNotNull(reminderPdf);
    }

    @Test
    @Ignore
    public void sendReminderViaEmail() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Reminder Mail");
        email.setBody("Here's your reminder");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
        email.setRecipients(emailRecipients);
        reminderService.sendReminderViaEmail(reminder.getId(), email);
    }

    @Test
    public void cancel() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        reminderService.cancelReminder(reminder.getId());
        assertEquals(ReminderStatus.CANCELED, reminderService.getReminderById(reminder.getId()).getStatus());
    }

    private Reminder createReminder(final int number) {
        final Client client = new Client();
        client.setName("ReminderServiceTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setNumber(number);
        invoice.setDueDate(new Date());
        invoiceService.createInvoice(invoice);
        invoiceService.completeInvoice(invoice.getId(), null);

        final Reminder reminder = new Reminder();
        reminder.setInvoiceId(invoice.getId());

        final ReminderItem reminderItem1 = new ReminderItem();
        reminderItem1.setTitle("Reminder Item #1");
        reminderItem1.setUnitPrice(new BigDecimal("11.11"));
        reminderItem1.setQuantity(BigDecimal.ONE);
        reminder.addReminderItem(reminderItem1);

        final ReminderItem reminderItem2 = new ReminderItem();
        reminderItem2.setTitle("Reminder Item #2");
        reminderItem2.setUnitPrice(new BigDecimal("22.22"));
        reminderItem2.setQuantity(BigDecimal.ONE);
        reminder.addReminderItem(reminderItem2);

        reminderService.createReminder(reminder);
        return reminder;
    }

}
