package net.siegmar.billomat4j.sdk.offer;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.offer.Offer;


public class OfferCustomFieldTest extends AbstractCustomFieldServiceTest {

    public OfferCustomFieldTest() {
        setService(offerService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("OfferCustomFieldTest");
        clientService.createClient(client);

        final Offer offer = new Offer();
        offer.setClientId(client.getId());
        offer.setIntro("OfferCustomFieldTest");
        offerService.createOffer(offer);
        return offer.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = offerService.getOfferById(ownerId).getClientId();
        offerService.deleteOffer(ownerId);
        clientService.deleteClient(clientId);
    }

}
