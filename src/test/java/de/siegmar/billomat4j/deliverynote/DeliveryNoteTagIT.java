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

package de.siegmar.billomat4j.deliverynote;

import de.siegmar.billomat4j.AbstractTagIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteTag;


public class DeliveryNoteTagIT extends AbstractTagIT<DeliveryNoteTag> {

    public DeliveryNoteTagIT() {
        setService(deliveryNoteService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("DeliveryNoteTagTest Client");
        clientService.createClient(client);

        final DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setClientId(client.getId());
        deliveryNoteService.createDeliveryNote(deliveryNote);
        return deliveryNote.getId();
    }

    @Override
    protected DeliveryNoteTag constructTag(final int ownerId) {
        final DeliveryNoteTag tag = new DeliveryNoteTag();
        tag.setDeliveryNoteId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = deliveryNoteService.getDeliveryNoteById(ownerId).getClientId();
        deliveryNoteService.deleteDeliveryNote(ownerId);
        clientService.deleteClient(clientId);
    }

}
