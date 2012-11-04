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
package net.siegmar.billomat4j.sdk.confirmation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.EmailRecipients;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.confirmation.Confirmation;
import net.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationFilter;
import net.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationItem;
import net.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationPdf;
import net.siegmar.billomat4j.sdk.domain.confirmation.ConfirmationStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class ConfirmationServiceTest extends AbstractServiceTest {

    // Confirmation

    @After
    public void cleanup() {
        // Clean up all confirmations
        List<Confirmation> confirmations = confirmationService.findConfirmations(null);
        if (!confirmations.isEmpty()) {
            for (final Confirmation confirmation : confirmations) {
                final int clientId = confirmation.getClientId();
                confirmationService.deleteConfirmation(confirmation.getId());
                clientService.deleteClient(clientId);
            }

            confirmations = confirmationService.findConfirmations(null);
            assertTrue(confirmations.isEmpty());
        }
    }

    @Test
    public void findAll() {
        List<Confirmation> confirmations = confirmationService.findConfirmations(null);
        assertTrue(confirmations.isEmpty());

        final Confirmation confirmation1 = createConfirmation(1);
        final Confirmation confirmation2 = createConfirmation(2);

        confirmations = confirmationService.findConfirmations(null);
        assertEquals(2, confirmations.size());
        assertEquals(confirmation1.getId(), confirmations.get(0).getId());
        assertEquals(confirmation2.getId(), confirmations.get(1).getId());
    }

    @Test
    public void findFiltered() {
        assertTrue(confirmationService.findConfirmations(null).isEmpty());

        final Confirmation confirmation1 = createConfirmation(1);
        createConfirmation(2);

        final List<Confirmation> confirmations = confirmationService.findConfirmations(new ConfirmationFilter().byConfirmationNumber("1"));
        assertEquals(1, confirmations.size());
        assertEquals(confirmation1.getId(), confirmations.get(0).getId());
    }

    @Test
    public void create() {
        final Confirmation confirmation = createConfirmation(1);
        assertNotNull(confirmation.getId());
    }

    @Test
    public void getById() {
        final Confirmation confirmation = createConfirmation(1);
        assertEquals(confirmation.getId(), confirmationService.getConfirmationById(confirmation.getId()).getId());
    }

    @Test
    public void update() {
        final Confirmation confirmation = createConfirmation(1);
        confirmation.setLabel("Test Label");
        confirmationService.updateConfirmation(confirmation);
        assertEquals("Test Label", confirmation.getLabel());
        assertEquals("Test Label", confirmationService.getConfirmationById(confirmation.getId()).getLabel());
    }

    @Test
    public void delete() {
        final Confirmation confirmation = createConfirmation(1);

        final int clientId = confirmation.getClientId();
        confirmationService.deleteConfirmation(confirmation.getId());
        clientService.deleteClient(clientId);

        assertNull(confirmationService.getConfirmationById(confirmation.getId()));
    }

    @Test
    public void complete() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        assertEquals(ConfirmationStatus.COMPLETED, confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Confirmation confirmation = createConfirmation(1);
            confirmationService.completeConfirmation(confirmation.getId(), template.getId());
            assertEquals(ConfirmationStatus.COMPLETED, confirmationService.getConfirmationById(confirmation.getId()).getStatus());
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.CONFIRMATION);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void getConfirmationPdf() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        final ConfirmationPdf confirmationPdf = confirmationService.getConfirmationPdf(confirmation.getId());
        assertNotNull(confirmationPdf);
    }

    @Test
    @Ignore
    public void sendConfirmationViaEmail() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Confirmation Mail");
        email.setBody("Here's your confirmation");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
        email.setRecipients(emailRecipients);
        confirmationService.sendConfirmationViaEmail(confirmation.getId(), email);
    }

    @Test
    public void cancel() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.cancelConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.CANCELED, confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void clear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.CLEARED, confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void unclear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        confirmationService.unclearConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.COMPLETED, confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    private Confirmation createConfirmation(final int number) {
        final Client client = new Client();
        client.setName("ConfirmationServiceTest Client");
        clientService.createClient(client);

        final Confirmation confirmation = new Confirmation();
        confirmation.setClientId(client.getId());
        confirmation.setNumber(number);

        final ConfirmationItem confirmationItem1 = new ConfirmationItem();
        confirmationItem1.setTitle("Confirmation Item #1");
        confirmationItem1.setUnitPrice(new BigDecimal("11.11"));
        confirmationItem1.setQuantity(BigDecimal.ONE);
        confirmation.addConfirmationItem(confirmationItem1);

        final ConfirmationItem confirmationItem2 = new ConfirmationItem();
        confirmationItem2.setTitle("Confirmation Item #2");
        confirmationItem2.setUnitPrice(new BigDecimal("22.22"));
        confirmationItem2.setQuantity(BigDecimal.ONE);
        confirmation.addConfirmationItem(confirmationItem2);

        confirmationService.createConfirmation(confirmation);
        return confirmation;
    }

}
