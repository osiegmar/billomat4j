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

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.offer.OfferActionKey;
import de.siegmar.billomat4j.domain.offer.OfferComment;
import de.siegmar.billomat4j.domain.offer.OfferCommentFilter;
import de.siegmar.billomat4j.domain.offer.OfferComments;
import de.siegmar.billomat4j.domain.offer.OfferFilter;
import de.siegmar.billomat4j.domain.offer.OfferItem;
import de.siegmar.billomat4j.domain.offer.OfferItems;
import de.siegmar.billomat4j.domain.offer.OfferPdf;
import de.siegmar.billomat4j.domain.offer.OfferTag;
import de.siegmar.billomat4j.domain.offer.OfferTags;
import de.siegmar.billomat4j.domain.offer.Offers;

public class OfferService extends AbstractService implements GenericCustomFieldService,
    GenericTagService<OfferTag>, GenericCommentService<OfferActionKey, OfferComment, OfferCommentFilter>,
    GenericItemService<OfferItem> {

    private static final String RESOURCE = "offers";
    private static final String RESOURCE_ITEMS = "offer-items";
    private static final String RESOURCE_COMMENTS = "offer-comments";
    private static final String RESOURCE_TAGS = "offer-tags";

    public OfferService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Offer

    @Override
    public Optional<String> getCustomFieldValue(final int offerId) {
        return getCustomField(RESOURCE, offerId);
    }

    @Override
    public void setCustomFieldValue(final int offerId, final String value) {
        updateCustomField(RESOURCE, offerId, "offer", value);
    }

    /**
     * @param offerFilter offer filter, may be {@code null} to find unfiltered
     * @return offers found by filter criteria or an empty list if no offers were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Offer> findOffers(final OfferFilter offerFilter) {
        return getAllPagesFromResource(RESOURCE, Offers.class, offerFilter);
    }

    /**
     * Gets a offer by its id.
     *
     * @param offerId the offer's id
     * @return the offer
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Offer> getOfferById(final int offerId) {
        return getById(RESOURCE, Offer.class, offerId);
    }

    /**
     * Gets a offer by its offer number.
     *
     * @param offerNumber the offer number, must not be empty / {@code null}
     * @return the offer
     * @throws NullPointerException     if offerNumber is null
     * @throws IllegalArgumentException if offerNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<Offer> getOfferByNumber(final String offerNumber) {
        return single(findOffers(new OfferFilter().byOfferNumber(Validate.notEmpty(offerNumber))));
    }

    /**
     * @param offer the offer to create, must not be {@code null}
     * @throws NullPointerException if offer is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createOffer(final Offer offer) {
        create(RESOURCE, Validate.notNull(offer));
    }

    /**
     * @param offer the offer to update, must not be {@code null}
     * @throws NullPointerException if offer is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateOffer(final Offer offer) {
        update(RESOURCE, Validate.notNull(offer));
    }

    /**
     * @param offerId the id of the offer to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteOffer(final int offerId) {
        delete(RESOURCE, offerId);
    }

    /**
     * @param offerId the id of the offer to get the PDF for
     * @return the offer PDF
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<OfferPdf> getOfferPdf(final int offerId) {
        return getPdf(RESOURCE, OfferPdf.class, offerId, null);
    }

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#OPEN}.
     *
     * @param offerId    the id of the offer to update
     * @param templateId the id of the template to use for the resulting document or {@code null}
     *                   if no template should be used
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void completeOffer(final int offerId, final Integer templateId) {
        completeDocument(RESOURCE, offerId, templateId);
    }

    /**
     * @param offerId    the id of the offer to send an email for
     * @param offerEmail the email configuration
     * @throws NullPointerException if offerEmail is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void sendOfferViaEmail(final int offerId, final Email offerEmail) {
        sendEmail(RESOURCE, offerId, offerEmail);
    }

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#CANCELED}.
     *
     * @param offerId the id of the offer to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void cancelOffer(final int offerId) {
        transit(RESOURCE, "cancel", offerId);
    }

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#WON}.
     *
     * @param offerId the id of the offer to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void winOffer(final int offerId) {
        transit(RESOURCE, "win", offerId);
    }

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#LOST}.
     *
     * @param offerId the id of the offer to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void loseOffer(final int offerId) {
        transit(RESOURCE, "lose", offerId);
    }

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#CLEARED}.
     *
     * @param offerId the id of the offer to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void clearOffer(final int offerId) {
        transit(RESOURCE, "clear", offerId);
    }

    /**
     * Reverts the status change of {@link #clearOffer(int)}.
     *
     * @param offerId the id of the offer to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void unclearOffer(final int offerId) {
        transit(RESOURCE, "unclear", offerId);
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
    public Optional<OfferItem> getItemById(final int offerItemId) {
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
    public Optional<OfferComment> getCommentById(final int offerCommentId) {
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
    public Optional<OfferTag> getTagById(final int offerTagId) {
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
