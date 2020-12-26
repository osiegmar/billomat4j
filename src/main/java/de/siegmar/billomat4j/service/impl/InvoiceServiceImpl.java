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

package de.siegmar.billomat4j.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceComment;
import de.siegmar.billomat4j.domain.invoice.InvoiceCommentFilter;
import de.siegmar.billomat4j.domain.invoice.InvoiceComments;
import de.siegmar.billomat4j.domain.invoice.InvoiceFilter;
import de.siegmar.billomat4j.domain.invoice.InvoiceGroup;
import de.siegmar.billomat4j.domain.invoice.InvoiceGroupFilter;
import de.siegmar.billomat4j.domain.invoice.InvoiceGroups;
import de.siegmar.billomat4j.domain.invoice.InvoiceItem;
import de.siegmar.billomat4j.domain.invoice.InvoiceItems;
import de.siegmar.billomat4j.domain.invoice.InvoicePayment;
import de.siegmar.billomat4j.domain.invoice.InvoicePaymentFilter;
import de.siegmar.billomat4j.domain.invoice.InvoicePayments;
import de.siegmar.billomat4j.domain.invoice.InvoicePdf;
import de.siegmar.billomat4j.domain.invoice.InvoiceTag;
import de.siegmar.billomat4j.domain.invoice.InvoiceTags;
import de.siegmar.billomat4j.domain.invoice.Invoices;
import de.siegmar.billomat4j.service.InvoiceService;

public class InvoiceServiceImpl extends AbstractService implements InvoiceService {

    private static final String RESOURCE = "invoices";
    private static final String RESOURCE_ITEMS = "invoice-items";
    private static final String RESOURCE_COMMENTS = "invoice-comments";
    private static final String RESOURCE_PAYMENTS = "invoice-payments";
    private static final String RESOURCE_TAGS = "invoice-tags";

    public InvoiceServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Invoice

    @Override
    public String getCustomFieldValue(final int invoiceId) {
        return getCustomField(RESOURCE, invoiceId);
    }

    @Override
    public void setCustomFieldValue(final int invoiceId, final String value) {
        updateCustomField(RESOURCE, invoiceId, "invoice", value);
    }

    @Override
    public List<Invoice> findInvoices(final InvoiceFilter invoiceFilter) {
        return getAllPagesFromResource(RESOURCE, Invoices.class, invoiceFilter);
    }

    @Override
    public List<InvoiceGroup> getGroupedInvoices(final InvoiceGroupFilter invoiceGroupFilter,
            final InvoiceFilter invoiceFilter) {

        Validate.notNull(invoiceGroupFilter);
        Validate.isTrue(invoiceGroupFilter.isConfigured());
        return getAllFromResource(RESOURCE, InvoiceGroups.class, new CombinedFilter(invoiceGroupFilter, invoiceFilter));
    }

    @Override
    public Invoice getInvoiceById(final int id) {
        return getById(RESOURCE, Invoice.class, id);
    }

    @Override
    public Invoice getInvoiceByNumber(final String invoiceNumber) {
        return single(findInvoices(new InvoiceFilter().byInvoiceNumber(Validate.notEmpty(invoiceNumber))));
    }

    @Override
    public void createInvoice(final Invoice invoice) {
        create(RESOURCE, Validate.notNull(invoice));
    }

    @Override
    public void updateInvoice(final Invoice invoice) {
        update(RESOURCE, Validate.notNull(invoice));
    }

    @Override
    public void deleteInvoice(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public InvoicePdf getInvoicePdf(final int id) {
        return getInvoicePdf(id, null);
    }

    private InvoicePdf getInvoicePdf(final int id, final Map<String, String> filter) {
        return getPdf(RESOURCE, InvoicePdf.class, id, filter);
    }

    @Override
    public InvoicePdf getInvoiceSignedPdf(final int id) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getInvoicePdf(id, filter);
    }

    @Override
    public void completeInvoice(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void uploadInvoiceSignedPdf(final int invoiceId, final byte[] pdf) {
        uploadSignedPdf(RESOURCE, invoiceId, Validate.notNull(pdf));
    }

    @Override
    public void sendInvoiceViaEmail(final int invoiceId, final Email invoiceEmail) {
        sendEmail(RESOURCE, invoiceId, invoiceEmail);
    }

    @Override
    public void cancelInvoice(final int id) {
        transit(RESOURCE, "cancel", id);
    }

    // InvoiceItem

    @Override
    public List<InvoiceItem> getItems(final int invoiceId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, InvoiceItems.class, invoiceIdFilter(invoiceId));
    }

    private GenericFilter invoiceIdFilter(final Integer invoiceId) {
        return invoiceId == null ? null : new GenericFilter("invoice_id", invoiceId);
    }

    @Override
    public InvoiceItem getItemById(final int invoiceItemId) {
        return getById(RESOURCE_ITEMS, InvoiceItem.class, invoiceItemId);
    }

    @Override
    public void createItem(final InvoiceItem invoiceItem) {
        create(RESOURCE_ITEMS, Validate.notNull(invoiceItem));
    }

    @Override
    public void updateItem(final InvoiceItem invoiceItem) {
        update(RESOURCE_ITEMS, Validate.notNull(invoiceItem));
    }

    @Override
    public void deleteItem(final int invoiceItemId) {
        delete(RESOURCE_ITEMS, invoiceItemId);
    }

    // InvoiceComment

    @Override
    public List<InvoiceComment> findComments(final int invoiceId, final InvoiceCommentFilter invoiceCommentFilter) {
        final Filter filter = new CombinedFilter(invoiceIdFilter(invoiceId), invoiceCommentFilter);
        return getAllPagesFromResource(RESOURCE_COMMENTS, InvoiceComments.class, filter);
    }

    @Override
    public InvoiceComment getCommentById(final int invoiceCommentId) {
        return getById(RESOURCE_COMMENTS, InvoiceComment.class, invoiceCommentId);
    }

    @Override
    public void createComment(final InvoiceComment invoiceComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(invoiceComment));
    }

    @Override
    public void deleteComment(final int invoiceCommentId) {
        delete(RESOURCE_COMMENTS, Validate.notNull(invoiceCommentId));
    }

    // InvoicePayment

    @Override
    public List<InvoicePayment> findPayments(final InvoicePaymentFilter invoicePaymentFilter) {
        return getAllPagesFromResource(RESOURCE_PAYMENTS, InvoicePayments.class, invoicePaymentFilter);
    }

    @Override
    public InvoicePayment getPaymentById(final int invoicePaymentId) {
        return getById(RESOURCE_PAYMENTS, InvoicePayment.class, invoicePaymentId);
    }

    @Override
    public void createPayment(final InvoicePayment invoicePayment) {
        create(RESOURCE_PAYMENTS, Validate.notNull(invoicePayment));
    }

    @Override
    public void deletePayment(final int invoicePaymentId) {
        delete(RESOURCE_PAYMENTS, invoicePaymentId);
    }

    // InvoiceTag

    @Override
    public List<InvoiceTag> getTags(final Integer invoiceId) {
        return getAllPagesFromResource(RESOURCE_TAGS, InvoiceTags.class, invoiceIdFilter(invoiceId));
    }

    @Override
    public InvoiceTag getTagById(final int invoiceTagId) {
        return getById(RESOURCE_TAGS, InvoiceTag.class, invoiceTagId);
    }

    @Override
    public void createTag(final InvoiceTag invoiceTag) {
        create(RESOURCE_TAGS, Validate.notNull(invoiceTag));
    }

    @Override
    public void deleteTag(final int invoiceTagId) {
        delete(RESOURCE_TAGS, Validate.notNull(invoiceTagId));
    }

}
