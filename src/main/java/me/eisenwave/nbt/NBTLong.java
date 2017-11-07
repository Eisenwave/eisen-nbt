package me.eisenwave.nbt;

/**
 * The {@code TAG_Long} tag.
 */
public final class NBTLong extends NBTTag implements Cloneable {

    private long value;
    
    public NBTLong(long value) {
        this.value = value;
    }
    
    @Override
    public Long getValue() {
        return value;
    }

    public long getLongValue() {
        return value;
    }
    
    public void setLongValue(long value) {
        this.value = value;
    }
    
    @Override
    public NBTType getType() {
        return NBTType.LONG;
    }
    
    // MISC
    
    @Override
    public String toMSONString() {
        return value+"L";
    }
    
    @Override
    public NBTLong clone() {
        return new NBTLong(value);
    }

}
