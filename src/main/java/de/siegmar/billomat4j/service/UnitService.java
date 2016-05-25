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

import de.siegmar.billomat4j.domain.unit.Unit;
import de.siegmar.billomat4j.domain.unit.UnitFilter;

/**
 * @see <a href="http://www.billomat.com/api/einheiten/">API Einheiten</a>
 */
public interface UnitService extends GenericCustomFieldService {

    /**
     * @param unitFilter
     *            unit filter, may be {@code null} to find unfiltered
     * @return units found by filter criteria or an empty list if no units were found - never {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Unit> findUnits(UnitFilter unitFilter);

    /**
     * Gets a unit by its id.
     *
     * @param unitId
     *            the unit's id
     * @return the unit or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Unit getUnitById(int unitId);

    /**
     * @param unit
     *            the unit to create, must not be {@code null}
     * @throws NullPointerException
     *             if unit is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createUnit(Unit unit);

    /**
     * @param unit
     *            the unit to update, must not be {@code null}
     * @throws NullPointerException
     *             if unit is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateUnit(Unit unit);

    /**
     * @param unitId
     *            the id of the unit to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteUnit(int unitId);

}
