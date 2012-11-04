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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractItem;
import net.siegmar.billomat4j.sdk.service.AbstractItemService;

import org.junit.Test;

public abstract class AbstractItemTest<I extends AbstractItem> extends AbstractServiceTest {

    private AbstractItemService<I> service;

    public void setService(final AbstractItemService<I> service) {
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
            assertEquals(2, item.getQuantity().intValue());

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
