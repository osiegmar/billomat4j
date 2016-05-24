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
package de.siegmar.billomat4j.sdk.confirmation;

import de.siegmar.billomat4j.sdk.domain.client.Client;
import de.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import de.siegmar.billomat4j.sdk.domain.confirmation.Confirmation;

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
