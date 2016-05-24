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
package de.siegmar.billomat4j.sdk.service.impl;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteComment;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteItem;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNotes;
import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.Filter;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteCommentFilter;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteComments;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteFilter;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteItems;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNotePdf;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteTag;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteTags;
import de.siegmar.billomat4j.sdk.service.DeliveryNoteService;

import org.apache.commons.lang3.Validate;

public class DeliveryNoteServiceImpl extends AbstractService implements DeliveryNoteService {

    private static final String RESOURCE = "delivery-notes";
    private static final String RESOURCE_ITEMS = "delivery-note-items";
    private static final String RESOURCE_COMMENTS = "delivery-note-comments";
    private static final String RESOURCE_TAGS = "delivery-note-tags";

    public DeliveryNoteServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // DeliveryNote

    @Override
    public String getCustomFieldValue(final int deliveryNoteId) {
        return getCustomField(RESOURCE, deliveryNoteId);
    }

    @Override
    public void setCustomFieldValue(final int deliveryNoteId, final String value) {
        updateCustomField(RESOURCE, deliveryNoteId, "delivery-note", value);
    }

    @Override
    public List<DeliveryNote> findDeliveryNotes(final DeliveryNoteFilter deliveryNoteFilter) {
        return getAllPagesFromResource(RESOURCE, DeliveryNotes.class, deliveryNoteFilter);
    }

    @Override
    public DeliveryNote getDeliveryNoteById(final int id) {
        return getById(RESOURCE, DeliveryNote.class, id);
    }

    @Override
    public DeliveryNote getDeliveryNoteByNumber(final String deliveryNoteNumber) {
        Validate.notEmpty(deliveryNoteNumber);
        return single(findDeliveryNotes(new DeliveryNoteFilter().byDeliveryNoteNumber(deliveryNoteNumber)));
    }

    @Override
    public void createDeliveryNote(final DeliveryNote deliveryNote) {
        create(RESOURCE, Validate.notNull(deliveryNote));
    }

    @Override
    public void updateDeliveryNote(final DeliveryNote deliveryNote) {
        update(RESOURCE, Validate.notNull(deliveryNote));
    }

    @Override
    public void deleteDeliveryNote(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public DeliveryNotePdf getDeliveryNotePdf(final int id) {
        return getPdf(RESOURCE, DeliveryNotePdf.class, id, null);
    }

    @Override
    public void completeDeliveryNote(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void sendDeliveryNoteViaEmail(final int deliveryNoteId, final Email deliveryNoteEmail) {
        sendEmail(RESOURCE, deliveryNoteId, deliveryNoteEmail);
    }

    // DeliveryNoteItem

    @Override
    public List<DeliveryNoteItem> getItems(final int deliveryNoteId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, DeliveryNoteItems.class, deliveryNoteIdFilter(deliveryNoteId));
    }

    private GenericFilter deliveryNoteIdFilter(final Integer deliveryNoteId) {
        return deliveryNoteId == null ? null : new GenericFilter("delivery_note_id", deliveryNoteId);
    }

    @Override
    public DeliveryNoteItem getItemById(final int deliveryNoteItemId) {
        return getById(RESOURCE_ITEMS, DeliveryNoteItem.class, deliveryNoteItemId);
    }

    @Override
    public void createItem(final DeliveryNoteItem deliveryNoteItem) {
        create(RESOURCE_ITEMS, Validate.notNull(deliveryNoteItem));
    }

    @Override
    public void updateItem(final DeliveryNoteItem deliveryNoteItem) {
        update(RESOURCE_ITEMS, Validate.notNull(deliveryNoteItem));
    }

    @Override
    public void deleteItem(final int deliveryNoteItemId) {
        delete(RESOURCE_ITEMS, deliveryNoteItemId);
    }

    // DeliveryNoteComment

    @Override
    public List<DeliveryNoteComment> findComments(final int deliveryNoteId,
                                                  final DeliveryNoteCommentFilter deliveryNoteCommentFilter) {

        final Filter filter = new CombinedFilter(deliveryNoteIdFilter(deliveryNoteId), deliveryNoteCommentFilter);
        return getAllPagesFromResource(RESOURCE_COMMENTS, DeliveryNoteComments.class, filter);
    }

    @Override
    public DeliveryNoteComment getCommentById(final int deliveryNoteCommentId) {
        return getById(RESOURCE_COMMENTS, DeliveryNoteComment.class, deliveryNoteCommentId);
    }

    @Override
    public void createComment(final DeliveryNoteComment deliveryNoteComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(deliveryNoteComment));
    }

    @Override
    public void deleteComment(final int deliveryNoteCommentId) {
        delete(RESOURCE_COMMENTS, deliveryNoteCommentId);
    }

    // DeliveryNoteTag

    @Override
    public List<DeliveryNoteTag> getTags(final Integer deliveryNoteId) {
        return getAllPagesFromResource(RESOURCE_TAGS, DeliveryNoteTags.class, deliveryNoteIdFilter(deliveryNoteId));
    }

    @Override
    public DeliveryNoteTag getTagById(final int deliveryNoteTagId) {
        return getById(RESOURCE_TAGS, DeliveryNoteTag.class, deliveryNoteTagId);
    }

    @Override
    public void createTag(final DeliveryNoteTag deliveryNoteTag) {
        create(RESOURCE_TAGS, Validate.notNull(deliveryNoteTag));
    }

    @Override
    public void deleteTag(final int deliveryNoteTagId) {
        delete(RESOURCE_TAGS, Validate.notNull(deliveryNoteTagId));
    }

}
