package net.siegmar.billomat4j.sdk.client;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;


public class ClientCustomFieldTest extends AbstractCustomFieldServiceTest {

    public ClientCustomFieldTest() {
        setService(clientService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("ClientCustomFieldTest");
        clientService.createClient(client);
        return client.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        clientService.deleteClient(ownerId);
    }

}
