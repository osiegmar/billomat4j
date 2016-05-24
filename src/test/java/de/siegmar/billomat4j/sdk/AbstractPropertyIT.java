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
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import de.siegmar.billomat4j.sdk.domain.AbstractPropertyValue;
import de.siegmar.billomat4j.sdk.domain.settings.AbstractProperty;
import de.siegmar.billomat4j.sdk.domain.types.PropertyType;
import de.siegmar.billomat4j.sdk.service.GenericPropertyService;

public abstract class AbstractPropertyIT<P extends AbstractProperty, A extends AbstractPropertyValue>
    extends AbstractServiceIT {

    private GenericPropertyService<P, A> service;

    public void setService(final GenericPropertyService<P, A> service) {
        this.service = service;
    }

    @Test
    public void testProperty() {
        // Find
        List<P> articleProperties = service.getProperties();
        assertTrue(articleProperties.isEmpty());

        // Create
        final P articleProperty = buildProperty();
        articleProperty.setName("Test ArticleProperty");
        articleProperty.setType(PropertyType.TEXTFIELD);
        service.createProperty(articleProperty);
        assertNotNull(articleProperty.getId());

        // Find again
        articleProperties = service.getProperties();
        final P foundArticleProperty = articleProperties.get(0);
        assertEquals(foundArticleProperty.getId(), articleProperty.getId());

        // Update
        articleProperty.setDefaultValue("Default Value");
        service.updateProperty(articleProperty);
        assertEquals(articleProperty.getDefaultValue(), "Default Value");

        // Get By Id
        final P articlePropertyById = service.getPropertyById(articleProperty.getId());
        assertEquals(articlePropertyById.getDefaultValue(), "Default Value");

        // Delete
        service.deleteProperty(articleProperty.getId());
        articleProperties = service.getProperties();
        assertTrue(articleProperties.isEmpty());
    }

    @Test
    public void testPropertyValue() {
        // Create owner
        final int ownerId = createOwner();

        // Find no property values
        List<A> propertyValues = service.getPropertyValues(ownerId);
        assertTrue(propertyValues.isEmpty());

        // Create property
        final P property = buildProperty();
        property.setName("Test Property");
        property.setType(PropertyType.TEXTFIELD);
        service.createProperty(property);
        assertNotNull(property.getId());

        // Create property value
        final A propertyValue = buildPropertyValue(ownerId, property.getId());
        propertyValue.setValue("Test PropertyValue");
        service.createPropertyValue(propertyValue);
        assertNotNull(propertyValue.getId());

        // Find property values
        propertyValues = service.getPropertyValues(ownerId);
        assertFalse(propertyValues.isEmpty());
        assertEquals(propertyValues.get(0).getId(), propertyValue.getId());

        // Find property value
        assertEquals(service.getPropertyValueById(propertyValue.getId()).getId(), propertyValue.getId());

        // Cleanup
        service.deleteProperty(property.getId());
        deleteOwner(ownerId);
    }

    protected abstract int createOwner();
    protected abstract P buildProperty();
    protected abstract A buildPropertyValue(int ownerId, int propertyId);
    protected abstract void deleteOwner(int ownerId);

}
