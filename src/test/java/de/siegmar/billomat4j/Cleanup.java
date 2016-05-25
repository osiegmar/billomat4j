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
package de.siegmar.billomat4j;

import de.siegmar.billomat4j.domain.article.Article;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.confirmation.Confirmation;
import de.siegmar.billomat4j.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.domain.deliverynote.DeliveryNote;
import de.siegmar.billomat4j.domain.invoice.Invoice;
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.recurring.Recurring;
import de.siegmar.billomat4j.domain.reminder.Reminder;
import de.siegmar.billomat4j.domain.template.Template;

@SuppressWarnings({
    "checkstyle:uncommentedmain",
    "checkstyle:npathcomplexity",
    "checkstyle:cyclomaticcomplexity"
    })
public class Cleanup extends AbstractServiceIT {

    public static void main(final String[] args) {
        new Cleanup().cleanup();
    }

    private void cleanup() {
        for (final Confirmation confirmation : confirmationService.findConfirmations(null)) {
            confirmationService.deleteConfirmation(confirmation.getId());
        }

        for (final Template template : templateService.findTemplates(null)) {
            templateService.deleteTemplate(template.getId());
        }

        for (final CreditNote creditNote : creditNoteService.findCreditNotes(null)) {
            creditNoteService.deleteCreditNote(creditNote.getId());
        }

        for (final DeliveryNote deliveryNote : deliveryNoteService.findDeliveryNotes(null)) {
            deliveryNoteService.deleteDeliveryNote(deliveryNote.getId());
        }

        for (final Offer offer : offerService.findOffers(null)) {
            offerService.deleteOffer(offer.getId());
        }

        for (final Recurring recurring : recurringService.findRecurrings(null)) {
            recurringService.deleteRecurring(recurring.getId());
        }

        for (final Reminder reminder : reminderService.findReminders(null)) {
            reminderService.deleteReminder(reminder.getId());
        }

        for (final Invoice invoice : invoiceService.findInvoices(null)) {
            invoiceService.deleteInvoice(invoice.getId());
        }

        for (final Article article : articleService.findArticles(null)) {
            articleService.deleteArticle(article.getId());
        }

        for (final Client client : clientService.findClients(null)) {
            clientService.deleteClient(client.getId());
        }
    }

}
