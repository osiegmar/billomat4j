/*
 * Copyright 2023 Oliver Siegmar
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
import de.siegmar.billomat4j.domain.letter.Letter;
import de.siegmar.billomat4j.domain.letter.LetterFilter;
import de.siegmar.billomat4j.domain.letter.LetterPdf;
import de.siegmar.billomat4j.domain.letter.Letters;
import lombok.NonNull;

public class LetterService extends AbstractService {

    private static final String RESOURCE = "letters";

    public LetterService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Letter

    /**
     * @param letterFilter letter filter, may be {@code null} to find unfiltered
     * @return letters found by filter criteria or an empty list if no letters were found - never
     * {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Letter> findLetters(final LetterFilter letterFilter) {
        return getAllPagesFromResource(RESOURCE, Letters.class, letterFilter);
    }

    /**
     * Gets an letter by its id.
     *
     * @param letterId the letter's id
     * @return the letter
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Letter> getLetterById(final int letterId) {
        return getById(RESOURCE, Letter.class, letterId);
    }

    /**
     * @param letter the letter to create, must not be {@code null}
     * @throws NullPointerException if letter is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createLetter(@NonNull final Letter letter) {
        create(RESOURCE, letter);
    }

    /**
     * @param letter the letter to update, must not be {@code null}
     * @throws NullPointerException if letter is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateLetter(@NonNull final Letter letter) {
        update(RESOURCE, letter);
    }

    /**
     * @param letterId the id of the letter to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteLetter(final int letterId) {
        delete(RESOURCE, letterId);
    }

    /**
     * @param letterId the id of the letter to get the PDF for
     * @return the letter PDF
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<LetterPdf> getLetterPdf(final int letterId) {
        return getPdf(RESOURCE, LetterPdf.class, letterId, null);
    }

    /**
     * Sets the letter status to {@link de.siegmar.billomat4j.domain.letter.LetterStatus#COMPLETED}.
     *
     * @param letterId   the id of the letter to update
     * @param templateId the id of the template to use for the resulting document or {@code null}
     *                   if no template should be used
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void completeLetter(final int letterId, final Integer templateId) {
        completeDocument(RESOURCE, letterId, templateId);
    }

    /**
     * @param letterId    the id of the letter to send an email for
     * @param letterEmail the email configuration
     * @throws NullPointerException if letterEmail is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void sendLetterViaEmail(final int letterId, final Email letterEmail) {
        sendEmail(RESOURCE, letterId, letterEmail);
    }

}
