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
package net.siegmar.billomat4j.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.siegmar.billomat4j.sdk.service.impl.BillomatConfiguration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

// CSOFF: HideUtilityClassConstructor
// CSOFF: UncommentedMain
public final class BillomatCli {

    public static void main(final String[] args) throws Exception {
        final Options options = new Options();
        options.addOption(OptionBuilder.create("invoiceexport"));

        final CommandLineParser parser = new PosixParser();
        final CommandLine cmd = parser.parse(options, args);

        final BillomatConfiguration billomatConfiguration = initConfig();

        if (cmd.hasOption("invoiceexport")) {
            final InvoiceCLIService invoiceCLIService = new InvoiceCLIService(billomatConfiguration);
            invoiceCLIService.exportInvoices();
        } else {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("billomat4j", options);
        }
    }

    private static BillomatConfiguration initConfig() {
        final Properties p = loadProperties();
        final BillomatConfiguration cfg = new BillomatConfiguration();
        cfg.setBillomatId(p.getProperty("billomatId"));
        cfg.setApiKey(p.getProperty("billomatApiKey"));
        cfg.setSecure(true);
        return cfg;
    }

    private static Properties loadProperties() {
        final String userHome = System.getProperty("user.home");
        final File confFile = new File(userHome, ".billomat.properties");

        final Properties p = new Properties();
        try (InputStream in = new FileInputStream(confFile)) {
            p.load(in);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return p;
    }

}
