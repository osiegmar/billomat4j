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
package net.siegmar.billomat4j.sdk.creditnote;

import net.siegmar.billomat4j.sdk.AbstractTagTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteTag;


public class CreditNoteTagTest extends AbstractTagTest<CreditNoteTag> {

    public CreditNoteTagTest() {
        setService(creditNoteService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("CreditNoteTagTest Client");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNoteService.createCreditNote(creditNote);
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
        final int clientId = creditNoteService.getCreditNoteById(ownerId).getClientId();
        creditNoteService.deleteCreditNote(ownerId);
        clientService.deleteClient(clientId);
    }

}
