/*
 * Copyright 2023 Oliver Siegmar
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

package de.siegmar.billomat4j.domain;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

public final class ByteString {

    private final byte[] data;

    private ByteString(final byte[] data) {
        this.data = Objects.requireNonNull(data).clone();
    }

    public static ByteString of(final byte[] data) {
        return new ByteString(data);
    }

    public byte[] toBytes() {
        return data.clone();
    }

    public void transferTo(final OutputStream out) throws IOException {
        out.write(data);
    }

    public void saveTo(final Path path, final OpenOption... openOptions) throws IOException {
        Files.write(path, data, openOptions);
    }

    @Override
    public String toString() {
        return "ByteString[" + data.length + " bytes]";
    }

}
