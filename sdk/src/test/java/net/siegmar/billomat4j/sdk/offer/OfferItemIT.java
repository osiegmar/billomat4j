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

import net.siegmar.billomat4j.sdk.AbstractItemIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.offer.Offer;
import net.siegmar.billomat4j.sdk.domain.offer.OfferItem;


public class OfferItemIT extends AbstractItemIT<OfferItem> {

    public OfferItemIT() {
        setService(offerService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("OfferItemTest Client");
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
    protected OfferItem buildItem(final int ownerId) {
        final OfferItem item = new OfferItem();
        item.setOfferId(ownerId);
        return item;
    }

}
