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
