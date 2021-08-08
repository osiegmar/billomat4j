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

package de.siegmar.billomat4j.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.domain.unit.Unit;
import de.siegmar.billomat4j.domain.unit.UnitFilter;
import de.siegmar.billomat4j.service.impl.UnitService;

public class UnitServiceIT {

    private final List<Unit> createdUnits = new ArrayList<>();
    private final UnitService unitService = ServiceHolder.UNIT;

    @AfterEach
    public void cleanup() {
        for (final Unit unit : createdUnits) {
            unitService.deleteUnit(unit.getId());
        }
        createdUnits.clear();
    }

    @Test
    public void findAll() {
        assertTrue(unitService.findUnits(null).isEmpty());
        createUnit("Test Unit 1 (findAll)");
        assertFalse(unitService.findUnits(null).isEmpty());
    }

    private Unit createUnit(final String name) {
        final Unit unit = new Unit();
        unit.setName(name);
        unitService.createUnit(unit);
        createdUnits.add(unit);

        return unit;
    }

    @Test
    public void findFiltered() {
        createUnit("Test Unit 1 (findFiltered)");
        createUnit("Test Unit 2 (findFiltered)");

        final List<Unit> units = unitService.findUnits(new UnitFilter().byName("Test Unit 1 (findFiltered)"));
        assertEquals(1, units.size());
        assertEquals("Test Unit 1 (findFiltered)", units.get(0).getName());
    }

    @Test
    public void create() {
        final Unit unit = createUnit("Test Unit 1 (create)");
        assertNotNull(unit.getId());
    }

    @Test
    public void update() {
        final Unit unit = createUnit("Test Unit 1 (update)");
        assertNotNull(unit.getId());

        unit.setName("Test Unit 1 (updated)");
        unitService.updateUnit(unit);
        assertEquals("Test Unit 1 (updated)", unit.getName());
        assertEquals("Test Unit 1 (updated)", unitService.getUnitById(unit.getId()).orElseThrow().getName());
    }

}
