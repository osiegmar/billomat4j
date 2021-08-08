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

package de.siegmar.billomat4j.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.client.Contact;
import de.siegmar.billomat4j.service.impl.ClientService;

public class ContactIT {

    private final ClientService clientService = ServiceHolder.CLIENT;
    private final List<Client> createdClients = new ArrayList<>();

    @AfterEach
    public void cleanupClient() {
        for (final Client client : createdClients) {
            clientService.deleteClient(client.getId());
        }
    }

    @Test
    public void test() {
        final Client client = createClient();

        final Contact contact = new Contact();
        contact.setClientId(client.getId());
        contact.setFirstName("Test");
        contact.setLastName("Contact");
        clientService.createContact(contact);
        assertNotNull(contact.getId());

        final List<Contact> contacts = clientService.findContacts(client.getId());
        assertEquals(1, contacts.size());
        assertEquals("Test", contacts.get(0).getFirstName());

        final Contact foundContact = clientService.getContact(contacts.get(0).getId()).orElseThrow();
        assertEquals("Test", foundContact.getFirstName());

        clientService.deleteContact(foundContact.getId());
        assertEquals(0, clientService.findContacts(client.getId()).size());
    }

    private Client createClient() {
        final Client client = new Client();
        client.setName("ContactTest Client");
        client.setCountryCode("DE");
        clientService.createClient(client);
        createdClients.add(client);
        assertEquals(0, clientService.findContacts(client.getId()).size());
        return client;
    }

}
