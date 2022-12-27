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

package integrationtest.creditnote;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.domain.ByteString;
import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteGroup;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteGroupFilter;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteItem;
import de.siegmar.billomat4j.domain.creditnote.CreditNoteStatus;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.CreditNoteService;
import de.siegmar.billomat4j.service.TemplateService;
import integrationtest.ResourceLoader;
import integrationtest.ServiceHolder;
import integrationtest.TemplateSetup;

@ExtendWith(TemplateSetup.class)
public class CreditNoteServiceIT {

    private final List<CreditNote> createdCreditNotes = new ArrayList<>();
    private final CreditNoteService creditNoteService = ServiceHolder.CREDITNOTE;
    private final ClientService clientService = ServiceHolder.CLIENT;
    private final TemplateService templateService = ServiceHolder.TEMPLATE;

    // CreditNote

    @AfterEach
    public void cleanup() {
        for (final CreditNote creditNote : createdCreditNotes) {
            final int clientId = creditNote.getClientId();
            creditNoteService.deleteCreditNote(creditNote.getId());
            clientService.deleteClient(clientId);
        }
        createdCreditNotes.clear();
    }

    @Test
    public void findAll() {
        assertTrue(creditNoteService.findCreditNotes(null).isEmpty());
        createCreditNote(1);
        assertFalse(creditNoteService.findCreditNotes(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final CreditNoteFilter creditNoteFilter = new CreditNoteFilter().byCreditNoteNumber("1");
        List<CreditNote> creditNotes = creditNoteService.findCreditNotes(creditNoteFilter);
        assertTrue(creditNotes.isEmpty());

        final CreditNote creditNote1 = createCreditNote(1);
        createCreditNote(2);

        creditNotes = creditNoteService.findCreditNotes(creditNoteFilter);
        assertEquals(1, creditNotes.size());
        assertEquals(creditNote1.getId(), creditNotes.get(0).getId());
    }

    @Test
    public void findByCreditNoteNumber() {
        final Client client = new Client();
        client.setName("CreditNoteServiceTest Client");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNote.setNumberPre("I");
        creditNote.setNumber(5);
        creditNoteService.createCreditNote(creditNote);
        createdCreditNotes.add(creditNote);

        assertEquals(creditNote.getId(), creditNoteService.getCreditNoteByNumber("I5").orElseThrow().getId());
    }

    @Test
    public void getAllCreditNoteGroups() {
        final CreditNote creditNote1 = createCreditNote(1);
        final CreditNote creditNote2 = createCreditNote(2);

        final List<CreditNoteGroup> creditNoteGroups =
                creditNoteService.getGroupedCreditNotes(new CreditNoteGroupFilter().byDay(), null);

        assertEquals(1, creditNoteGroups.size());

        final CreditNoteGroup creditNoteGroup = creditNoteGroups.get(0);
        assertEquals(creditNote1.getTotalNet().add(creditNote2.getTotalNet()), creditNoteGroup.getTotalNet());
    }

    @Test
    public void create() {
        final CreditNote creditNote = createCreditNote(1);
        assertNotNull(creditNote.getId());
    }

    @Test
    public void getById() {
        final CreditNote creditNote = createCreditNote(1);
        assertEquals(creditNote.getId(), creditNoteService.getCreditNoteById(creditNote.getId()).orElseThrow().getId());
    }

    @Test
    public void update() {
        final CreditNote creditNote = createCreditNote(1);
        creditNote.setLabel("Test Label");
        creditNoteService.updateCreditNote(creditNote);
        assertEquals("Test Label", creditNote.getLabel());
        assertEquals("Test Label", creditNoteService.getCreditNoteById(creditNote.getId()).orElseThrow().getLabel());
    }

    @Test
    public void delete() {
        final CreditNote creditNote = createCreditNote(1);

        final int clientId = creditNote.getClientId();
        creditNoteService.deleteCreditNote(creditNote.getId());
        clientService.deleteClient(clientId);

        assertTrue(creditNoteService.getCreditNoteById(creditNote.getId()).isEmpty());

        createdCreditNotes.remove(creditNote);
    }

    @Test
    public void complete() {
        final CreditNote creditNote = createCreditNote(1);
        assertEquals(CreditNoteStatus.DRAFT, creditNote.getStatus());
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        assertEquals(CreditNoteStatus.OPEN, creditNoteService
            .getCreditNoteById(creditNote.getId()).orElseThrow().getStatus());
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final CreditNote creditNote = createCreditNote(1);
            assertEquals(CreditNoteStatus.DRAFT, creditNote.getStatus());
            creditNoteService.completeCreditNote(creditNote.getId(), null);
            assertEquals(CreditNoteStatus.OPEN, creditNoteService
                .getCreditNoteById(creditNote.getId()).orElseThrow().getStatus());
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.CREDIT_NOTE);
        template.setData(ByteString.of(ResourceLoader.loadFile("template.rtf")));

        return template;
    }

    @Test
    public void uploadSignedPdf() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        creditNoteService.uploadCreditNoteSignedPdf(creditNote.getId(), "dummy".getBytes(StandardCharsets.US_ASCII));

        assertArrayEquals("dummy".getBytes(StandardCharsets.US_ASCII),
            creditNoteService.getCreditNoteSignedPdf(creditNote.getId()).orElseThrow().getData().toBytes());
    }

    @Test
    public void getCreditNotePdf() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        assertTrue(creditNoteService.getCreditNotePdf(creditNote.getId()).isPresent());
    }

    @Test
    @Disabled("E-Mail")
    public void sendCreditNoteViaEmail() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        final Email email = new Email();
        email.setSubject("Test CreditNote Mail");
        email.setBody("Here's your creditNote");
        email.setFrom(ServiceHolder.getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(ServiceHolder.getEmail());
        email.setRecipients(emailRecipients);
        creditNoteService.sendCreditNoteViaEmail(creditNote.getId(), email);
    }

    private CreditNote createCreditNote(final int number) {
        final Client client = new Client();
        client.setName("CreditNoteServiceTest Client");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNote.setNumber(number);

        final CreditNoteItem creditNoteItem1 = new CreditNoteItem();
        creditNoteItem1.setTitle("CreditNote Item #1");
        creditNoteItem1.setUnitPrice(new BigDecimal("11.11"));
        creditNoteItem1.setQuantity(BigDecimal.ONE);
        creditNote.addCreditNoteItem(creditNoteItem1);

        final CreditNoteItem creditNoteItem2 = new CreditNoteItem();
        creditNoteItem2.setTitle("CreditNote Item #2");
        creditNoteItem2.setUnitPrice(new BigDecimal("22.22"));
        creditNoteItem2.setQuantity(BigDecimal.ONE);
        creditNote.addCreditNoteItem(creditNoteItem2);

        creditNoteService.createCreditNote(creditNote);

        createdCreditNotes.add(creditNote);

        return creditNote;
    }

}
