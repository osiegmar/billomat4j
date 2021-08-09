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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.domain.AbstractItem;
import de.siegmar.billomat4j.service.GenericItemService;

public abstract class AbstractItemIT<I extends AbstractItem> {

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
            assertEquals(2, item.getQuantity().intValue());

            service.deleteItem(item.getId());
            assertTrue(service.getItemById(item.getId()).isEmpty());
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int createOwner();

    protected abstract void deleteOwner(int ownerId);

    protected abstract I buildItem(int ownerId);

}
