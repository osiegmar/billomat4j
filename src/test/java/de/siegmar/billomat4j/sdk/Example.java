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
package de.siegmar.billomat4j.sdk;

import java.time.LocalDate;

import de.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import de.siegmar.billomat4j.sdk.domain.invoice.InvoiceFilter;
import de.siegmar.billomat4j.sdk.domain.invoice.InvoiceStatus;
import de.siegmar.billomat4j.sdk.service.InvoiceService;
import de.siegmar.billomat4j.sdk.service.impl.BillomatConfiguration;
import de.siegmar.billomat4j.sdk.service.impl.InvoiceServiceImpl;

@SuppressWarnings({
    "checkstyle:hideutilityclassconstructor",
    "checkstyle:uncommentedmain",
    "checkstyle:magicnumber"
    })
public class Example {

    public static void main(final String[] args) {
        final BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
        billomatConfiguration.setBillomatId("<billomatId>");
        billomatConfiguration.setApiKey("<apiKey>");

        // Optional Settings for registered Apps
        billomatConfiguration.setAppId("<appId>");
        billomatConfiguration.setAppSecret("<appSecret>");

        final InvoiceService invoiceService = new InvoiceServiceImpl(billomatConfiguration);

        System.out.println("Paid invoices for the last 30 days:");

        final InvoiceFilter invoiceFilter = new InvoiceFilter()
            .byFrom(LocalDate.now().minusDays(30))
            .byTo(LocalDate.now())
            .byStatus(InvoiceStatus.PAID);

        for (final Invoice invoice : invoiceService.findInvoices(invoiceFilter)) {
            System.out.println("Invoice " + invoice.getInvoiceNumber() + ": " + invoice.getTotalNet());
        }
    }

}
