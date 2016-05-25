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
package de.siegmar.billomat4j.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationComment;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationCommentFilter;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationComments;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationFilter;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationItem;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationItems;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationPdf;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationTag;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationTags;
import de.siegmar.billomat4j.domain.confirmation.Confirmations;
import de.siegmar.billomat4j.service.ConfirmationService;

public class ConfirmationServiceImpl extends AbstractService implements ConfirmationService {

    private static final String RESOURCE = "confirmations";
    private static final String RESOURCE_ITEMS = "confirmation-items";
    private static final String RESOURCE_COMMENTS = "confirmation-comments";
    private static final String RESOURCE_TAGS = "confirmation-tags";

    public ConfirmationServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Confirmation

    @Override
    public String getCustomFieldValue(final int confirmationId) {
        return getCustomField(RESOURCE, confirmationId);
    }

    @Override
    public void setCustomFieldValue(final int confirmationId, final String value) {
        updateCustomField(RESOURCE, confirmationId, "confirmation", value);
    }

    @Override
    public List<Confirmation> findConfirmations(final ConfirmationFilter confirmationFilter) {
        return getAllPagesFromResource(RESOURCE, Confirmations.class, confirmationFilter);
    }

    @Override
    public Confirmation getConfirmationById(final int id) {
        return getById(RESOURCE, Confirmation.class, id);
    }

    @Override
    public Confirmation getConfirmationByNumber(final String confirmationNumber) {
        Validate.notEmpty(confirmationNumber);
        return single(findConfirmations(new ConfirmationFilter().byConfirmationNumber(confirmationNumber)));
    }

    @Override
    public void createConfirmation(final Confirmation confirmation) {
        create(RESOURCE, Validate.notNull(confirmation));
    }

    @Override
    public void updateConfirmation(final Confirmation confirmation) {
        update(RESOURCE, Validate.notNull(confirmation));
    }

    @Override
    public void deleteConfirmation(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public ConfirmationPdf getConfirmationPdf(final int id) {
        return getPdf(RESOURCE, ConfirmationPdf.class, id, null);
    }

    @Override
    public void completeConfirmation(final int id, final Integer templateId) {
        completeDocument(RESOURCE, id, templateId);
    }

    @Override
    public void sendConfirmationViaEmail(final int confirmationId, final Email confirmationEmail) {
        sendEmail(RESOURCE, confirmationId, confirmationEmail);
    }

    @Override
    public void cancelConfirmation(final int id) {
        transit(RESOURCE, "cancel", id);
    }

    @Override
    public void clearConfirmation(final int id) {
        transit(RESOURCE, "clear", id);
    }

    @Override
    public void unclearConfirmation(final int id) {
        transit(RESOURCE, "unclear", id);
    }

    // ConfirmationItem

    @Override
    public List<ConfirmationItem> getItems(final int confirmationId) {
        return getAllPagesFromResource(RESOURCE_ITEMS, ConfirmationItems.class, confirmationIdFilter(confirmationId));
    }

    private GenericFilter confirmationIdFilter(final Integer confirmationId) {
        return confirmationId == null ? null : new GenericFilter("confirmation_id", confirmationId);
    }

    @Override
    public ConfirmationItem getItemById(final int confirmationItemId) {
        return getById(RESOURCE_ITEMS, ConfirmationItem.class, confirmationItemId);
    }

    @Override
    public void createItem(final ConfirmationItem confirmationItem) {
        create(RESOURCE_ITEMS, Validate.notNull(confirmationItem));
    }

    @Override
    public void updateItem(final ConfirmationItem confirmationItem) {
        update(RESOURCE_ITEMS, Validate.notNull(confirmationItem));
    }

    @Override
    public void deleteItem(final int confirmationItemId) {
        delete(RESOURCE_ITEMS, confirmationItemId);
    }

    // ConfirmationComment

    @Override
    public List<ConfirmationComment> findComments(final int confirmationId,
                                                  final ConfirmationCommentFilter confirmationCommentFilter) {

        final Filter filter = new CombinedFilter(confirmationIdFilter(confirmationId), confirmationCommentFilter);
        return getAllPagesFromResource(RESOURCE_COMMENTS, ConfirmationComments.class, filter);
    }

    @Override
    public ConfirmationComment getCommentById(final int confirmationCommentId) {
        return getById(RESOURCE_COMMENTS, ConfirmationComment.class, confirmationCommentId);
    }

    @Override
    public void createComment(final ConfirmationComment confirmationComment) {
        create(RESOURCE_COMMENTS, Validate.notNull(confirmationComment));
    }

    @Override
    public void deleteComment(final int confirmationCommentId) {
        delete(RESOURCE_COMMENTS, confirmationCommentId);
    }

    // ConfirmationTag

    @Override
    public List<ConfirmationTag> getTags(final Integer confirmationId) {
        return getAllPagesFromResource(RESOURCE_TAGS, ConfirmationTags.class, confirmationIdFilter(confirmationId));
    }

    @Override
    public ConfirmationTag getTagById(final int confirmationTagId) {
        return getById(RESOURCE_TAGS, ConfirmationTag.class, confirmationTagId);
    }

    @Override
    public void createTag(final ConfirmationTag confirmationTag) {
        create(RESOURCE_TAGS, Validate.notNull(confirmationTag));
    }

    @Override
    public void deleteTag(final int confirmationTagId) {
        delete(RESOURCE_TAGS, confirmationTagId);
    }

}
