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

package de.siegmar.billomat4j.reminder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import de.siegmar.billomat4j.AbstractServiceIT;
import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceItem;
import de.siegmar.billomat4j.domain.reminder.Reminder;
import de.siegmar.billomat4j.domain.reminder.ReminderFilter;
import de.siegmar.billomat4j.domain.reminder.ReminderItem;
import de.siegmar.billomat4j.domain.reminder.ReminderPdf;
import de.siegmar.billomat4j.domain.reminder.ReminderStatus;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;

public class ReminderServiceIT extends AbstractServiceIT {

    private final List<Reminder> createdReminders = new ArrayList<>();
    private final List<Client> createdClients = new ArrayList<>();

    // Reminder

    @AfterMethod
    public void cleanup() {
        for (final Reminder reminder : createdReminders) {
            reminderService.deleteReminder(reminder.getId());
            invoiceService.deleteInvoice(reminder.getInvoiceId());
        }
        for (final Client client : createdClients) {
            clientService.deleteClient(client.getId());
        }
        createdReminders.clear();
        createdClients.clear();
    }

    @Test
    public void findAll() {
        assertTrue(reminderService.findReminders(null).isEmpty());
        createReminder(1);
        assertFalse(reminderService.findReminders(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final ReminderFilter reminderFilter = new ReminderFilter().byInvoiceNumber("1");
        List<Reminder> reminders = reminderService.findReminders(reminderFilter);
        assertTrue(reminders.isEmpty());

        final Reminder reminder1 = createReminder(1);
        createReminder(2);

        reminders = reminderService.findReminders(reminderFilter);
        assertEquals(reminders.size(), 1);
        assertEquals(reminders.get(0).getId(), reminder1.getId());
    }

    @Test
    public void create() {
        final Reminder reminder = createReminder(1);
        assertNotNull(reminder.getId());
    }

    @Test
    public void getById() {
        final Reminder reminder = createReminder(1);
        assertEquals(reminderService.getReminderById(reminder.getId()).getId(), reminder.getId());
    }

    @Test
    public void update() {
        final Reminder reminder = createReminder(1);
        reminder.setLabel("Test Label");
        reminderService.updateReminder(reminder);
        assertEquals(reminder.getLabel(), "Test Label");
        assertEquals(reminderService.getReminderById(reminder.getId()).getLabel(), "Test Label");
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

        createdClients.clear();
        createdReminders.clear();
    }

    @Test
    public void complete() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        assertEquals(reminderService.getReminderById(reminder.getId()).getStatus(), ReminderStatus.OPEN);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Reminder reminder = createReminder(1);
            reminderService.completeReminder(reminder.getId(), null);
            assertEquals(reminderService.getReminderById(reminder.getId()).getStatus(), ReminderStatus.OPEN);
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
        reminderService.uploadReminderSignedPdf(reminder.getId(), "dummy".getBytes(StandardCharsets.US_ASCII));

        assertEquals(reminderService.getReminderSignedPdf(reminder.getId()).getBase64file(),
            "dummy".getBytes(StandardCharsets.US_ASCII));
    }

    @Test
    public void getReminderPdf() {
        final Reminder reminder = createReminder(1);
        reminderService.completeReminder(reminder.getId(), null);
        final ReminderPdf reminderPdf = reminderService.getReminderPdf(reminder.getId());
        assertNotNull(reminderPdf);
    }

    @Test(enabled = false)
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
        assertEquals(reminderService.getReminderById(reminder.getId()).getStatus(), ReminderStatus.CANCELED);
    }

    private Reminder createReminder(final int number) {
        final Client client = new Client();
        client.setName("ReminderServiceTest Client");
        clientService.createClient(client);

        createdClients.add(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setNumber(number);
        invoice.setDueDate(LocalDate.now());
        final InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setUnitPrice(BigDecimal.ONE);
        invoiceItem.setQuantity(BigDecimal.ONE);
        invoice.addInvoiceItem(invoiceItem);
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

        createdReminders.add(reminder);

        return reminder;
    }

}
