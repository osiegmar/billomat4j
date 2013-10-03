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
package net.siegmar.billomat4j.sdk.invoice;

import net.siegmar.billomat4j.sdk.AbstractItemIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceItem;


public class InvoiceItemIT extends AbstractItemIT<InvoiceItem> {

    public InvoiceItemIT() {
        setService(invoiceService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("InvoiceItemTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoiceService.createInvoice(invoice);

        return invoice.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = invoiceService.getInvoiceById(ownerId).getClientId();
        invoiceService.deleteInvoice(ownerId);
        clientService.deleteClient(clientId);
    }

    @Override
    protected InvoiceItem buildItem(final int ownerId) {
        final InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(ownerId);
        return item;
    }

}
