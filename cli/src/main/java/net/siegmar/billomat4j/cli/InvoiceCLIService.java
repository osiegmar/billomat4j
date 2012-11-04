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
package net.siegmar.billomat4j.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoicePdf;
import net.siegmar.billomat4j.sdk.service.InvoiceService;
import net.siegmar.billomat4j.sdk.service.impl.BillomatConfiguration;
import net.siegmar.billomat4j.sdk.service.impl.InvoiceServiceImpl;

public class InvoiceCLIService {

    private final InvoiceService invoiceService;

    public InvoiceCLIService(final BillomatConfiguration billomatConfiguration) {
        invoiceService = new InvoiceServiceImpl(billomatConfiguration);
    }

    public void exportInvoices() {
        final List<Invoice> invoices = invoiceService.findInvoices(null);
        for (final Invoice invoice : invoices) {
            System.out.print("Saving Invoice PDF " + invoice.getId() + " ...");
            final InvoicePdf invoicePdf = invoiceService.getInvoicePdf(invoice.getId());
            try {
                invoicePdf.saveTo(new File("/tmp", invoicePdf.getFilename()));
            } catch (final IOException e) {
                throw new IllegalStateException(e);
            }
            System.out.println(" Done.");
        }
    }

}
