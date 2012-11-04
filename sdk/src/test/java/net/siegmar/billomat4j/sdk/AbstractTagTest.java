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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.AbstractTag;
import net.siegmar.billomat4j.sdk.service.AbstractTagService;

import org.junit.Test;

public abstract class AbstractTagTest<T extends AbstractTag> extends AbstractServiceTest {

    private AbstractTagService<T> service;

    public void setService(final AbstractTagService<T> service) {
        this.service = service;
    }

    @Test
    public void testTag() {
        // Find no tags
        List<T> tags = service.getTags(null);
        assertTrue(tags.isEmpty());

        // Create owner
        final int owner1 = createOwner();
        final int owner2 = createOwner();

        try {
            // Create confirmation tags
            final T tag1 = createTag(owner1, "Test ConfirmationTag1");
            final T tag2 = createTag(owner2, "Test ConfirmationTag2");

            // Find tags
            tags = service.getTags(null);
            assertEquals(2, tags.size());

            // Find tags by owner
            assertEquals(tag1.getId(), service.getTags(owner1).get(0).getId());
            assertEquals(tag2.getId(), service.getTags(owner2).get(0).getId());

            // Find tag
            assertEquals(tag1.getId(), service.getTagById(tag1.getId()).getId());

            // Cleanup
            service.deleteTag(tag1.getId());
            service.deleteTag(tag2.getId());
        } finally {
            deleteOwner(owner1);
            deleteOwner(owner2);
        }
    }

    private T createTag(final int ownerId, final String name) {
        final T tag = constructTag(ownerId);
        tag.setName(name);
        service.createTag(tag);
        return tag;
    }

    protected abstract int createOwner();
    protected abstract T constructTag(int ownerId);
    protected abstract void deleteOwner(int ownerId);

}
