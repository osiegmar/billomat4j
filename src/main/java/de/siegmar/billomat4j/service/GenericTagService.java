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

package de.siegmar.billomat4j.service;

import java.util.List;
import java.util.Optional;

import de.siegmar.billomat4j.domain.AbstractTag;

public interface GenericTagService<T extends AbstractTag> {

    /**
     * Find tags by their owner object id.
     *
     * @param ownerId
     *            the id of the tags owner object
     * @return all found tags or an empty list if no tags were found - never {@code null}
     * @throws ServiceException
     *             if an error occurred while accessing the web service
     */
    List<T> getTags(Integer ownerId);

    /**
     * Gets a tag by its id.
     *
     * @param tagId
     *            the tags id
     * @return the tag
     * @throws ServiceException
     *             if an error occurred while accessing the web service
     */
    Optional<T> getTagById(int tagId);

    /**
     * @param tag
     *            the tag to create, must not be {@code null}
     * @throws NullPointerException
     *             if tag is null
     * @throws ServiceException
     *             if an error occurred while accessing the web service
     */
    void createTag(T tag);

    /**
     * @param tagId
     *            the id of the tag to be deleted
     * @throws ServiceException
     *             if an error occurred while accessing the web service
     */
    void deleteTag(int tagId);

}
