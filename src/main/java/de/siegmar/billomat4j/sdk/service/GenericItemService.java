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

import de.siegmar.billomat4j.sdk.domain.AbstractItem;

public interface GenericItemService<I extends AbstractItem> {

    /**
     * @param ownerId
     *            the id of owning entity
     * @return items found by filter criteria or an empty list if no items were found - never {@code null}
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<I> getItems(int ownerId);

    /**
     * Gets an item by its id.
     *
     * @param itemId
     *            the item's id
     * @return the item or {@code null} if not found
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    I getItemById(int itemId);

    /**
     * @param item
     *            the item to create, must not be {@code null}
     * @throws NullPointerException
     *             if item is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createItem(I item);

    /**
     * @param item
     *            the item to update, must not be {@code null}
     * @throws NullPointerException
     *             if item is null
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateItem(I item);

    /**
     * @param id
     *            the id of the item to be deleted
     * @throws de.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteItem(int itemId);

}
