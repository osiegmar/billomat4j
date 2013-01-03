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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceIT;
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class InvoiceServiceIT extends AbstractServiceIT {

    private final List<Invoice> createdInvoices = new ArrayList<>();

    // Invoice

    @AfterMethod
    public void cleanup() {
        for (final Invoice invoice : createdInvoices) {
            final int clientId = invoice.getClientId();
            invoiceService.deleteInvoice(invoice.getId());
            clientService.deleteClient(clientId);
        }
        createdInvoices.clear();
    }

    @Test
    public void findAll() {
        assertTrue(invoiceService.findInvoices(null).isEmpty());
        createInvoice(1);
        assertFalse(invoiceService.findInvoices(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final InvoiceFilter invoiceFilter = new InvoiceFilter().byInvoiceNumber("1");
        List<Invoice> invoices = invoiceService.findInvoices(invoiceFilter);
        assertTrue(invoices.isEmpty());

        final Invoice invoice1 = createInvoice(1);
        createInvoice(2);

        invoices = invoiceService.findInvoices(invoiceFilter);
        assertEquals(invoices.size(), 1);
        assertEquals(invoices.get(0).getId(), invoice1.getId());
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
        createdInvoices.add(invoice);

        assertEquals(invoiceService.getInvoiceByNumber("I5").getId(), invoice.getId());
    }

    @Test
    public void getAllInvoiceGroups() {
        final Invoice invoice1 = createInvoice(1);
        final Invoice invoice2 = createInvoice(2);

        final List<InvoiceGroup> invoiceGroups = invoiceService.getGroupedInvoices(new InvoiceGroupFilter().byDay(), null);
        assertEquals(invoiceGroups.size(), 1);

        final InvoiceGroup invoiceGroup = invoiceGroups.get(0);
        assertEquals(invoiceGroup.getTotalNet(), invoice1.getTotalNet().add(invoice2.getTotalNet()));
    }

    @Test
    public void create() {
        final Invoice invoice = createInvoice(1);
        assertNotNull(invoice.getId());
    }

    @Test
    public void getById() {
        final Invoice invoice = createInvoice(1);
        assertEquals(invoiceService.getInvoiceById(invoice.getId()).getId(), invoice.getId());
    }

    @Test
    public void update() {
        final Invoice invoice = createInvoice(1);
        invoice.setLabel("Test Label");
        invoiceService.updateInvoice(invoice);
        assertEquals(invoice.getLabel(), "Test Label");
        assertEquals(invoiceService.getInvoiceById(invoice.getId()).getLabel(), "Test Label");
    }

    @Test
    public void delete() {
        final Invoice invoice = createInvoice(1);

        final int clientId = invoice.getClientId();
        invoiceService.deleteInvoice(invoice.getId());
        clientService.deleteClient(clientId);

        assertNull(invoiceService.getInvoiceById(invoice.getId()));

        createdInvoices.remove(invoice);
    }

    @Test
    public void complete() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        assertEquals(invoiceService.getInvoiceById(invoice.getId()).getStatus(), InvoiceStatus.OPEN);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Invoice invoice = createInvoice(1);
            invoiceService.completeInvoice(invoice.getId(), null);
            assertEquals(invoiceService.getInvoiceById(invoice.getId()).getStatus(), InvoiceStatus.OPEN);
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

        assertEquals(invoiceService.getInvoiceSignedPdf(invoice.getId()).getBase64file(), "dummy".getBytes());
    }

    @Test
    public void getInvoicePdf() {
        final Invoice invoice = createInvoice(1);
        invoiceService.completeInvoice(invoice.getId(), null);
        final InvoicePdf invoicePdf = invoiceService.getInvoicePdf(invoice.getId());
        assertNotNull(invoicePdf);
    }

    @Test(enabled = false)
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
        assertEquals(invoiceService.getInvoiceById(invoice.getId()).getStatus(), InvoiceStatus.CANCELED);
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

        createdInvoices.add(invoice);

        return invoice;
    }

}
