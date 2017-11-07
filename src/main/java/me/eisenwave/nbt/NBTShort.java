package me.eisenwave.nbt;

/**
 * The {@code TAG_Short} tag.
 */
public final class NBTShort extends NBTTag implements Cloneable {

    private short value;
    
    public NBTShort(short value) {
        this.value = value;
    }
    
    @Override
    public Short getValue() {
        return value;
    }

    public short getShortValue() {
        return value;
    }
    
    public void setShortValue(short value) {
        this.value = value;
    }
    
    @Override
    public NBTType getType() {
        return NBTType.SHORT;
    }
    
    // MISC
    
    @Override
    public String toMSONString() {
        return value+"s";
    }
    
    @Override
    public NBTShort clone() {
        return new NBTShort(value);
    }

}
