package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_8_R2.entity;

import net.minecraft.server.v1_8_R2.EntityLiving;
import net.sourcewriters.minecraft.vcompat.reflection.entity.NmsEntityLiving;

public abstract class EntityLiving1_8_R2<E extends EntityLiving> extends Entity1_8_R2<E> implements NmsEntityLiving {

    public EntityLiving1_8_R2(E handle) {
        super(handle);
    }

    @Override
    public void setCollidable(boolean collidable) {

    }

}