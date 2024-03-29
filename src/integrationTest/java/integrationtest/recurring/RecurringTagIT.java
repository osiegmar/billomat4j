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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package integrationtest.recurring;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.recurring.Recurring;
import de.siegmar.billomat4j.domain.recurring.RecurringTag;
import integrationtest.AbstractTagIT;
import integrationtest.ServiceHolder;

public class RecurringTagIT extends AbstractTagIT<RecurringTag> {

    public RecurringTagIT() {
        setService(ServiceHolder.RECURRING);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("RecurringTagTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final Recurring recurring = new Recurring();
        recurring.setClientId(client.getId());
        ServiceHolder.RECURRING.createRecurring(recurring);
        return recurring.getId();
    }

    @Override
    protected RecurringTag constructTag(final int ownerId) {
        final RecurringTag tag = new RecurringTag();
        tag.setRecurringId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.RECURRING.getRecurringById(ownerId).orElseThrow().getClientId();
        ServiceHolder.RECURRING.deleteRecurring(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

}
