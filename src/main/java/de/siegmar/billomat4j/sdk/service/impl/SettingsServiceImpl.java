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
package de.siegmar.billomat4j.sdk.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.sdk.domain.settings.CountryTax;
import de.siegmar.billomat4j.sdk.domain.settings.CountryTaxes;
import de.siegmar.billomat4j.sdk.domain.settings.ReminderText;
import de.siegmar.billomat4j.sdk.domain.settings.ReminderTexts;
import de.siegmar.billomat4j.sdk.domain.settings.Settings;
import de.siegmar.billomat4j.sdk.domain.settings.Tax;
import de.siegmar.billomat4j.sdk.domain.settings.Taxes;
import de.siegmar.billomat4j.sdk.service.SettingsService;

public class SettingsServiceImpl extends AbstractService implements SettingsService {

    private static final String RESOURCE = "settings";
    private static final String TAX_RESOURCE = "taxes";
    private static final String COUNTRY_TAX_RESOURCE = "country-taxes";
    private static final String REMINDER_TEXT_RESOURCE = "reminder-texts";

    public SettingsServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Settings

    @Override
    public Settings getSettings() {
        try {
            final byte[] data = requestHelper.get(RESOURCE, null, null, null);
            return objectReader.withType(Settings.class).readValue(data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSettings(final Settings settings) {
        try {
            final byte[] requestData = objectWriter.writeValueAsBytes(Validate.notNull(settings));
            final byte[] responseData = requestHelper.put(RESOURCE, null, null, requestData);
            objectReader.withType(Settings.class).withValueToUpdate(settings).readValue(responseData);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    // Tax

    @Override
    public List<Tax> getTaxes() {
        return getAllPagesFromResource(TAX_RESOURCE, Taxes.class, null);
    }

    @Override
    public Tax getTaxById(final int taxId) {
        return getById(TAX_RESOURCE, Tax.class, taxId);
    }

    @Override
    public void createTax(final Tax tax) {
        create(TAX_RESOURCE, Validate.notNull(tax));
    }

    @Override
    public void updateTax(final Tax tax) {
        update(TAX_RESOURCE, Validate.notNull(tax));
    }

    @Override
    public void deleteTax(final int taxId) {
        delete(TAX_RESOURCE, taxId);
    }

    // CountryTax

    @Override
    public List<CountryTax> getCountryTaxes() {
        return getAllPagesFromResource(COUNTRY_TAX_RESOURCE, CountryTaxes.class, null);
    }

    @Override
    public CountryTax getCountryTaxById(final int countryTaxId) {
        return getById(COUNTRY_TAX_RESOURCE, CountryTax.class, countryTaxId);
    }

    @Override
    public void createCountryTax(final CountryTax countryTax) {
        create(COUNTRY_TAX_RESOURCE, Validate.notNull(countryTax));
    }

    @Override
    public void updateCountryTax(final CountryTax countryTax) {
        update(COUNTRY_TAX_RESOURCE, Validate.notNull(countryTax));
    }

    @Override
    public void deleteCountryTax(final int countryTaxId) {
        delete(COUNTRY_TAX_RESOURCE, countryTaxId);
    }

    // ReminderText

    @Override
    public List<ReminderText> getReminderTexts() {
        return getAllPagesFromResource(REMINDER_TEXT_RESOURCE, ReminderTexts.class, null);
    }

    @Override
    public ReminderText getReminderTextById(final int reminderTextId) {
        return getById(REMINDER_TEXT_RESOURCE, ReminderText.class, reminderTextId);
    }

    @Override
    public void createReminderText(final ReminderText reminderText) {
        create(REMINDER_TEXT_RESOURCE, Validate.notNull(reminderText));
    }

    @Override
    public void updateReminderText(final ReminderText reminderText) {
        update(REMINDER_TEXT_RESOURCE, Validate.notNull(reminderText));
    }

    @Override
    public void deleteReminderText(final int reminderTextId) {
        delete(REMINDER_TEXT_RESOURCE, reminderTextId);
    }

}
