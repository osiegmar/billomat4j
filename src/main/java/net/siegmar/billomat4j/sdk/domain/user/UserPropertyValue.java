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
package net.siegmar.billomat4j.sdk.domain.user;

import net.siegmar.billomat4j.sdk.domain.AbstractPropertyValue;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user-property-value")
public class UserPropertyValue extends AbstractPropertyValue {

    private Integer userId;
    private Integer userPropertyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getUserPropertyId() {
        return userPropertyId;
    }

    public void setUserPropertyId(final Integer userPropertyId) {
        this.userPropertyId = userPropertyId;
    }

}