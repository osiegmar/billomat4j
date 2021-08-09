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
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.unit.Unit;
import de.siegmar.billomat4j.domain.unit.UnitFilter;
import de.siegmar.billomat4j.domain.unit.Units;

public class UnitService extends AbstractService implements GenericCustomFieldService {

    private static final String RESOURCE = "units";

    public UnitService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    @Override
    public Optional<String> getCustomFieldValue(final int unitId) {
        return getCustomField(RESOURCE, unitId);
    }

    @Override
    public void setCustomFieldValue(final int unitId, final String value) {
        updateCustomField(RESOURCE, unitId, "unit", value);
    }

    /**
     * @param unitFilter unit filter, may be {@code null} to find unfiltered
     * @return units found by filter criteria or an empty list if no units were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Unit> findUnits(final UnitFilter unitFilter) {
        return getAllPagesFromResource(RESOURCE, Units.class, unitFilter);
    }

    /**
     * Gets a unit by its id.
     *
     * @param unitId the unit's id
     * @return the unit
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Unit> getUnitById(final int unitId) {
        return getById(RESOURCE, Unit.class, unitId);
    }

    /**
     * @param unit the unit to create, must not be {@code null}
     * @throws NullPointerException if unit is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createUnit(final Unit unit) {
        create(RESOURCE, Validate.notNull(unit));
    }

    /**
     * @param unit the unit to update, must not be {@code null}
     * @throws NullPointerException if unit is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateUnit(final Unit unit) {
        update(RESOURCE, Validate.notNull(unit));
    }

    /**
     * @param unitId the id of the unit to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteUnit(final int unitId) {
        delete(RESOURCE, unitId);
    }

}
