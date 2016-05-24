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
package de.siegmar.billomat4j.sdk.service;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteActionKey;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteComment;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteCommentFilter;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteFilter;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteGroup;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteGroupFilter;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteItem;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNotePayment;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNotePaymentFilter;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNotePdf;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteTag;

/**
 * @see http://www.billomat.com/api/gutschriften/
 * @see http://www.billomat.com/api/gutschriften/positionen/
 * @see http://www.billomat.com/api/gutschriften/kommentare-und-historie-3/
 * @see http://www.billomat.com/api/gutschriften/zahlungen/
 * @see http://www.billomat.com/api/gutschriften/schlagworte/
 */
public interface CreditNoteService extends
    GenericCustomFieldService,
    GenericTagService<CreditNoteTag>,
    GenericCommentService<CreditNoteActionKey, CreditNoteComment, CreditNoteCommentFilter>,
    GenericItemService<CreditNoteItem>,
    GenericPaymentService<CreditNotePayment, CreditNotePaymentFilter> {

    /**
     * @param creditNoteFilter
     *            credit note filter, may be {@code null} to find unfiltered
     * @return credit notes found by filter criteria or an empty list if no credit notes were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<CreditNote> findCreditNotes(CreditNoteFilter creditNoteFilter);

    /**
     * @param creditNoteGroupFilter
     *            the group definition, must not be {@code null}
     * @param creditNoteFilter
     *            the filter criteria, optional - may be {@code null}
     * @return grouped credit note list or an empty list if no credit notes were found - never {@code null}
     * @throws NullPointerException
     *             if creditNoteGroupFilter is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<CreditNoteGroup> getGroupedCreditNotes(CreditNoteGroupFilter creditNoteGroupFilter,
            CreditNoteFilter creditNoteFilter);

    /**
     * Gets a credit note by its id.
     *
     * @param creditNoteId
     *            the credit note's id
     * @return the credit note or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    CreditNote getCreditNoteById(int creditNoteId);

    /**
     * Gets a credit note by its credit note number.
     *
     * @param creditNoteNumber
     *            the credit note number, must not be empty / {@code null}
     * @return the credit note or {@code null} if not found
     * @throws NullPointerException
     *             if creditNoteNumber is null
     * @throws IllegalArgumentException
     *             if creditNoteNumber is empty
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    CreditNote getCreditNoteByNumber(String creditNoteNumber);

    /**
     * @param creditNote
     *            the credit note to create, must not be {@code null}
     * @throws NullPointerException
     *             if creditNote is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createCreditNote(CreditNote creditNote);

    /**
     * @param creditNote
     *            the credit note to update, must not be {@code null}
     * @throws NullPointerException
     *             if creditNote is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateCreditNote(CreditNote creditNote);

    /**
     * @param creditNoteId
     *            the id of the credit note to be deleted
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteCreditNote(int creditNoteId);

    /**
     * @param creditNoteId
     *            the id of the credit note to get the PDF for
     * @return the credit note PDF or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     * @see #getCreditNoteSignedPdf(int)
     */
    CreditNotePdf getCreditNotePdf(int creditNoteId);

    /**
     * @param creditNoteId
     *            the id of the credit note to get the signed PDF for
     * @return the signed credit note PDF or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     * @see #uploadCreditNoteSignedPdf(int, byte[])
     * @see #getCreditNotePdf(int)
     */
    CreditNotePdf getCreditNoteSignedPdf(int creditNoteId);

    /**
     * Sets the credit note status to {@link de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteStatus#OPEN}.
     *
     * @param creditNoteId
     *            the id of the credit note to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void completeCreditNote(int creditNoteId, Integer templateId);

    /**
     * @param creditNoteId
     *            the id of the credit note to upload the signed PDF for
     * @param pdf
     *            the signed PDF as binary data (must not be {@code null})
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     * @throws NullPointerException
     *             if pdf is null
     * @see #getCreditNoteSignedPdf(int)
     */
    void uploadCreditNoteSignedPdf(int creditNoteId, byte[] pdf);

    /**
     * @param creditNoteId
     *            the id of the credit note to send an email for
     * @param creditNoteEmail
     *            the email configuration
     * @throws NullPointerException
     *             if creditNoteEmail is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void sendCreditNoteViaEmail(int creditNoteId, Email creditNoteEmail);

}
