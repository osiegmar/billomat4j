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
package de.siegmar.billomat4j.sdk.service;

import java.util.List;

import de.siegmar.billomat4j.sdk.domain.settings.CountryTax;
import de.siegmar.billomat4j.sdk.domain.settings.Tax;
import de.siegmar.billomat4j.sdk.domain.settings.ReminderText;
import de.siegmar.billomat4j.sdk.domain.settings.Settings;
import de.siegmar.billomat4j.sdk.service.impl.ServiceException;

/**
 * @see http://www.billomat.com/api/einstellungen/
 * @see http://www.billomat.com/api/einstellungen/steuersaetze/
 * @see http://www.billomat.com/api/einstellungen/steuerfreie-laender/
 * @see http://www.billomat.com/api/einstellungen/mahnstufen/
 */
public interface SettingsService {

    // Settings

    /**
     * Gets the account's main settings.
     *
     * @return the account's main settings
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    Settings getSettings();

    /**
     * @param settings
     *            the settings to update, must not be {@code null}
     * @throws NullPointerException
     *             if settings is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void updateSettings(Settings settings);

    // Tax

    /**
     * @return all configured taxes or an empty list if no taxes were found - never {@code null}
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    List<Tax> getTaxes();

    /**
     * Gets a tax by its id.
     *
     * @param taxId
     *            the tax's id
     * @return the tax or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    Tax getTaxById(int taxId);

    /**
     * @param tax
     *            the tax to create, must not be {@code null}
     * @throws NullPointerException
     *             if tax is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void createTax(Tax tax);

    /**
     * @param tax
     *            the tax to update, must not be {@code null}
     * @throws NullPointerException
     *             if tax is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void updateTax(Tax tax);

    /**
     * @param taxId
     *            the id of the tax to be deleted
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteTax(int taxId);

    // CountryTax

    /**
     * @return all configured country taxes or an empty list if no country taxes were found - never {@code null}
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    List<CountryTax> getCountryTaxes();

    /**
     * Gets a country tax by its id.
     *
     * @param countryTaxId
     *            the country tax's id
     * @return the country tax or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    CountryTax getCountryTaxById(int countryTaxId);

    /**
     * @param countryTax
     *            the country tax to create, must not be {@code null}
     * @throws NullPointerException
     *             if countryTax is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void createCountryTax(CountryTax countryTax);

    /**
     * @param countryTax
     *            the country tax to update, must not be {@code null}
     * @throws NullPointerException
     *             if countryTax is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void updateCountryTax(CountryTax countryTax);

    /**
     * @param countryTaxId
     *            the id of the country tax to be deleted
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteCountryTax(int countryTaxId);

    // ReminderText

    /**
     * @return all configured reminder texts or an empty list if no reminder texts were found - never {@code null}
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    List<ReminderText> getReminderTexts();

    /**
     * Gets a reminder text by its id.
     *
     * @param reminderTextId
     *            the reminder text's id
     * @return the reminder text or {@code null} if not found
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    ReminderText getReminderTextById(int reminderTextId);

    /**
     * @param reminderText
     *            the reminder text to create, must not be {@code null}
     * @throws NullPointerException
     *             if reminderText is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void createReminderText(ReminderText reminderText);

    /**
     * @param reminderText
     *            the reminder text to update, must not be {@code null}
     * @throws NullPointerException
     *             if reminderText is null
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void updateReminderText(ReminderText reminderText);

    /**
     * @param reminderTextId
     *            the id of the reminder text to be deleted
     * @throws ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteReminderText(int reminderTextId);

}
