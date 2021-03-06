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

import java.math.BigDecimal;
import java.time.LocalDate;

import de.siegmar.billomat4j.AbstractItemIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceItem;
import de.siegmar.billomat4j.domain.reminder.Reminder;
import de.siegmar.billomat4j.domain.reminder.ReminderItem;

public class ReminderItemIT extends AbstractItemIT<ReminderItem> {

    public ReminderItemIT() {
        setService(reminderService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("ReminderItemTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setDueDate(LocalDate.now());
        final InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setUnitPrice(BigDecimal.ONE);
        invoiceItem.setQuantity(BigDecimal.ONE);
        invoice.addInvoiceItem(invoiceItem);
        invoiceService.createInvoice(invoice);
        invoiceService.completeInvoice(invoice.getId(), null);

        final Reminder reminder = new Reminder();
        reminder.setInvoiceId(invoice.getId());
        reminderService.createReminder(reminder);

        return reminder.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final Reminder reminder = reminderService.getReminderById(ownerId);
        final int clientId = invoiceService.getInvoiceById(reminder.getInvoiceId()).getClientId();
        reminderService.deleteReminder(ownerId);
        invoiceService.deleteInvoice(reminder.getInvoiceId());
        clientService.deleteClient(clientId);
    }

    @Override
    protected ReminderItem buildItem(final int ownerId) {
        final ReminderItem item = new ReminderItem();
        item.setReminderId(ownerId);
        return item;
    }

}
