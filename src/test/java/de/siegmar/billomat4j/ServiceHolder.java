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

import de.siegmar.billomat4j.service.ArticleService;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.ConfirmationService;
import de.siegmar.billomat4j.service.CreditNoteService;
import de.siegmar.billomat4j.service.DeliveryNoteService;
import de.siegmar.billomat4j.service.InvoiceService;
import de.siegmar.billomat4j.service.OfferService;
import de.siegmar.billomat4j.service.RecurringService;
import de.siegmar.billomat4j.service.ReminderService;
import de.siegmar.billomat4j.service.SettingsService;
import de.siegmar.billomat4j.service.TemplateService;
import de.siegmar.billomat4j.service.UnitService;
import de.siegmar.billomat4j.service.UserService;
import de.siegmar.billomat4j.service.impl.ArticleServiceImpl;
import de.siegmar.billomat4j.service.impl.BillomatConfiguration;
import de.siegmar.billomat4j.service.impl.ClientServiceImpl;
import de.siegmar.billomat4j.service.impl.ConfirmationServiceImpl;
import de.siegmar.billomat4j.service.impl.CreditNoteServiceImpl;
import de.siegmar.billomat4j.service.impl.DeliveryNoteServiceImpl;
import de.siegmar.billomat4j.service.impl.InvoiceServiceImpl;
import de.siegmar.billomat4j.service.impl.OfferServiceImpl;
import de.siegmar.billomat4j.service.impl.RecurringServiceImpl;
import de.siegmar.billomat4j.service.impl.ReminderServiceImpl;
import de.siegmar.billomat4j.service.impl.SettingsServiceImpl;
import de.siegmar.billomat4j.service.impl.TemplateServiceImpl;
import de.siegmar.billomat4j.service.impl.UnitServiceImpl;
import de.siegmar.billomat4j.service.impl.UserServiceImpl;

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

        ARTICLE = new ArticleServiceImpl(cfg);
        CLIENT = new ClientServiceImpl(cfg);
        CONFIRMATION = new ConfirmationServiceImpl(cfg);
        CREDITNOTE = new CreditNoteServiceImpl(cfg);
        DELIVERYNOTE = new DeliveryNoteServiceImpl(cfg);
        INVOICE = new InvoiceServiceImpl(cfg);
        OFFER = new OfferServiceImpl(cfg);
        RECURRING = new RecurringServiceImpl(cfg);
        REMINDER = new ReminderServiceImpl(cfg);
        SETTINGS = new SettingsServiceImpl(cfg);
        TEMPLATE = new TemplateServiceImpl(cfg);
        UNIT = new UnitServiceImpl(cfg);
        USER = new UserServiceImpl(cfg);
    }

    private ServiceHolder() {
    }

    public static String getEmail() {
        return PROPERTIES.getProperty("email");
    }

}
