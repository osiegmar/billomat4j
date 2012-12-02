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
package net.siegmar.billomat4j.sdk.user;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.user.User;
import net.siegmar.billomat4j.sdk.domain.user.UserFilter;

import org.testng.annotations.Test;

public class UserServiceTest extends AbstractServiceTest {

    // User

    @Test
    public void findAll() {
        final List<User> users = userService.findUsers(null);
        assertFalse(users.isEmpty());
    }

    @Test
    public void findFiltered() {
        assertTrue(userService.findUsers(new UserFilter().byFirstName("!!&%$$")).isEmpty());
        assertFalse(userService.findUsers(new UserFilter().byEmail("@")).isEmpty());
    }

    @Test
    public void getById() {
        final User user = userService.findUsers(new UserFilter().byEmail("@")).iterator().next();
        final User user2 = userService.getUserById(user.getId());
        assertEquals(user2.getId(), user.getId());
    }

    @Test
    public void getMySelf() {
        final User mySelf = userService.getMySelf();
        assertNotNull(mySelf);
    }

}
