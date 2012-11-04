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
package net.siegmar.billomat4j.sdk.suite;

import net.siegmar.billomat4j.sdk.article.ArticleTagTest;
import net.siegmar.billomat4j.sdk.client.ClientTagTest;
import net.siegmar.billomat4j.sdk.confirmation.ConfirmationTagTest;
import net.siegmar.billomat4j.sdk.creditnote.CreditNoteTagTest;
import net.siegmar.billomat4j.sdk.deliverynote.DeliveryNoteTagTest;
import net.siegmar.billomat4j.sdk.invoice.InvoiceTagTest;
import net.siegmar.billomat4j.sdk.offer.OfferTagTest;
import net.siegmar.billomat4j.sdk.recurring.RecurringTagTest;
import net.siegmar.billomat4j.sdk.reminder.ReminderTagTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleTagTest.class,
    ClientTagTest.class,
    ConfirmationTagTest.class,
    CreditNoteTagTest.class,
    DeliveryNoteTagTest.class,
    InvoiceTagTest.class,
    OfferTagTest.class,
    RecurringTagTest.class,
    ReminderTagTest.class
})
public class TagTestSuite {

}
