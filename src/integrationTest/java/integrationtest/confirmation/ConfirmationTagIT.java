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

package integrationtest.confirmation;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationTag;
import integrationtest.AbstractTagIT;
import integrationtest.ServiceHolder;

public class ConfirmationTagIT extends AbstractTagIT<ConfirmationTag> {

    public ConfirmationTagIT() {
        setService(ServiceHolder.CONFIRMATION);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("ConfirmationTagTest Client");
        ServiceHolder.CLIENT.createClient(client);

        final Confirmation confirmation = new Confirmation();
        confirmation.setClientId(client.getId());
        ServiceHolder.CONFIRMATION.createConfirmation(confirmation);
        return confirmation.getId();
    }

    @Override
    protected ConfirmationTag constructTag(final int ownerId) {
        final ConfirmationTag tag = new ConfirmationTag();
        tag.setConfirmationId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = ServiceHolder.CONFIRMATION.getConfirmationById(ownerId).orElseThrow().getClientId();
        ServiceHolder.CONFIRMATION.deleteConfirmation(ownerId);
        ServiceHolder.CLIENT.deleteClient(clientId);
    }

}
