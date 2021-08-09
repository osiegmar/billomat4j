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

package integrationtest.reminder;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceItem;
import de.siegmar.billomat4j.domain.reminder.Reminder;
import integrationtest.AbstractCustomFieldServiceIT;
import integrationtest.ServiceHolder;
import integrationtest.TemplateSetup;

@ExtendWith(TemplateSetup.class)
public class ReminderCustomFieldIT extends AbstractCustomFieldServiceIT {

    public ReminderCustomFieldIT() {
        setService(ServiceHolder.REMINDER);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("ReminderCustomFieldTest");
        ServiceHolder.CLIENT.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setIntro("InvoiceCustomFieldTest");
        invoice.setDueDate(LocalDate.now());
        final InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setUnitPrice(BigDecimal.ONE);
        invoiceItem.setQuantity(BigDecimal.ONE);
        invoice.addInvoiceItem(invoiceItem);
        ServiceHolder.INVOICE.createInvoice(invoice);
        ServiceHolder.INVOICE.completeInvoice(invoice.getId(), null);

        final Reminder reminder = new Reminder();
        reminder.setInvoiceId(invoice.getId());
        reminder.setIntro("ReminderCustomFieldTest");
        ServiceHolder.REMINDER.createReminder(reminder);
        return reminder.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int invoiceId = ServiceHolder.REMINDER.getReminderById(ownerId).orElseThrow().getInvoiceId();
        final int clientId = ServiceHolder.INVOICE.getInvoiceById(invoiceId).orElseThrow().getClientId();
        ServiceHolder.REMINDER.deleteReminder(ownerId);
        ServiceHolder.INVOICE.deleteInvoice(invoiceId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

}
