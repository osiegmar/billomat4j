package net.siegmar.billomat4j.sdk.user;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;

public class UserCustomFieldTest extends AbstractCustomFieldServiceTest {

    public UserCustomFieldTest() {
        setService(userService);
    }

    @Override
    protected int buildOwner() {
        return userService.findUsers(null).iterator().next().getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
    }

}
