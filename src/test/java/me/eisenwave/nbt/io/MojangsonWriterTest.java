package me.eisenwave.nbt.io;

import me.eisenwave.nbt.NBTNamedTag;
import me.eisenwave.nbt.io.MojangsonParser;
import me.eisenwave.nbt.io.MojangsonWriter;
import org.junit.Test;

import static org.junit.Assert.*;

public class MojangsonWriterTest {
    
    
    @Test
    public void test() throws Exception {
        NBTNamedTag tag = MojangsonParser.parse("{this:\"is\", \"a\": 123, test: 10b, and: [0, 1, 2, 3, 4], key: {a:2s, b:4s, c:5s}}");
        
        System.out.println(MojangsonWriter.print(tag));
    }
    
}