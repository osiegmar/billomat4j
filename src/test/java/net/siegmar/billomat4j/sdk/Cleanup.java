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
package net.siegmar.billomat4j.sdk;

import net.siegmar.billomat4j.sdk.domain.article.Article;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.confirmation.Confirmation;
import net.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;
import net.siegmar.billomat4j.sdk.domain.deliverynote.DeliveryNote;
import net.siegmar.billomat4j.sdk.domain.invoice.Invoice;
import net.siegmar.billomat4j.sdk.domain.offer.Offer;
import net.siegmar.billomat4j.sdk.domain.recurring.Recurring;
import net.siegmar.billomat4j.sdk.domain.reminder.Reminder;

public class Cleanup extends AbstractServiceIT {

    public static void main(final String[] args) {
        new Cleanup().cleanup();
    }

    private void cleanup() {
        for (final Confirmation confirmation : confirmationService.findConfirmations(null)) {
            confirmationService.deleteConfirmation(confirmation.getId());
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
