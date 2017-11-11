package eisenwave.nbt;

/**
 * A named tag consisting of a {@link String} for its name and a {@link NBTTag} for its value.
 */
public class NBTNamedTag {

    private final String name;
    private final NBTTag tag;

    /**
     * Constructs a new named tag.
     *
     * @param name the name
     * @param tag the tag
     */
    public NBTNamedTag(String name, NBTTag tag) {
        this.name = name;
        this.tag = tag;
    }

    /**
     * Returns the name of the tag.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tag.
     *
     * @return the tag
     */
    public NBTTag getTag() {
        return tag;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NBTNamedTag && equals((NBTNamedTag) obj);
    }
    
    public boolean equals(NBTNamedTag tag) {
        return this.name.equals(tag.name) && this.tag.equals(tag.tag);
    }

}
