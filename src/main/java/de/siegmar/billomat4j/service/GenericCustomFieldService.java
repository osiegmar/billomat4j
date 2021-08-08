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

public interface GenericCustomFieldService {

    /**
     * Gets a custom field value.
     *
     * @param ownerId the id of the custom field owning object
     * @return the custom field value
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    String getCustomFieldValue(int ownerId);

    /**
     * Sets a custom field value.
     *
     * @param ownerId the id of the custom field owning object
     * @param value the value to be set
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occurred while accessing the web service
     */
    void setCustomFieldValue(int ownerId, String value);

}
