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
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
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
import de.siegmar.billomat4j.service.CreditNoteService;

public class CreditNoteServiceImpl extends AbstractService implements CreditNoteService {

    private static final String RESOURCE = "credit-notes";
    private static final String RESOURCE_ITEMS = "credit-note-items";
    private static final String RESOURCE_COMMENTS = "credit-note-comments";
    private static final String RESOURCE_PAYMENTS = "credit-note-payments";
    private static final String RESOURCE_TAGS = "credit-note-tags";

    public CreditNoteServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // CreditNote

    @Override
    public String getCustomFieldValue(final int creditNoteId) {
        return getCustomField(RESOURCE, creditNoteId);
    }

    @Override
    public void setCustomFieldValue(final int creditNoteId, final String value) {
        updateCustomField(RESOURCE, creditNoteId, "credit-note", value);
    }

    @Override
    public List<CreditNote> findCreditNotes(final CreditNoteFilter creditNoteFilter) {
        return getAllPagesFromResource(RESOURCE, CreditNotes.class, creditNoteFilter);
    }

    @Override
    public List<CreditNoteGroup> getGroupedCreditNotes(final CreditNoteGroupFilter creditNoteGroupFilter,
                                                       final CreditNoteFilter creditNoteFilter) {

        Validate.notNull(creditNoteGroupFilter);
        Validate.isTrue(creditNoteGroupFilter.isConfigured());
        final CombinedFilter filter = new CombinedFilter(creditNoteGroupFilter, creditNoteFilter);
        return getAllFromResource(RESOURCE, CreditNoteGroups.class, filter);
    }

    @Override
    public CreditNote getCreditNoteById(final int id) {
        return getById(RESOURCE, CreditNote.class, id);
    }

    @Override
    public CreditNote getCreditNoteByNumber(final String creditNoteNumber) {
        return single(findCreditNotes(new CreditNoteFilter().byCreditNoteNumber(Validate.notEmpty(creditNoteNumber))));
    }

    @Override
    public void createCreditNote(final CreditNote creditNote) {
        create(RESOURCE, Validate.notNull(creditNote));
    }

    @Override
    public void updateCreditNote(final CreditNote creditNote) {
        update(RESOURCE, Validate.notNull(creditNote));
    }

    @Override
    public void deleteCreditNote(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public CreditNotePdf getCreditNotePdf(final int id) {
        return getCreditNotePdf(id, null);
    }

    private CreditNotePdf getCreditNotePdf(final int id, final Map<String, String> filter) {
        return getPdf(RESOURCE, CreditNotePdf.class, id, filter);
    }

    @Override
    public CreditNotePdf getCreditNoteSignedPdf(final int id) {
        final Map<String, String> filter = Collections.singletonMap("type", "signed");
        return getCreditNotePdf(id, filter);
    }

    @Override
    public void completeCreditNote(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void uploadCreditNoteSignedPdf(final int creditNoteId, final byte[] pdf) {
        uploadSignedPdf(RESOURCE, creditNoteId, Validate.notNull(pdf));
    }

    @Override
    public void sendCreditNoteViaEmail(final int creditNoteId, final Email creditNoteEmail) {
        sendEmail(RESOURCE, creditNoteId, creditNoteEmail);
    }

    // CreditNoteItem

    @Override
    public List<CreditNoteItem> getItems(final int creditNoteId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, CreditNoteItems.class, creditNoteIdFilter(creditNoteId));
    }

    @Override
    public CreditNoteItem getItemById(final int creditNoteItemId) {
        return getById(RESOURCE_ITEMS, CreditNoteItem.class, creditNoteItemId);
    }

    @Override
    public void createItem(final CreditNoteItem creditNoteItem) {
        create(RESOURCE_ITEMS, Validate.notNull(creditNoteItem));
    }

    @Override
    public void updateItem(final CreditNoteItem creditNoteItem) {
        update(RESOURCE_ITEMS, Validate.notNull(creditNoteItem));
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
    public CreditNoteComment getCommentById(final int creditNoteCommentId) {
        return getById(RESOURCE_COMMENTS, CreditNoteComment.class, creditNoteCommentId);
    }

    @Override
    public void createComment(final CreditNoteComment creditNoteComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(creditNoteComment));
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
    public CreditNotePayment getPaymentById(final int creditNotePaymentId) {
        return getById(RESOURCE_PAYMENTS, CreditNotePayment.class, creditNotePaymentId);
    }

    @Override
    public void createPayment(final CreditNotePayment creditNotePayment) {
        create(RESOURCE_PAYMENTS, Validate.notNull(creditNotePayment));
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
    public CreditNoteTag getTagById(final int creditNoteTagId) {
        return getById(RESOURCE_TAGS, CreditNoteTag.class, creditNoteTagId);
    }

    @Override
    public void createTag(final CreditNoteTag creditNoteTag) {
        create(RESOURCE_TAGS, Validate.notNull(creditNoteTag));
    }

    @Override
    public void deleteTag(final int creditNoteTagId) {
        delete(RESOURCE_TAGS, creditNoteTagId);
    }

}
