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
package net.siegmar.billomat4j.sdk.service;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteActionKey;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteComment;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteCommentFilter;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteFilter;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteItem;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNotePdf;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteTag;

/**
 * @see http://www.billomat.com/api/lieferscheine/
 * @see http://www.billomat.com/api/lieferscheine/positionen/
 * @see http://www.billomat.com/api/lieferscheine/kommentare-und-historie/
 * @see http://www.billomat.com/api/lieferscheine/schlagworte/
 */
public interface DeliveryNoteService extends
    AbstractCustomFieldService,
    AbstractTagService<DeliveryNoteTag>,
    AbstractCommentService<DeliveryNoteActionKey, DeliveryNoteComment, DeliveryNoteCommentFilter>,
    AbstractItemService<DeliveryNoteItem> {

    /**
     * @param deliveryNoteFilter
     *            delivery note filter, may be {@code null} to find unfiltered
     * @return delivery notes found by filter criteria or an empty list if no delivery notes were found - never
     *         {@code null}
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<DeliveryNote> findDeliveryNotes(DeliveryNoteFilter deliveryNoteFilter);

    /**
     * Gets a delivery note by its id.
     *
     * @param deliveryNoteId
     *            the delivery note's id
     * @return the delivery note or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    DeliveryNote getDeliveryNoteById(int deliveryNoteId);

    /**
     * Gets a delivery note by its delivery note number.
     *
     * @param deliveryNoteNumber
     *            the delivery note number, must not be empty / {@code null}
     * @return the delivery note or {@code null} if not found
     * @throws NullPointerException
     *             if deliveryNoteNumber is null
     * @throws IllegalArgumentException
     *             if deliveryNoteNumber is empty
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    DeliveryNote getDeliveryNoteByNumber(String deliveryNoteNumber);

    /**
     * @param deliveryNote
     *            the delivery note to create, must not be {@code null}
     * @throws NullPointerException
     *             if deliveryNote is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createDeliveryNote(DeliveryNote deliveryNote);

    /**
     * @param deliveryNote
     *            the delivery note to update, must not be {@code null}
     * @throws NullPointerException
     *             if deliveryNote is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateDeliveryNote(DeliveryNote deliveryNote);

    /**
     * @param deliveryNoteId
     *            the id of the delivery note to be deleted
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteDeliveryNote(int deliveryNoteId);

    /**
     * @param deliveryNoteId
     *            the id of the delivery note to get the PDF for
     * @return the delivery note PDF or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    DeliveryNotePdf getDeliveryNotePdf(int deliveryNoteId);

    /**
     * Sets the delivery note status to
     * {@link net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteStatus#COMPLETED}.
     *
     * @param deliveryNoteId
     *            the id of the delivery note to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void completeDeliveryNote(int deliveryNoteId, Integer templateId);

    /**
     * @param deliveryNoteId
     *            the id of the delivery note to send an email for
     * @param deliveryNoteEmail
     *            the email configuration
     * @throws NullPointerException
     *             if deliveryNoteEmail is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void sendDeliveryNoteViaEmail(int deliveryNoteId, Email deliveryNoteEmail);

}
