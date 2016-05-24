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

import de.siegmar.billomat4j.sdk.domain.recurring.Recurring;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringEmailReceiver;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringFilter;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringItem;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringTag;

/**
 * @see http://www.billomat.com/api/abo-rechnungen/
 * @see http://www.billomat.com/api/abo-rechnungen/positionen/
 * @see http://www.billomat.com/api/abo-rechnungen/schlagworte/
 */
public interface RecurringService extends
    GenericCustomFieldService,
    GenericTagService<RecurringTag>,
    GenericItemService<RecurringItem> {

    /**
     * @param recurringFilter
     *            recurring filter, may be {@code null} to find unfiltered
     * @return recurrings found by filter criteria or an empty list if no recurrings were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Recurring> findRecurrings(RecurringFilter recurringFilter);

    /**
     * Gets a recurring by its id.
     *
     * @param recurringId
     *            the recurring's id
     * @return the recurring or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Recurring getRecurringById(int recurringId);

    /**
     * @param recurring
     *            the recurring to create, must not be {@code null}
     * @throws NullPointerException
     *             if recurring is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createRecurring(Recurring recurring);

    /**
     * @param recurring
     *            the recurring to update, must not be {@code null}
     * @throws NullPointerException
     *             if recurring is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateRecurring(Recurring recurring);

    /**
     * @param recurringId
     *            the id of the recurring to be deleted
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteRecurring(int recurringId);

    /**
     * @param recurringId
     *            the recurring id
     * @return email receivers found for the given recurring id or an empty list if no items were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<RecurringEmailReceiver> getRecurringEmailReceivers(int recurringId);

    /**
     * Gets a recurring receiver by its id.
     *
     * @param recurringEmailReceiverId
     *            the recurring receiver's id
     * @return the recurring receiver or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    RecurringEmailReceiver getRecurringEmailReceiverById(int recurringEmailReceiverId);

    /**
     * @param recurringEmailReceiver
     *            the recurring receiver to create, must not be {@code null}
     * @throws NullPointerException
     *             if recurringEmailReceiver is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createRecurringEmailReceiver(RecurringEmailReceiver recurringEmailReceiver);

    /**
     * @param recurringEmailReceiver
     *            the recurring receiver to update, must not be {@code null}
     * @throws NullPointerException
     *             if recurringEmailReceiver is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateRecurringEmailReceiver(RecurringEmailReceiver recurringEmailReceiver);

    /**
     * @param recurringEmailReceiverId
     *            the id of the recurring receiver to be deleted
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteRecurringEmailReceiver(int recurringEmailReceiverId);

}
