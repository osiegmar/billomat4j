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
package de.siegmar.billomat4j.sdk;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

import de.siegmar.billomat4j.sdk.domain.AbstractItem;
import de.siegmar.billomat4j.sdk.service.GenericItemService;

import org.testng.annotations.Test;

public abstract class AbstractItemIT<I extends AbstractItem> extends AbstractServiceIT {

    private GenericItemService<I> service;

    public void setService(final GenericItemService<I> service) {
        this.service = service;
    }

    @Test
    public void testItem() {
        final int ownerId = createOwner();
        try {
            final I item = buildItem(ownerId);
            item.setQuantity(BigDecimal.ONE);
            item.setUnitPrice(new BigDecimal("1.11"));
            service.createItem(item);
            assertNotNull(item.getId());

            final List<I> items = service.getItems(ownerId);
            assertFalse(items.isEmpty());

            item.setQuantity(BigDecimal.valueOf(2));
            service.updateItem(item);
            assertEquals(item.getQuantity().intValue(), 2);

            service.deleteItem(item.getId());
            assertNull(service.getItemById(item.getId()));
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int createOwner();
    protected abstract void deleteOwner(int ownerId);
    protected abstract I buildItem(int ownerId);

}
