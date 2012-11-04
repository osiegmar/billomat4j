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
package net.siegmar.billomat4j.sdk.service;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.client.ClientFilter;
import net.siegmar.billomat4j.sdk.domain.client.ClientPropertyValue;
import net.siegmar.billomat4j.sdk.domain.client.ClientTag;
import net.siegmar.billomat4j.sdk.domain.settings.ClientProperty;

/**
 * @see http://www.billomat.com/api/kunden/
 * @see http://www.billomat.com/api/kunden/attribute/
 * @see http://www.billomat.com/api/kunden/schlagworte/
 * @see http://www.billomat.com/api/einstellungen/kunden-attribute/
 */
public interface ClientService extends
    AbstractCustomFieldService,
    AbstractPropertyService<ClientProperty, ClientPropertyValue>,
    AbstractTagService<ClientTag> {

    /**
     * @see http://www.billomat.com/api/account/
     * @return the Billomat account as a client
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Client getMySelf();

    /**
     * @param clientFilter
     *            client filter, may be {@code null} to find unfiltered
     * @return clients found by filter criteria or an empty list if no clients were found - never {@code null}
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Client> findClients(ClientFilter clientFilter);

    /**
     * Gets a client by its id.
     *
     * @param clientId
     *            the client's id
     * @return the client or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Client getClientById(int clientId);

    /**
     * Gets a client by its client number.
     *
     * @param clientNumber
     *            the client number, must not be empty / {@code null}
     * @return the client or {@code null} if not found
     * @throws NullPointerException
     *             if clientNumber is null
     * @throws IllegalArgumentException
     *             if clientNumber is empty
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Client getClientByNumber(String clientNumber);

    /**
     * @param client
     *            the client to create, must not be {@code null}
     * @throws NullPointerException
     *             if client is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createClient(Client client);

    /**
     * @param client
     *            the client to update, must not be {@code null}
     * @throws NullPointerException
     *             if client is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateClient(Client client);

    /**
     * @param clientId
     *            the id of the client to be deleted
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteClient(int clientId);

}
