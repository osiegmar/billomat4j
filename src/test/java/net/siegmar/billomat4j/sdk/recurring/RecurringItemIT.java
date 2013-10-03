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
package net.siegmar.billomat4j.sdk.recurring;

import net.siegmar.billomat4j.sdk.AbstractItemIT;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.recurring.Recurring;
import net.siegmar.billomat4j.sdk.domain.recurring.RecurringItem;

public class RecurringItemIT extends AbstractItemIT<RecurringItem> {

    public RecurringItemIT() {
        setService(recurringService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("RecurringItemTest Client");
        clientService.createClient(client);

        final Recurring recurring = new Recurring();
        recurring.setClientId(client.getId());
        recurringService.createRecurring(recurring);

        return recurring.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = recurringService.getRecurringById(ownerId).getClientId();
        recurringService.deleteRecurring(ownerId);
        clientService.deleteClient(clientId);
    }

    @Override
    protected RecurringItem buildItem(final int ownerId) {
        final RecurringItem item = new RecurringItem();
        item.setRecurringId(ownerId);
        return item;
    }

}
