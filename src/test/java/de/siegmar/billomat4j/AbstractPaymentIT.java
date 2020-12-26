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

package de.siegmar.billomat4j;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.testng.annotations.Test;

import de.siegmar.billomat4j.domain.AbstractPayment;
import de.siegmar.billomat4j.domain.AbstractPaymentFilter;
import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.service.GenericPaymentService;

public abstract class AbstractPaymentIT<P extends AbstractPayment, F extends AbstractPaymentFilter<?>>
    extends AbstractServiceIT {

    private GenericPaymentService<P, F> service;

    public void setService(final GenericPaymentService<P, F> service) {
        this.service = service;
    }

    @Test
    public void payment() {
        final int ownerId = createOwner();

        try {
            List<P> payments = service.findPayments(null);
            assertTrue(payments.isEmpty());

            final P payment = buildPayment(ownerId);
            payment.setPaymentType(PaymentType.CASH);
            payment.setDate(LocalDate.now());
            payment.setAmount(new BigDecimal("22.50"));
            service.createPayment(payment);
            assertNotNull(payment.getId());

            payments = service.findPayments(null);
            assertFalse(payments.isEmpty());

            payments = service.findPayments(buildFilter());
            assertTrue(payments.isEmpty());

            assertNotNull(service.getPaymentById(payment.getId()));
            service.deletePayment(payment.getId());
            assertNull(service.getPaymentById(payment.getId()));
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int createOwner();

    protected abstract void deleteOwner(int ownerId);

    protected abstract P buildPayment(int ownerId);

    protected abstract F buildFilter();

}
