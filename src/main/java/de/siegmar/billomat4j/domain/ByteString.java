package de.siegmar.billomat4j.domain;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public final class ByteString {

    private final byte[] data;

    public ByteString(final byte[] data) {
        this.data = data.clone();
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

}
