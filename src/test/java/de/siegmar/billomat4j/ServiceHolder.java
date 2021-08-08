/*
 * Copyright 2021 Oliver Siegmar
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

import java.util.Properties;

import de.siegmar.billomat4j.service.impl.ArticleService;
import de.siegmar.billomat4j.service.impl.BillomatConfiguration;
import de.siegmar.billomat4j.service.impl.ClientService;
import de.siegmar.billomat4j.service.impl.ConfirmationService;
import de.siegmar.billomat4j.service.impl.CreditNoteService;
import de.siegmar.billomat4j.service.impl.DeliveryNoteService;
import de.siegmar.billomat4j.service.impl.InvoiceService;
import de.siegmar.billomat4j.service.impl.OfferService;
import de.siegmar.billomat4j.service.impl.RecurringService;
import de.siegmar.billomat4j.service.impl.ReminderService;
import de.siegmar.billomat4j.service.impl.SettingsService;
import de.siegmar.billomat4j.service.impl.TemplateService;
import de.siegmar.billomat4j.service.impl.UnitService;
import de.siegmar.billomat4j.service.impl.UserService;

@SuppressWarnings({
    "checkstyle:ClassDataAbstractionCoupling",
    "checkstyle:ClassFanOutComplexity"
})
public final class ServiceHolder {

    public static final ArticleService ARTICLE;
    public static final ClientService CLIENT;
    public static final ConfirmationService CONFIRMATION;
    public static final CreditNoteService CREDITNOTE;
    public static final DeliveryNoteService DELIVERYNOTE;
    public static final InvoiceService INVOICE;
    public static final OfferService OFFER;
    public static final RecurringService RECURRING;
    public static final ReminderService REMINDER;
    public static final SettingsService SETTINGS;
    public static final TemplateService TEMPLATE;
    public static final UnitService UNIT;
    public static final UserService USER;

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = ConfigUtil.loadProperties();
        final BillomatConfiguration cfg = ConfigUtil.configure(PROPERTIES);

        ARTICLE = new ArticleService(cfg);
        CLIENT = new ClientService(cfg);
        CONFIRMATION = new ConfirmationService(cfg);
        CREDITNOTE = new CreditNoteService(cfg);
        DELIVERYNOTE = new DeliveryNoteService(cfg);
        INVOICE = new InvoiceService(cfg);
        OFFER = new OfferService(cfg);
        RECURRING = new RecurringService(cfg);
        REMINDER = new ReminderService(cfg);
        SETTINGS = new SettingsService(cfg);
        TEMPLATE = new TemplateService(cfg);
        UNIT = new UnitService(cfg);
        USER = new UserService(cfg);
    }

    private ServiceHolder() {
    }

    public static String getEmail() {
        return PROPERTIES.getProperty("email");
    }

}
