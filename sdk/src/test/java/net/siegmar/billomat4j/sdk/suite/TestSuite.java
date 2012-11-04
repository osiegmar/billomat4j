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

import net.siegmar.billomat4j.sdk.article.ArticleServiceTest;
import net.siegmar.billomat4j.sdk.client.ClientServiceTest;
import net.siegmar.billomat4j.sdk.confirmation.ConfirmationServiceTest;
import net.siegmar.billomat4j.sdk.creditnote.CreditNoteServiceTest;
import net.siegmar.billomat4j.sdk.deliverynote.DeliveryNoteServiceTest;
import net.siegmar.billomat4j.sdk.invoice.InvoiceServiceTest;
import net.siegmar.billomat4j.sdk.offer.OfferServiceTest;
import net.siegmar.billomat4j.sdk.recurring.RecurringServiceTest;
import net.siegmar.billomat4j.sdk.reminder.ReminderServiceTest;
import net.siegmar.billomat4j.sdk.settings.SettingsServiceTest;
import net.siegmar.billomat4j.sdk.template.TemplateServiceTest;
import net.siegmar.billomat4j.sdk.unit.UnitServiceTest;
import net.siegmar.billomat4j.sdk.user.UserServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleServiceTest.class,
    ClientServiceTest.class,
    ConfirmationServiceTest.class,
    CreditNoteServiceTest.class,
    DeliveryNoteServiceTest.class,
    InvoiceServiceTest.class,
    OfferServiceTest.class,
    RecurringServiceTest.class,
    ReminderServiceTest.class,
    SettingsServiceTest.class,
    TemplateServiceTest.class,
    UnitServiceTest.class,
    UserServiceTest.class,
    CustomFieldTestSuite.class,
    PropertyTestSuite.class,
    TagTestSuite.class,
    CommentTestSuite.class,
    ItemTestSuite.class,
    PaymentTestSuite.class
})
public class TestSuite {

}
