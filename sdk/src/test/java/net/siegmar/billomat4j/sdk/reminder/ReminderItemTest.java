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

import java.util.Date;

import net.siegmar.billomat4j.sdk.AbstractItemTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.reminder.Reminder;
import net.siegmar.billomat4j.sdk.domain.reminder.ReminderItem;


public class ReminderItemTest extends AbstractItemTest<ReminderItem> {

    public ReminderItemTest() {
        setService(reminderService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("ReminderItemTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setDueDate(new Date());
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
