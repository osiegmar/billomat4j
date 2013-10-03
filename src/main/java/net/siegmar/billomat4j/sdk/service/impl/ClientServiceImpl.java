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
package net.siegmar.billomat4j.sdk.service.impl;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.client.ClientFilter;
import net.siegmar.billomat4j.sdk.domain.client.ClientPropertyValue;
import net.siegmar.billomat4j.sdk.domain.client.ClientPropertyValues;
import net.siegmar.billomat4j.sdk.domain.client.ClientTag;
import net.siegmar.billomat4j.sdk.domain.client.ClientTags;
import net.siegmar.billomat4j.sdk.domain.client.Clients;
import net.siegmar.billomat4j.sdk.domain.settings.ClientProperties;
import net.siegmar.billomat4j.sdk.domain.settings.ClientProperty;
import net.siegmar.billomat4j.sdk.service.ClientService;

import org.apache.commons.lang3.Validate;

public class ClientServiceImpl extends AbstractService implements ClientService {

    private static final String RESOURCE = "clients";
    private static final String PROPERTIES_RESOURCE = "client-properties";
    private static final String ATTRIBUTE_RESOURCE = "client-property-values";
    private static final String TAG_RESOURCE = "client-tags";

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

}
