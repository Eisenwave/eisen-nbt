package eisenwave.io;

import java.io.*;

/**
 * Throwaway object only meant to perform one deserialization of a reader.
 *<p>
 *     The parser interface is designed as a utility interface which is an extension of the {@link Deserializer}
 *     interface specifically for readable files.
 * </p>
 * <p>
 *     The only method to be implemented is {@link #fromReader(Reader)} which reads an object from a generic reader.
 * </p>
 *
 * @param <T> the type of object which is to be parsed
 */
public interface TextDeserializer<T> extends Deserializer<T> {

    /**
     * Deserializes an object from a {@link Reader}.
     *
     * @param reader the reader
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    abstract T fromReader(Reader reader) throws IOException;
    
    /**
     * Deserializes an object from a char array using a {@link CharArrayReader}.
     *
     * @param chars the char array
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    default T fromCharArray(char[] chars) throws IOException {
        return fromReader(new CharArrayReader(chars));
    }

    /**
     * Deserializes an object from a {@link String} using a {@link StringReader}.
     *
     * @param str the string
     * @return the deserialized object
     * @throws IOException if the deserialization fails
     */
    default T fromString(String str) throws IOException {
        return fromReader(new StringReader(str));
    }
    
    @Override
    default T fromStream(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream)) {
            return fromReader(reader);
        }
    }
    
    @Override
    default T fromFile(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            return fromReader(reader);
        }
    }


}
