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
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractPropertyValue;
import net.siegmar.billomat4j.sdk.domain.settings.AbstractProperty;
import net.siegmar.billomat4j.sdk.domain.types.PropertyType;
import net.siegmar.billomat4j.sdk.service.AbstractPropertyService;

import org.junit.Test;

public abstract class AbstractPropertyTest<P extends AbstractProperty, A extends AbstractPropertyValue> extends AbstractServiceTest {

    private AbstractPropertyService<P, A> service;

    public void setService(final AbstractPropertyService<P, A> service) {
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
        assertEquals(articleProperty.getId(), foundArticleProperty.getId());

        // Update
        articleProperty.setDefaultValue("Default Value");
        service.updateProperty(articleProperty);
        assertEquals("Default Value", articleProperty.getDefaultValue());

        // Get By Id
        final P articlePropertyById = service.getPropertyById(articleProperty.getId());
        assertEquals("Default Value", articlePropertyById.getDefaultValue());

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
        assertEquals(propertyValue.getId(), propertyValues.get(0).getId());

        // Find property value
        assertEquals(propertyValue.getId(), service.getPropertyValueById(propertyValue.getId()).getId());

        // Cleanup
        service.deleteProperty(property.getId());
        deleteOwner(ownerId);
    }

    protected abstract int createOwner();
    protected abstract P buildProperty();
    protected abstract A buildPropertyValue(int ownerId, int propertyId);
    protected abstract void deleteOwner(int ownerId);

}
