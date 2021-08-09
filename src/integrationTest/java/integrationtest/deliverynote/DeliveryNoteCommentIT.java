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

package integrationtest.deliverynote;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteActionKey;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteComment;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteCommentFilter;
import integrationtest.AbstractCommentIT;
import integrationtest.ServiceHolder;

public class DeliveryNoteCommentIT
    extends AbstractCommentIT<DeliveryNoteActionKey, DeliveryNoteComment, DeliveryNoteCommentFilter> {

    public DeliveryNoteCommentIT() {
        setService(ServiceHolder.DELIVERYNOTE);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("DeliveryNoteCommentTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setClientId(client.getId());
        ServiceHolder.DELIVERYNOTE.createDeliveryNote(deliveryNote);

        return deliveryNote.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.DELIVERYNOTE.getDeliveryNoteById(ownerId).orElseThrow().getClientId();
        ServiceHolder.DELIVERYNOTE.deleteDeliveryNote(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

    @Override
    protected DeliveryNoteComment buildComment(final int ownerId) {
        final DeliveryNoteComment comment = new DeliveryNoteComment();
        comment.setDeliveryNoteId(ownerId);
        return comment;
    }

    @Override
    protected DeliveryNoteActionKey buildActionKey() {
        return DeliveryNoteActionKey.CREATE;
    }

    @Override
    protected DeliveryNoteCommentFilter buildFilter() {
        return new DeliveryNoteCommentFilter().byActionKeys(DeliveryNoteActionKey.CANCEL);
    }

}
