package net.siegmar.billomat4j.sdk.reminder;

import java.util.Date;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.reminder.Reminder;


public class ReminderCustomFieldTest extends AbstractCustomFieldServiceTest {

    public ReminderCustomFieldTest() {
        setService(reminderService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("ReminderCustomFieldTest");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setIntro("InvoiceCustomFieldTest");
        invoice.setDueDate(new Date());
        invoiceService.createInvoice(invoice);
        invoiceService.completeInvoice(invoice.getId(), null);

        final Reminder reminder = new Reminder();
        reminder.setInvoiceId(invoice.getId());
        reminder.setIntro("ReminderCustomFieldTest");
        reminderService.createReminder(reminder);
        return reminder.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int invoiceId = reminderService.getReminderById(ownerId).getInvoiceId();
        final int clientId = invoiceService.getInvoiceById(invoiceId).getClientId();
        reminderService.deleteReminder(ownerId);
        invoiceService.deleteInvoice(invoiceId);
        clientService.deleteClient(clientId);
    }

}
