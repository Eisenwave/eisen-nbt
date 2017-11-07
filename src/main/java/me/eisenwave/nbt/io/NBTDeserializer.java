package me.eisenwave.nbt.io;

import me.eisenwave.io.Deserializer;
import me.eisenwave.nbt.NBTNamedTag;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NBTDeserializer implements Deserializer<NBTNamedTag> {
    
    private final boolean compressed;
    
    /**
     * Constructs a new NBT-Deserializer.
     *
     * @param compressed whether the input is g-zip compressed
     */
    public NBTDeserializer(boolean compressed) {
        this.compressed = compressed;
    }
    
    /**
     * Constructs a new NBT-Deserializer with enabled g-zip decompression.
     */
    public NBTDeserializer() {
        this(true);
    }
    
    @Override
    public NBTNamedTag fromStream(InputStream stream) throws IOException {
        NBTInputStream nbtStream = compressed?
            new NBTInputStream(new GZIPInputStream(stream)) :
            new NBTInputStream(stream);
        
        NBTNamedTag tag = nbtStream.readNamedTag();
        if (tag == null)
            throw new IOException("failed to read NBT tag due to EOS");
        else return tag;
    }
    
}
