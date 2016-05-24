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
package de.siegmar.billomat4j.sdk.service;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.settings.UserProperty;
import de.siegmar.billomat4j.sdk.service.impl.ServiceException;
import de.siegmar.billomat4j.sdk.domain.user.User;
import de.siegmar.billomat4j.sdk.domain.user.UserFilter;
import de.siegmar.billomat4j.sdk.domain.user.UserPropertyValue;

/**
 * @see http://www.billomat.com/api/benutzer/
 * @see http://www.billomat.com/api/benutzer/attribute/
 * @see http://www.billomat.com/api/einstellungen/benutzer-attribute/
 */
public interface UserService extends
    GenericCustomFieldService,
    GenericPropertyService<UserProperty, UserPropertyValue> {

    /**
     * @param userFilter
     *            user filter, may be {@code null} to find unfiltered
     * @return users found by filter criteria or an empty list if no users were found - never {@code null}
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    List<User> findUsers(UserFilter userFilter);

    /**
     * @param userId
     *            the user's id
     * @return the user or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    User getUserById(int userId);

    /**
     * @return the authenticated user
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    User getMySelf();

}
