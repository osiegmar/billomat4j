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
package net.siegmar.billomat4j.sdk.offer;

import net.siegmar.billomat4j.sdk.AbstractCommentIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.offer.Offer;
import net.siegmar.billomat4j.sdk.domain.offer.OfferActionKey;
import net.siegmar.billomat4j.sdk.domain.offer.OfferComment;
import net.siegmar.billomat4j.sdk.domain.offer.OfferCommentFilter;

public class OfferCommentIT extends AbstractCommentIT<OfferActionKey, OfferComment, OfferCommentFilter> {

    public OfferCommentIT() {
        setService(offerService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("OfferCommentTest Client");
        clientService.createClient(client);

        final Offer offer = new Offer();
        offer.setClientId(client.getId());
        offerService.createOffer(offer);

        return offer.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = offerService.getOfferById(ownerId).getClientId();
        offerService.deleteOffer(ownerId);
        clientService.deleteClient(clientId);
    }

    @Override
    protected OfferComment buildComment(final int ownerId) {
        final OfferComment comment = new OfferComment();
        comment.setOfferId(ownerId);
        return comment;
    }

    @Override
    protected OfferActionKey buildActionKey() {
        return OfferActionKey.CREATE;
    }

    @Override
    protected OfferCommentFilter buildFilter() {
        return new OfferCommentFilter().byActionKeys(OfferActionKey.CANCEL);
    }

}
