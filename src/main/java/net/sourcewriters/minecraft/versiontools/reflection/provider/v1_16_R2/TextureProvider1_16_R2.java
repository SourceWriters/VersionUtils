package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R2;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftSkull;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_16_R2.GameProfileSerializer;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.TileEntitySkull;
import net.sourcewriters.minecraft.versiontools.reflection.TextureProvider;

public class TextureProvider1_16_R2 extends TextureProvider<VersionControl1_16_R2> {

	private final AbstractReflect craftEntityStateRef = new Reflect(CraftBlockEntityState.class)
		.searchField("tileEntity", "tileEntity");
	private final AbstractReflect craftItemStackRef = new Reflect(CraftItemStack.class).searchField("handle", "handle");
	private final AbstractReflect craftMetaSkullRef = new Reflect(
		"org.bukkit.craftbukkit.v1_16_R2.inventory.CraftMetaSkull").searchField("profile", "profile");

	protected TextureProvider1_16_R2(VersionControl1_16_R2 versionControl) {
		super(versionControl);
	}

	@Override
	public GameProfile profileFromBlock(Block block) {
		if (!(block instanceof CraftSkull)) {
			return null;
		}
		TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
		return entitySkull.gameProfile;
	}

	@Override
	public GameProfile profileFromItem(org.bukkit.inventory.ItemStack itemStack) {
		if (!(itemStack.getItemMeta() instanceof SkullMeta))
			return null;
		ItemStack stack = itemStack instanceof CraftItemStack
			? (ItemStack) craftItemStackRef.getFieldValue("handle", itemStack)
			: CraftItemStack.asNMSCopy(itemStack);
		NBTTagCompound tag = stack.getOrCreateTag();
		return tag.hasKeyOfType("SkullOwner", 10) ? GameProfileSerializer.deserialize(tag.getCompound("SkullOwner"))
			: null;
	}

	@Override
	public org.bukkit.inventory.ItemStack getItem(GameProfile profile) {
		org.bukkit.inventory.ItemStack bukkitStack = new org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD);
		applyItem(bukkitStack, profile);
		return bukkitStack;
	}

	@Override
	public boolean applyItem(org.bukkit.inventory.ItemStack itemStack, String texture) {
		ItemMeta meta = itemStack.getItemMeta();
		if (!(meta instanceof SkullMeta))
			return false;
		SkullMeta skullMeta = (SkullMeta) meta;
		applyTexture((GameProfile) craftMetaSkullRef.getFieldValue("profile", skullMeta), texture);
		itemStack.setItemMeta(skullMeta);
		return false;
	}

	@Override
	public boolean applyBlock(Block block, String texture) {
		if (!(block instanceof CraftSkull)) {
			return false;
		}
		TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
		applyTexture(entitySkull.gameProfile, texture);
		return true;
	}

}