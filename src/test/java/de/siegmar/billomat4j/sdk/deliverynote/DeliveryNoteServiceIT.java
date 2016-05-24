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
package de.siegmar.billomat4j.sdk.deliverynote;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.siegmar.billomat4j.sdk.AbstractServiceIT;
import de.siegmar.billomat4j.sdk.domain.EmailRecipients;
import de.siegmar.billomat4j.sdk.domain.client.Client;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteItem;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteStatus;
import de.siegmar.billomat4j.sdk.domain.template.Template;
import de.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import de.siegmar.billomat4j.sdk.domain.Email;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteFilter;
import de.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNotePdf;
import de.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class DeliveryNoteServiceIT extends AbstractServiceIT {

    private final List<DeliveryNote> createdDeliveryNotes = new ArrayList<>();

    // DeliveryNote

    @AfterMethod
    public void cleanup() {
        for (final DeliveryNote deliveryNote : createdDeliveryNotes) {
            final int clientId = deliveryNote.getClientId();
            deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
            clientService.deleteClient(clientId);
        }
        createdDeliveryNotes.clear();
    }

    @Test
    public void findAll() {
        assertTrue(deliveryNoteService.findDeliveryNotes(null).isEmpty());
        createDeliveryNote(1);
        assertFalse(deliveryNoteService.findDeliveryNotes(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final DeliveryNoteFilter deliveryNoteFilter = new DeliveryNoteFilter().byDeliveryNoteNumber("1");
        List<DeliveryNote> deliveryNotes = deliveryNoteService.findDeliveryNotes(deliveryNoteFilter);
        assertTrue(deliveryNotes.isEmpty());

        final DeliveryNote deliveryNote1 = createDeliveryNote(1);
        createDeliveryNote(2);

        deliveryNotes = deliveryNoteService.findDeliveryNotes(deliveryNoteFilter);
        assertEquals(deliveryNotes.size(), 1);
        assertEquals(deliveryNotes.get(0).getId(), deliveryNote1.getId());
    }

    @Test
    public void findByDeliveryNoteNumber() {
        final Client client = new Client();
        client.setName("DeliveryNoteServiceTest Client");
        clientService.createClient(client);

        final DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setClientId(client.getId());
        deliveryNote.setNumberPre("I");
        deliveryNote.setNumber(5);
        deliveryNoteService.createDeliveryNote(deliveryNote);
        createdDeliveryNotes.add(deliveryNote);

        assertEquals(deliveryNoteService.getDeliveryNoteByNumber("I5").getId(), deliveryNote.getId());
    }

    @Test
    public void create() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertNotNull(deliveryNote.getId());
    }

    @Test
    public void getById() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertEquals(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getId(), deliveryNote.getId());
    }

    @Test
    public void update() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNote.setLabel("Test Label");
        deliveryNoteService.updateDeliveryNote(deliveryNote);
        assertEquals(deliveryNote.getLabel(), "Test Label");
        assertEquals(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getLabel(), "Test Label");
    }

    @Test
    public void delete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);

        final int clientId = deliveryNote.getClientId();
        deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
        clientService.deleteClient(clientId);

        assertNull(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()));

        createdDeliveryNotes.remove(deliveryNote);
    }

    @Test
    public void complete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        Assert.assertEquals(deliveryNote.getStatus(), DeliveryNoteStatus.DRAFT);
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        assertEquals(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getStatus(),
                DeliveryNoteStatus.CREATED);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final DeliveryNote deliveryNote = createDeliveryNote(1);
            assertEquals(deliveryNote.getStatus(), DeliveryNoteStatus.DRAFT);
            deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
            assertEquals(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getStatus(),
                    DeliveryNoteStatus.CREATED);
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.DELIVERY_NOTE);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void getDeliveryNotePdf() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        final DeliveryNotePdf deliveryNotePdf = deliveryNoteService.getDeliveryNotePdf(deliveryNote.getId());
        assertNotNull(deliveryNotePdf);
    }

    @Test(enabled = false)
    public void sendDeliveryNoteViaEmail() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        final Email email = new Email();
        email.setSubject("Test DeliveryNote Mail");
        email.setBody("Here's your deliveryNote");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
        email.setRecipients(emailRecipients);
        deliveryNoteService.sendDeliveryNoteViaEmail(deliveryNote.getId(), email);
    }

    private DeliveryNote createDeliveryNote(final int number) {
        final Client client = new Client();
        client.setName("DeliveryNoteServiceTest Client");
        clientService.createClient(client);

        final DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setClientId(client.getId());
        deliveryNote.setNumber(number);

        final DeliveryNoteItem deliveryNoteItem1 = new DeliveryNoteItem();
        deliveryNoteItem1.setTitle("DeliveryNote Item #1");
        deliveryNoteItem1.setUnitPrice(new BigDecimal("11.11"));
        deliveryNoteItem1.setQuantity(BigDecimal.ONE);
        deliveryNote.addDeliveryNoteItem(deliveryNoteItem1);

        final DeliveryNoteItem deliveryNoteItem2 = new DeliveryNoteItem();
        deliveryNoteItem2.setTitle("DeliveryNote Item #2");
        deliveryNoteItem2.setUnitPrice(new BigDecimal("22.22"));
        deliveryNoteItem2.setQuantity(BigDecimal.ONE);
        deliveryNote.addDeliveryNoteItem(deliveryNoteItem2);

        deliveryNoteService.createDeliveryNote(deliveryNote);

        createdDeliveryNotes.add(deliveryNote);

        return deliveryNote;
    }

}
