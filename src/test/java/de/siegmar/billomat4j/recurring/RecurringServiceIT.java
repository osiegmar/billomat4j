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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import de.siegmar.billomat4j.AbstractServiceIT;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.recurring.Recurring;
import de.siegmar.billomat4j.domain.recurring.RecurringCycle;
import de.siegmar.billomat4j.domain.recurring.RecurringEmailReceiver;
import de.siegmar.billomat4j.domain.recurring.RecurringFilter;
import de.siegmar.billomat4j.domain.recurring.RecurringItem;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.domain.types.RecipientType;

public class RecurringServiceIT extends AbstractServiceIT {

    private final List<Recurring> createdRecurrings = new ArrayList<>();

    // Recurring

    @AfterMethod
    public void cleanup() {
        for (final Recurring recurring : createdRecurrings) {
            final int clientId = recurring.getClientId();
            recurringService.deleteRecurring(recurring.getId());
            clientService.deleteClient(clientId);
        }
        createdRecurrings.clear();
    }

    @Test(singleThreaded = true)
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
        assertEquals(recurrings.size(), 1);
        assertEquals(recurrings.get(0).getId(), recurring1.getId());
    }

    @Test
    public void create() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        assertNotNull(recurring.getId());
    }

    @Test
    public void getById() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        assertEquals(recurringService.getRecurringById(recurring.getId()).getId(), recurring.getId());
    }

    @Test
    public void update() {
        final Recurring recurring = createRecurring(PaymentType.BANK_TRANSFER);
        recurring.setLabel("Test Label");
        recurringService.updateRecurring(recurring);
        assertEquals(recurring.getLabel(), "Test Label");
        assertEquals(recurringService.getRecurringById(recurring.getId()).getLabel(), "Test Label");
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

        assertEquals(recurringEmailReceivers.size(), 1);
        assertEquals(recurringEmailReceivers.get(0).getAddress(), "");

        final RecurringEmailReceiver recurringEmailReceiver = new RecurringEmailReceiver();
        recurringEmailReceiver.setRecurringId(recurring.getId());
        recurringEmailReceiver.setType(RecipientType.Bcc);
        recurringEmailReceiver.setAddress(getEmail());

        recurringService.createRecurringEmailReceiver(recurringEmailReceiver);
        assertNotNull(recurringEmailReceiver.getId());

        recurringEmailReceiver.setType(RecipientType.Cc);
        recurringService.updateRecurringEmailReceiver(recurringEmailReceiver);
        assertEquals(recurringService.getRecurringEmailReceiverById(recurringEmailReceiver.getId()).getType(),
                RecipientType.Cc);

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
