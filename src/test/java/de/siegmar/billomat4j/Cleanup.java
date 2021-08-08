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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import de.siegmar.billomat4j.domain.unit.Unit;

@SuppressWarnings({
    "checkstyle:uncommentedmain",
    "checkstyle:npathcomplexity",
    "checkstyle:cyclomaticcomplexity"
    })
public class Cleanup {

    private static final Logger LOG = LoggerFactory.getLogger(Cleanup.class);

    public static void main(final String[] args) {
        new Cleanup().cleanup();
    }

    private void cleanup() {
        for (final Unit unit : ServiceHolder.UNIT.findUnits(null)) {
            LOG.info("Delete unit {}", unit);
            ServiceHolder.UNIT.deleteUnit(unit.getId());
        }

        for (final Confirmation confirmation : ServiceHolder.CONFIRMATION.findConfirmations(null)) {
            LOG.info("Delete confirmation {}", confirmation);
            ServiceHolder.CONFIRMATION.deleteConfirmation(confirmation.getId());
        }

        for (final Template template : ServiceHolder.TEMPLATE.findTemplates(null)) {
            LOG.info("Delete template {}", template);
            ServiceHolder.TEMPLATE.deleteTemplate(template.getId());
        }

        for (final CreditNote creditNote : ServiceHolder.CREDITNOTE.findCreditNotes(null)) {
            LOG.info("Delete creditNote {}", creditNote);
            ServiceHolder.CREDITNOTE.deleteCreditNote(creditNote.getId());
        }

        for (final DeliveryNote deliveryNote : ServiceHolder.DELIVERYNOTE.findDeliveryNotes(null)) {
            LOG.info("Delete deliveryNote {}", deliveryNote);
            ServiceHolder.DELIVERYNOTE.deleteDeliveryNote(deliveryNote.getId());
        }

        for (final Offer offer : ServiceHolder.OFFER.findOffers(null)) {
            LOG.info("Delete offer {}", offer);
            ServiceHolder.OFFER.deleteOffer(offer.getId());
        }

        for (final Recurring recurring : ServiceHolder.RECURRING.findRecurrings(null)) {
            LOG.info("Delete recurring {}", recurring);
            ServiceHolder.RECURRING.deleteRecurring(recurring.getId());
        }

        for (final Reminder reminder : ServiceHolder.REMINDER.findReminders(null)) {
            LOG.info("Delete reminder {}", reminder);
            ServiceHolder.REMINDER.deleteReminder(reminder.getId());
        }

        for (final Invoice invoice : ServiceHolder.INVOICE.findInvoices(null)) {
            LOG.info("Delete invoice {}", invoice);
            ServiceHolder.INVOICE.deleteInvoice(invoice.getId());
        }

        for (final Article article : ServiceHolder.ARTICLE.findArticles(null)) {
            LOG.info("Delete article {}", article);
            ServiceHolder.ARTICLE.deleteArticle(article.getId());
        }

        for (final Client client : ServiceHolder.CLIENT.findClients(null)) {
            LOG.info("Delete client {}", client);
            ServiceHolder.CLIENT.deleteClient(client.getId());
        }
    }

}
