package org.bundlemaker.core.internal.parser;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.StringCache;
import org.bundlemaker.core.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModifiableResourceCache implements IResourceCache {

	/** the element map */
	Map<IResourceKey, Resource> _resourceMap;

	/** the dependency store */
	IPersistentDependencyStore _dependencyStore;

	private StringCache _stringCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link ModifiableResourceCache}.
	 * </p>
	 * 
	 * @param dependencyStore
	 */
	public ModifiableResourceCache(IPersistentDependencyStore dependencyStore,
			StringCache stringCache) {

		// set the dependency store
		_dependencyStore = dependencyStore;

		//
		_stringCache = stringCache;

		// set the element map
		_resourceMap = new HashMap<IResourceKey, Resource>();
	}

	/**
	 * <p>
	 * </p>
	 */
	public synchronized void clear() throws CoreException {

		// clear the map
		_resourceMap.clear();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	public synchronized void commit() throws CoreException {

		// update all
		for (Resource modifiableResource : _resourceMap.values()) {
			_dependencyStore.updateResource(modifiableResource);

			if (modifiableResource.getPath().endsWith(".java")
					&& modifiableResource.getAssociatedResources().isEmpty()) {
				System.out
						.println(" missing - " + modifiableResource.getPath());
			}
		}

		// commit the store
		_dependencyStore.commit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Resource getOrCreateModifiableResource(
			IResourceKey resourceKey) {

		//
		Resource resource = _resourceMap.get(resourceKey);

		// return result if != null
		if (resource != null) {
			return resource;
		}

		// create a new one if necessary
		resource = new Resource(resourceKey.getContentId(),
				resourceKey.getRoot(), resourceKey.getPath(), _stringCache);

		// store the Resource
		_resourceMap.put(new ResourceKey(resourceKey.getContentId(),
				resourceKey.getRoot(), resourceKey.getPath()), resource);

		// return the result
		return resource;
	}
}
