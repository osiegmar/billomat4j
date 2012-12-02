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
package net.siegmar.billomat4j.sdk;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractComment;
import net.siegmar.billomat4j.sdk.domain.ActionKey;
import net.siegmar.billomat4j.sdk.domain.Filter;
import net.siegmar.billomat4j.sdk.service.AbstractCommentService;

import org.testng.annotations.Test;

public abstract class AbstractCommentTest<K extends ActionKey, C extends AbstractComment<K>, F extends Filter> extends AbstractServiceTest {

    private AbstractCommentService<K, C, F> service;

    public void setService(final AbstractCommentService<K, C, F> service) {
        this.service = service;
    }

    @Test
    public void testComment() {
        final int ownerId = createOwner();
        try {
            // Filter with no match
            assertTrue(service.findComments(ownerId, buildFilter()).isEmpty());

            final List<C> comments = service.findComments(ownerId, null);
            assertEquals(comments.size(), 1);

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
