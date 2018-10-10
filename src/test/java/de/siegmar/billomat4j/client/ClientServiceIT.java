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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import de.siegmar.billomat4j.AbstractServiceIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.client.ClientFilter;

public class ClientServiceIT extends AbstractServiceIT {

    private final List<Client> createdClients = new ArrayList<>();

    // Client

    @AfterMethod
    public void cleanup() {
        for (final Client client : createdClients) {
            clientService.deleteClient(client.getId());
        }
        createdClients.clear();
    }

    @Test
    public void findAll() {
        // Find
        List<Client> clients = clientService.findClients(null);
        assertTrue(clients.isEmpty());

        // Create #1
        final Client client1 = buildClient("ClientServiceTest Company");
        createClient(client1);
        assertNotNull(client1.getId());

        // Create #2
        final Client client2 = buildClient("ClientServiceTest Company");
        client2.setName("Test Company2");
        createClient(client2);
        assertNotNull(client2.getId());

        // Find again
        clients = clientService.findClients(null);
        assertEquals(clients.size(), 2);
        assertEquals(clients.get(0).getId(), client1.getId());
        assertEquals(clients.get(1).getId(), client2.getId());
    }

    private Client buildClient(final String name) {
        final Client client = new Client();
        client.setName(name);
        client.setCountryCode("DE");
        return client;
    }

    private void createClient(final Client client) {
        clientService.createClient(client);
        createdClients.add(client);
    }

    @Test
    public void findByNumber() {
        final Client client = new Client();
        client.setNumberPre("KD");
        client.setNumberLength(5);
        client.setNumber(8);
        createClient(client);

        assertNotNull(clientService.getClientByNumber("KD00008"));
    }


    @Test
    public void findFiltered() {
        // Find
        final ClientFilter clientFilter = new ClientFilter().byCountryCode("AT");
        List<Client> clients = clientService.findClients(clientFilter);
        assertTrue(clients.isEmpty());

        // Create #1
        final Client client1 = buildClient("ClientServiceTest Company1");
        client1.setCountryCode("AT");
        createClient(client1);
        assertNotNull(client1.getId());

        // Create #2
        final Client client2 = buildClient("ClientServiceTest Company2");
        client2.setCountryCode("CH");
        createClient(client2);
        assertNotNull(client2.getId());

        // Find again
        clients = clientService.findClients(clientFilter);
        assertEquals(clients.size(), 1);
        assertEquals(clients.get(0).getId(), client1.getId());
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
        createClient(client);
        assertNotNull(client.getId());

        // Get
        final Client clientById = clientService.getClientById(client.getId());
        assertEquals(clientById.getId(), client.getId());
    }

    @Test
    public void updateWithNewObject() {
        // Create #1
        final Client client = buildClient("ClientServiceTest Company");
        client.setNumber(9999);
        createClient(client);
        assertNotNull(client.getId());

        // Update
        final Client updateClient = new Client();
        updateClient.setId(client.getId());
        updateClient.setName("Updated Company");
        clientService.updateClient(updateClient);

        assertEquals(updateClient.getName(), "Updated Company");
        assertEquals(updateClient.getClientNumber(), "9999");

        // Get
        final Client updatedClient = clientService.getClientById(client.getId());
        assertEquals(updatedClient.getName(), "Updated Company");
        assertEquals(updatedClient.getClientNumber(), "9999");
    }

    @Test
    public void updateWithExistingObject() {
        // Create #1
        final Client client = buildClient("ClientServiceTest Company");
        client.setNumber(9999);
        createClient(client);
        assertNotNull(client.getId());

        // Update
        client.setName("Updated Company");
        clientService.updateClient(client);
        assertEquals(client.getName(), "Updated Company");
        assertEquals(client.getClientNumber(), "9999");

        // Get
        final Client updatedClient = clientService.getClientById(client.getId());
        assertEquals(updatedClient.getName(), "Updated Company");
        assertEquals(updatedClient.getClientNumber(), "9999");
    }

    @Test
    public void archiveCustomer() {
        // Create #1
        final Client client = buildClient("ClientServiceTest Company");
        client.setNumber(9999);
        createClient(client);
        assertNotNull(client.getId());
        assertFalse(client.getArchived());

        // Update
        client.setArchived(true);
        clientService.updateClient(client);
        assertTrue(client.getArchived());
    }

    @Test
    public void setSepaMandate() {
        final String sepaMandateNr = "DRSTE3452TZERTS89ZUTZBVS67";
        final LocalDate sepaDate = LocalDate.of(2018, 10, 10);
        final Client client = buildClient("Sepa Company");
        client.setSepaMandate(sepaMandateNr);
        client.setSepaMandateDate(sepaDate);
        clientService.createClient(client);
        assertNotNull(client.getId());
    }

}
