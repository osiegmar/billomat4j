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

package de.siegmar.billomat4j.creditnote;

import de.siegmar.billomat4j.AbstractTagIT;
import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteTag;

public class CreditNoteTagIT extends AbstractTagIT<CreditNoteTag> {

    public CreditNoteTagIT() {
        setService(ServiceHolder.CREDITNOTE);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("CreditNoteTagTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        ServiceHolder.CREDITNOTE.createCreditNote(creditNote);
        return creditNote.getId();
    }

    @Override
    protected CreditNoteTag constructTag(final int ownerId) {
        final CreditNoteTag tag = new CreditNoteTag();
        tag.setCreditNoteId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.CREDITNOTE.getCreditNoteById(ownerId).getClientId();
        ServiceHolder.CREDITNOTE.deleteCreditNote(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

}
