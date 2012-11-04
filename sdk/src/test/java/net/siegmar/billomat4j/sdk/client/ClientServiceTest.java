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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.client.ClientFilter;

import org.junit.After;
import org.junit.Test;

public class ClientServiceTest extends AbstractServiceTest {

    // Client

    @After
    public void cleanup() {
        // Clean up all Clients
        List<Client> clients = clientService.findClients(null);
        if (!clients.isEmpty()) {
            for (final Client client : clients) {
                clientService.deleteClient(client.getId());
            }

            clients = clientService.findClients(null);
            assertTrue(clients.isEmpty());
        }
    }

    @Test
    public void findAll() {
        // Find
        List<Client> clients = clientService.findClients(null);
        assertTrue(clients.isEmpty());

        // Create #1
        final Client client1 = buildClient("ClientServiceTest Company");
        clientService.createClient(client1);
        assertNotNull(client1.getId());

        // Create #2
        final Client client2 = buildClient("ClientServiceTest Company");
        client2.setName("Test Company2");
        clientService.createClient(client2);
        assertNotNull(client2.getId());

        // Find again
        clients = clientService.findClients(null);
        assertEquals(2, clients.size());
        assertEquals(client1.getId(), clients.get(0).getId());
        assertEquals(client2.getId(), clients.get(1).getId());
    }

    private Client buildClient(final String name) {
        final Client client = new Client();
        client.setName(name);
        client.setCountryCode("DE");
        return client;
    }

    @Test
    public void findByNumber() {
        final Client client = new Client();
        client.setNumberPre("KD");
        client.setNumberLength(5);
        client.setNumber(8);
        clientService.createClient(client);

        assertNotNull(clientService.getClientByNumber("KD00008"));
    }


    @Test
    public void findFiltered() {
        // Find
        final ClientFilter clientFilter = new ClientFilter().byCountryCode("DE");
        List<Client> clients = clientService.findClients(clientFilter);
        assertTrue(clients.isEmpty());

        // Create #1
        final Client client1 = buildClient("ClientServiceTest Company");
        clientService.createClient(client1);
        assertNotNull(client1.getId());

        // Create #2
        final Client client2 = buildClient("ClientServiceTest Company");
        client2.setName("Test Company2");
        client2.setCountryCode("CH");
        clientService.createClient(client2);
        assertNotNull(client2.getId());

        // Find again
        clients = clientService.findClients(clientFilter);
        assertEquals(1, clients.size());
        final Client foundClient = clients.iterator().next();
        assertEquals(client1.getId(), foundClient.getId());
    }

    @Test
    public void getMySelf() {
        final Client mySelf = clientService.getMySelf();
        assertNotNull(mySelf.getId());
    }

    @Test
    public void getById() {
        // Create
        final Client client = buildClient("ClientServiceTest Company");
        clientService.createClient(client);
        assertNotNull(client.getId());

        // Get
        final Client clientById = clientService.getClientById(client.getId());
        assertEquals(client.getId(), clientById.getId());
    }

    @Test
    public void updateWithNewObject() {
        // Create #1
        final Client client = buildClient("ClientServiceTest Company");
        client.setNumber(9999);
        clientService.createClient(client);
        assertNotNull(client.getId());

        // Update
        final Client updateClient = new Client();
        updateClient.setId(client.getId());
        updateClient.setName("Updated Company");
        clientService.updateClient(updateClient);

        assertEquals("Updated Company", updateClient.getName());
        assertEquals("9999", updateClient.getClientNumber());

        // Get
        final Client updatedClient = clientService.getClientById(client.getId());
        assertEquals("Updated Company", updatedClient.getName());
        assertEquals("9999", updatedClient.getClientNumber());
    }

    @Test
    public void updateWithExistingObject() {
        // Create #1
        final Client client = buildClient("ClientServiceTest Company");
        client.setNumber(9999);
        clientService.createClient(client);
        assertNotNull(client.getId());

        // Update
        client.setName("Updated Company");
        clientService.updateClient(client);
        assertEquals("Updated Company", client.getName());
        assertEquals("9999", client.getClientNumber());

        // Get
        final Client updatedClient = clientService.getClientById(client.getId());
        assertEquals("Updated Company", updatedClient.getName());
        assertEquals("9999", updatedClient.getClientNumber());
    }

}
