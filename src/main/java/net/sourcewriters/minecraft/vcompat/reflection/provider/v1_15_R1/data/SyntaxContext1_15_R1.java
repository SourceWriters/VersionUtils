package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_15_R1.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;

import com.syntaxphoenix.syntaxapi.data.IDataContainer;

import net.sourcewriters.minecraft.vcompat.reflection.data.WrappedContext;

public final class SyntaxContext1_15_R1 extends WrappedContext<PersistentDataAdapterContext> implements PersistentDataAdapterContext {

    private final PersistentDataAdapterContext context;

    public SyntaxContext1_15_R1(PersistentDataAdapterContext context) {
        this.context = context;
    }

    @Override
    public PersistentDataAdapterContext getHandle() {
        return context;
    }

    @Override
    public PersistentDataContainer newPersistentDataContainer() {
        return context.newPersistentDataContainer();
    }

    @Override
    public IDataContainer newDataContainer() {
        return newContainer();
    }

    @Override
    public SyntaxContainer1_15_R1 newContainer() {
        return new SyntaxContainer1_15_R1(context.newPersistentDataContainer());
    }

}