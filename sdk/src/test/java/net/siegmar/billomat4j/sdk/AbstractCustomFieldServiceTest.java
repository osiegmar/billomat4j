package net.siegmar.billomat4j.sdk;

import static org.junit.Assert.assertEquals;

import net.siegmar.billomat4j.sdk.service.AbstractCustomFieldService;

import org.junit.Test;

public abstract class AbstractCustomFieldServiceTest extends AbstractServiceTest {

    private AbstractCustomFieldService service;

    public void setService(final AbstractCustomFieldService service) {
        this.service = service;
    }

    @Test
    public void customField() {
        final int ownerId = buildOwner();

        try {
            assertEquals("", service.getCustomFieldValue(ownerId));
            service.setCustomFieldValue(ownerId, "foo");
            assertEquals("foo", service.getCustomFieldValue(ownerId));
            service.setCustomFieldValue(ownerId, "");
            assertEquals("", service.getCustomFieldValue(ownerId));
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int buildOwner();
    protected abstract void deleteOwner(int ownerId);

}