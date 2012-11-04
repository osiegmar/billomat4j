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
package net.siegmar.billomat4j.sdk.service.impl;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.settings.UserProperties;
import net.siegmar.billomat4j.sdk.domain.settings.UserProperty;
import net.siegmar.billomat4j.sdk.domain.user.User;
import net.siegmar.billomat4j.sdk.domain.user.UserFilter;
import net.siegmar.billomat4j.sdk.domain.user.UserPropertyValue;
import net.siegmar.billomat4j.sdk.domain.user.UserPropertyValues;
import net.siegmar.billomat4j.sdk.domain.user.Users;
import net.siegmar.billomat4j.sdk.service.UserService;

import org.apache.commons.lang3.Validate;

public class UserServiceImpl extends AbstractService implements UserService {

    private static final String RESOURCE = "users";
    private static final String PROPERTIES_RESOURCE = "user-properties";
    private static final String ATTRIBUTE_RESOURCE = "user-property-values";

    public UserServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // User

    @Override
    public String getCustomFieldValue(final int userId) {
        return getCustomField(RESOURCE, userId);
    }

    @Override
    public void setCustomFieldValue(final int userId, final String value) {
        updateCustomField(RESOURCE, userId, "user", value);
    }

    @Override
    public List<User> findUsers(final UserFilter userFilter) {
        return getAllPagesFromResource(RESOURCE, Users.class, userFilter);
    }

    @Override
    public User getUserById(final int id) {
        return getById(RESOURCE, User.class, id);
    }

    @Override
    public User getMySelf() {
        return getMySelf(RESOURCE, User.class);
    }

    // UserProperty

    @Override
    public List<UserProperty> getProperties() {
        return getAllPagesFromResource(PROPERTIES_RESOURCE, UserProperties.class, null);
    }

    @Override
    public UserProperty getPropertyById(final int userPropertyId) {
        return getById(PROPERTIES_RESOURCE, UserProperty.class, userPropertyId);
    }

    @Override
    public void createProperty(final UserProperty userProperty) {
        create(PROPERTIES_RESOURCE, Validate.notNull(userProperty));
    }

    @Override
    public void updateProperty(final UserProperty userProperty) {
        update(PROPERTIES_RESOURCE, Validate.notNull(userProperty));
    }

    @Override
    public void deleteProperty(final int userPropertyId) {
        delete(PROPERTIES_RESOURCE, userPropertyId);
    }

    // UserPropertyValue

    @Override
    public List<UserPropertyValue> getPropertyValues(final int userId) {
        final GenericFilter userIdFilter = new GenericFilter("user_id", userId);
        return getAllPagesFromResource(ATTRIBUTE_RESOURCE, UserPropertyValues.class, userIdFilter);
    }

    @Override
    public UserPropertyValue getPropertyValueById(final int userPropertyValueId) {
        return getById(ATTRIBUTE_RESOURCE, UserPropertyValue.class, userPropertyValueId);
    }

    @Override
    public void createPropertyValue(final UserPropertyValue userPropertyValue) {
        create(ATTRIBUTE_RESOURCE, Validate.notNull(userPropertyValue));
    }

}
