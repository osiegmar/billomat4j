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

package de.siegmar.billomat4j.recurring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.recurring.Recurring;
import de.siegmar.billomat4j.domain.recurring.RecurringCycle;
import de.siegmar.billomat4j.domain.recurring.RecurringEmailReceiver;
import de.siegmar.billomat4j.domain.recurring.RecurringFilter;
import de.siegmar.billomat4j.domain.recurring.RecurringItem;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.domain.types.RecipientType;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.RecurringService;

public class RecurringServiceIT {

    private final List<Recurring> createdRecurrings = new ArrayList<>();
    private final RecurringService recurringService = ServiceHolder.RECURRING;
    private final ClientService clientService = ServiceHolder.CLIENT;

    // Recurring

    @AfterEach
    public void cleanup() {
        for (final Recurring recurring : createdRecurrings) {
            final int clientId = recurring.getClientId();
            recurringService.deleteRecurring(recurring.getId());
            clientService.deleteClient(clientId);
        }
        createdRecurrings.clear();
    }

    @Test
    public void findAll() {
        assertTrue(recurringService.findRecurrings(null).isEmpty());
        createRecurring(PaymentType.BANK_TRANSFER);
        assertFalse(recurringService.findRecurrings(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final RecurringFilter recurringFilter = new RecurringFilter().byPaymentType(PaymentType.BANK_TRANSFER);
        List<Recurring> recurrings = recurringService.findRecurrings(recurringFilter);
        assertTrue(recurrings.isEmpty());

        final Recurring recurring1 = createRecurring(PaymentType.BANK_TRANSFER);
        createRecurring(PaymentType.BANK_CARD);

        recurrings = recurringService.findRecurrings(recurringFilter);
        assertEquals(1, recurrings.size());
        assertEquals(recurring1.getId(), recurrings.get(0).getId());
    }

    @Test
    public void create() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        assertNotNull(recurring.getId());
    }

    @Test
    public void getById() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        assertEquals(recurring.getId(), recurringService.getRecurringById(recurring.getId()).getId());
    }

    @Test
    public void update() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        recurring.setLabel("Test Label");
        recurringService.updateRecurring(recurring);
        assertEquals("Test Label", recurring.getLabel());
        assertEquals("Test Label", recurringService.getRecurringById(recurring.getId()).getLabel());
    }

    @Test
    public void delete() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);

        final int clientId = recurring.getClientId();
        recurringService.deleteRecurring(recurring.getId());
        clientService.deleteClient(clientId);

        assertNull(recurringService.getRecurringById(recurring.getId()));

        createdRecurrings.remove(recurring);
    }

    @Test
    public void emailReceiver() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);

        final List<RecurringEmailReceiver> recurringEmailReceivers =
                recurringService.getRecurringEmailReceivers(recurring.getId());

        assertTrue(recurringEmailReceivers.isEmpty());

        final RecurringEmailReceiver recurringEmailReceiver = new RecurringEmailReceiver();
        recurringEmailReceiver.setRecurringId(recurring.getId());
        recurringEmailReceiver.setType(RecipientType.Bcc);
        recurringEmailReceiver.setAddress(ServiceHolder.getEmail());

        recurringService.createRecurringEmailReceiver(recurringEmailReceiver);
        assertNotNull(recurringEmailReceiver.getId());

        recurringEmailReceiver.setType(RecipientType.Cc);
        recurringService.updateRecurringEmailReceiver(recurringEmailReceiver);
        assertEquals(RecipientType.Cc,
            recurringService.getRecurringEmailReceiverById(recurringEmailReceiver.getId()).getType());

        recurringService.deleteRecurringEmailReceiver(recurringEmailReceiver.getId());
    }

    private Recurring createRecurring(final PaymentType paymentType) {
        final Client client = new Client();
        client.setName("RecurringServiceTest Client");
        clientService.createClient(client);

        final Recurring recurring = new Recurring();
        recurring.setClientId(client.getId());
        recurring.setNextCreationDate(LocalDate.now().plusYears(1));
        recurring.setCycle(RecurringCycle.YEARLY);
        recurring.setPaymentTypes(paymentType);

        final RecurringItem recurringItem1 = new RecurringItem();
        recurringItem1.setTitle("Recurring Item #1");
        recurringItem1.setUnitPrice(new BigDecimal("11.11"));
        recurringItem1.setQuantity(BigDecimal.ONE);
        recurring.addRecurringItem(recurringItem1);

        final RecurringItem recurringItem2 = new RecurringItem();
        recurringItem2.setTitle("Recurring Item #2");
        recurringItem2.setUnitPrice(new BigDecimal("22.22"));
        recurringItem2.setQuantity(BigDecimal.ONE);
        recurring.addRecurringItem(recurringItem2);

        recurringService.createRecurring(recurring);

        createdRecurrings.add(recurring);

        return recurring;
    }

}
