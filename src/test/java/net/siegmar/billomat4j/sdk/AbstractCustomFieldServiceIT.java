package net.siegmar.billomat4j.sdk;

import static org.testng.Assert.assertEquals;
import net.siegmar.billomat4j.sdk.service.AbstractCustomFieldService;

import org.testng.annotations.Test;

public abstract class AbstractCustomFieldServiceIT extends AbstractServiceIT {

    private AbstractCustomFieldService service;

    public void setService(final AbstractCustomFieldService service) {
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
