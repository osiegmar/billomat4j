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

package integrationtest.letter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.letter.Letter;
import de.siegmar.billomat4j.domain.letter.LetterFilter;
import de.siegmar.billomat4j.domain.letter.LetterPdf;
import de.siegmar.billomat4j.domain.letter.LetterStatus;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.LetterService;
import integrationtest.ServiceHolder;
import integrationtest.TemplateSetup;

@ExtendWith(TemplateSetup.class)
public class LetterServiceIT {

    private final List<Letter> createdLetters = new ArrayList<>();
    private final LetterService letterService = ServiceHolder.LETTER;
    private final ClientService clientService = ServiceHolder.CLIENT;

    // Letter

    @AfterEach
    public void cleanup() {
        for (final Letter letter : createdLetters) {
            final int clientId = letter.getClientId();
            letterService.deleteLetter(letter.getId());
            clientService.deleteClient(clientId);
        }
        createdLetters.clear();
    }

    @Test
    public void findAll() {
        assertTrue(letterService.findLetters(null).isEmpty());
        createLetter("#findAll");
        assertFalse(letterService.findLetters(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final LetterFilter letterFilter = new LetterFilter().byLabel("test#1");
        List<Letter> letters = letterService.findLetters(letterFilter);
        assertTrue(letters.isEmpty());

        final Letter letter1 = createLetter("test#1");
        createLetter("test#2");

        letters = letterService.findLetters(letterFilter);
        assertEquals(1, letters.size());
        assertEquals(letter1.getId(), letters.get(0).getId());
    }

    @Test
    public void create() {
        final Letter letter = createLetter("#create");
        assertNotNull(letter.getId());
    }

    @Test
    public void getById() {
        final Letter letter = createLetter("#id");
        assertEquals(letter.getId(), letterService.getLetterById(letter.getId()).orElseThrow().getId());
    }

    @Test
    public void update() {
        final Letter letter = createLetter("#update");
        letter.setIntro("Test intro");
        letterService.updateLetter(letter);
        assertEquals("Test intro", letter.getIntro());
        assertEquals("Test intro", letterService.getLetterById(letter.getId()).orElseThrow().getIntro());
    }

    @Test
    public void delete() {
        final Letter letter = createLetter("#delete");

        final int clientId = letter.getClientId();
        letterService.deleteLetter(letter.getId());
        clientService.deleteClient(clientId);

        assertTrue(letterService.getLetterById(letter.getId()).isEmpty());

        createdLetters.remove(letter);
    }

    @Test
    public void complete() {
        final Letter letter = createLetter("#complete");
        letterService.completeLetter(letter.getId(), null);
        assertEquals(LetterStatus.COMPLETED, letterService.getLetterById(letter.getId()).orElseThrow().getStatus());
    }

    @Test
    public void pdf() {
        final Letter letter = createLetter("#pdf");
        letterService.completeLetter(letter.getId(), null);
        assertEquals(LetterStatus.COMPLETED, letterService.getLetterById(letter.getId()).orElseThrow().getStatus());

        final LetterPdf letterPdf = letterService.getLetterPdf(letter.getId()).orElseThrow();
        assertEquals(letter.getId(), letterPdf.getLetterId());
        assertTrue(letterPdf.getFilesize() > 0);
    }

    @Test
    @Disabled("E-Mail")
    public void sendLetterViaEmail() {
        final Letter letter = createLetter("#email");
        letterService.completeLetter(letter.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Letter Mail");
        email.setBody("Here's your letter");
        email.setFrom(ServiceHolder.getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(ServiceHolder.getEmail());
        email.setRecipients(emailRecipients);
        letterService.sendLetterViaEmail(letter.getId(), email);
    }

    private Letter createLetter(final String label) {
        final Client client = new Client();
        client.setName("LetterServiceTest Client");
        clientService.createClient(client);

        final Letter letter = new Letter();
        letter.setClientId(client.getId());
        letter.setLabel(label);

        letterService.createLetter(letter);

        createdLetters.add(letter);

        return letter;
    }

}
