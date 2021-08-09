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

package integrationtest.deliverynote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteFilter;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteItem;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNotePdf;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNoteStatus;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.DeliveryNoteService;
import integrationtest.ServiceHolder;
import integrationtest.TemplateSetup;

@ExtendWith(TemplateSetup.class)
public class DeliveryNoteServiceIT {

    private final List<DeliveryNote> createdDeliveryNotes = new ArrayList<>();
    private final DeliveryNoteService deliveryNoteService = ServiceHolder.DELIVERYNOTE;
    private final ClientService clientService = ServiceHolder.CLIENT;

    // DeliveryNote

    @AfterEach
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
        createdDeliveryNotes.add(deliveryNote);

        assertEquals(deliveryNote.getId(), deliveryNoteService.getDeliveryNoteByNumber("I5").orElseThrow().getId());
    }

    @Test
    public void create() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertNotNull(deliveryNote.getId());
    }

    @Test
    public void getById() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertEquals(deliveryNote.getId(), deliveryNoteService
            .getDeliveryNoteById(deliveryNote.getId()).orElseThrow().getId());
    }

    @Test
    public void update() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNote.setLabel("Test Label");
        deliveryNoteService.updateDeliveryNote(deliveryNote);
        assertEquals("Test Label", deliveryNote.getLabel());
        assertEquals("Test Label", deliveryNoteService
            .getDeliveryNoteById(deliveryNote.getId()).orElseThrow().getLabel());
    }

    @Test
    public void delete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);

        final int clientId = deliveryNote.getClientId();
        deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
        clientService.deleteClient(clientId);

        assertTrue(deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).isEmpty());

        createdDeliveryNotes.remove(deliveryNote);
    }

    @Test
    public void complete() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        assertEquals(DeliveryNoteStatus.DRAFT, deliveryNote.getStatus());
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        assertEquals(DeliveryNoteStatus.CREATED,
            deliveryNoteService.getDeliveryNoteById(deliveryNote.getId()).orElseThrow().getStatus());
    }

    @Test
    public void getDeliveryNotePdf() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        final DeliveryNotePdf deliveryNotePdf = deliveryNoteService
            .getDeliveryNotePdf(deliveryNote.getId()).orElseThrow();
        assertNotNull(deliveryNotePdf);
    }

    @Test
    @Disabled("E-Mail")
    public void sendDeliveryNoteViaEmail() {
        final DeliveryNote deliveryNote = createDeliveryNote(1);
        deliveryNoteService.completeDeliveryNote(deliveryNote.getId(), null);
        final Email email = new Email();
        email.setSubject("Test DeliveryNote Mail");
        email.setBody("Here's your deliveryNote");
        email.setFrom(ServiceHolder.getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(ServiceHolder.getEmail());
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
