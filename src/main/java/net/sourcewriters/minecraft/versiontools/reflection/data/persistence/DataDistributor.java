package net.sourcewriters.minecraft.versiontools.reflection.data.persistence;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import net.sourcewriters.minecraft.versiontools.reflection.DataProvider;

public class DataDistributor<K> {

	protected final Map<K, PersistentContainer> containers = Collections.synchronizedMap(new HashMap<>());

	protected final DataProvider dataProvider;

	protected final File parent;
	protected final Function<K, String> namingFunction;
	protected final Supplier<K> randomFunction;

	public DataDistributor(DataProvider dataProvider, File parent, Supplier<K> randomFunction) {
		this(dataProvider, parent, Object::toString, randomFunction);
	}

	public DataDistributor(DataProvider dataProvider, File parent, Function<K, String> namingFunction,
		Supplier<K> randomFunction) {
		this.dataProvider = dataProvider;
		this.parent = parent;
		this.namingFunction = namingFunction;
		this.randomFunction = randomFunction;
	}

	/*
	 * 
	 */

	public PersistentContainer create() {
		K key = randomFunction.get();
		while (containers.containsKey(key))
			key = randomFunction.get();
		PersistentContainer container = new PersistentContainer(new File(parent, namingFunction.apply(key) + ".nbt"),
			dataProvider.getRegistry());
		containers.put(key, container);
		return container;
	}

	public PersistentContainer get(K key) {
		return containers
			.computeIfAbsent(key,
				(key0) -> new PersistentContainer(new File(parent, namingFunction.apply(key0) + ".nbt"),
					dataProvider.getRegistry()));
	}

	public boolean delete(K key) {
		if (!containers.containsKey(key))
			return false;
		PersistentContainer container = containers.get(key);
		File location = container.getLocation();
		container.delete();
		if (location.exists()) {
			return location.delete();
		}
		return true;
	}

	public void shutdown() {
		containers.values().forEach(PersistentContainer::shutdown);
	}

}
