/*
 * Copyright 2012 Oliver Siegmar
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
package de.siegmar.billomat4j.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractComment<K extends ActionKey> extends AbstractMeta {

    private Integer userId;
    private String comment;
    private Integer contactId;

    @JsonProperty("actionkey")
    private K actionKey;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public K getActionKey() {
        return actionKey;
    }

    public void setActionKey(final K actionKey) {
        this.actionKey = actionKey;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(final Integer contactId) {
        this.contactId = contactId;
    }

}
