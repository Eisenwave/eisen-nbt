package eisenwave.io;

import java.io.*;

/**
 * Throwaway object for writing objects to files.
 * <p>
 *     The serializer interface is designed as a utility interface, giving each implementation several methods for
 *     serializing to URL's, files, byte arrays and more.
 * </p>
 * <p>
 *     The only method to be implemented is {@link #toStream(Object, OutputStream)} which writes an object into
 *     a generic output stream.
 * </p>
 *
 * @param <T> the type of object which is to be serialized
 */
public interface Serializer<T> {

    /**
     * Writes the object into a {@link OutputStream}.
     *
     * @param object the object
     * @param stream the output stream
     * @throws IOException if an I/O error occurs
     */
    public void toStream(T object, OutputStream stream) throws IOException;

    /**
     * Writes the object into a {@link File} using a {@link FileOutputStream}.
     *
     * @param object the object
     * @param file the file
     * @throws IOException if an I/O error occurs
     */
    public default void toFile(T object, File file) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            try (BufferedOutputStream buffStream = new BufferedOutputStream(stream)) {
                toStream(object, buffStream);
            }
        }
    }

    /**
     * Writes the object into bytes {@code byte[]} using a {@link ByteArrayOutputStream} with set capacity.
     *
     * @param object the object
     * @param capacity the byte buffer capacity used by the stream
     * @return a byte array containing the serialized object
     * @throws IOException if an I/O error occurs
     * @see ByteArrayOutputStream#ByteArrayOutputStream(int)
     */
    public default byte[] toBytes(T object, int capacity) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(capacity);
        this.toStream(object, stream);
        stream.close();
        return stream.toByteArray();
    }

    /**
     * Writes the object into bytes {@code byte[]} using a {@link ByteArrayOutputStream} with unset capacity.
     *
     * @param object the object
     * @return a byte array containing the serialized object
     * @throws IOException if an I/O error occurs
     * @see ByteArrayOutputStream#ByteArrayOutputStream()
     */
    public default byte[] toBytes(T object) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.toStream(object, stream);
        stream.close();
        return stream.toByteArray();
    }

}
