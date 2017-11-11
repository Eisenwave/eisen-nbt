package eisenwave.nbt.io;

import eisenwave.nbt.NBTCompound;
import eisenwave.nbt.NBTNamedTag;
import org.junit.Test;

import static org.junit.Assert.*;

public class MojangsonParserTest {
    
    @Test
    public void parseEmpty() throws Exception {
        NBTNamedTag tag = MojangsonParser.parse("{}");
        
        assertEquals("", tag.getName());
    }
    
    @Test
    public void parseUnnamed() throws Exception {
        NBTNamedTag tag = MojangsonParser.parse("{this:\"is\", \"a\": 123, test: 10b}");
        NBTCompound compound = (NBTCompound) tag.getTag();
        
        assertEquals("", tag.getName());
        assertEquals("is", compound.getString("this"));
        assertEquals(123, compound.getInt("a"));
        assertEquals((byte) 10, compound.getByte("test"));
    }
    
    @Test
    public void parseNamed() throws Exception {
        NBTNamedTag tag = MojangsonParser.parse("Root : {this:\"is\", \"a\": 123, test: 10b}");
        NBTCompound compound = (NBTCompound) tag.getTag();
        
        assertEquals("Root", tag.getName());
        assertEquals("is", compound.getString("this"));
        assertEquals(123, compound.getInt("a"));
        assertEquals((byte) 10, compound.getByte("test"));
    }
    
}