package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_16_R2.tools;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.block.CraftSkull;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import net.minecraft.server.v1_16_R2.TileEntitySkull;
import net.sourcewriters.minecraft.vcompat.reflection.tools.BlockTools;
import net.sourcewriters.minecraft.vcompat.utils.constants.MinecraftConstants;

public class BlockTools1_16_R2 extends BlockTools {

    private final AbstractReflect craftEntityStateRef = new Reflect(CraftSkull.class).searchField("tileEntity", "tileEntity");

    @Override
    public void setHeadTexture(Block block, String texture) {
        if (!(block instanceof CraftSkull)) {
            return;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        PropertyMap map = entitySkull.gameProfile.getProperties();
        map.removeAll("textures");
        map.put("textures", new Property("textures", MinecraftConstants.TEXTURE_SIGNATURE, texture));
    }

    @Override
    public String getHeadTexture(Block block) {
        if (!(block instanceof CraftSkull)) {
            return null;
        }
        TileEntitySkull entitySkull = (TileEntitySkull) craftEntityStateRef.getFieldValue("tileEntity", block);
        return entitySkull.gameProfile.getProperties().get("textures").iterator().next().getValue();
    }

}