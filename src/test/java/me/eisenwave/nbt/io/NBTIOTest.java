package me.eisenwave.nbt.io;

import me.eisenwave.nbt.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class NBTIOTest {
    
    /**
     * Tests whether NBT data can be written and read.
     *
     * @throws IOException if the test fails
     */
    @Test
    public void writeNamedTag() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        NBTOutputStream nbtOut = new NBTOutputStream(byteOut);
        
        nbtOut.writeNamedTag("byte", new NBTByte((byte) 8));
        nbtOut.writeNamedTag("short", new NBTShort((short) 16));
        nbtOut.writeNamedTag("int", new NBTInt(2));
        nbtOut.writeNamedTag("long", new NBTLong(64L));
        nbtOut.writeNamedTag("float", new NBTFloat(32F));
        nbtOut.writeNamedTag("double", new NBTDouble(64D));
        nbtOut.writeNamedTag("bytes", new NBTByteArray(new byte[] {1, 2, 3}));
        nbtOut.writeNamedTag("ints", new NBTIntArray(new int[] {1, 2, 3, 4, 5, 6}));
        nbtOut.writeNamedTag("list", new NBTList(NBTType.INT, new NBTInt(1), new NBTInt(2), new NBTInt(3)));
        nbtOut.writeNamedTag("compound", new NBTCompound(
            new NBTNamedTag("a", new NBTInt(1)),
            new NBTNamedTag("b", new NBTFloat(2))
        ));
        
        byte[] bytes = byteOut.toByteArray();
        byteOut.close();
        
        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        NBTInputStream nbtIn = new NBTInputStream(byteIn);
    
        NBTNamedTag tag;
        while ((tag = nbtIn.readNamedTag()) != null) {
            assertNotNull(tag.getTag().getValue());
        }
    }
    
}