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

package de.siegmar.billomat4j.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.invoice.InvoiceActionKey;
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

public class InvoiceService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<InvoiceTag>,
    GenericCommentService<InvoiceActionKey, InvoiceComment, InvoiceCommentFilter>,
    GenericItemService<InvoiceItem>, GenericPaymentService<InvoicePayment, InvoicePaymentFilter> {

    private static final String RESOURCE = "invoices";
    private static final String RESOURCE_ITEMS = "invoice-items";
    private static final String RESOURCE_COMMENTS = "invoice-comments";
    private static final String RESOURCE_PAYMENTS = "invoice-payments";
    private static final String RESOURCE_TAGS = "invoice-tags";

    public InvoiceService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Invoice

    @Override
    public Optional<String> getCustomFieldValue(final int invoiceId) {
        return getCustomField(RESOURCE, invoiceId);
    }

    @Override
    public void setCustomFieldValue(final int invoiceId, final String value) {
        updateCustomField(RESOURCE, invoiceId, "invoice", value);
    }

    /**
     * @param invoiceFilter invoice filter, may be {@code null} to find unfiltered
     * @return invoices found by filter criteria or an empty list if no invoices were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Invoice> findInvoices(final InvoiceFilter invoiceFilter) {
        return getAllPagesFromResource(RESOURCE, Invoices.class, invoiceFilter);
    }

    /**
     * @param invoiceGroupFilter the group definition, must not be {@code null}
     * @param invoiceFilter      the filter criteria, optional - may be {@code null}
     * @return grouped invoice list or an empty list if no invoices were found - never {@code null}
     * @throws NullPointerException if invoiceGroupFilter is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public List<InvoiceGroup> getGroupedInvoices(final InvoiceGroupFilter invoiceGroupFilter,
                                                 final InvoiceFilter invoiceFilter) {

        Validate.notNull(invoiceGroupFilter);
        Validate.isTrue(invoiceGroupFilter.isConfigured());
        return getAllFromResource(RESOURCE, InvoiceGroups.class, new CombinedFilter(invoiceGroupFilter, invoiceFilter));
    }

    /**
     * Gets a invoice by its id.
     *
     * @param invoiceId the invoice's id
     * @return the invoice
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Invoice> getInvoiceById(final int invoiceId) {
        return getById(RESOURCE, Invoice.class, invoiceId);
    }

    /**
     * Gets a invoice by its invoice number.
     *
     * @param invoiceNumber the invoice number, must not be empty / {@code null}
     * @return the invoice
     * @throws NullPointerException     if invoiceNumber is null
     * @throws IllegalArgumentException if invoiceNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<Invoice> getInvoiceByNumber(final String invoiceNumber) {
        return single(findInvoices(new InvoiceFilter().byInvoiceNumber(Validate.notEmpty(invoiceNumber))));
    }

    /**
     * @param invoice the invoice to create, must not be {@code null}
     * @throws NullPointerException if invoice is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createInvoice(final Invoice invoice) {
        create(RESOURCE, Validate.notNull(invoice));
    }

    /**
     * @param invoice the invoice to update, must not be {@code null}
     * @throws NullPointerException if invoice is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateInvoice(final Invoice invoice) {
        update(RESOURCE, Validate.notNull(invoice));
    }

    /**
     * @param invoiceId the id of the invoice to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteInvoice(final int invoiceId) {
        delete(RESOURCE, invoiceId);
    }

    /**
     * @param invoiceId the id of the invoice to get the PDF for
     * @return the invoice PDF
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<InvoicePdf> getInvoicePdf(final int invoiceId) {
        return getInvoicePdf(invoiceId, null);
    }

    private Optional<InvoicePdf> getInvoicePdf(final int invoiceId, final Map<String, String> filter) {
        return getPdf(RESOURCE, InvoicePdf.class, invoiceId, filter);
    }

    /**
     * @param invoiceId the id of the invoice to get the signed PDF for
     * @return the signed invoice PDF
     * @throws ServiceException if an error occurred while accessing the web service
     * @see #uploadInvoiceSignedPdf(int, byte[])
     * @see #getInvoicePdf(int)
     */
    public Optional<InvoicePdf> getInvoiceSignedPdf(final int invoiceId) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getInvoicePdf(invoiceId, filter);
    }

    /**
     * Sets the invoice status to {@link de.siegmar.billomat4j.domain.invoice.InvoiceStatus#OPEN}
     * or {@link de.siegmar.billomat4j.domain.invoice.InvoiceStatus#OVERDUE}.
     *
     * @param invoiceId  the id of the invoice to update
     * @param templateId the id of the template to use for the resulting document or {@code null}
     *                   if no template should be used
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void completeInvoice(final int invoiceId, final Integer templateId) {
        completeDocument(RESOURCE, invoiceId, templateId);
    }

    /**
     * @param invoiceId the id of the invoice to upload the signed PDF for
     * @param pdf       the signed PDF as binary data (must not be {@code null})
     * @throws ServiceException     if an error occurred while accessing the web service
     * @throws NullPointerException if pdf is null
     * @see #getInvoiceSignedPdf(int)
     */
    public void uploadInvoiceSignedPdf(final int invoiceId, final byte[] pdf) {
        uploadSignedPdf(RESOURCE, invoiceId, Validate.notNull(pdf));
    }

    /**
     * @param invoiceId    the id of the invoice to send an email for
     * @param invoiceEmail the email configuration
     * @throws NullPointerException if invoiceEmail is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void sendInvoiceViaEmail(final int invoiceId, final Email invoiceEmail) {
        sendEmail(RESOURCE, invoiceId, invoiceEmail);
    }

    /**
     * Sets the invoice status to {@link de.siegmar.billomat4j.domain.invoice.InvoiceStatus#CANCELED}.
     *
     * @param invoiceId the id of the invoice to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void cancelInvoice(final int invoiceId) {
        transit(RESOURCE, "cancel", invoiceId);
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
    public Optional<InvoiceItem> getItemById(final int invoiceItemId) {
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
    public Optional<InvoiceComment> getCommentById(final int invoiceCommentId) {
        return getById(RESOURCE_COMMENTS, InvoiceComment.class, invoiceCommentId);
    }

    @Override
    public void createComment(final InvoiceComment invoiceComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(invoiceComment));
    }

    @Override
    public void deleteComment(final int invoiceCommentId) {
        delete(RESOURCE_COMMENTS, invoiceCommentId);
    }

    // InvoicePayment

    @Override
    public List<InvoicePayment> findPayments(final InvoicePaymentFilter invoicePaymentFilter) {
        return getAllPagesFromResource(RESOURCE_PAYMENTS, InvoicePayments.class, invoicePaymentFilter);
    }

    @Override
    public Optional<InvoicePayment> getPaymentById(final int invoicePaymentId) {
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
    public Optional<InvoiceTag> getTagById(final int invoiceTagId) {
        return getById(RESOURCE_TAGS, InvoiceTag.class, invoiceTagId);
    }

    @Override
    public void createTag(final InvoiceTag invoiceTag) {
        create(RESOURCE_TAGS, Validate.notNull(invoiceTag));
    }

    @Override
    public void deleteTag(final int invoiceTagId) {
        delete(RESOURCE_TAGS, invoiceTagId);
    }

}
