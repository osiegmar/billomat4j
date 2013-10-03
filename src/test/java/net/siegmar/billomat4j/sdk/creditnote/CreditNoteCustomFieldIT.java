package net.siegmar.billomat4j.sdk.creditnote;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;


public class CreditNoteCustomFieldIT extends AbstractCustomFieldServiceIT {

    public CreditNoteCustomFieldIT() {
        setService(creditNoteService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("CreditNoteCustomFieldTest");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNote.setIntro("CreditNoteCustomFieldTest");
        creditNoteService.createCreditNote(creditNote);
        return creditNote.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = creditNoteService.getCreditNoteById(ownerId).getClientId();
        creditNoteService.deleteCreditNote(ownerId);
        clientService.deleteClient(clientId);
    }

}
