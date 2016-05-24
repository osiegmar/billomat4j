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
package de.siegmar.billomat4j.sdk.client;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.siegmar.billomat4j.sdk.AbstractServiceIT;
import de.siegmar.billomat4j.sdk.domain.client.Client;
import de.siegmar.billomat4j.sdk.domain.client.Contact;

public class ContactIT extends AbstractServiceIT {

    private Client client;

    @BeforeClass
    public void setupClient() {
        client = new Client();
        client.setName("ContactTest Client");
        client.setCountryCode("DE");
        clientService.createClient(client);
        assertEquals(clientService.findContacts(client.getId()).size(), 0);
    }

    @AfterClass
    public void cleanupClient() {
        clientService.deleteClient(client.getId());
    }

    @Test
    public void test() {
        final Contact contact = new Contact();
        contact.setClientId(client.getId());
        contact.setFirstName("Test");
        contact.setLastName("Contact");
        clientService.createContact(contact);
        assertNotNull(contact.getId());

        final List<Contact> contacts = clientService.findContacts(client.getId());
        assertEquals(contacts.size(), 1);
        assertEquals(contacts.get(0).getFirstName(), "Test");

        final Contact foundContact = clientService.getContact(contacts.get(0).getId());
        assertEquals(foundContact.getFirstName(), "Test");

        clientService.deleteContact(foundContact.getId());
        assertEquals(clientService.findContacts(client.getId()).size(), 0);
    }

}
