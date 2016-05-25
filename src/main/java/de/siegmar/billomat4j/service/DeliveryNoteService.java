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

import java.util.List;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteActionKey;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteComment;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteCommentFilter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteFilter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteItem;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNotePdf;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteTag;

/**
 * @see <a href="http://www.billomat.com/api/lieferscheine/">API Lieferscheine</a>
 * @see <a href="http://www.billomat.com/api/lieferscheine/positionen/">API Lieferscheine/Positionen</a>
 * @see <a href="http://www.billomat.com/api/lieferscheine/kommentare-und-historie/">API Lieferscheine/Kommentare</a>
 * @see <a href="http://www.billomat.com/api/lieferscheine/schlagworte/">API Lieferscheine/Schlagworte</a>
 */
public interface DeliveryNoteService extends
    GenericCustomFieldService,
    GenericTagService<DeliveryNoteTag>,
    GenericCommentService<DeliveryNoteActionKey, DeliveryNoteComment, DeliveryNoteCommentFilter>,
    GenericItemService<DeliveryNoteItem> {

    /**
     * @param deliveryNoteFilter
     *            delivery note filter, may be {@code null} to find unfiltered
     * @return delivery notes found by filter criteria or an empty list if no delivery notes were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<DeliveryNote> findDeliveryNotes(DeliveryNoteFilter deliveryNoteFilter);

    /**
     * Gets a delivery note by its id.
     *
     * @param deliveryNoteId
     *            the delivery note's id
     * @return the delivery note or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
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
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    DeliveryNote getDeliveryNoteByNumber(String deliveryNoteNumber);

    /**
     * @param deliveryNote
     *            the delivery note to create, must not be {@code null}
     * @throws NullPointerException
     *             if deliveryNote is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createDeliveryNote(DeliveryNote deliveryNote);

    /**
     * @param deliveryNote
     *            the delivery note to update, must not be {@code null}
     * @throws NullPointerException
     *             if deliveryNote is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateDeliveryNote(DeliveryNote deliveryNote);

    /**
     * @param deliveryNoteId
     *            the id of the delivery note to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteDeliveryNote(int deliveryNoteId);

    /**
     * @param deliveryNoteId
     *            the id of the delivery note to get the PDF for
     * @return the delivery note PDF or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    DeliveryNotePdf getDeliveryNotePdf(int deliveryNoteId);

    /**
     * Sets the delivery note status to
     * {@link de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteStatus#CREATED}.
     *
     * @param deliveryNoteId
     *            the id of the delivery note to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
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
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void sendDeliveryNoteViaEmail(int deliveryNoteId, Email deliveryNoteEmail);

}
