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

package de.siegmar.billomat4j.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.recurring.Recurring;
import de.siegmar.billomat4j.domain.recurring.RecurringEmailReceiver;
import de.siegmar.billomat4j.domain.recurring.RecurringEmailReceivers;
import de.siegmar.billomat4j.domain.recurring.RecurringFilter;
import de.siegmar.billomat4j.domain.recurring.RecurringItem;
import de.siegmar.billomat4j.domain.recurring.RecurringItems;
import de.siegmar.billomat4j.domain.recurring.RecurringTag;
import de.siegmar.billomat4j.domain.recurring.RecurringTags;
import de.siegmar.billomat4j.domain.recurring.Recurrings;

public class RecurringService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<RecurringTag>,
    GenericItemService<RecurringItem> {

    private static final String RESOURCE = "recurrings";
    private static final String RESOURCE_ITEMS = "recurring-items";
    private static final String RESOURCE_TAGS = "recurring-tags";
    private static final String RESOURCE_EMAIL_RECEIVER = "recurring-email-receivers";

    public RecurringService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Recurring

    @Override
    public Optional<String> getCustomFieldValue(final int recurringId) {
        return getCustomField(RESOURCE, recurringId);
    }

    @Override
    public void setCustomFieldValue(final int recurringId, final String value) {
        updateCustomField(RESOURCE, recurringId, "recurring", value);
    }

    /**
     * @param recurringFilter recurring filter, may be {@code null} to find unfiltered
     * @return recurrings found by filter criteria or an empty list if no recurrings were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Recurring> findRecurrings(final RecurringFilter recurringFilter) {
        return getAllPagesFromResource(RESOURCE, Recurrings.class, recurringFilter);
    }

    /**
     * Gets a recurring by its id.
     *
     * @param recurringId the recurring's id
     * @return the recurring
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Recurring> getRecurringById(final int recurringId) {
        return getById(RESOURCE, Recurring.class, recurringId);
    }

    /**
     * @param recurring the recurring to create, must not be {@code null}
     * @throws NullPointerException if recurring is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createRecurring(final Recurring recurring) {
        create(RESOURCE, Validate.notNull(recurring));
    }

    /**
     * @param recurring the recurring to update, must not be {@code null}
     * @throws NullPointerException if recurring is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateRecurring(final Recurring recurring) {
        update(RESOURCE, Validate.notNull(recurring));
    }

    /**
     * @param recurringId the id of the recurring to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteRecurring(final int recurringId) {
        delete(RESOURCE, recurringId);
    }

    // RecurringItem

    @Override
    public List<RecurringItem> getItems(final int recurringId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, RecurringItems.class, recurringIdFilter(recurringId));
    }

    private GenericFilter recurringIdFilter(final Integer recurringId) {
        return recurringId == null ? null : new GenericFilter("recurring_id", recurringId);
    }

    @Override
    public Optional<RecurringItem> getItemById(final int recurringItemId) {
        return getById(RESOURCE_ITEMS, RecurringItem.class, recurringItemId);
    }

    @Override
    public void createItem(final RecurringItem recurringItem) {
        create(RESOURCE_ITEMS, Validate.notNull(recurringItem));
    }

    @Override
    public void updateItem(final RecurringItem recurringItem) {
        update(RESOURCE_ITEMS, Validate.notNull(recurringItem));
    }

    @Override
    public void deleteItem(final int recurringItemId) {
        delete(RESOURCE_ITEMS, recurringItemId);
    }

    // RecurringTag

    @Override
    public List<RecurringTag> getTags(final Integer recurringId) {
        return getAllPagesFromResource(RESOURCE_TAGS, RecurringTags.class, recurringIdFilter(recurringId));
    }

    @Override
    public Optional<RecurringTag> getTagById(final int recurringTagId) {
        return getById(RESOURCE_TAGS, RecurringTag.class, recurringTagId);
    }

    @Override
    public void createTag(final RecurringTag recurringTag) {
        create(RESOURCE_TAGS, Validate.notNull(recurringTag));
    }

    @Override
    public void deleteTag(final int recurringTagId) {
        delete(RESOURCE_TAGS, recurringTagId);
    }

    // RecurringEmailReceiver

    /**
     * @param recurringId the recurring id
     * @return email receivers found for the given recurring id or an empty list if no items were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<RecurringEmailReceiver> getRecurringEmailReceivers(final int recurringId) {
        return getAllPagesFromResource(RESOURCE_EMAIL_RECEIVER, RecurringEmailReceivers.class,
            recurringIdFilter(recurringId));
    }

    /**
     * Gets a recurring receiver by its id.
     *
     * @param recurringEmailReceiverId the recurring receiver's id
     * @return the recurring receiver
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<RecurringEmailReceiver> getRecurringEmailReceiverById(final int recurringEmailReceiverId) {
        return getById(RESOURCE_EMAIL_RECEIVER, RecurringEmailReceiver.class, recurringEmailReceiverId);
    }

    /**
     * @param recurringEmailReceiver the recurring receiver to create, must not be {@code null}
     * @throws NullPointerException if recurringEmailReceiver is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createRecurringEmailReceiver(final RecurringEmailReceiver recurringEmailReceiver) {
        create(RESOURCE_EMAIL_RECEIVER, Validate.notNull(recurringEmailReceiver));
    }

    /**
     * @param recurringEmailReceiver the recurring receiver to update, must not be {@code null}
     * @throws NullPointerException if recurringEmailReceiver is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateRecurringEmailReceiver(final RecurringEmailReceiver recurringEmailReceiver) {
        update(RESOURCE_EMAIL_RECEIVER, Validate.notNull(recurringEmailReceiver));
    }

    /**
     * @param recurringEmailReceiverId the id of the recurring receiver to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteRecurringEmailReceiver(final int recurringEmailReceiverId) {
        delete(RESOURCE_EMAIL_RECEIVER, recurringEmailReceiverId);
    }

}
