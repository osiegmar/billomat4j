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

import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoicePayment;
import de.siegmar.billomat4j.domain.invoice.InvoicePaymentFilter;
import de.siegmar.billomat4j.domain.types.PaymentType;
import integrationtest.AbstractPaymentIT;
import integrationtest.ServiceHolder;
import integrationtest.TemplateSetup;

@ExtendWith(TemplateSetup.class)
public class InvoicePaymentIT extends AbstractPaymentIT<InvoicePayment, InvoicePaymentFilter> {

    public InvoicePaymentIT() {
        setService(ServiceHolder.INVOICE);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("InvoicePaymentTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        ServiceHolder.INVOICE.createInvoice(invoice);
        ServiceHolder.INVOICE.completeInvoice(invoice.getId(), null);

        return invoice.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.INVOICE.getInvoiceById(ownerId).orElseThrow().getClientId();
        ServiceHolder.INVOICE.deleteInvoice(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

    @Override
    protected InvoicePayment buildPayment(final int ownerId) {
        final InvoicePayment invoicePayment = new InvoicePayment();
        invoicePayment.setInvoiceId(ownerId);
        return invoicePayment;
    }

    @Override
    protected InvoicePaymentFilter buildFilter() {
        return new InvoicePaymentFilter().byType(PaymentType.COUPON);
    }

}
