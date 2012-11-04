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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.EmailRecipients;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceFilter;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceGroup;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceGroupFilter;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceItem;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoicePdf;
import net.siegmar.billomat4j.sdk.domain.invoice.InvoiceStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class InvoiceServiceTest extends AbstractServiceTest {

    // Invoice

    @After
    public void cleanup() {
        // Clean up all invoices
        List<Invoice> invoices = invoiceService.findInvoices(null);
        if (!invoices.isEmpty()) {
            for (final Invoice invoice : invoices) {
                final int clientId = invoice.getClientId();
                invoiceService.deleteInvoice(invoice.getId());
                clientService.deleteClient(clientId);
            }

            invoices = invoiceService.findInvoices(null);
            assertTrue(invoices.isEmpty());
        }
    }

    @Test
    public void findAll() {
        List<Invoice> invoices = invoiceService.findInvoices(null);
        assertTrue(invoices.isEmpty());

        final Invoice invoice1 = createInvoice(1);
        final Invoice invoice2 = createInvoice(2);

        invoices = invoiceService.findInvoices(null);
        assertEquals(2, invoices.size());
        assertEquals(invoice1.getId(), invoices.get(0).getId());
        assertEquals(invoice2.getId(), invoices.get(1).getId());
    }

    @Test
    public void findFiltered() {
        assertTrue(invoiceService.findInvoices(null).isEmpty());

        final Invoice invoice1 = createInvoice(1);
        createInvoice(2);

        final List<Invoice> invoices = invoiceService.findInvoices(new InvoiceFilter().byInvoiceNumber("1"));
        assertEquals(1, invoices.size());
        assertEquals(invoice1.getId(), invoices.get(0).getId());
    }

    @Test
    public void findByInvoiceNumber() {
        final Client client = new Client();
        client.setName("InvoiceServiceTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setNumberPre("I");
        invoice.setNumber(5);
        invoiceService.createInvoice(invoice);

        assertEquals(invoice.getId(), invoiceService.getInvoiceByNumber("I5").getId());
    }

    @Test
    public void getAllInvoiceGroups() {
        final Invoice invoice1 = createInvoice(1);
        final Invoice invoice2 = createInvoice(2);

        final List<InvoiceGroup> invoiceGroups = invoiceService.getGroupedInvoices(new InvoiceGroupFilter().byDay(), null);
        assertEquals(1, invoiceGroups.size());

        final InvoiceGroup invoiceGroup = invoiceGroups.get(0);
        assertEquals(invoice1.getTotalNet().add(invoice2.getTotalNet()), invoiceGroup.getTotalNet());
    }

    @Test
    public void create() {
        final Invoice invoice = createInvoice(1);
        assertNotNull(invoice.getId());
    }

    @Test
    public void getById() {
        final Invoice invoice = createInvoice(1);
        assertEquals(invoice.getId(), invoiceService.getInvoiceById(invoice.getId()).getId());
    }

    @Test
    public void update() {
        final Invoice invoice = createInvoice(1);
        invoice.setLabel("Test Label");
        invoiceService.updateInvoice(invoice);
        assertEquals("Test Label", invoice.getLabel());
        assertEquals("Test Label", invoiceService.getInvoiceById(invoice.getId()).getLabel());
    }

    @Test
    public void delete() {
        final Invoice invoice = createInvoice(1);

        final int clientId = invoice.getClientId();
        invoiceService.deleteInvoice(invoice.getId());
        clientService.deleteClient(clientId);

        assertNull(invoiceService.getInvoiceById(invoice.getId()));
    }

    @Test
    public void complete() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        assertEquals(InvoiceStatus.OPEN, invoiceService.getInvoiceById(invoice.getId()).getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Invoice invoice = createInvoice(1);
            invoiceService.completeInvoice(invoice.getId(), null);
            assertEquals(InvoiceStatus.OPEN, invoiceService.getInvoiceById(invoice.getId()).getStatus());
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.INVOICE);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void uploadSignedPdf() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        invoiceService.uploadInvoiceSignedPdf(invoice.getId(), "dummy".getBytes());

        assertArrayEquals("dummy".getBytes(), invoiceService.getInvoiceSignedPdf(invoice.getId()).getBase64file());
    }

    @Test
    public void getInvoicePdf() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        final InvoicePdf invoicePdf = invoiceService.getInvoicePdf(invoice.getId());
        assertNotNull(invoicePdf);
    }

    @Test
    @Ignore
    public void sendInvoiceViaEmail() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Invoice Mail");
        email.setBody("Here's your invoice");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
        email.setRecipients(emailRecipients);
        invoiceService.sendInvoiceViaEmail(invoice.getId(), email);
    }

    @Test
    public void cancel() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        invoiceService.cancelInvoice(invoice.getId());
        assertEquals(InvoiceStatus.CANCELED, invoiceService.getInvoiceById(invoice.getId()).getStatus());
    }

    private Invoice createInvoice(final int number) {
        final Client client = new Client();
        client.setName("InvoiceServiceTest Client");
        clientService.createClient(client);

        final Invoice invoice = new Invoice();
        invoice.setClientId(client.getId());
        invoice.setNumber(number);

        final InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setTitle("Invoice Item #1");
        invoiceItem1.setUnitPrice(new BigDecimal("11.11"));
        invoiceItem1.setQuantity(BigDecimal.ONE);
        invoice.addInvoiceItem(invoiceItem1);

        final InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setTitle("Invoice Item #2");
        invoiceItem2.setUnitPrice(new BigDecimal("22.22"));
        invoiceItem2.setQuantity(BigDecimal.ONE);
        invoice.addInvoiceItem(invoiceItem2);

        invoiceService.createInvoice(invoice);
        return invoice;
    }

}
