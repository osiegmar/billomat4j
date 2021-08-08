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

import de.siegmar.billomat4j.domain.AbstractPropertyValue;
import de.siegmar.billomat4j.domain.settings.AbstractProperty;

public interface GenericPropertyService<P extends AbstractProperty, V extends AbstractPropertyValue> {

    // Property

    /**
     * @return all configured properties or an empty list if no properties were configured - never {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    List<P> getProperties();

    /**
     * Gets a property by its id.
     *
     * @param propertyId
     *            the property's id
     * @return the property or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    P getPropertyById(int propertyId);

    /**
     * @param property
     *            the property to create, must not be {@code null}
     * @throws NullPointerException
     *             if property is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    void createProperty(P property);

    /**
     * @param property
     *            the property to update, must not be {@code null}
     * @throws NullPointerException
     *             if property is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    void updateProperty(P property);

    /**
     * @param propertyId
     *            the id of the property to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    void deleteProperty(int propertyId);

    // PropertyValue

    /**
     * @param ownerId
     *            the id of the propery value owning object
     * @return the property values for the specified owner or an empty list if no property values were found - never
     *         {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    List<V> getPropertyValues(int ownerId);

    /**
     * Gets a property value by its id.
     *
     * @param propertyValueId
     *            the property value's id
     * @return the property value or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    V getPropertyValueById(int propertyValueId);

    /**
     * @param propertyValue
     *            the property value to create, must not be {@code null}
     * @throws NullPointerException
     *             if propertyValue is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    void createPropertyValue(V propertyValue);

}
