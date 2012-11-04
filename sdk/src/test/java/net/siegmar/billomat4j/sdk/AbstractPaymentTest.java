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
package net.siegmar.billomat4j.sdk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractPayment;
import net.siegmar.billomat4j.sdk.domain.AbstractPaymentFilter;
import net.siegmar.billomat4j.sdk.domain.types.PaymentType;
import net.siegmar.billomat4j.sdk.service.AbstractPaymentService;

import org.junit.Test;

public abstract class AbstractPaymentTest<P extends AbstractPayment, F extends AbstractPaymentFilter<?>> extends AbstractServiceTest {

    private AbstractPaymentService<P, F> service;

    public void setService(final AbstractPaymentService<P, F> service) {
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
            payment.setDate(new Date());
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
