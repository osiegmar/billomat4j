package net.siegmar.billomat4j.sdk.user;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;

public class UserCustomFieldIT extends AbstractCustomFieldServiceIT {

    public UserCustomFieldIT() {
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
