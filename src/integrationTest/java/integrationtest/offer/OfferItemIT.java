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

package integrationtest.offer;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.offer.OfferItem;
import integrationtest.AbstractItemIT;
import integrationtest.ServiceHolder;

public class OfferItemIT extends AbstractItemIT<OfferItem> {

    public OfferItemIT() {
        setService(ServiceHolder.OFFER);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("OfferItemTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final Offer offer = new Offer();
        offer.setClientId(client.getId());
        ServiceHolder.OFFER.createOffer(offer);

        return offer.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.OFFER.getOfferById(ownerId).orElseThrow().getClientId();
        ServiceHolder.OFFER.deleteOffer(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

    @Override
    protected OfferItem buildItem(final int ownerId) {
        final OfferItem item = new OfferItem();
        item.setOfferId(ownerId);
        return item;
    }

}
