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

import java.util.List;
import java.util.Optional;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteActionKey;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteComment;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteCommentFilter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteComments;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteFilter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteItem;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteItems;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNotePdf;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteTag;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteTags;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNotes;
import lombok.NonNull;

public class DeliveryNoteService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<DeliveryNoteTag>,
    GenericCommentService<DeliveryNoteActionKey, DeliveryNoteComment, DeliveryNoteCommentFilter>,
    GenericItemService<DeliveryNoteItem> {

    private static final String RESOURCE = "delivery-notes";
    private static final String RESOURCE_ITEMS = "delivery-note-items";
    private static final String RESOURCE_COMMENTS = "delivery-note-comments";
    private static final String RESOURCE_TAGS = "delivery-note-tags";

    public DeliveryNoteService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // DeliveryNote

    @Override
    public Optional<String> getCustomFieldValue(final int deliveryNoteId) {
        return getCustomField(RESOURCE, deliveryNoteId);
    }

    @Override
    public void setCustomFieldValue(final int deliveryNoteId, final String value) {
        updateCustomField(RESOURCE, deliveryNoteId, "delivery-note", value);
    }

    public List<DeliveryNote> findDeliveryNotes(final DeliveryNoteFilter deliveryNoteFilter) {
        return getAllPagesFromResource(RESOURCE, DeliveryNotes.class, deliveryNoteFilter);
    }

    public Optional<DeliveryNote> getDeliveryNoteById(final int deliveryNoteId) {
        return getById(RESOURCE, DeliveryNote.class, deliveryNoteId);
    }

    public Optional<DeliveryNote> getDeliveryNoteByNumber(@NonNull final String deliveryNoteNumber) {
        if (deliveryNoteNumber.isEmpty()) {
            throw new IllegalArgumentException("deliveryNoteNumber must not be empty");
        }
        return single(findDeliveryNotes(new DeliveryNoteFilter().byDeliveryNoteNumber(deliveryNoteNumber)));
    }

    public void createDeliveryNote(@NonNull final DeliveryNote deliveryNote) {
        create(RESOURCE, deliveryNote);
    }

    public void updateDeliveryNote(@NonNull final DeliveryNote deliveryNote) {
        update(RESOURCE, deliveryNote);
    }

    public void deleteDeliveryNote(final int deliveryNoteId) {
        delete(RESOURCE, deliveryNoteId);
    }

    public Optional<DeliveryNotePdf> getDeliveryNotePdf(final int deliveryNoteId) {
        return getPdf(RESOURCE, DeliveryNotePdf.class, deliveryNoteId, null);
    }

    public void completeDeliveryNote(final int deliveryNoteId, final Integer templateId) {
        completeDocument(RESOURCE, deliveryNoteId, templateId);
    }

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
    public Optional<DeliveryNoteItem> getItemById(final int deliveryNoteItemId) {
        return getById(RESOURCE_ITEMS, DeliveryNoteItem.class, deliveryNoteItemId);
    }

    @Override
    public void createItem(@NonNull final DeliveryNoteItem deliveryNoteItem) {
        create(RESOURCE_ITEMS, deliveryNoteItem);
    }

    @Override
    public void updateItem(@NonNull final DeliveryNoteItem deliveryNoteItem) {
        update(RESOURCE_ITEMS, deliveryNoteItem);
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
    public Optional<DeliveryNoteComment> getCommentById(final int deliveryNoteCommentId) {
        return getById(RESOURCE_COMMENTS, DeliveryNoteComment.class, deliveryNoteCommentId);
    }

    @Override
    public void createComment(@NonNull final DeliveryNoteComment deliveryNoteComment) {
        create(RESOURCE_COMMENTS, deliveryNoteComment);
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
    public Optional<DeliveryNoteTag> getTagById(final int deliveryNoteTagId) {
        return getById(RESOURCE_TAGS, DeliveryNoteTag.class, deliveryNoteTagId);
    }

    @Override
    public void createTag(@NonNull final DeliveryNoteTag deliveryNoteTag) {
        create(RESOURCE_TAGS, deliveryNoteTag);
    }

    @Override
    public void deleteTag(final int deliveryNoteTagId) {
        delete(RESOURCE_TAGS, deliveryNoteTagId);
    }

}
