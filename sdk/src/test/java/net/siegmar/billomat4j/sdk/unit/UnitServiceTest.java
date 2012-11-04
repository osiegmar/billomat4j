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
package net.siegmar.billomat4j.sdk.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.unit.Unit;
import net.siegmar.billomat4j.sdk.domain.unit.UnitFilter;

import org.junit.After;
import org.junit.Test;

public class UnitServiceTest extends AbstractServiceTest {

    @After
    public void cleanup() {
        final List<Unit> units = unitService.findUnits(null);
        for (final Unit unit : units) {
            unitService.deleteUnit(unit.getId());
        }

        assertTrue(unitService.findUnits(null).isEmpty());
    }

    @Test
    public void findAll() {
        List<Unit> units = unitService.findUnits(null);
        assertTrue(units.isEmpty());

        createUnit("Test Unit 1");
        createUnit("Test Unit 2");

        units = unitService.findUnits(null);
        assertEquals(2, units.size());
    }

    private Unit createUnit(final String name) {
        final Unit unit = new Unit();
        unit.setName(name);
        unitService.createUnit(unit);

        return unit;
    }

    @Test
    public void findFiltered() {
        createUnit("Test Unit 1");
        createUnit("Test Unit 2");

        final List<Unit> units = unitService.findUnits(new UnitFilter().byName("Test Unit 1"));
        assertEquals(1, units.size());
        assertEquals("Test Unit 1", units.get(0).getName());
    }

    @Test
    public void create() {
        final Unit unit = createUnit("Test Unit 1");
        assertNotNull(unit.getId());
    }

    @Test
    public void update() {
        final Unit unit = createUnit("Test Unit 1");
        assertNotNull(unit.getId());

        unit.setName("Test Unit 1 Updated");
        unitService.updateUnit(unit);
        assertEquals("Test Unit 1 Updated", unit.getName());
        assertEquals("Test Unit 1 Updated", unitService.getUnitById(unit.getId()).getName());
    }

}
