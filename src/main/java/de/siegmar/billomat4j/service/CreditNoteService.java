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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteActionKey;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteComment;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteCommentFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteComments;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteGroup;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteGroupFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteGroups;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteItem;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteItems;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePayment;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePaymentFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePayments;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePdf;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteTag;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteTags;
import de.siegmar.billomat4j.domain.creditnote.CreditNotes;
import lombok.NonNull;

public class CreditNoteService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<CreditNoteTag>,
    GenericCommentService<CreditNoteActionKey, CreditNoteComment, CreditNoteCommentFilter>,
    GenericItemService<CreditNoteItem>, GenericPaymentService<CreditNotePayment, CreditNotePaymentFilter> {

    private static final String RESOURCE = "credit-notes";
    private static final String RESOURCE_ITEMS = "credit-note-items";
    private static final String RESOURCE_COMMENTS = "credit-note-comments";
    private static final String RESOURCE_PAYMENTS = "credit-note-payments";
    private static final String RESOURCE_TAGS = "credit-note-tags";

    public CreditNoteService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // CreditNote

    @Override
    public Optional<String> getCustomFieldValue(final int creditNoteId) {
        return getCustomField(RESOURCE, creditNoteId);
    }

    @Override
    public void setCustomFieldValue(final int creditNoteId, final String value) {
        updateCustomField(RESOURCE, creditNoteId, "credit-note", value);
    }

    /**
     * @param creditNoteFilter credit note filter, may be {@code null} to find unfiltered
     * @return credit notes found by filter criteria or an empty list if no credit notes were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<CreditNote> findCreditNotes(final CreditNoteFilter creditNoteFilter) {
        return getAllPagesFromResource(RESOURCE, CreditNotes.class, creditNoteFilter);
    }

    /**
     * @param creditNoteGroupFilter the group definition, must not be {@code null}
     * @param creditNoteFilter      the filter criteria, optional - may be {@code null}
     * @return grouped credit note list or an empty list if no credit notes were found - never {@code null}
     * @throws NullPointerException if creditNoteGroupFilter is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public List<CreditNoteGroup> getGroupedCreditNotes(@NonNull final CreditNoteGroupFilter creditNoteGroupFilter,
                                                       final CreditNoteFilter creditNoteFilter) {

        if (!creditNoteGroupFilter.isConfigured()) {
            throw new IllegalArgumentException("creditNoteGroupFilter must be configured");
        }
        final CombinedFilter filter = new CombinedFilter(creditNoteGroupFilter, creditNoteFilter);
        return getAllFromResource(RESOURCE, CreditNoteGroups.class, filter);
    }

    /**
     * Gets a credit note by its id.
     *
     * @param creditNoteId the credit note's id
     * @return the credit note
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<CreditNote> getCreditNoteById(final int creditNoteId) {
        return getById(RESOURCE, CreditNote.class, creditNoteId);
    }

    /**
     * Gets a credit note by its credit note number.
     *
     * @param creditNoteNumber the credit note number, must not be empty / {@code null}
     * @return the credit note
     * @throws NullPointerException     if creditNoteNumber is null
     * @throws IllegalArgumentException if creditNoteNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<CreditNote> getCreditNoteByNumber(@NonNull final String creditNoteNumber) {
        if (creditNoteNumber.isEmpty()) {
            throw new IllegalArgumentException("creditNoteNumber must not be empty");
        }
        return single(findCreditNotes(new CreditNoteFilter().byCreditNoteNumber(creditNoteNumber)));
    }

    /**
     * @param creditNote the credit note to create, must not be {@code null}
     * @throws NullPointerException if creditNote is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createCreditNote(@NonNull final CreditNote creditNote) {
        create(RESOURCE, creditNote);
    }

    /**
     * @param creditNote the credit note to update, must not be {@code null}
     * @throws NullPointerException if creditNote is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateCreditNote(@NonNull final CreditNote creditNote) {
        update(RESOURCE, creditNote);
    }

    /**
     * @param creditNoteId the id of the credit note to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteCreditNote(final int creditNoteId) {
        delete(RESOURCE, creditNoteId);
    }

    /**
     * @param creditNoteId the id of the credit note to get the PDF for
     * @return the credit note PDF
     * @throws ServiceException if an error occurred while accessing the web service
     * @see #getCreditNoteSignedPdf(int)
     */
    public Optional<CreditNotePdf> getCreditNotePdf(final int creditNoteId) {
        return getCreditNotePdf(creditNoteId, null);
    }

    private Optional<CreditNotePdf> getCreditNotePdf(final int creditNoteId, final Map<String, String> filter) {
        return getPdf(RESOURCE, CreditNotePdf.class, creditNoteId, filter);
    }

    /**
     * @param creditNoteId the id of the credit note to get the signed PDF for
     * @return the signed credit note PDF
     * @throws ServiceException if an error occurred while accessing the web service
     * @see #uploadCreditNoteSignedPdf(int, byte[])
     * @see #getCreditNotePdf(int)
     */
    public Optional<CreditNotePdf> getCreditNoteSignedPdf(final int creditNoteId) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getCreditNotePdf(creditNoteId, filter);
    }

    /**
     * Sets the credit note status to {@link de.siegmar.billomat4j.domain.creditnote.CreditNoteStatus#OPEN}.
     *
     * @param creditNoteId the id of the credit note to update
     * @param templateId   the id of the template to use for the resulting document or {@code null}
     *                     if no template should be used
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void completeCreditNote(final int creditNoteId, final Integer templateId) {
        completeDocument(RESOURCE, creditNoteId, templateId);
    }

    /**
     * @param creditNoteId the id of the credit note to upload the signed PDF for
     * @param pdf          the signed PDF as binary data (must not be {@code null})
     * @throws ServiceException     if an error occurred while accessing the web service
     * @throws NullPointerException if pdf is null
     * @see #getCreditNoteSignedPdf(int)
     */
    public void uploadCreditNoteSignedPdf(final int creditNoteId, @NonNull final byte[] pdf) {
        uploadSignedPdf(RESOURCE, creditNoteId, pdf);
    }

    /**
     * @param creditNoteId    the id of the credit note to send an email for
     * @param creditNoteEmail the email configuration
     * @throws NullPointerException if creditNoteEmail is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void sendCreditNoteViaEmail(final int creditNoteId, final Email creditNoteEmail) {
        sendEmail(RESOURCE, creditNoteId, creditNoteEmail);
    }

    // CreditNoteItem

    @Override
    public List<CreditNoteItem> getItems(final int creditNoteId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, CreditNoteItems.class, creditNoteIdFilter(creditNoteId));
    }

    @Override
    public Optional<CreditNoteItem> getItemById(final int creditNoteItemId) {
        return getById(RESOURCE_ITEMS, CreditNoteItem.class, creditNoteItemId);
    }

    @Override
    public void createItem(@NonNull final CreditNoteItem creditNoteItem) {
        create(RESOURCE_ITEMS, creditNoteItem);
    }

    @Override
    public void updateItem(@NonNull final CreditNoteItem creditNoteItem) {
        update(RESOURCE_ITEMS, creditNoteItem);
    }

    @Override
    public void deleteItem(final int creditNoteItemId) {
        delete(RESOURCE_ITEMS, creditNoteItemId);
    }

    // CreditNoteComment

    @Override
    public List<CreditNoteComment> findComments(final int creditNoteId,
                                                final CreditNoteCommentFilter creditNoteCommentFilter) {

        final Filter filter = new CombinedFilter(creditNoteIdFilter(creditNoteId), creditNoteCommentFilter);
        return getAllPagesFromResource(RESOURCE_COMMENTS, CreditNoteComments.class, filter);
    }

    private GenericFilter creditNoteIdFilter(final Integer creditNoteId) {
        return creditNoteId == null ? null : new GenericFilter("credit_note_id", creditNoteId);
    }

    @Override
    public Optional<CreditNoteComment> getCommentById(final int creditNoteCommentId) {
        return getById(RESOURCE_COMMENTS, CreditNoteComment.class, creditNoteCommentId);
    }

    @Override
    public void createComment(@NonNull final CreditNoteComment creditNoteComment) {
        create(RESOURCE_COMMENTS, creditNoteComment);
    }

    @Override
    public void deleteComment(final int creditNoteCommentId) {
        delete(RESOURCE_COMMENTS, creditNoteCommentId);
    }

    // CreditNotePayment

    @Override
    public List<CreditNotePayment> findPayments(final CreditNotePaymentFilter creditNotePaymentFilter) {
        return getAllPagesFromResource(RESOURCE_PAYMENTS, CreditNotePayments.class, creditNotePaymentFilter);
    }

    @Override
    public Optional<CreditNotePayment> getPaymentById(final int creditNotePaymentId) {
        return getById(RESOURCE_PAYMENTS, CreditNotePayment.class, creditNotePaymentId);
    }

    @Override
    public void createPayment(@NonNull final CreditNotePayment creditNotePayment) {
        create(RESOURCE_PAYMENTS, creditNotePayment);
    }

    @Override
    public void deletePayment(final int creditNotePaymentId) {
        delete(RESOURCE_PAYMENTS, creditNotePaymentId);
    }

    // CreditNoteTag

    @Override
    public List<CreditNoteTag> getTags(final Integer creditNoteId) {
        return getAllPagesFromResource(RESOURCE_TAGS, CreditNoteTags.class, creditNoteIdFilter(creditNoteId));
    }

    @Override
    public Optional<CreditNoteTag> getTagById(final int creditNoteTagId) {
        return getById(RESOURCE_TAGS, CreditNoteTag.class, creditNoteTagId);
    }

    @Override
    public void createTag(@NonNull final CreditNoteTag creditNoteTag) {
        create(RESOURCE_TAGS, creditNoteTag);
    }

    @Override
    public void deleteTag(final int creditNoteTagId) {
        delete(RESOURCE_TAGS, creditNoteTagId);
    }

}
