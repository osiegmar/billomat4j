package net.siegmar.billomat4j.sdk.unit;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.unit.Unit;


public class UnitCustomFieldIT extends AbstractCustomFieldServiceIT {

    public UnitCustomFieldIT() {
        setService(unitService);
    }

    @Override
    protected int buildOwner() {
        final Unit unit = new Unit();
        unit.setName("UnitCustomFieldTest");
        unitService.createUnit(unit);
        return unit.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        unitService.deleteUnit(ownerId);
    }

}
