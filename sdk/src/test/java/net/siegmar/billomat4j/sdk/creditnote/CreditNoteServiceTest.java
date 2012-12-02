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
package net.siegmar.billomat4j.sdk.creditnote;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.EmailRecipients;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteFilter;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteGroup;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteGroupFilter;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteItem;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNotePdf;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class CreditNoteServiceTest extends AbstractServiceTest {

    private final List<CreditNote> createdCreditNotes = new ArrayList<>();

    // CreditNote

    @AfterMethod
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
        assertEquals(creditNotes.size(), 1);
        assertEquals(creditNotes.get(0).getId(), creditNote1.getId());
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

        assertEquals(creditNoteService.getCreditNoteByNumber("I5").getId(), creditNote.getId());
    }

    @Test
    public void getAllCreditNoteGroups() {
        final CreditNote creditNote1 = createCreditNote(1);
        final CreditNote creditNote2 = createCreditNote(2);

        final List<CreditNoteGroup> creditNoteGroups = creditNoteService.getGroupedCreditNotes(new CreditNoteGroupFilter().byDay(), null);
        assertEquals(creditNoteGroups.size(), 1);

        final CreditNoteGroup creditNoteGroup = creditNoteGroups.get(0);
        assertEquals(creditNoteGroup.getTotalNet(), creditNote1.getTotalNet().add(creditNote2.getTotalNet()));
    }

    @Test
    public void create() {
        final CreditNote creditNote = createCreditNote(1);
        assertNotNull(creditNote.getId());
    }

    @Test
    public void getById() {
        final CreditNote creditNote = createCreditNote(1);
        assertEquals(creditNoteService.getCreditNoteById(creditNote.getId()).getId(), creditNote.getId());
    }

    @Test
    public void update() {
        final CreditNote creditNote = createCreditNote(1);
        creditNote.setLabel("Test Label");
        creditNoteService.updateCreditNote(creditNote);
        assertEquals(creditNote.getLabel(), "Test Label");
        assertEquals(creditNoteService.getCreditNoteById(creditNote.getId()).getLabel(), "Test Label");
    }

    @Test
    public void delete() {
        final CreditNote creditNote = createCreditNote(1);

        final int clientId = creditNote.getClientId();
        creditNoteService.deleteCreditNote(creditNote.getId());
        clientService.deleteClient(clientId);

        assertNull(creditNoteService.getCreditNoteById(creditNote.getId()));

        createdCreditNotes.remove(creditNote);
    }

    @Test
    public void complete() {
        final CreditNote creditNote = createCreditNote(1);
        assertEquals(creditNote.getStatus(), CreditNoteStatus.DRAFT);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        assertEquals(creditNoteService.getCreditNoteById(creditNote.getId()).getStatus(), CreditNoteStatus.OPEN);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final CreditNote creditNote = createCreditNote(1);
            assertEquals(creditNote.getStatus(), CreditNoteStatus.DRAFT);
            creditNoteService.completeCreditNote(creditNote.getId(), null);
            assertEquals(creditNoteService.getCreditNoteById(creditNote.getId()).getStatus(), CreditNoteStatus.OPEN);
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.CREDIT_NOTE);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void uploadSignedPdf() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        creditNoteService.uploadCreditNoteSignedPdf(creditNote.getId(), "dummy".getBytes());

        assertEquals(creditNoteService.getCreditNoteSignedPdf(creditNote.getId()).getBase64file(), "dummy".getBytes());
    }

    @Test
    public void getCreditNotePdf() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        final CreditNotePdf creditNotePdf = creditNoteService.getCreditNotePdf(creditNote.getId());
        assertNotNull(creditNotePdf);
    }

    @Test(enabled = false)
    public void sendCreditNoteViaEmail() {
        final CreditNote creditNote = createCreditNote(1);
        creditNoteService.completeCreditNote(creditNote.getId(), null);
        final Email email = new Email();
        email.setSubject("Test CreditNote Mail");
        email.setBody("Here's your creditNote");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
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
