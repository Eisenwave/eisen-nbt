package me.eisenwave.nbt;

/**
 * The type of an NBT-NBTTag.
 */
public enum NBTType {
    /** Used to mark the end of compounds tags. May also be the type of empty list tags. */
    END("TAG_End", false, false, false),

    /** A signed integer (8 bits). Sometimes used for booleans. (-128 to 127) */
    BYTE("TAG_Byte", true, true, false),

    /** A signed integer (16 bits). (-2<sup>15</sup> to 2<sup>15</sup>-1) */
    SHORT("TAG_Short", true, true, false),

    /** A signed integer (32 bits). (-2<sup>31</sup> to 2<sup>31</sup>-1) */
    INT("TAG_Int", true, true, false),

    /** A signed integer (64 bits). (-2<sup>63</sup> to 2<sup>63</sup>-1) */
    LONG("TAG_Long", true, true, false),

    /** A signed (IEEE 754-2008) floating point number (32 bits).  */
    FLOAT("TAG_Float", true, true, false),

    /** A signed (IEEE 754-2008) floating point number (64 bits).  */
    DOUBLE("TAG_Double", true, true, false),

    /** An array of bytes with max payload size of maximum value of {@link #INT}. */
    BYTE_ARRAY("TAG_Byte_Array", false, false, true),

    /** UTF-8 encoded string. */
    STRING("TAG_String", true, false, false),

    /** A list of unnamed tags of equal type. */
    LIST("TAG_List", false, false, false),

    /** Compound of named tags followed by {@link #END}. */
    COMPOUND("TAG_Compound", false, false, false),

    /** An array of {@link #INT} with max payload size of maximum value of {@link #INT}. */
    INT_ARRAY("TAG_Int_Array", false, false, true),
    
    LONG_ARRAY("TAG_Long_Array", false, false, true);

    final String name;
    final boolean numeric, primitive, array;
    final byte id;

    NBTType(String name, boolean primitive, boolean numeric, boolean array) {
        this.name = name;
        this.id = (byte) ordinal();
        this.numeric = numeric;
        this.primitive = primitive;
        this.array = array;
    }
    
    /**
     * Returns the type with the given id.
     *
     * @param id the id
     * @return the type
     */
    public static NBTType getById(byte id) {
        return values()[id];
    }

    /**
     * <p>
     *     Returns the id of this tag type.
     * </p>
     * <p>
     *     Although this method is currently equivalent to {@link #ordinal()}, it should always be used in its stead,
     *     since it is not guaranteed that this behavior will remain consistent.
     * </p>
     *
     * @return the id of this tag type
     */
    public byte getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    /**
     * <p>
     *     Returns whether this tag type is numeric.
     * </p>
     * <p>
     *     All tag types with payloads that are representable as a {@link Number} are compliant with this definition.
     * </p>
     *
     * @return whether this type is numeric
     */
    public boolean isNumeric() {
        return numeric;
    }
    
    /**
     * Returns whether this tag type is primitive, meaning that it is not a {@link NBTByteArray}, {@link NBTIntArray},
     * {@link NBTList}, {@link NBTCompound} or {@link NBTEnd}.
     *
     * @return whether this type is numeric
     */
    public boolean isPrimitive() {
        return primitive;
    }
    
    /**
     * Returns whether this tag type is is an array type such as {@link NBTByteArray} or {@link NBTIntArray}.
     *
     * @return whether this type is an array type
     */
    public boolean isArray() {
        return array;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
