package net.siegmar.billomat4j.sdk.invoice;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;


public class InvoiceCustomFieldIT extends AbstractCustomFieldServiceIT {

    public InvoiceCustomFieldIT() {
        setService(invoiceService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("InvoiceCustomFieldTest");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setIntro("InvoiceCustomFieldTest");
        invoiceService.createInvoice(invoice);
        return invoice.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = invoiceService.getInvoiceById(ownerId).getClientId();
        invoiceService.deleteInvoice(ownerId);
        clientService.deleteClient(clientId);
    }

}
