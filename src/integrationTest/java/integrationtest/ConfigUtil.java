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

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

import de.siegmar.billomat4j.service.BillomatConfiguration;

final class ConfigUtil {

    private ConfigUtil() {
    }

    static BillomatConfiguration configure() {
        return configure(loadProperties());
    }

    static BillomatConfiguration configure(final Properties prop) {
        final BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
        billomatConfiguration.setBillomatId(prop.getProperty("billomatId"));
        billomatConfiguration.setApiKey(prop.getProperty("billomatApiKey"));
        billomatConfiguration.setAppId(prop.getProperty("billomatAppId"));
        billomatConfiguration.setAppSecret(prop.getProperty("billomatAppSecret"));
        billomatConfiguration.setSecure(true);
        billomatConfiguration.setIgnoreUnknownProperties(true);

        return billomatConfiguration;
    }

    static Properties loadProperties() {
        final Properties p = new Properties();
        // no try-with-resources because of Spotbugs RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE bug
        try {
            final InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream("billomat.properties");
            if (in != null) {
                try {
                    p.load(in);
                } finally {
                    in.close();
                }
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        return p;
    }

}
