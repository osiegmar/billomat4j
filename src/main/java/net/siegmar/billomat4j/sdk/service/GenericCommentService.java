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
package net.siegmar.billomat4j.sdk.service;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractComment;
import net.siegmar.billomat4j.sdk.domain.ActionKey;
import net.siegmar.billomat4j.sdk.domain.Filter;

public interface GenericCommentService<K extends ActionKey, C extends AbstractComment<K>, F extends Filter> {

    /**
     * @param ownerId
     *            the id of owning entity
     * @param commentFilter
     *            comment filter, may be {@code null} to find unfiltered
     * @return comments found by filter criteria or an empty list if no comments were found - never {@code null}
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<C> findComments(int ownerId, F commentFilter);

    /**
     * Gets a comment by its id.
     *
     * @param commentId
     *            the comment's id
     * @return the comment or {@code null} if not found
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    C getCommentById(int commentId);

    /**
     * @param comment
     *            the comment to create, must not be {@code null}
     * @throws NullPointerException
     *             if comment is null
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createComment(C comment);

    /**
     * @param commentId
     *            the id of the comment to be deleted
     * @throws net.siegmar.billomat4j.sdk.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteComment(int commentId);

}