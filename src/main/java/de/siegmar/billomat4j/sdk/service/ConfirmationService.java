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
package de.siegmar.billomat4j.sdk.service;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationCommentFilter;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationFilter;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationItem;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationStatus;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationTag;
import de.siegmar.billomat4j.sdk.service.impl.ServiceException;
import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationActionKey;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationComment;
import de.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationPdf;

/**
 * @see http://www.billomat.com/api/auftragsbestaetigungen/
 * @see http://www.billomat.com/api/auftragsbestaetigungen/positionen/
 * @see http://www.billomat.com/api/auftragsbestaetigungen/kommentare-und-historie-2/
 * @see http://www.billomat.com/api/auftragsbestaetigungen/schlagworte/
 */
public interface ConfirmationService extends
    GenericCustomFieldService,
    GenericTagService<ConfirmationTag>,
    GenericCommentService<ConfirmationActionKey, ConfirmationComment, ConfirmationCommentFilter>,
    GenericItemService<ConfirmationItem> {

    /**
     * @param confirmationFilter
     *            confirmation filter, may be {@code null} to find unfiltered
     * @return confirmations found by filter criteria or an empty list if no confirmations were found - never
     *         {@code null}
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    List<Confirmation> findConfirmations(ConfirmationFilter confirmationFilter);

    /**
     * Gets a confirmation by its id.
     *
     * @param confirmationId
     *            the confirmation's id
     * @return the confirmation or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    Confirmation getConfirmationById(int confirmationId);

    /**
     * Gets a confirmation by its confirmation number.
     *
     * @param confirmationNumber
     *            the confirmation number, must not be empty / {@code null}
     * @return the confirmation or {@code null} if not found
     * @throws NullPointerException
     *             if confirmationNumber is null
     * @throws IllegalArgumentException
     *             if confirmationNumber is empty
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    Confirmation getConfirmationByNumber(String confirmationNumber);

    /**
     * @param confirmation
     *            the confirmation to create, must not be {@code null}
     * @throws NullPointerException
     *             if confirmation is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void createConfirmation(Confirmation confirmation);

    /**
     * @param confirmation
     *            the confirmation to update, must not be {@code null}
     * @throws NullPointerException
     *             if confirmation is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void updateConfirmation(Confirmation confirmation);

    /**
     * @param confirmationId
     *            the id of the confirmation to be deleted
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteConfirmation(int confirmationId);

    /**
     * @param confirmationId
     *            the id of the confirmation to get the PDF for
     * @return the confirmation PDF or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    ConfirmationPdf getConfirmationPdf(int confirmationId);

    /**
     * Sets the confirmation status to
     * {@link ConfirmationStatus#COMPLETED}.
     *
     * @param confirmationId
     *            the id of the confirmation to update
     * @param templateId
     *            the id of the template to use for the resulting document or {@code null} of no template should be used
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void completeConfirmation(int confirmationId, Integer templateId);

    /**
     * @param confirmationId
     *            the id of the confirmation to send an email for
     * @param confirmationEmail
     *            the email configuration
     * @throws NullPointerException
     *             if confirmationEmail is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void sendConfirmationViaEmail(int confirmationId, Email confirmationEmail);

    /**
     * Sets the confirmation status to
     * {@link ConfirmationStatus#CANCELED}.
     *
     * @param confirmationId
     *            the id of the confirmation to update
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void cancelConfirmation(int confirmationId);

    /**
     * Sets the confirmation status to {@link ConfirmationStatus#CLEARED}
     * .
     *
     * @param confirmationId
     *            the id of the confirmation to update
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void clearConfirmation(int confirmationId);

    /**
     * Reverts the status change of {@link #clearConfirmation(int)}.
     *
     * @param confirmationId
     *            the id of the confirmation to update
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void unclearConfirmation(int confirmationId);

}
