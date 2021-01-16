package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_13_R2;

import java.util.Set;

import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.nbt.*;

import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTNumber;
import net.minecraft.server.v1_13_R2.NBTTagByte;
import net.minecraft.server.v1_13_R2.NBTTagByteArray;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagEnd;
import net.minecraft.server.v1_13_R2.NBTTagFloat;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagIntArray;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagLong;
import net.minecraft.server.v1_13_R2.NBTTagLongArray;
import net.minecraft.server.v1_13_R2.NBTTagShort;
import net.minecraft.server.v1_13_R2.NBTTagString;
import net.sourcewriters.minecraft.versiontools.reflection.BukkitConversion;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContext;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SimpleSyntaxContext;
import net.sourcewriters.minecraft.versiontools.reflection.entity.NmsEntityType;

public class BukkitConversion1_13_R2 extends BukkitConversion<VersionControl1_13_R2> {

	protected BukkitConversion1_13_R2(VersionControl1_13_R2 versionControl) {
		super(versionControl);
	}

	@Override
	public EntityType toEntityType(NmsEntityType type) {
		try {
			return EntityType.valueOf(type.name());
		} catch (IllegalArgumentException ignore) {
			return null;
		}
	}

	@Override
	public NmsEntityType fromEntityType(EntityType type) {
		try {
			return NmsEntityType.valueOf(type.name());
		} catch (IllegalArgumentException ignore) {
			return null;
		}
	}

	@Override
	public NBTBase toMinecraftTag(NbtTag tag) {
		switch (tag.getType()) {
		case BYTE:
			return new NBTTagByte((byte) tag.getValue());
		case SHORT:
			return new NBTTagShort((short) tag.getValue());
		case INT:
			return new NBTTagInt((int) tag.getValue());
		case LONG:
			return new NBTTagLong((long) tag.getValue());
		case FLOAT:
			return new NBTTagFloat((float) tag.getValue());
		case DOUBLE:
			return new NBTTagDouble((double) tag.getValue());
		case STRING:
			return new NBTTagString((String) tag.getValue());
		case BYTE_ARRAY:
			return new NBTTagByteArray((byte[]) tag.getValue());
		case INT_ARRAY:
			return new NBTTagIntArray((int[]) tag.getValue());
		case LONG_ARRAY:
			return new NBTTagLongArray((long[]) tag.getValue());
		case LIST:
			return toMinecraftList((NbtList<?>) tag);
		case COMPOUND:
			return toMinecraftCompound((NbtCompound) tag);
		case END:
			return new NBTTagEnd();
		default:
			return null;
		}
	}

	@Override
	public NbtTag fromMinecraftTag(Object raw) {
		if (!(raw instanceof NBTBase))
			return null;
		NBTBase tag = (NBTBase) raw;
		NbtType type = NbtType.getById(tag.getTypeId());
		switch (type) {
		case BYTE:
			return new NbtByte(((NBTNumber) tag).asByte());
		case SHORT:
			return new NbtShort(((NBTNumber) tag).asShort());
		case INT:
			return new NbtInt(((NBTNumber) tag).asInt());
		case LONG:
			return new NbtLong(((NBTNumber) tag).asLong());
		case FLOAT:
			return new NbtFloat(((NBTNumber) tag).asFloat());
		case DOUBLE:
			return new NbtDouble(((NBTNumber) tag).asDouble());
		case STRING:
			return new NbtString(((NBTTagString) tag).asString());
		case BYTE_ARRAY:
			return new NbtByteArray(((NBTTagByteArray) tag).c());
		case INT_ARRAY:
			return new NbtIntArray(((NBTTagIntArray) tag).d());
		case LONG_ARRAY:
			return new NbtLongArray(((NBTTagLongArray) tag).d());
		case LIST:
			return fromMinecraftList(tag);
		case COMPOUND:
			return fromMinecraftCompound(tag);
		case END:
			return NbtEnd.INSTANCE;
		default:
			return null;
		}
	}

	@Override
	public NBTTagList toMinecraftList(NbtList<?> list) {
		NBTTagList output = new NBTTagList();
		for (NbtTag tag : list) {
			output.add(toMinecraftTag(tag));
		}
		return output;
	}

	@Override
	public NbtList<NbtTag> fromMinecraftList(Object raw) {
		if (!(raw instanceof NBTTagList))
			return null;
		NBTTagList list = (NBTTagList) raw;
		NbtList<NbtTag> output = new NbtList<>(NbtType.getById(list.getTypeId()));
		for (NBTBase base : list) {
			output.add(fromMinecraftTag(base));
		}
		return output;
	}

	@Override
	public NBTTagCompound toMinecraftCompound(NbtCompound compound) {
		NBTTagCompound output = new NBTTagCompound();
		if (compound.isEmpty())
			return output;
		Set<String> keys = compound.getKeys();
		for (String key : keys) {
			output.set(key, toMinecraftTag(compound.get(key)));
		}
		return output;
	}

	@Override
	public NbtCompound fromMinecraftCompound(Object raw) {
		if (!(raw instanceof NBTTagCompound))
			return null;
		NBTTagCompound compound = (NBTTagCompound) raw;
		NbtCompound output = new NbtCompound();
		if (compound.isEmpty())
			return output;
		Set<String> keys = compound.getKeys();
		for (String key : keys) {
			output.set(key, fromMinecraftTag(compound.get(key)));
		}
		return output;
	}

	@Override
	public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
		return CraftItemStack.asBukkitCopy(ItemStack.a(toMinecraftCompound(compound)));
	}

	@Override
	public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
		return fromMinecraftCompound(CraftItemStack.asNMSCopy(itemStack).getOrCreateTag());
	}

	@Override
	public WrappedContext<DataAdapterContext> createContext(DataAdapterContext context) {
		return new SimpleSyntaxContext(context);
	}

}