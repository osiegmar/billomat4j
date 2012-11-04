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

import net.siegmar.billomat4j.sdk.article.ArticleCustomFieldTest;
import net.siegmar.billomat4j.sdk.client.ClientCustomFieldTest;
import net.siegmar.billomat4j.sdk.confirmation.ConfirmationCustomFieldTest;
import net.siegmar.billomat4j.sdk.creditnote.CreditNoteCustomFieldTest;
import net.siegmar.billomat4j.sdk.deliverynote.DeliveryNoteCustomFieldTest;
import net.siegmar.billomat4j.sdk.invoice.InvoiceCustomFieldTest;
import net.siegmar.billomat4j.sdk.offer.OfferCustomFieldTest;
import net.siegmar.billomat4j.sdk.recurring.RecurringCustomFieldTest;
import net.siegmar.billomat4j.sdk.reminder.ReminderCustomFieldTest;
import net.siegmar.billomat4j.sdk.template.TemplateCustomFieldTest;
import net.siegmar.billomat4j.sdk.unit.UnitCustomFieldTest;
import net.siegmar.billomat4j.sdk.user.UserCustomFieldTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleCustomFieldTest.class,
    ClientCustomFieldTest.class,
    ConfirmationCustomFieldTest.class,
    CreditNoteCustomFieldTest.class,
    DeliveryNoteCustomFieldTest.class,
    InvoiceCustomFieldTest.class,
    OfferCustomFieldTest.class,
    RecurringCustomFieldTest.class,
    ReminderCustomFieldTest.class,
    TemplateCustomFieldTest.class,
    UnitCustomFieldTest.class,
    UserCustomFieldTest.class
})
public class CustomFieldTestSuite {

}
