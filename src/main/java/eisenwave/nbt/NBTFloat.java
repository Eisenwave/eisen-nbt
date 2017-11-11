package eisenwave.nbt;

/**
 * The {@code TAG_Float} tag.
 */
public final class NBTFloat extends NBTTag implements Cloneable {

    private float value;
    
    public NBTFloat(float value) {
        this.value = value;
    }
    
    @Override
    public Float getValue() {
        return value;
    }

    public float getFloatValue() {
        return value;
    }
    
    public void setFloatValue(float value) {
        this.value = value;
    }
    
    @Override
    public NBTType getType() {
        return NBTType.FLOAT;
    }
    
    // MISC
    
    @Override
    public String toMSONString() {
        return value+"f";
    }
    
    @Override
    public NBTFloat clone() {
        return new NBTFloat(value);
    }

}
