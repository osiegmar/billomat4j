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

package de.siegmar.billomat4j.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.client.ClientFilter;
import de.siegmar.billomat4j.domain.client.ClientPropertyValue;
import de.siegmar.billomat4j.domain.client.ClientPropertyValues;
import de.siegmar.billomat4j.domain.client.ClientTag;
import de.siegmar.billomat4j.domain.client.ClientTags;
import de.siegmar.billomat4j.domain.client.Clients;
import de.siegmar.billomat4j.domain.client.Contact;
import de.siegmar.billomat4j.domain.client.Contacts;
import de.siegmar.billomat4j.domain.settings.ClientProperties;
import de.siegmar.billomat4j.domain.settings.ClientProperty;

public class ClientService extends AbstractService
    implements GenericCustomFieldService, GenericPropertyService<ClientProperty,
    ClientPropertyValue>, GenericTagService<ClientTag> {

    private static final String RESOURCE = "clients";
    private static final String PROPERTIES_RESOURCE = "client-properties";
    private static final String ATTRIBUTE_RESOURCE = "client-property-values";
    private static final String TAG_RESOURCE = "client-tags";
    private static final String CONTACT_RESOURCE = "contacts";

    public ClientService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Client

    @Override
    public Optional<String> getCustomFieldValue(final int clientId) {
        return getCustomField(RESOURCE, clientId);
    }

    @Override
    public void setCustomFieldValue(final int clientId, final String value) {
        updateCustomField(RESOURCE, clientId, "client", value);
    }

    /**
     * @return the Billomat account as a client
     * @throws ServiceException if an error occurred while accessing the web service
     * @see <a href="https://www.billomat.com/api/account/">API Account</a>
     */
    public Client getMySelf() {
        return getMySelf(RESOURCE, Client.class);
    }

    /**
     * @param clientFilter client filter, may be {@code null} to find unfiltered
     * @return clients found by filter criteria or an empty list if no clients were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Client> findClients(final ClientFilter clientFilter) {
        return getAllPagesFromResource(RESOURCE, Clients.class, clientFilter);
    }

    /**
     * Gets a client by its id.
     *
     * @param clientId the client's id
     * @return the client
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Client> getClientById(final int clientId) {
        return getById(RESOURCE, Client.class, clientId);
    }

    /**
     * Gets a client by its client number.
     *
     * @param clientNumber the client number, must not be empty / {@code null}
     * @return the client
     * @throws NullPointerException     if clientNumber is null
     * @throws IllegalArgumentException if clientNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<Client> getClientByNumber(final String clientNumber) {
        return single(findClients(new ClientFilter().byClientNumber(Validate.notEmpty(clientNumber))));
    }

    /**
     * @param client the client to create, must not be {@code null}
     * @throws NullPointerException if client is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createClient(final Client client) {
        create(RESOURCE, Validate.notNull(client));
    }

    /**
     * @param client the client to update, must not be {@code null}
     * @throws NullPointerException if client is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateClient(final Client client) {
        update(RESOURCE, Validate.notNull(client));
    }

    /**
     * @param clientId the id of the client to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteClient(final int clientId) {
        delete(RESOURCE, clientId);
    }

    // ClientProperty

    @Override
    public List<ClientProperty> getProperties() {
        return getAllPagesFromResource(PROPERTIES_RESOURCE, ClientProperties.class, null);
    }

    @Override
    public Optional<ClientProperty> getPropertyById(final int clientPropertyId) {
        return getById(PROPERTIES_RESOURCE, ClientProperty.class, clientPropertyId);
    }

    @Override
    public void createProperty(final ClientProperty clientProperty) {
        create(PROPERTIES_RESOURCE, Validate.notNull(clientProperty));
    }

    @Override
    public void updateProperty(final ClientProperty clientProperty) {
        update(PROPERTIES_RESOURCE, Validate.notNull(clientProperty));
    }

    @Override
    public void deleteProperty(final int clientPropertyId) {
        delete(PROPERTIES_RESOURCE, clientPropertyId);
    }

    // ClientPropertyValue

    @Override
    public List<ClientPropertyValue> getPropertyValues(final int clientId) {
        return getAllPagesFromResource(ATTRIBUTE_RESOURCE, ClientPropertyValues.class, clientIdFilter(clientId));
    }

    private GenericFilter clientIdFilter(final Integer clientId) {
        return clientId == null ? null : new GenericFilter("client_id", clientId);
    }

    @Override
    public Optional<ClientPropertyValue> getPropertyValueById(final int clientPropertyValueId) {
        return getById(ATTRIBUTE_RESOURCE, ClientPropertyValue.class, clientPropertyValueId);
    }

    @Override
    public void createPropertyValue(final ClientPropertyValue clientPropertyValue) {
        create(ATTRIBUTE_RESOURCE, Validate.notNull(clientPropertyValue));
    }

    // ClientTag

    @Override
    public List<ClientTag> getTags(final Integer clientId) {
        return getAllPagesFromResource(TAG_RESOURCE, ClientTags.class, clientIdFilter(clientId));
    }

    @Override
    public Optional<ClientTag> getTagById(final int clientTagId) {
        return getById(TAG_RESOURCE, ClientTag.class, clientTagId);
    }

    @Override
    public void createTag(final ClientTag clientTag) {
        create(TAG_RESOURCE, Validate.notNull(clientTag));
    }

    @Override
    public void deleteTag(final int clientTagId) {
        delete(TAG_RESOURCE, clientTagId);
    }

    // Contact

    /**
     * @param clientId the id of the client the contacts belong to
     * @return all contacts for the specified client or an empty list if no contacts were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Contact> findContacts(final int clientId) {
        return getAllPagesFromResource(CONTACT_RESOURCE, Contacts.class, clientIdFilter(clientId));
    }

    /**
     * Gets a contact by its id.
     *
     * @param contactId the contact's id
     * @return the contact
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Contact> getContact(final int contactId) {
        return getById(CONTACT_RESOURCE, Contact.class, contactId);
    }

    /**
     * @param contact the contact to create, must not be {@code null}
     * @throws NullPointerException if contact is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createContact(final Contact contact) {
        create(CONTACT_RESOURCE, Validate.notNull(contact));
    }

    /**
     * @param contact the contact to update, must not be {@code null}
     * @throws NullPointerException if contact is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateContact(final Contact contact) {
        update(CONTACT_RESOURCE, Validate.notNull(contact));
    }

    /**
     * @param contactId the id of the contact to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteContact(final int contactId) {
        delete(CONTACT_RESOURCE, contactId);
    }

}
