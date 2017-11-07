package me.eisenwave.nbt.io;

import me.eisenwave.nbt.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class MojangsonWriter extends Writer {
    
    private static final String
    NEWLINE = System.getProperty("line.separator"),
    INDENT = "    ";
    
    private static final Pattern SIMPLE_STRING = Pattern.compile("[A-Za-z0-9._+-]+");
    
    private final Writer writer;
    private final boolean pretty;
    
    private int indent = 0;
    
    /**
     * Constructs a new writer.
     *
     * @param writer the writer
     * @param pretty whether to "pretty-print", adding whitespace, line breaks and indent
     */
    public MojangsonWriter(Writer writer, boolean pretty) {
        this.writer = Objects.requireNonNull(writer);
        this.pretty = pretty;
    }
    
    /**
     * Constructs a new writer with disabled pretty printing.
     *
     * @param writer the writer
     */
    public MojangsonWriter(Writer writer) {
        this(writer, false);
    }
    
    public void writeNamedTag(String name, NBTTag root) throws IOException {
        if (!name.isEmpty()) {
            write((new NBTString(name).toMSONString()));
            write(':');
            if (pretty) write(' ');
        }
        
        writeTag(root);
    }
    
    public void writeNamedTag(NBTNamedTag nbt) throws IOException {
        writeNamedTag(nbt.getName(), nbt.getTag());
    }
    
    public void writeTag(NBTTag tag) throws IOException {
        if (!pretty) {
            write(tag.toMSONString());
            return;
        }
        
        NBTType type = tag.getType();
        if (type == NBTType.END || type.isPrimitive() || type.isArray()) {
            writer.write(tag.toMSONString());
        }
        else if (type == NBTType.COMPOUND) {
            writeCompound((NBTCompound) tag);
        }
        else if (type == NBTType.LIST) {
            writeList((NBTList) tag);
        }
    }
    
    @SuppressWarnings("Duplicates")
    public void writeCompound(NBTCompound compound) throws IOException {
        write('{');
        
        if (!compound.isEmpty()) {
            boolean simple = isSimple(compound);
            
            if (!simple) {
                indent++;
                endLn();
            }
    
            Map<String, NBTTag> map = compound.getValue();
            Set<String> keys = map.keySet();
            boolean first = true;
    
            for (String key : keys) {
                if (first)
                    first = false;
                else if (simple)
                    write(", ");
                else {
                    write(",");
                    endLn();
                }
                write(SIMPLE_STRING.matcher(key).matches()? key : NBTString.toMSONString(key));
                write(": ");
                writeTag(map.get(key));
            }
            
            if (!simple) {
                indent--;
                endLn();
            }
        }
        
        write('}');
    }
    
    @SuppressWarnings("Duplicates")
    public void writeList(NBTList list) throws IOException {
        write('[');
    
        if (!list.isEmpty()) {
            boolean simple = isSimple(list);
            if (!simple) {
                indent++;
                endLn();
            }
            
            Iterator<NBTTag> iter = list.iterator();
            boolean first = true;
    
            while (iter.hasNext()) {
                if (first)
                    first = false;
                else if (simple)
                    write(", ");
                else {
                    write(",");
                    endLn();
                }
                writeTag(iter.next());
            }
    
            if (!simple) {
                indent--;
                endLn();
            }
        }
        
        write(']');
    }
    
    /**
     * Writes the indentation as many times as necessary.
     *
     * @throws IOException if an I/O error occurs
     */
    protected void indent() throws IOException {
        if (indent == 1) {
            writer.write(INDENT);
        }
        else if (indent > 0) {
            for (int i = 0; i < indent; i++)
                writer.append(INDENT);
        }
    }
    
    /**
     * Ends the line with the native new line sequence. This will be CRLF on Windows and LF on most Unix systems.
     *
     * @throws IOException if an I/O error occurs
     */
    protected void endLn() throws IOException {
        writer.write(NEWLINE);
        indent();
    }
    
    // WRITER IMPL
    
    @Override
    public void write(int c) throws IOException {
        writer.write(c);
    }
    
    @Override
    public void write(String str) throws IOException {
        writer.write(str);
    }
    
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }
    
    @Override
    public void flush() throws IOException {
        writer.flush();
    }
    
    @Override
    public void close() throws IOException {
        writer.close();
    }
    
    // UTIL
    
    private static boolean isSimple(NBTList list) {
        return list.isEmpty() || list.getElementType().isPrimitive();
    }
    
    private static boolean isSimple(NBTCompound compound) {
        if (compound.size() > 3)
            return false;
        for (NBTTag val : compound.getValue().values()) {
            if (!val.getType().isPrimitive())
                return false;
        }
        return true;
    }
    
}
