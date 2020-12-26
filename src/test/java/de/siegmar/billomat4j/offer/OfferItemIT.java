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

package de.siegmar.billomat4j.offer;

import de.siegmar.billomat4j.AbstractItemIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.offer.OfferItem;

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
