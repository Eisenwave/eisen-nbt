# OpenNBT

## Introduction
This library is a lightweight API for reading, writing and generally working with the formats:
- [NBT (Named Binary Tag)](https://minecraft.gamepedia.com/NBT_format), a binary format developed by Mojang for use in
  *Minecraft*
- **Mojangson**, a [JSON](https://json.org/)-inspired text format developed by Mojang for serializing NBT as text.
It supports the latest NBT version `19133`.

The NBT model can be found in `me.eisenwave.nbt`. I/O related classes can be found in `me.eisenwave.nbt.io`.


## Usage

Reading and writing NBT files should be primarily done by using the NBT and Mojangson implementations of the
`Serializer` and `Deserializer` interfaces.

The following example demonstrates how to read, write and interact with this API. Let's assume we want to change the
world name in a `level.dat` file:
```Java
void setWorldName(String worldName, File file) throws IOException {
    NBTNamedTag namedTag = new NBTDeserializer().fromFile(file);
    // the NBT file format dictates that the root tag MUST be a compound
    NBTCompound root = (NBTCompound) namedTag.getTag();
    NBTCompound data = root.getCompoundTag("Data");
    data.putString("LevelName", worldName);
    new NBTSerializer().toFile(namedTag, file);
}
```

## Notes on the Mojangson Syntax

Since an official documentation of the Mojangson format doesn't exist, I will be providing a few notes on the
differences between JSON and Mojangson.

### Simple Strings
Any strings in Mojangson can either be regular JSON Strings or "simple" Strings without quotes, as long as they
consist of only alphanumeric characters as well as `-`, `+`, `.` and `_`. For example:
`{"key":"value"}` can be written as `{key:value}`

### Number Suffixes
Mojangson uses suffixes for numbers to specify their exact type, similar to C-like languages.

Type | Suffix
---  | ---
byte | b
short | s
int | *none*
long | l
float | f
double | d

If no suffix is specified and the value is a number, it is interpreted as an `int` or a `double` depending on whether it
is provided in integer format.

### Byte, Int, Long Arrays
Regular lists in both JSON and Mojangson start with an opening `[` bracket.
The beginning of byte, long and int arrays can be specified with:
- `[B;` for byte arrays
- `[I;` for int arrays
- `[L;` for long arrays

### Empty Lists
Thanks to array prefixes and and number suffixes Mojangson can be mapped onto NBT almost exactly with the exception
of empty lists. This makes the conversion from NBT to Mojangson not perfectly invertible.

Am empty Mojangson list `[]` is parsed as an empty `NBTList` with type `TAG_End`, a solution also used by Mojang. This
can be observed in some *Minecraft* world data files.

**To achieve maximum software compatibility, support empty `Tag_End` lists.**

## Credit

Most source code is original and written by Jan Schultke with the exception of the `MojangsonParser` utility class
which has been created by decompiling, de-obfuscating and modifying Minecraft source code.

Inspiration for the design of this software has been drawn from [WorldEdit](https://github.com/sk89q/WorldEdit/)'s
NBT API.
