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

import org.testng.annotations.Test;

import de.siegmar.billomat4j.sdk.service.GenericCustomFieldService;

public abstract class AbstractCustomFieldServiceIT extends AbstractServiceIT {

    private GenericCustomFieldService service;

    public void setService(final GenericCustomFieldService service) {
        this.service = service;
    }

    @Test
    public void customField() {
        final int ownerId = buildOwner();

        try {
            assertEquals(service.getCustomFieldValue(ownerId), "");
            service.setCustomFieldValue(ownerId, "foo");
            assertEquals(service.getCustomFieldValue(ownerId), "foo");
            service.setCustomFieldValue(ownerId, "");
            assertEquals(service.getCustomFieldValue(ownerId), "");
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int buildOwner();
    protected abstract void deleteOwner(int ownerId);

}
