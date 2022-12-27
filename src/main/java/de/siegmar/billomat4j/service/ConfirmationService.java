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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.service;

import java.util.List;
import java.util.Optional;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.Filter;
import de.siegmar.billomat4j.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationActionKey;
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
import lombok.NonNull;

public class ConfirmationService extends AbstractService
    implements GenericCustomFieldService, GenericTagService<ConfirmationTag>,
    GenericCommentService<ConfirmationActionKey, ConfirmationComment,
        ConfirmationCommentFilter>, GenericItemService<ConfirmationItem> {

    private static final String RESOURCE = "confirmations";
    private static final String RESOURCE_ITEMS = "confirmation-items";
    private static final String RESOURCE_COMMENTS = "confirmation-comments";
    private static final String RESOURCE_TAGS = "confirmation-tags";

    public ConfirmationService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Confirmation

    @Override
    public Optional<String> getCustomFieldValue(final int confirmationId) {
        return getCustomField(RESOURCE, confirmationId);
    }

    @Override
    public void setCustomFieldValue(final int confirmationId, final String value) {
        updateCustomField(RESOURCE, confirmationId, "confirmation", value);
    }

    /**
     * @param confirmationFilter confirmation filter, may be {@code null} to find unfiltered
     * @return confirmations found by filter criteria or an empty list if no confirmations were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Confirmation> findConfirmations(final ConfirmationFilter confirmationFilter) {
        return getAllPagesFromResource(RESOURCE, Confirmations.class, confirmationFilter);
    }

    /**
     * Gets a confirmation by its id.
     *
     * @param confirmationId the confirmation's id
     * @return the confirmation
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Confirmation> getConfirmationById(final int confirmationId) {
        return getById(RESOURCE, Confirmation.class, confirmationId);
    }

    /**
     * Gets a confirmation by its confirmation number.
     *
     * @param confirmationNumber the confirmation number, must not be empty / {@code null}
     * @return the confirmation
     * @throws NullPointerException     if confirmationNumber is null
     * @throws IllegalArgumentException if confirmationNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<Confirmation> getConfirmationByNumber(@NonNull final String confirmationNumber) {
        if (confirmationNumber.isEmpty()) {
            throw new IllegalArgumentException("confirmationNumber must not be empty");
        }
        return single(findConfirmations(new ConfirmationFilter().byConfirmationNumber(confirmationNumber)));
    }

    /**
     * @param confirmation the confirmation to create, must not be {@code null}
     * @throws NullPointerException if confirmation is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createConfirmation(@NonNull final Confirmation confirmation) {
        create(RESOURCE, confirmation);
    }

    /**
     * @param confirmation the confirmation to update, must not be {@code null}
     * @throws NullPointerException if confirmation is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateConfirmation(@NonNull final Confirmation confirmation) {
        update(RESOURCE, confirmation);
    }

    /**
     * @param confirmationId the id of the confirmation to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteConfirmation(final int confirmationId) {
        delete(RESOURCE, confirmationId);
    }

    /**
     * @param confirmationId the id of the confirmation to get the PDF for
     * @return the confirmation PDF
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<ConfirmationPdf> getConfirmationPdf(final int confirmationId) {
        return getPdf(RESOURCE, ConfirmationPdf.class, confirmationId, null);
    }

    /**
     * Sets the confirmation status to
     * {@link de.siegmar.billomat4j.domain.confirmation.ConfirmationStatus#COMPLETED}.
     *
     * @param confirmationId the id of the confirmation to update
     * @param templateId     the id of the template to use for the resulting document or {@code null}
     *                       if no template should be used
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void completeConfirmation(final int confirmationId, final Integer templateId) {
        completeDocument(RESOURCE, confirmationId, templateId);
    }

    /**
     * @param confirmationId    the id of the confirmation to send an email for
     * @param confirmationEmail the email configuration
     * @throws NullPointerException if confirmationEmail is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void sendConfirmationViaEmail(final int confirmationId, final Email confirmationEmail) {
        sendEmail(RESOURCE, confirmationId, confirmationEmail);
    }

    /**
     * Sets the confirmation status to
     * {@link de.siegmar.billomat4j.domain.confirmation.ConfirmationStatus#CANCELED}.
     *
     * @param confirmationId the id of the confirmation to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void cancelConfirmation(final int confirmationId) {
        transit(RESOURCE, "cancel", confirmationId);
    }

    /**
     * Sets the confirmation status to {@link de.siegmar.billomat4j.domain.confirmation.ConfirmationStatus#CLEARED}
     * .
     *
     * @param confirmationId the id of the confirmation to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void clearConfirmation(final int confirmationId) {
        transit(RESOURCE, "clear", confirmationId);
    }

    /**
     * Reverts the status change of {@link #clearConfirmation(int)}.
     *
     * @param confirmationId the id of the confirmation to update
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void unclearConfirmation(final int confirmationId) {
        transit(RESOURCE, "unclear", confirmationId);
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
    public Optional<ConfirmationItem> getItemById(final int confirmationItemId) {
        return getById(RESOURCE_ITEMS, ConfirmationItem.class, confirmationItemId);
    }

    @Override
    public void createItem(@NonNull final ConfirmationItem confirmationItem) {
        create(RESOURCE_ITEMS, confirmationItem);
    }

    @Override
    public void updateItem(@NonNull final ConfirmationItem confirmationItem) {
        update(RESOURCE_ITEMS, confirmationItem);
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
    public Optional<ConfirmationComment> getCommentById(final int confirmationCommentId) {
        return getById(RESOURCE_COMMENTS, ConfirmationComment.class, confirmationCommentId);
    }

    @Override
    public void createComment(@NonNull final ConfirmationComment confirmationComment) {
        create(RESOURCE_COMMENTS, confirmationComment);
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
    public Optional<ConfirmationTag> getTagById(final int confirmationTagId) {
        return getById(RESOURCE_TAGS, ConfirmationTag.class, confirmationTagId);
    }

    @Override
    public void createTag(@NonNull final ConfirmationTag confirmationTag) {
        create(RESOURCE_TAGS, confirmationTag);
    }

    @Override
    public void deleteTag(final int confirmationTagId) {
        delete(RESOURCE_TAGS, confirmationTagId);
    }

}
