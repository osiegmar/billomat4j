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
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class Billomat4JSettings {

    private static final Properties SETTINGS;

    static {
        SETTINGS = loadProperties("billomat4j-settings.properties");
    }

    private Billomat4JSettings() {
    }

    private static Properties loadProperties(final String name) {
        final Properties properties = new Properties();
        try (InputStream is = getResource(name)) {
            properties.load(is);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        return properties;
    }

    private static InputStream getResource(final String name) {
        return Billomat4JSettings.class.getClassLoader().getResourceAsStream(name);
    }

    public static String getVersion() {
        return SETTINGS.getProperty("version");
    }

}
