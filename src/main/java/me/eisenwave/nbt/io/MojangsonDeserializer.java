package me.eisenwave.nbt.io;

import me.eisenwave.nbt.NBTNamedTag;
import me.eisenwave.io.TextDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class MojangsonDeserializer implements TextDeserializer<NBTNamedTag> {
    
    @Override
    public NBTNamedTag fromReader(Reader reader) throws IOException {
        BufferedReader buffReader = reader instanceof BufferedReader?
            (BufferedReader) reader :
            new BufferedReader(reader);
        
        String mson = buffReader.lines().collect(Collectors.joining());
        
        return MojangsonParser.parse(mson);
    }
    
}
