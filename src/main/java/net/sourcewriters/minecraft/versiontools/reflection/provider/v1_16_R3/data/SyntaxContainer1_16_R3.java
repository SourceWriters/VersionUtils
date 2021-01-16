package net.sourcewriters.minecraft.versiontools.reflection.provider.v1_16_R3.data;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

import net.sourcewriters.minecraft.versiontools.reflection.data.WrapType;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedContainer;
import net.sourcewriters.minecraft.versiontools.reflection.data.WrappedKey;
import net.sourcewriters.minecraft.versiontools.reflection.data.type.ObjectType;
import net.sourcewriters.minecraft.versiontools.reflection.data.wrap.SyntaxKey;

public final class SyntaxContainer1_16_R3 extends WrappedContainer implements IDataContainer {

	public static final PersistentDataType<Object, Object> OBJECT_TYPE = new SimpleBukkitType1_16_R3<>(ObjectType.INSTANCE);

	private final PersistentDataContainer container;

	public SyntaxContainer1_16_R3(PersistentDataContainer container) {
		this.container = container;
	}

	@Override
	public PersistentDataContainer getHandle() {
		return container;
	}

	@Override
	public IDataContainer getAsSyntaxContainer() {
		return new SyntaxContainer1_16_R3(container);
	}

	/*
	 * 
	 */

	@Override
	public boolean has(IKey key) {
		return has(new SyntaxKey(key));
	}

	@Override
	public boolean has(String key, DataType<?, ?> type) {
		return has(NamespacedKey.fromString(key), type);
	}

	@Override
	public boolean has(IKey key, DataType<?, ?> type) {
		return has(new SyntaxKey(key), WrappedType1_16_R3.wrap(type));
	}

	@Override
	public <C> C get(String key, DataType<?, C> type) {
		return get(NamespacedKey.fromString(key), type);
	}

	@Override
	public <C> C get(IKey key, DataType<?, C> type) {
		return get(new SyntaxKey(key), WrappedType1_16_R3.wrap(type));
	}

	@Override
	public Object get(String key) {
		return get(NamespacedKey.fromString(key));
	}

	@Override
	public Object get(IKey key) {
		return get(new SyntaxKey(key));
	}

	@Override
	public <E, V> void set(String key, E value, DataType<V, E> type) {
		set(wrappedKey(key), value, WrappedType1_16_R3.wrap(type));
	}

	@Override
	public <E, V> void set(IKey key, E value, DataType<V, E> type) {
		set(new SyntaxKey(key), value, WrappedType1_16_R3.wrap(type));
	}

	@Override
	public boolean remove(String key) {
		return remove(wrappedKey(key));
	}

	@Override
	public boolean remove(IKey key) {
		return remove(new SyntaxKey(key));
	}

	@Override
	public IKey[] getKeys() {
		return container.getKeys().stream().map(BukkitKey1_16_R3::new).map(WrappedKey::getNamespacedKey).toArray(IKey[]::new);
	}

	@Override
	public Set<String> getKeyspaces() {
		return container.getKeys().stream().map(org.bukkit.NamespacedKey::toString).collect(Collectors.toSet());
	}

	@Override
	public DataAdapterContext getAdapterContext() {
		return getContext();
	}

	/*
	 * 
	 */

	@Override
	public SyntaxContext1_16_R3 getContext() {
		return new SyntaxContext1_16_R3(container.getAdapterContext());
	}

	@Override
	public boolean has(String key) {
		return has(wrappedKey(key));
	}

	@Override
	public boolean has(WrappedKey<?> key) {
		return container.has(BukkitKey1_16_R3.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> boolean has(String key, WrapType<P, C> type) {
		return has(wrappedKey(key), type);
	}

	@Override
	public <P, C> boolean has(WrappedKey<?> key, WrapType<P, C> type) {
		return container.has(BukkitKey1_16_R3.asBukkit(key), new SimpleBukkitType1_16_R3<>(type));
	}

	@Override
	public Object get(WrappedKey<?> key) {
		return container.get(BukkitKey1_16_R3.asBukkit(key), OBJECT_TYPE);
	}

	@Override
	public <P, C> C get(String key, WrapType<P, C> type) {
		return get(wrappedKey(key), type);
	}

	@Override
	public <P, C> C get(WrappedKey<?> key, WrapType<P, C> type) {
		return container.get(BukkitKey1_16_R3.asBukkit(key), new SimpleBukkitType1_16_R3<>(type));
	}

	@Override
	public <B> void set(String key, B value, WrapType<?, B> type) {
		set(wrappedKey(key), value, type);
	}

	@Override
	public <B> void set(WrappedKey<?> key, B value, WrapType<?, B> type) {
		container.set(BukkitKey1_16_R3.asBukkit(key), new SimpleBukkitType1_16_R3<>(type), value);
	}

	@Override
	public boolean remove(WrappedKey<?> key) {
		Object value = get(key);
		container.remove(BukkitKey1_16_R3.asBukkit(key));
		return value != null && get(key) == null;
	}

	@Override
	public Set<String> keySet() {
		return getKeyspaces();
	}

	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}

	@Override
	public int size() {
		return container.getKeys().size();
	}

}