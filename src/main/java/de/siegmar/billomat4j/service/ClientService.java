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
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.service;

import java.util.List;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.client.ClientFilter;
import de.siegmar.billomat4j.domain.client.ClientPropertyValue;
import de.siegmar.billomat4j.domain.client.ClientTag;
import de.siegmar.billomat4j.domain.client.Contact;
import de.siegmar.billomat4j.domain.settings.ClientProperty;

/**
 * @see <a href="http://www.billomat.com/api/kunden/">API Kunden</a>
 * @see <a href="http://www.billomat.com/api/kunden/attribute/">API Kunden/Attribute</a>
 * @see <a href="http://www.billomat.com/api/kunden/schlagworte/">API Kunden/Schlagworte</a>
 * @see <a href="http://www.billomat.com/api/einstellungen/kunden-attribute/">API Kunden/Attribute/Einstellungen</a>
 * @see <a href="http://www.billomat.com/api/kunden/kontakte">API Kunden/Kontakte</a>
 */
public interface ClientService extends
    GenericCustomFieldService,
    GenericPropertyService<ClientProperty, ClientPropertyValue>,
    GenericTagService<ClientTag> {

    /**
     * @see <a href="http://www.billomat.com/api/account/">API Account</a>
     * @return the Billomat account as a client
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Client getMySelf();

    /**
     * @param clientFilter
     *            client filter, may be {@code null} to find unfiltered
     * @return clients found by filter criteria or an empty list if no clients were found - never {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Client> findClients(ClientFilter clientFilter);

    /**
     * Gets a client by its id.
     *
     * @param clientId
     *            the client's id
     * @return the client or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
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
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Client getClientByNumber(String clientNumber);

    /**
     * @param client
     *            the client to create, must not be {@code null}
     * @throws NullPointerException
     *             if client is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createClient(Client client);

    /**
     * @param client
     *            the client to update, must not be {@code null}
     * @throws NullPointerException
     *             if client is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateClient(Client client);

    /**
     * @param clientId
     *            the id of the client to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteClient(int clientId);

    /**
     * @param clientId
     *            the id of the client the contacts belong to
     * @return all contacts for the specified client or an empty list if no contacts were found - never {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Contact> findContacts(int clientId);

    /**
     * Gets a contact by its id.
     *
     * @param contactId
     *            the contact's id
     * @return the contact or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Contact getContact(int contactId);

    /**
     * @param contact
     *            the contact to create, must not be {@code null}
     * @throws NullPointerException
     *             if contact is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createContact(Contact contact);

    /**
     * @param contact
     *            the contact to update, must not be {@code null}
     * @throws NullPointerException
     *             if contact is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateContact(Contact contact);

    /**
     * @param contactId
     *            the id of the contact to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteContact(int contactId);

}
