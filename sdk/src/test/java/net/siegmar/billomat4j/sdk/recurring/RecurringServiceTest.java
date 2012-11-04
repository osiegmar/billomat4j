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
package net.siegmar.billomat4j.sdk.recurring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.recurring.Recurring;
import net.siegmar.billomat4j.sdk.domain.recurring.RecurringCycle;
import net.siegmar.billomat4j.sdk.domain.recurring.RecurringFilter;
import net.siegmar.billomat4j.sdk.domain.recurring.RecurringItem;
import net.siegmar.billomat4j.sdk.domain.types.PaymentType;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Test;

public class RecurringServiceTest extends AbstractServiceTest {

    // Recurring

    @After
    public void cleanup() {
        // Clean up all recurrings
        List<Recurring> recurrings = recurringService.findRecurrings(null);
        if (!recurrings.isEmpty()) {
            for (final Recurring recurring : recurrings) {
                final int clientId = recurring.getClientId();
                recurringService.deleteRecurring(recurring.getId());
                clientService.deleteClient(clientId);
            }

            recurrings = recurringService.findRecurrings(null);
            assertTrue(recurrings.isEmpty());
        }
    }

    @Test
    public void findAll() {
        List<Recurring> recurrings = recurringService.findRecurrings(null);
        assertTrue(recurrings.isEmpty());

        final Recurring recurring1 = createRecurring(PaymentType.BANK_TRANSFER);
        final Recurring recurring2 = createRecurring(PaymentType.BANK_TRANSFER);

        recurrings = recurringService.findRecurrings(null);
        assertEquals(2, recurrings.size());
        assertEquals(recurring1.getId(), recurrings.get(0).getId());
        assertEquals(recurring2.getId(), recurrings.get(1).getId());
    }

    @Test
    public void findFiltered() {
        assertTrue(recurringService.findRecurrings(null).isEmpty());

        final Recurring recurring1 = createRecurring(PaymentType.BANK_TRANSFER);
        createRecurring(PaymentType.BANK_CARD);

        final List<Recurring> recurrings = recurringService.findRecurrings(new RecurringFilter().byPaymentType(PaymentType.BANK_TRANSFER));
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
    }

    private Recurring createRecurring(final PaymentType paymentType) {
        final Client client = new Client();
        client.setName("RecurringServiceTest Client");
        clientService.createClient(client);

        final Recurring recurring = new Recurring();
        recurring.setClientId(client.getId());
        recurring.setNextCreationDate(DateUtils.addYears(new Date(), 1));
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
        return recurring;
    }

}
