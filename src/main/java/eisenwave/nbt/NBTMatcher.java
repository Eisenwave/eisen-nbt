package eisenwave.nbt;

import java.util.List;
import java.util.Objects;

public class NBTMatcher {
    
    /**
     * Check whether the pattern and the matching tag are completely identical. This will cause values which are
     * present in the matching tag but not in the pattern tag to prevent a match.
     */
    private final static int
    EXACT = 0b1,
    /**
     * Ignore values, only verify that the NBT tags match in types.
     */
    TYPES_ONLY = 0b10;
    
    private final NBTTag pattern;
    private final int flags;
    
    public NBTMatcher(NBTTag pattern, int flags) {
        this.pattern = Objects.requireNonNull(pattern);
        this.flags = flags;
    }
    
    public boolean matches(NBTTag tag) {
        return pattern.equals(tag);
    }
    
    /**
     * <p>
     *     Custom implementation of a {@link NBTType#LIST} tag which caches the type id, hash and emptiness.
     * </p>
     * <p>
     *     This custom list is optimized for being matched against a high quantity of other lists.
     * </p>
     */
    private static class NBTMatcherList extends NBTTag {
        
        private final int hash;
        private final byte type;
        private final boolean empty;
        private final NBTTag[] value;
        
        public NBTMatcherList(NBTList tag) {
            this.empty = tag.isEmpty();
            this.hash = tag.hashCode();
            this.type = tag.getTypeId();
            this.value = tag.getValue().toArray(new NBTTag[tag.size()]);
        }
    
        @Override
        public boolean equals(Object obj) {
            return obj instanceof NBTList && equals((NBTList) obj);
        }
    
        public boolean equals(NBTList tag) {
            return this.empty && tag.isEmpty()
                || this.type == tag.getTypeId() && this.hash == tag.hashCode() && this.equals(tag.getValue());
        }
        
        public boolean equals(List<NBTTag> tags) {
            int index = 0;
            for (NBTTag tag : tags)
                if (!tag.equals(value[index++]))
                    return false;
            return true;
        }
    
        @Override
        public Object getValue() {
            return value;
        }
    
        @Override
        public NBTType getType() {
            return NBTType.LIST;
        }
    
        @Override
        public String toMSONString() {
            throw new UnsupportedOperationException();
        }
        
    }
    
}
