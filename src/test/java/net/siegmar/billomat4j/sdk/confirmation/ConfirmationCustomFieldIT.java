package net.siegmar.billomat4j.sdk.confirmation;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.confirmation.Confirmation;


public class ConfirmationCustomFieldIT extends AbstractCustomFieldServiceIT {

    public ConfirmationCustomFieldIT() {
        setService(confirmationService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("ConfirmationCustomFieldTest");
        clientService.createClient(client);

        final Confirmation confirmation = new Confirmation();
        confirmation.setClientId(client.getId());
        confirmation.setIntro("ConfirmationCustomFieldTest");
        confirmationService.createConfirmation(confirmation);
        return confirmation.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = confirmationService.getConfirmationById(ownerId).getClientId();
        confirmationService.deleteConfirmation(ownerId);
        clientService.deleteClient(clientId);
    }

}
