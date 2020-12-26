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

package de.siegmar.billomat4j.service.impl;

import java.util.List;

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
import de.siegmar.billomat4j.service.ClientService;

public class ClientServiceImpl extends AbstractService implements ClientService {

    private static final String RESOURCE = "clients";
    private static final String PROPERTIES_RESOURCE = "client-properties";
    private static final String ATTRIBUTE_RESOURCE = "client-property-values";
    private static final String TAG_RESOURCE = "client-tags";
    private static final String CONTACT_RESOURCE = "contacts";

    public ClientServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Client

    @Override
    public String getCustomFieldValue(final int clientId) {
        return getCustomField(RESOURCE, clientId);
    }

    @Override
    public void setCustomFieldValue(final int clientId, final String value) {
        updateCustomField(RESOURCE, clientId, "client", value);
    }

    @Override
    public Client getMySelf() {
        return getMySelf(RESOURCE, Client.class);
    }

    @Override
    public List<Client> findClients(final ClientFilter clientFilter) {
        return getAllPagesFromResource(RESOURCE, Clients.class, clientFilter);
    }

    @Override
    public Client getClientById(final int id) {
        return getById(RESOURCE, Client.class, id);
    }

    @Override
    public Client getClientByNumber(final String clientNumber) {
        return single(findClients(new ClientFilter().byClientNumber(Validate.notEmpty(clientNumber))));
    }

    @Override
    public void createClient(final Client client) {
        create(RESOURCE, Validate.notNull(client));
    }

    @Override
    public void updateClient(final Client client) {
        update(RESOURCE, Validate.notNull(client));
    }

    @Override
    public void deleteClient(final int id) {
        delete(RESOURCE, id);
    }

    // ClientProperty

    @Override
    public List<ClientProperty> getProperties() {
        return getAllPagesFromResource(PROPERTIES_RESOURCE, ClientProperties.class, null);
    }

    @Override
    public ClientProperty getPropertyById(final int clientPropertyId) {
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
    public ClientPropertyValue getPropertyValueById(final int clientPropertyValueId) {
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
    public ClientTag getTagById(final int clientTagId) {
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

    @Override
    public List<Contact> findContacts(final int clientId) {
        return getAllPagesFromResource(CONTACT_RESOURCE, Contacts.class, clientIdFilter(clientId));
    }

    @Override
    public Contact getContact(final int contactId) {
        return getById(CONTACT_RESOURCE, Contact.class, contactId);
    }

    @Override
    public void createContact(final Contact contact) {
        create(CONTACT_RESOURCE, Validate.notNull(contact));
    }

    @Override
    public void updateContact(final Contact contact) {
        update(CONTACT_RESOURCE, Validate.notNull(contact));
    }

    @Override
    public void deleteContact(final int contactId) {
        delete(CONTACT_RESOURCE, contactId);
    }

}
