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

import net.siegmar.billomat4j.sdk.confirmation.ConfirmationItemTest;
import net.siegmar.billomat4j.sdk.creditnote.CreditNoteItemTest;
import net.siegmar.billomat4j.sdk.deliverynote.DeliveryNoteItemTest;
import net.siegmar.billomat4j.sdk.invoice.InvoiceItemTest;
import net.siegmar.billomat4j.sdk.offer.OfferItemTest;
import net.siegmar.billomat4j.sdk.recurring.RecurringItemTest;
import net.siegmar.billomat4j.sdk.reminder.ReminderItemTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfirmationItemTest.class,
    CreditNoteItemTest.class,
    DeliveryNoteItemTest.class,
    InvoiceItemTest.class,
    OfferItemTest.class,
    RecurringItemTest.class,
    ReminderItemTest.class
})
public class ItemTestSuite {

}
