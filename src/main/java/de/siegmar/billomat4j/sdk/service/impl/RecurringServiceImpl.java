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
package de.siegmar.billomat4j.sdk.service.impl;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.recurring.RecurringEmailReceivers;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringItem;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringItems;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringTags;
import de.siegmar.billomat4j.sdk.domain.recurring.Recurrings;
import de.siegmar.billomat4j.sdk.service.RecurringService;
import de.siegmar.billomat4j.sdk.domain.recurring.Recurring;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringEmailReceiver;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringFilter;
import de.siegmar.billomat4j.sdk.domain.recurring.RecurringTag;

import org.apache.commons.lang3.Validate;

public class RecurringServiceImpl extends AbstractService implements RecurringService {

    private static final String RESOURCE = "recurrings";
    private static final String RESOURCE_ITEMS = "recurring-items";
    private static final String RESOURCE_TAGS = "recurring-tags";
    private static final String RESOURCE_EMAIL_RECEIVER = "recurring-email-receivers";

    public RecurringServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Recurring

    @Override
    public String getCustomFieldValue(final int recurringId) {
        return getCustomField(RESOURCE, recurringId);
    }

    @Override
    public void setCustomFieldValue(final int recurringId, final String value) {
        updateCustomField(RESOURCE, recurringId, "recurring", value);
    }

    @Override
    public List<Recurring> findRecurrings(final RecurringFilter recurringFilter) {
        return getAllPagesFromResource(RESOURCE, Recurrings.class, recurringFilter);
    }

    @Override
    public Recurring getRecurringById(final int id) {
        return getById(RESOURCE, Recurring.class, id);
    }

    @Override
    public void createRecurring(final Recurring recurring) {
        create(RESOURCE, Validate.notNull(recurring));
    }

    @Override
    public void updateRecurring(final Recurring recurring) {
        update(RESOURCE, Validate.notNull(recurring));
    }

    @Override
    public void deleteRecurring(final int id) {
        delete(RESOURCE, id);
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
    public RecurringItem getItemById(final int recurringItemId) {
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
    public RecurringTag getTagById(final int recurringTagId) {
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

    @Override
    public List<RecurringEmailReceiver> getRecurringEmailReceivers(final int recurringId) {
        return getAllPagesFromResource(RESOURCE_EMAIL_RECEIVER, RecurringEmailReceivers.class,
                recurringIdFilter(recurringId));
    }

    @Override
    public RecurringEmailReceiver getRecurringEmailReceiverById(final int recurringEmailReceiverId) {
        return getById(RESOURCE_EMAIL_RECEIVER, RecurringEmailReceiver.class, recurringEmailReceiverId);
    }

    @Override
    public void createRecurringEmailReceiver(final RecurringEmailReceiver recurringEmailReceiver) {
        create(RESOURCE_EMAIL_RECEIVER, Validate.notNull(recurringEmailReceiver));
    }

    @Override
    public void updateRecurringEmailReceiver(final RecurringEmailReceiver recurringEmailReceiver) {
        update(RESOURCE_EMAIL_RECEIVER, Validate.notNull(recurringEmailReceiver));
    }

    @Override
    public void deleteRecurringEmailReceiver(final int recurringEmailReceiverId) {
        delete(RESOURCE_EMAIL_RECEIVER, recurringEmailReceiverId);
    }

}
