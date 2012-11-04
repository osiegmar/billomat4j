package net.siegmar.billomat4j.sdk.recurring;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.recurring.Recurring;


public class RecurringCustomFieldTest extends AbstractCustomFieldServiceTest {

    public RecurringCustomFieldTest() {
        setService(recurringService);
    }

    @Override
    protected int buildOwner() {
        final Client client = new Client();
        client.setName("RecurringCustomFieldTest");
        clientService.createClient(client);

        final Recurring recurring = new Recurring();
        recurring.setClientId(client.getId());
        recurring.setIntro("RecurringCustomFieldTest");
        recurringService.createRecurring(recurring);
        return recurring.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = recurringService.getRecurringById(ownerId).getClientId();
        recurringService.deleteRecurring(ownerId);
        clientService.deleteClient(clientId);
    }

}
