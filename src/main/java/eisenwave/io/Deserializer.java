package eisenwave.io;

import java.io.*;
import java.net.URL;

/**
 * Throwaway object only meant to perform one deserialization of a stream.
 *<p>
 *     The deserializer interface is designed as a utility interface, giving each implementation several methods for
 *     reading from URL's, files, byte arrays and more.
 * </p>
 * <p>
 *     The only method to be implemented is {@link #fromStream(InputStream)} which reads an object from a generic
 *     input stream.
 * </p>
 *
 * @param <T> the type of object which is to be deserialized
 */
public interface Deserializer<T> {

    /**
     * Deserializes an object from an {@link InputStream}.
     *
     * @param stream the stream
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    public abstract T fromStream(InputStream stream) throws IOException;

    /**
     * Deserializes an object from a {@link File} using a {@link FileInputStream}.
     *
     * @param file the file
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    public default T fromFile(File file) throws IOException {
        try (InputStream stream = new FileInputStream(file)) {
            try (BufferedInputStream buffStream = new BufferedInputStream(stream)) {
                return fromStream(buffStream);
            }
        }
    }

    /**
     * Deserializes an object from a {@code byte[]} using a {@link ByteArrayInputStream}.
     *
     * @param bytes the byte array
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    public default T fromBytes(byte[] bytes) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        return fromStream(stream);
    }

    /**
     * Deserializes an object from a {@link Class} and a resource path by opening a stream to the resource via the
     * {@link ClassLoader}.
     *
     * @param clazz the class which's class loader is to be used
     * @param resPath the path to the resource
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    public default T fromResource(Class<?> clazz, String resPath) throws IOException {
        try (InputStream stream = clazz.getClassLoader().getResourceAsStream(resPath)) {
            if (stream == null) throw new IOException("resource \""+resPath+"\" could not be found");
            return fromStream(stream);
        }
    }

    /**
     * Deserializes an object from a {@link URL} by opening a stream to it.
     *
     * @param url the url
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    public default T fromURL(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            return fromStream(stream);
        }
    }
    
}
