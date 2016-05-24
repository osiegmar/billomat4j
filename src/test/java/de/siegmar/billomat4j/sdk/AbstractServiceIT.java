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
package de.siegmar.billomat4j.sdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import de.siegmar.billomat4j.sdk.service.ArticleService;
import de.siegmar.billomat4j.sdk.service.ClientService;
import de.siegmar.billomat4j.sdk.service.CreditNoteService;
import de.siegmar.billomat4j.sdk.service.RecurringService;
import de.siegmar.billomat4j.sdk.service.SettingsService;
import de.siegmar.billomat4j.sdk.service.UserService;
import de.siegmar.billomat4j.sdk.service.impl.ArticleServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.ClientServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.DeliveryNoteServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.InvoiceServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.OfferServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.ReminderServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.SettingsServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.UnitServiceImpl;
import de.siegmar.billomat4j.sdk.template.TemplateServiceIT;
import de.siegmar.billomat4j.sdk.service.ConfirmationService;
import de.siegmar.billomat4j.sdk.service.DeliveryNoteService;
import de.siegmar.billomat4j.sdk.service.InvoiceService;
import de.siegmar.billomat4j.sdk.service.OfferService;
import de.siegmar.billomat4j.sdk.service.ReminderService;
import de.siegmar.billomat4j.sdk.service.TemplateService;
import de.siegmar.billomat4j.sdk.service.UnitService;
import de.siegmar.billomat4j.sdk.service.impl.BillomatConfiguration;
import de.siegmar.billomat4j.sdk.service.impl.ConfirmationServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.CreditNoteServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.RecurringServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.TemplateServiceImpl;
import de.siegmar.billomat4j.sdk.service.impl.UserServiceImpl;

import org.apache.commons.io.FileUtils;

public abstract class AbstractServiceIT {

    private static final Properties PROPERTIES;

    protected final ArticleService articleService;
    protected final ClientService clientService;
    protected final ConfirmationService confirmationService;
    protected final CreditNoteService creditNoteService;
    protected final DeliveryNoteService deliveryNoteService;
    protected final InvoiceService invoiceService;
    protected final OfferService offerService;
    protected final RecurringService recurringService;
    protected final ReminderService reminderService;
    protected final SettingsService settingsService;
    protected final TemplateService templateService;
    protected final UnitService unitService;
    protected final UserService userService;

    static {
        PROPERTIES = loadProperties();
    }

    protected AbstractServiceIT() {
        final BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
        billomatConfiguration.setBillomatId(PROPERTIES.getProperty("billomatId"));
        billomatConfiguration.setApiKey(PROPERTIES.getProperty("billomatApiKey"));
        billomatConfiguration.setAppId(PROPERTIES.getProperty("billomatAppId"));
        billomatConfiguration.setAppSecret(PROPERTIES.getProperty("billomatAppSecret"));
        billomatConfiguration.setSecure(true);
        billomatConfiguration.setIgnoreUnknownProperties(true);

        articleService = new ArticleServiceImpl(billomatConfiguration);
        clientService = new ClientServiceImpl(billomatConfiguration);
        confirmationService = new ConfirmationServiceImpl(billomatConfiguration);
        creditNoteService = new CreditNoteServiceImpl(billomatConfiguration);
        deliveryNoteService = new DeliveryNoteServiceImpl(billomatConfiguration);
        invoiceService = new InvoiceServiceImpl(billomatConfiguration);
        offerService = new OfferServiceImpl(billomatConfiguration);
        recurringService = new RecurringServiceImpl(billomatConfiguration);
        reminderService = new ReminderServiceImpl(billomatConfiguration);
        settingsService = new SettingsServiceImpl(billomatConfiguration);
        templateService = new TemplateServiceImpl(billomatConfiguration);
        unitService = new UnitServiceImpl(billomatConfiguration);
        userService = new UserServiceImpl(billomatConfiguration);
    }

    protected String getEmail() {
        return PROPERTIES.getProperty("email");
    }

    private static Properties loadProperties() {
        final Properties p = new Properties();
        try (InputStream in = AbstractServiceIT.class.getResourceAsStream("/billomat.properties")) {
            p.load(in);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }

        return p;
    }

    protected byte[] loadFile(final String name) {
        File f;
        try {
            f = new File(TemplateServiceIT.class.getResource("/" + name).toURI().toURL().getFile());
            return FileUtils.readFileToByteArray(f);
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
