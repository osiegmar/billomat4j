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
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

public final class ResourceLoader {

    private ResourceLoader() {
    }

    public static byte[] loadFile(final String name) {
        final File f;
        try {
            f = new File(ResourceLoader.class.getResource("/" + name).toURI().toURL().getFile());
            return FileUtils.readFileToByteArray(f);
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
