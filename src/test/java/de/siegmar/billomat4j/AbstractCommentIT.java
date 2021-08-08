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

package de.siegmar.billomat4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.domain.AbstractComment;
import de.siegmar.billomat4j.domain.ActionKey;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.service.GenericCommentService;

public abstract class AbstractCommentIT<K extends ActionKey, C extends AbstractComment<K>, F extends Filter> {

    private GenericCommentService<K, C, F> service;

    public void setService(final GenericCommentService<K, C, F> service) {
        this.service = service;
    }

    @Test
    public void testComment() {
        final int ownerId = createOwner();
        try {
            // Filter with no match
            assertTrue(service.findComments(ownerId, buildFilter()).isEmpty());

            final List<C> comments = service.findComments(ownerId, null);
            assertEquals(1, comments.size());

            final C comment = buildComment(ownerId);
            comment.setComment("Test Comment");
            service.createComment(comment);
            assertNotNull(comment.getId());

            service.deleteComment(comment.getId());
            assertNull(service.getCommentById(comment.getId()));
        } finally {
            deleteOwner(ownerId);
        }
    }

    protected abstract int createOwner();

    protected abstract void deleteOwner(int ownerId);

    protected abstract C buildComment(int ownerId);

    protected abstract K buildActionKey();

    protected abstract F buildFilter();

}
