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
package net.siegmar.billomat4j.sdk.deliverynote;

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
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteFilter;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteItem;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNotePdf;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNoteStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class DeliveryNoteServiceTest extends AbstractServiceTest {

    // DeliveryNote

    @After
    public void cleanup() {
        // Clean up all deliveryNotes
        List<DeliveryNote> deliveryNotes = deliveryNoteService.findDeliveryNotes(null);
        if (!deliveryNotes.isEmpty()) {
            for (final DeliveryNote deliveryNote : deliveryNotes) {
                final int clientId = deliveryNote.getClientId();
                deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
                clientService.deleteClient(clientId);
            }

            deliveryNotes = deliveryNoteService.findDeliveryNotes(null);
            assertTrue(deliveryNotes.isEmpty());
        }
    }

    @Test
    public void findAll() {
        List<DeliveryNote> deliveryNotes = deliveryNoteService.findDeliveryNotes(null);
        assertTrue(deliveryNotes.isEmpty());

        final DeliveryNote deliveryNote1 = createDeliveryNote(1);
        final DeliveryNote deliveryNote2 = createDeliveryNote(2);

        deliveryNotes = deliveryNoteService.findDeliveryNotes(null);
        assertEquals(2, deliveryNotes.size());
        assertEquals(deliveryNote1.getId(), deliveryNotes.get(0).getId());
        assertEquals(deliveryNote2.getId(), deliveryNotes.get(1).getId());
    }

    @Test
    public void findFiltered() {
        assertTrue(deliveryNoteService.findDeliveryNotes(null).isEmpty());

        final DeliveryNote deliveryNote1 = createDeliveryNote(1);
        createDeliveryNote(2);

        final List<DeliveryNote> deliveryNotes = deliveryNoteService.findDeliveryNotes(new DeliveryNoteFilter().byDeliveryNoteNumber("1"));
        assertEquals(1, deliveryNotes.size());
        assertEquals(deliveryNote1.getId(), deliveryNotes.get(0).getId());
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

        assertEquals(deliveryNote.getId(), deliveryNoteService.getDeliveryNoteByNumber("I5").getId());
    }

    @Test
    public void create() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertNotNull(deliveryNote.getId());
    }

    @Test
    public void getById() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertEquals(deliveryNote.getId(), deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getId());
    }

    @Test
    public void update() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNote.setLabel("Test Label");
        deliveryNoteService.updateDeliveryNote(deliveryNote);
        assertEquals("Test Label", deliveryNote.getLabel());
        assertEquals("Test Label", deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getLabel());
    }

    @Test
    public void delete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);

        final int clientId = deliveryNote.getClientId();
        deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
        clientService.deleteClient(clientId);

        assertNull(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()));
    }

    @Test
    public void complete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertEquals(DeliveryNoteStatus.DRAFT, deliveryNote.getStatus());
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        assertEquals(DeliveryNoteStatus.CREATED, deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final DeliveryNote deliveryNote = createDeliveryNote(1);
            assertEquals(DeliveryNoteStatus.DRAFT, deliveryNote.getStatus());
            deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
            assertEquals(DeliveryNoteStatus.CREATED, deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).getStatus());
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

    @Test
    @Ignore
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
        return deliveryNote;
    }

}
