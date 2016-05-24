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

import de.siegmar.billomat4j.sdk.domain.offer.OfferComments;
import de.siegmar.billomat4j.sdk.domain.offer.OfferFilter;
import de.siegmar.billomat4j.sdk.domain.offer.OfferTags;
import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.Filter;
import de.siegmar.billomat4j.sdk.domain.offer.Offer;
import de.siegmar.billomat4j.sdk.domain.offer.OfferComment;
import de.siegmar.billomat4j.sdk.domain.offer.OfferCommentFilter;
import de.siegmar.billomat4j.sdk.domain.offer.OfferItem;
import de.siegmar.billomat4j.sdk.domain.offer.OfferItems;
import de.siegmar.billomat4j.sdk.domain.offer.OfferPdf;
import de.siegmar.billomat4j.sdk.domain.offer.OfferTag;
import de.siegmar.billomat4j.sdk.domain.offer.Offers;
import de.siegmar.billomat4j.sdk.service.OfferService;

import org.apache.commons.lang3.Validate;

public class OfferServiceImpl extends AbstractService implements OfferService {

    private static final String RESOURCE = "offers";
    private static final String RESOURCE_ITEMS = "offer-items";
    private static final String RESOURCE_COMMENTS = "offer-comments";
    private static final String RESOURCE_TAGS = "offer-tags";

    public OfferServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Offer

    @Override
    public String getCustomFieldValue(final int offerId) {
        return getCustomField(RESOURCE, offerId);
    }

    @Override
    public void setCustomFieldValue(final int offerId, final String value) {
        updateCustomField(RESOURCE, offerId, "offer", value);
    }

    @Override
    public List<Offer> findOffers(final OfferFilter offerFilter) {
        return getAllPagesFromResource(RESOURCE, Offers.class, offerFilter);
    }

    @Override
    public Offer getOfferById(final int id) {
        return getById(RESOURCE, Offer.class, id);
    }

    @Override
    public Offer getOfferByNumber(final String offerNumber) {
        return single(findOffers(new OfferFilter().byOfferNumber(Validate.notEmpty(offerNumber))));
    }

    @Override
    public void createOffer(final Offer offer) {
        create(RESOURCE, Validate.notNull(offer));
    }

    @Override
    public void updateOffer(final Offer offer) {
        update(RESOURCE, Validate.notNull(offer));
    }

    @Override
    public void deleteOffer(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public OfferPdf getOfferPdf(final int id) {
        return getPdf(RESOURCE, OfferPdf.class, id, null);
    }

    @Override
    public void completeOffer(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void sendOfferViaEmail(final int offerId, final Email offerEmail) {
        sendEmail(RESOURCE, offerId, offerEmail);
    }

    @Override
    public void cancelOffer(final int id) {
        transit(RESOURCE, "cancel", id);
    }

    @Override
    public void winOffer(final int id) {
        transit(RESOURCE, "win", id);
    }

    @Override
    public void loseOffer(final int id) {
        transit(RESOURCE, "lose", id);
    }

    @Override
    public void clearOffer(final int id) {
        transit(RESOURCE, "clear", id);
    }

    @Override
    public void unclearOffer(final int id) {
        transit(RESOURCE, "unclear", id);
    }

    // OfferItem

    @Override
    public List<OfferItem> getItems(final int offerId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, OfferItems.class, offerIdFilter(offerId));
    }

    private GenericFilter offerIdFilter(final Integer offerId) {
        return offerId == null ? null : new GenericFilter("offer_id", offerId);
    }

    @Override
    public OfferItem getItemById(final int offerItemId) {
        return getById(RESOURCE_ITEMS, OfferItem.class, offerItemId);
    }

    @Override
    public void createItem(final OfferItem offerItem) {
        create(RESOURCE_ITEMS, Validate.notNull(offerItem));
    }

    @Override
    public void updateItem(final OfferItem offerItem) {
        update(RESOURCE_ITEMS, Validate.notNull(offerItem));
    }

    @Override
    public void deleteItem(final int offerItemId) {
        delete(RESOURCE_ITEMS, offerItemId);
    }

    // OfferComment

    @Override
    public List<OfferComment> findComments(final int offerId, final OfferCommentFilter offerCommentFilter) {
        final Filter filter = new CombinedFilter(offerIdFilter(offerId), offerCommentFilter);
        return getAllPagesFromResource(RESOURCE_COMMENTS, OfferComments.class, filter);
    }

    @Override
    public OfferComment getCommentById(final int offerCommentId) {
        return getById(RESOURCE_COMMENTS, OfferComment.class, offerCommentId);
    }

    @Override
    public void createComment(final OfferComment offerComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(offerComment));
    }

    @Override
    public void deleteComment(final int offerCommentId) {
        delete(RESOURCE_COMMENTS, offerCommentId);
    }

    // OfferTag

    @Override
    public List<OfferTag> getTags(final Integer offerId) {
        return getAllPagesFromResource(RESOURCE_TAGS, OfferTags.class, offerIdFilter(offerId));
    }

    @Override
    public OfferTag getTagById(final int offerTagId) {
        return getById(RESOURCE_TAGS, OfferTag.class, offerTagId);
    }

    @Override
    public void createTag(final OfferTag offerTag) {
        create(RESOURCE_TAGS, Validate.notNull(offerTag));
    }

    @Override
    public void deleteTag(final int offerTagId) {
        delete(RESOURCE_TAGS, offerTagId);
    }

}
