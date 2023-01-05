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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package integrationtest;

import java.util.Optional;
import java.util.Properties;

import de.siegmar.billomat4j.service.ArticleService;
import de.siegmar.billomat4j.service.BillomatConfiguration;
import de.siegmar.billomat4j.service.ClientService;
import de.siegmar.billomat4j.service.ConfirmationService;
import de.siegmar.billomat4j.service.CreditNoteService;
import de.siegmar.billomat4j.service.DeliveryNoteService;
import de.siegmar.billomat4j.service.InvoiceService;
import de.siegmar.billomat4j.service.LetterService;
import de.siegmar.billomat4j.service.OfferService;
import de.siegmar.billomat4j.service.RecurringService;
import de.siegmar.billomat4j.service.ReminderService;
import de.siegmar.billomat4j.service.SettingsService;
import de.siegmar.billomat4j.service.TemplateService;
import de.siegmar.billomat4j.service.UnitService;
import de.siegmar.billomat4j.service.UserService;

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
    public static final LetterService LETTER;
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
        env("BILLOMAT_ID").ifPresent(e -> PROPERTIES.setProperty("billomatId", e));
        env("BILLOMAT_API_KEY").ifPresent(e -> PROPERTIES.setProperty("billomatApiKey", e));
        env("BILLOMAT_APP_ID").ifPresent(e -> PROPERTIES.setProperty("billomatAppId", e));
        env("BILLOMAT_APP_SECRET").ifPresent(e -> PROPERTIES.setProperty("billomatAppSecret", e));

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
        LETTER = new LetterService(cfg);
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

    private static Optional<String> env(final String name) {
        return Optional.ofNullable(System.getenv().get(name));
    }

}
