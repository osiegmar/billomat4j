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

import de.siegmar.billomat4j.AbstractPaymentIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePayment;
import de.siegmar.billomat4j.domain.creditnote.CreditNotePaymentFilter;
import de.siegmar.billomat4j.domain.types.PaymentType;


public class CreditNotePaymentIT extends AbstractPaymentIT<CreditNotePayment, CreditNotePaymentFilter> {

    public CreditNotePaymentIT() {
        setService(creditNoteService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("CreditNotePaymentTest Client");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNoteService.createCreditNote(creditNote);
        creditNoteService.completeCreditNote(creditNote.getId(), null);

        return creditNote.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = creditNoteService.getCreditNoteById(ownerId).getClientId();
        creditNoteService.deleteCreditNote(ownerId);
        clientService.deleteClient(clientId);
    }

    @Override
    protected CreditNotePayment buildPayment(final int ownerId) {
        final CreditNotePayment creditNotePayment = new CreditNotePayment();
        creditNotePayment.setCreditNoteId(ownerId);
        return creditNotePayment;
    }

    @Override
    protected CreditNotePaymentFilter buildFilter() {
        return new CreditNotePaymentFilter().byType(PaymentType.COUPON);
    }

}
