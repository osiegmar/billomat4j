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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.siegmar.billomat4j.domain.settings.CountryTax;
import de.siegmar.billomat4j.domain.settings.CountryTaxes;
import de.siegmar.billomat4j.domain.settings.ReminderText;
import de.siegmar.billomat4j.domain.settings.ReminderTexts;
import de.siegmar.billomat4j.domain.settings.Settings;
import de.siegmar.billomat4j.domain.settings.Tax;
import de.siegmar.billomat4j.domain.settings.Taxes;
import lombok.NonNull;

public class SettingsService extends AbstractService {

    private static final String RESOURCE = "settings";
    private static final String TAX_RESOURCE = "taxes";
    private static final String COUNTRY_TAX_RESOURCE = "country-taxes";
    private static final String REMINDER_TEXT_RESOURCE = "reminder-texts";

    public SettingsService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Settings

    /**
     * Gets the account's main settings.
     *
     * @return the account's main settings
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Settings getSettings() {
        try {
            final byte[] data = requestHelper.get(RESOURCE, null, null, null);
            return objectReader.forType(Settings.class).readValue(data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param settings the settings to update, must not be {@code null}
     * @throws NullPointerException if settings is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateSettings(final Settings settings) {
        try {
            final byte[] requestData = objectWriter.writeValueAsBytes(Objects.requireNonNull(settings));
            final byte[] responseData = requestHelper.put(RESOURCE, null, null, requestData);
            objectReader.forType(Settings.class).withValueToUpdate(settings).readValue(responseData);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

    // Tax

    /**
     * @return all configured taxes or an empty list if no taxes were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Tax> getTaxes() {
        return getAllPagesFromResource(TAX_RESOURCE, Taxes.class, null);
    }

    /**
     * Gets a tax by its id.
     *
     * @param taxId the tax's id
     * @return the tax
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Tax> getTaxById(final int taxId) {
        return getById(TAX_RESOURCE, Tax.class, taxId);
    }

    /**
     * @param tax the tax to create, must not be {@code null}
     * @throws NullPointerException if tax is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createTax(@NonNull final Tax tax) {
        create(TAX_RESOURCE, tax);
    }

    /**
     * @param tax the tax to update, must not be {@code null}
     * @throws NullPointerException if tax is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateTax(@NonNull final Tax tax) {
        update(TAX_RESOURCE, tax);
    }

    /**
     * @param taxId the id of the tax to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteTax(final int taxId) {
        delete(TAX_RESOURCE, taxId);
    }

    // CountryTax

    /**
     * @return all configured country taxes or an empty list if no country taxes were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<CountryTax> getCountryTaxes() {
        return getAllPagesFromResource(COUNTRY_TAX_RESOURCE, CountryTaxes.class, null);
    }

    /**
     * Gets a country tax by its id.
     *
     * @param countryTaxId the country tax's id
     * @return the country tax
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<CountryTax> getCountryTaxById(final int countryTaxId) {
        return getById(COUNTRY_TAX_RESOURCE, CountryTax.class, countryTaxId);
    }

    /**
     * @param countryTax the country tax to create, must not be {@code null}
     * @throws NullPointerException if countryTax is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createCountryTax(@NonNull final CountryTax countryTax) {
        create(COUNTRY_TAX_RESOURCE, countryTax);
    }

    /**
     * @param countryTax the country tax to update, must not be {@code null}
     * @throws NullPointerException if countryTax is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateCountryTax(@NonNull final CountryTax countryTax) {
        update(COUNTRY_TAX_RESOURCE, countryTax);
    }

    /**
     * @param countryTaxId the id of the country tax to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteCountryTax(final int countryTaxId) {
        delete(COUNTRY_TAX_RESOURCE, countryTaxId);
    }

    // ReminderText

    /**
     * @return all configured reminder texts or an empty list if no reminder texts were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<ReminderText> getReminderTexts() {
        return getAllPagesFromResource(REMINDER_TEXT_RESOURCE, ReminderTexts.class, null);
    }

    /**
     * Gets a reminder text by its id.
     *
     * @param reminderTextId the reminder text's id
     * @return the reminder text
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<ReminderText> getReminderTextById(final int reminderTextId) {
        return getById(REMINDER_TEXT_RESOURCE, ReminderText.class, reminderTextId);
    }

    /**
     * @param reminderText the reminder text to create, must not be {@code null}
     * @throws NullPointerException if reminderText is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createReminderText(@NonNull final ReminderText reminderText) {
        create(REMINDER_TEXT_RESOURCE, reminderText);
    }

    /**
     * @param reminderText the reminder text to update, must not be {@code null}
     * @throws NullPointerException if reminderText is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateReminderText(@NonNull final ReminderText reminderText) {
        update(REMINDER_TEXT_RESOURCE, reminderText);
    }

    /**
     * @param reminderTextId the id of the reminder text to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteReminderText(final int reminderTextId) {
        delete(REMINDER_TEXT_RESOURCE, reminderTextId);
    }

}
