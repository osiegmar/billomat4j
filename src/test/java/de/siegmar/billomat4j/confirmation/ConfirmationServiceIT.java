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

package de.siegmar.billomat4j.confirmation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.ResourceLoader;
import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.TemplateSetup;
import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationFilter;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationItem;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationPdf;
import de.siegmar.billomat4j.domain.confirmation.ConfirmationStatus;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.ConfirmationService;
import de.siegmar.billomat4j.service.TemplateService;

@ExtendWith(TemplateSetup.class)
public class ConfirmationServiceIT {

    private final List<Confirmation> createdConfirmations = new ArrayList<>();
    private final ConfirmationService confirmationService = ServiceHolder.CONFIRMATION;
    private final ClientService clientService = ServiceHolder.CLIENT;
    private final TemplateService templateService = ServiceHolder.TEMPLATE;

    // Confirmation

    @AfterEach
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

        createdConfirmations.remove(confirmation);
    }

    @Test
    public void complete() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        assertEquals(ConfirmationStatus.COMPLETED,
            confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Confirmation confirmation = createConfirmation(1);
            confirmationService.completeConfirmation(confirmation.getId(), template.getId());
            assertEquals(ConfirmationStatus.COMPLETED,
                confirmationService.getConfirmationById(confirmation.getId()).getStatus());
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.CONFIRMATION);
        template.setTemplateFile(ResourceLoader.loadFile("template.rtf"));

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
    @Disabled("E-Mail")
    public void sendConfirmationViaEmail() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Confirmation Mail");
        email.setBody("Here's your confirmation");
        email.setFrom(ServiceHolder.getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(ServiceHolder.getEmail());
        email.setRecipients(emailRecipients);
        confirmationService.sendConfirmationViaEmail(confirmation.getId(), email);
    }

    @Test
    public void cancel() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.cancelConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.CANCELED,
            confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void clear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.CLEARED,
            confirmationService.getConfirmationById(confirmation.getId()).getStatus());
    }

    @Test
    public void unclear() {
        final Confirmation confirmation = createConfirmation(1);
        confirmationService.completeConfirmation(confirmation.getId(), null);
        confirmationService.clearConfirmation(confirmation.getId());
        confirmationService.unclearConfirmation(confirmation.getId());
        assertEquals(ConfirmationStatus.COMPLETED,
            confirmationService.getConfirmationById(confirmation.getId()).getStatus());
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
