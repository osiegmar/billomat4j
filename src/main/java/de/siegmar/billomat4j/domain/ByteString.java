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
