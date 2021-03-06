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
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.offer.OfferActionKey;
import de.siegmar.billomat4j.domain.offer.OfferComment;
import de.siegmar.billomat4j.domain.offer.OfferCommentFilter;
import de.siegmar.billomat4j.domain.offer.OfferFilter;
import de.siegmar.billomat4j.domain.offer.OfferItem;
import de.siegmar.billomat4j.domain.offer.OfferPdf;
import de.siegmar.billomat4j.domain.offer.OfferTag;

/**
 * @see <a href="http://www.billomat.com/api/angebote/">API Angebote</a>
 * @see <a href="http://www.billomat.com/api/angebote/positionen/">API Angebote/Positionen</a>
 * @see <a href="http://www.billomat.com/api/angebote/kommentare-und-historie-4/">API Angebote/Kommentare</a>
 * @see <a href="http://www.billomat.com/api/angebote/schlagworte/">API Angebote/Schlagworte</a>
 */
public interface OfferService extends
    GenericCustomFieldService,
    GenericTagService<OfferTag>,
    GenericCommentService<OfferActionKey, OfferComment, OfferCommentFilter>,
    GenericItemService<OfferItem> {

    /**
     * @param offerFilter
     *            offer filter, may be {@code null} to find unfiltered
     * @return offers found by filter criteria or an empty list if no offers were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Offer> findOffers(OfferFilter offerFilter);

    /**
     * Gets a offer by its id.
     *
     * @param offerId
     *            the offer's id
     * @return the offer or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Offer getOfferById(int offerId);

    /**
     * Gets a offer by its offer number.
     *
     * @param offerNumber
     *            the offer number, must not be empty / {@code null}
     * @return the offer or {@code null} if not found
     * @throws NullPointerException
     *             if offerNumber is null
     * @throws IllegalArgumentException
     *             if offerNumber is empty
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Offer getOfferByNumber(String offerNumber);

    /**
     * @param offer
     *            the offer to create, must not be {@code null}
     * @throws NullPointerException
     *             if offer is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createOffer(Offer offer);

    /**
     * @param offer
     *            the offer to update, must not be {@code null}
     * @throws NullPointerException
     *             if offer is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateOffer(Offer offer);

    /**
     * @param offerId
     *            the id of the offer to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteOffer(int offerId);

    /**
     * @param offerId
     *            the id of the offer to get the PDF for
     * @return the offer PDF or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    OfferPdf getOfferPdf(int offerId);

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#OPEN}.
     *
     * @param offerId
     *            the id of the offer to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void completeOffer(int offerId, Integer templateId);

    /**
     * @param offerId
     *            the id of the offer to send an email for
     * @param offerEmail
     *            the email configuration
     * @throws NullPointerException
     *             if offerEmail is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void sendOfferViaEmail(int offerId, Email offerEmail);

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#CANCELED}.
     *
     * @param offerId
     *            the id of the offer to update
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void cancelOffer(int offerId);

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#WON}.
     *
     * @param offerId
     *            the id of the offer to update
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void winOffer(int offerId);

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#LOST}.
     *
     * @param offerId
     *            the id of the offer to update
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void loseOffer(int offerId);

    /**
     * Sets the offer status to {@link de.siegmar.billomat4j.domain.offer.OfferStatus#CLEARED}.
     *
     * @param offerId
     *            the id of the offer to update
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void clearOffer(int offerId);

    /**
     * Reverts the status change of {@link #clearOffer(int)}.
     *
     * @param offerId
     *            the id of the offer to update
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void unclearOffer(int offerId);

}
