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
package net.siegmar.billomat4j.sdk.client;

import net.siegmar.billomat4j.sdk.AbstractPropertyTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.client.ClientPropertyValue;
import net.siegmar.billomat4j.sdk.domain.settings.ClientProperty;


public class ClientPropertyTest extends AbstractPropertyTest<ClientProperty, ClientPropertyValue> {

    public ClientPropertyTest() {
        setService(clientService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("ClientPropertyTest Client");
        clientService.createClient(client);
        return client.getId();
    }

    @Override
    protected ClientProperty buildProperty() {
        return new ClientProperty();
    }

    @Override
    protected ClientPropertyValue buildPropertyValue(final int ownerId, final int propertyId) {
        final ClientPropertyValue clientPropertyValue = new ClientPropertyValue();
        clientPropertyValue.setClientId(ownerId);
        clientPropertyValue.setClientPropertyId(propertyId);
        return clientPropertyValue;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        clientService.deleteClient(ownerId);
    }

}
