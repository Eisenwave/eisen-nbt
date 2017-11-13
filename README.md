# OpenNBT

## Introduction
This library is a lightweight API for reading and writing NBT and Mojangson files.

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

## Credit

Most source code is original and written by Jan Schultke with the exception of the `MojangsonParser` utility class
which has been created by decompiling, de-obfuscating and modifying Minecraft source code.

Inspiration for the design of this software has been drawn from [WorldEdit](https://github.com/sk89q/WorldEdit/)'s
NBT API.
