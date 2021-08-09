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

package integrationtest.invoice;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceTag;
import integrationtest.AbstractTagIT;
import integrationtest.ServiceHolder;

public class InvoiceTagIT extends AbstractTagIT<InvoiceTag> {

    public InvoiceTagIT() {
        setService(ServiceHolder.INVOICE);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("InvoiceTagTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        ServiceHolder.INVOICE.createInvoice(invoice);
        return invoice.getId();
    }

    @Override
    protected InvoiceTag constructTag(final int ownerId) {
        final InvoiceTag tag = new InvoiceTag();
        tag.setInvoiceId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.INVOICE.getInvoiceById(ownerId).orElseThrow().getClientId();
        ServiceHolder.INVOICE.deleteInvoice(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

}
