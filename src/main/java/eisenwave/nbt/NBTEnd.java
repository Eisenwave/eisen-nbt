package eisenwave.nbt;

/**
 * The {@code TAG_End} tag.
 */
public final class NBTEnd extends NBTTag implements Cloneable {
    
    public final static NBTEnd INSTANCE = new NBTEnd();
    
    private NBTEnd() {}
    
    @Override
    public Void getValue() {
        return null;
    }
    
    @Override
    public NBTType getType() {
        return NBTType.END;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NBTEnd;
    }

    @Override
    public String toMSONString() {
        return "END";
    }
    
    @Override
    public NBTEnd clone() {
        return new NBTEnd();
    }
    
}
