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
package de.siegmar.billomat4j.sdk.domain;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("pdf")
public abstract class AbstractDocumentPdf extends AbstractMeta {

    private String filename;
    private String mimetype;
    private int filesize;
    private byte[] base64file;

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(final String mimetype) {
        this.mimetype = mimetype;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(final int filesize) {
        this.filesize = filesize;
    }

    public byte[] getBase64file() {
        return base64file.clone();
    }

    public void setBase64file(final byte[] base64file) {
        this.base64file = base64file.clone();
    }

    public void saveTo(final File target) throws IOException {
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(target))) {
            os.write(base64file);
        }
    }

}
