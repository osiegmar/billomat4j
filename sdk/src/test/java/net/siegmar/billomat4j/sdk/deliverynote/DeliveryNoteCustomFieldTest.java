package net.siegmar.billomat4j.sdk.deliverynote;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;


public class DeliveryNoteCustomFieldTest extends AbstractCustomFieldServiceTest {

    public DeliveryNoteCustomFieldTest() {
        setService(deliveryNoteService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("DeliveryNoteCustomFieldTest");
        clientService.createClient(client);

        final DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setClientId(client.getId());
        deliveryNote.setIntro("DeliveryNoteCustomFieldTest");
        deliveryNoteService.createDeliveryNote(deliveryNote);
        return deliveryNote.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = deliveryNoteService.getDeliveryNoteById(ownerId).getClientId();
        deliveryNoteService.deleteDeliveryNote(ownerId);
        clientService.deleteClient(clientId);
    }

}
