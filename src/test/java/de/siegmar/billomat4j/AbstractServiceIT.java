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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

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
import de.siegmar.billomat4j.template.TemplateServiceIT;

@SuppressWarnings("checkstyle:classfanoutcomplexity")
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
            throw new UncheckedIOException(e);
        }

        return p;
    }

    protected byte[] loadFile(final String name) {
        final File f;
        try {
            f = new File(TemplateServiceIT.class.getResource("/" + name).toURI().toURL().getFile());
            return FileUtils.readFileToByteArray(f);
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
