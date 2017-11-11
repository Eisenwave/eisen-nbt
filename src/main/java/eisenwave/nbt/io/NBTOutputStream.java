package eisenwave.nbt.io;

import eisenwave.nbt.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An NBTInputStream extends {@link DataOutputStream} by allowing to write named tags.
 */
public final class NBTOutputStream extends DataOutputStream {

    private final static Charset UTF_8 = Charset.forName("UTF-8");
    
    private final static int END_ID = NBTType.END.getId();

    /**
     * Creates a new {@code NBTOutputStream}, which will write data to the
     * specified underlying output stream.
     * 
     * @param out the output stream
     */
    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a tag.
     * 
     * @param tag the tag to write
     * @throws IOException if an I/O error occurs
     */
    public void writeNamedTag(String name, NBTTag tag) throws IOException {
        Objects.requireNonNull(tag);

        int typeId = tag.getType().getId();
        byte[] nameBytes = name.getBytes(UTF_8);

        writeByte(typeId);
        writeShort(nameBytes.length);
        write(nameBytes);

        if (typeId == END_ID)
            throw new IOException("Named TAG_End not permitted.");

        writeTag(tag);
    }
    
    /**
     * Writes a tag.
     *
     * @param tag the tag to write
     * @throws IOException if an I/O error occurs
     */
    public void writeNamedTag(NBTNamedTag tag) throws IOException {
        writeNamedTag(tag.getName(), tag.getTag());
    }

    /**
     * Writes a tag payload.
     * 
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTag(NBTTag tag) throws IOException {
        switch (tag.getType()) {
            case END: break;
            case BYTE: writeByte(((NBTByte) tag).getByteValue()); break;
            case SHORT: writeShort(((NBTShort) tag).getShortValue()); break;
            case INT: writeInt(((NBTInt) tag).getIntValue()); break;
            case LONG: writeLong(((NBTLong) tag).getLongValue()); break;
            case FLOAT: writeFloat(((NBTFloat) tag).getFloatValue()); break;
            case DOUBLE: writeDouble(((NBTDouble) tag).getDoubleValue()); break;
            case BYTE_ARRAY: writeTagByteArray((NBTByteArray) tag); break;
            case STRING: writeTagString((NBTString) tag); break;
            case LIST: writeTagList((NBTList) tag); break;
            case COMPOUND: writeTagCompound((NBTCompound) tag); break;
            case INT_ARRAY: writeTagIntArray((NBTIntArray) tag); break;
            case LONG_ARRAY: writeTagLongArray((NBTLongArray) tag); break;
            default: throw new IOException("invalid tag type: " + tag.getType());
        }
    }

    /**
     * Writes a {@code TAG_String} tag.
     *
     * @param tag the tag.
     * @throws IOException if an I/O error occurs
     */
    public void writeTagString(NBTString tag) throws IOException {
        byte[] bytes = tag.getValue().getBytes(UTF_8);
        writeShort(bytes.length);
        write(bytes);
    }

    /**
     * Writes a {@code TAG_Byte_Array} tag.
     * 
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagByteArray(NBTByteArray tag) throws IOException {
        byte[] bytes = tag.getValue();
        writeInt(bytes.length);
        write(bytes);
    }

    /**
     * Writes a {@code TAG_List} tag.
     * 
     * @param tag the tag.
     * @throws IOException if an I/O error occurs
     */
    public void writeTagList(NBTList tag) throws IOException {
        NBTType type = tag.getElementType();
        List<? extends NBTTag> tags = tag.getValue();
        int size = tags.size();

        writeByte(type.getId());
        writeInt(size);
        for (NBTTag element : tags)
            writeTag(element);
    }
    
    /**
     * Writes a {@code TAG_Compound} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagCompound(NBTCompound tag) throws IOException {
        for (Map.Entry<String, NBTTag> entry : tag.getValue().entrySet()) {
            writeNamedTag(entry.getKey(), entry.getValue());
        }
        writeByte(END_ID);
    }

    /**
     * Writes a {@code TAG_Int_Array} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagIntArray(NBTIntArray tag) throws IOException {
        writeInt(tag.length());
        for (int aData : tag.getValue())
            writeInt(aData);
    }
    
    /**
     * Writes a {@code TAG_Long_Array} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagLongArray(NBTLongArray tag) throws IOException {
        writeInt(tag.length());
        for (long aData : tag.getValue())
            writeLong(aData);
    }
    
}
