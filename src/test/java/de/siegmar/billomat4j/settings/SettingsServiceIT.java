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

package de.siegmar.billomat4j.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.AbstractServiceIT;
import de.siegmar.billomat4j.domain.settings.CountryTax;
import de.siegmar.billomat4j.domain.settings.ReminderText;
import de.siegmar.billomat4j.domain.settings.Settings;
import de.siegmar.billomat4j.domain.settings.Tax;

public class SettingsServiceIT extends AbstractServiceIT {

    // Settings

    @Test
    public void get() {
        final Settings settings = settingsService.getSettings();
        assertNotNull(settings);
    }

    @Test
    public void update() {
        // Get
        Settings settings = settingsService.getSettings();
        assertEquals("", settings.getInvoiceIntro());

        // Update
        settings.setInvoiceIntro("Invoice Intro");
        settingsService.updateSettings(settings);
        assertEquals("Invoice Intro", settings.getInvoiceIntro());

        // Compare
        settings = settingsService.getSettings();
        assertEquals("Invoice Intro", settings.getInvoiceIntro());

        // Revert
        settings.setInvoiceIntro("");
        settingsService.updateSettings(settings);

        // Compare
        settings = settingsService.getSettings();
        assertEquals("", settings.getInvoiceIntro());
    }

    // Tax

    @Test
    public void tax() {
        assertTrue(settingsService.getTaxes().isEmpty());

        final Tax tax = new Tax();
        tax.setName("Test Tax");
        tax.setRate(new BigDecimal("19"));
        tax.setDefaultTax(true);
        settingsService.createTax(tax);
        final Integer taxId = tax.getId();
        assertNotNull(taxId);

        tax.setName("Test Tax Update");
        settingsService.updateTax(tax);
        assertEquals("Test Tax Update", tax.getName());
        assertEquals("Test Tax Update", settingsService.getTaxById(taxId).getName());

        settingsService.deleteTax(taxId);
        assertNull(settingsService.getTaxById(taxId));
        assertTrue(settingsService.getTaxes().isEmpty());
    }

    // CountryTax

    @Test
    public void countryTax() {
        assertTrue(settingsService.getCountryTaxes().isEmpty());

        final CountryTax countryTax = new CountryTax();
        countryTax.setCountryCode("DE");
        settingsService.createCountryTax(countryTax);
        final Integer taxId = countryTax.getId();
        assertNotNull(taxId);

        countryTax.setCountryCode("AT");
        settingsService.updateCountryTax(countryTax);
        assertEquals("AT", countryTax.getCountryCode());
        assertEquals("AT", settingsService.getCountryTaxById(taxId).getCountryCode());

        settingsService.deleteCountryTax(taxId);
        assertNull(settingsService.getCountryTaxById(taxId));
        assertTrue(settingsService.getCountryTaxes().isEmpty());
    }

    // ReminderText

    @Test
    public void reminderText() {
        assertTrue(settingsService.getReminderTexts().isEmpty());

        final ReminderText reminderText = new ReminderText();
        reminderText.setName("Test ReminderText");
        reminderText.setSubject("Test Subject");
        reminderText.setHeader("Test Header");
        reminderText.setFooter("Test Footer");
        reminderText.setChargeName("Test ChargeName");
        reminderText.setChargeDescription("Test ChargeDescription");
        reminderText.setChargeAmount(new BigDecimal("2.50"));
        settingsService.createReminderText(reminderText);
        final Integer reminderTextId = reminderText.getId();
        assertNotNull(reminderTextId);

        reminderText.setName("Test ReminderText Update");
        settingsService.updateReminderText(reminderText);
        assertEquals("Test ReminderText Update", reminderText.getName());
        assertEquals("Test ReminderText Update", settingsService.getReminderTextById(reminderTextId).getName());

        settingsService.deleteReminderText(reminderTextId);
        assertNull(settingsService.getReminderTextById(reminderTextId));
        assertTrue(settingsService.getReminderTexts().isEmpty());
    }

}
