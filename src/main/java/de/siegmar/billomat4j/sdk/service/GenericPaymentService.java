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
package de.siegmar.billomat4j.sdk.service;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.AbstractPayment;
import de.siegmar.billomat4j.sdk.domain.AbstractPaymentFilter;

public interface GenericPaymentService<P extends AbstractPayment, F extends AbstractPaymentFilter<?>> {

    /**
     * @param paymentFilter
     *            payment filter, may be {@code null} to find unfiltered
     * @return payments found by filter criteria or an empty list if no payments were found - never {@code null}
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<P> findPayments(F paymentFilter);

    /**
     * Gets a payment by its id.
     *
     * @param paymentId
     *            the payment's id
     * @return the payment or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    P getPaymentById(int paymentId);

    /**
     * @param payment
     *            the payment to create, must not be {@code null}
     * @throws NullPointerException
     *             if payment is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createPayment(P payment);

    /**
     * @param paymentId
     *            the id of the payment to be deleted
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deletePayment(int paymentId);

}
