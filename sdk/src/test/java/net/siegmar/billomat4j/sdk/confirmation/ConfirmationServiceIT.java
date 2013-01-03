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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceIT;
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ConfirmationServiceIT extends AbstractServiceIT {

    private final List<Confirmation> createdConfirmations = new ArrayList<>();

    // Confirmation

    @AfterMethod
    public void cleanup() {
        for (final Confirmation confirmation : createdConfirmations) {
            confirmationService.deleteConfirmation(confirmation.getId());
            clientService.deleteClient(confirmation.getClientId());
        }
        createdConfirmations.clear();
    }

    @Test
    public void findAll() {
        assertTrue(confirmationService.findConfirmations(null).isEmpty());
        createConfirmation(1);
        assertFalse(confirmationService.findConfirmations(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final ConfirmationFilter confirmationFilter = new ConfirmationFilter().byConfirmationNumber("1");
        List<Confirmation> confirmations = confirmationService.findConfirmations(confirmationFilter);
        assertTrue(confirmations.isEmpty());

        final Confirmation confirmation1 = createConfirmation(1);
        createConfirmation(2);

        confirmations = confirmationService.findConfirmations(confirmationFilter);
        assertEquals(confirmations.size(), 1);
        assertEquals(confirmations.get(0).getId(), confirmation1.getId());
    }

    @Test
    public void create() {
        final Confirmation confirmation = createConfirmation(1);
        assertNotNull(confirmation.getId());
    }

    @Test
    public void getById() {
        final Confirmation confirmation = createConfirmation(1);
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getId(), confirmation.getId());
    }

    @Test
    public void update() {
        final Confirmation confirmation = createConfirmation(1);
        confirmation.setLabel("Test Label");
        confirmationService.updateConfirmation(confirmation);
        assertEquals(confirmation.getLabel(), "Test Label");
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getLabel(), "Test Label");
    }

    @Test
    public void delete() {
        final Confirmation confirmation = createConfirmation(1);

        final int clientId = confirmation.getClientId();
        confirmationService.deleteConfirmation(confirmation.getId());
        clientService.deleteClient(clientId);

        assertNull(confirmationService.getConfirmationById(confirmation.getId()));

        createdConfirmations.remove(confirmation);
    }

    @Test
    public void complete() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getStatus(), ConfirmationStatus.COMPLETED);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Confirmation confirmation = createConfirmation(1);
            confirmationService.completeConfirmation(confirmation.getId(), template.getId());
            assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getStatus(), ConfirmationStatus.COMPLETED);
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

    @Test(enabled = false)
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
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getStatus(), ConfirmationStatus.CANCELED);
    }

    @Test
    public void clear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getStatus(), ConfirmationStatus.CLEARED);
    }

    @Test
    public void unclear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        confirmationService.unclearConfirmation(confirmation.getId());
        assertEquals(confirmationService.getConfirmationById(confirmation.getId()).getStatus(), ConfirmationStatus.COMPLETED);
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
        createdConfirmations.add(confirmation);

        return confirmation;
    }

}
