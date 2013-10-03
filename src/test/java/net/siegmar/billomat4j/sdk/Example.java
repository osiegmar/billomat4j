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
package net.siegmar.billomat4j.sdk;

import java.util.Date;

import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceFilter;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceStatus;
import net.siegmar.billomat4j.sdk.service.InvoiceService;
import net.siegmar.billomat4j.sdk.service.impl.BillomatConfiguration;
import net.siegmar.billomat4j.sdk.service.impl.InvoiceServiceImpl;

import org.apache.commons.lang3.time.DateUtils;

public class Example {

    public static void main(final String[] args) {
        final BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
        billomatConfiguration.setBillomatId("<billomatId>");
        billomatConfiguration.setApiKey("<apiKey>");

        final InvoiceService invoiceService = new InvoiceServiceImpl(billomatConfiguration);

        System.out.println("Paid invoices for the last 30 days:");

        final InvoiceFilter invoiceFilter = new InvoiceFilter()
            .byFrom(DateUtils.addDays(new Date(), -30))
            .byTo(new Date())
            .byStatus(InvoiceStatus.PAID);

        for (final Invoice invoice : invoiceService.findInvoices(invoiceFilter)) {
            System.out.println("Invoice " + invoice.getInvoiceNumber() + ": " + invoice.getTotalNet());
        }
    }

}
