package me.eisenwave.nbt.io;

import me.eisenwave.nbt.NBTNamedTag;
import me.eisenwave.io.TextSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class MojangsonSerializer implements TextSerializer<NBTNamedTag> {
    
    private final boolean pretty;
    
    public MojangsonSerializer(boolean pretty) {
        this.pretty = pretty;
    }
    
    public MojangsonSerializer() {
        this(false);
    }
    
    @Override
    public void toWriter(NBTNamedTag nbt, Writer writer) throws IOException {
        MojangsonWriter msonWriter = new MojangsonWriter(writer, pretty);
        msonWriter.writeNamedTag(nbt);
        msonWriter.endLn(); // end last line to comply with POSIX standard
    }
    
    @Override
    public String toString(NBTNamedTag nbt) {
        StringWriter stringWriter = new StringWriter();
        try {
            toWriter(nbt, stringWriter);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return stringWriter.toString();
    }
    
}
