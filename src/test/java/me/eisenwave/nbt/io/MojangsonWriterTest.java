package me.eisenwave.nbt.io;

import me.eisenwave.nbt.NBTNamedTag;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.*;

public class MojangsonWriterTest {
    
    
    @Test
    public void test() throws Exception {
        NBTNamedTag tag = MojangsonParser.parse("{this:\"is\", \"a\": 123, test: 10b, and: [0, 1, 2, 3, 4], key: {a:2s, b:4s, c:5s}}");
    
        StringWriter writer = new StringWriter();
        new MojangsonWriter(writer).writeNamedTag(tag);
    }
    
}