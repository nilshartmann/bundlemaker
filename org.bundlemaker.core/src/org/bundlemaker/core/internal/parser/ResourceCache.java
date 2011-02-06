package org.bundlemaker.core.internal.parser;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.resource.IModifiableResource;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.spi.resource.FlyWeightCache;
import org.bundlemaker.core.spi.resource.Resource;
import org.bundlemaker.core.spi.resource.Type;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceCache implements IResourceCache {

	/** the element map */
	private Map<IResourceKey, Resource> _resourceMap;

	/** the element map */
	private Map<String, Type> _typeMap;

	/** the dependency store */
	private IPersistentDependencyStore _dependencyStore;

	/** - */
	private FlyWeightCache _flyWeightCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceCache}.
	 * </p>
	 * 
	 * @param dependencyStore
	 */
	public ResourceCache(IPersistentDependencyStore dependencyStore) {

		Assert.isNotNull(dependencyStore);

		// set the dependency store
		_dependencyStore = dependencyStore;

		// set the element map
		_resourceMap = new HashMap<IResourceKey, Resource>();

		//
		_typeMap = new HashMap<String, Type>();

		//
		_flyWeightCache = new FlyWeightCache();
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
	public synchronized void commit(IProgressMonitor progressMonitor)
			throws CoreException {

		//
		if (progressMonitor != null) {
			progressMonitor.beginTask("write to disc", _resourceMap.values()
					.size());
		}

		// update all
		for (IModifiableResource modifiableResource : _resourceMap.values()) {
			_dependencyStore.updateResource(modifiableResource);

			//
			if (progressMonitor != null) {
				progressMonitor.worked(1);
			}
		}

		// commit the store
		_dependencyStore.commit();

		//
		if (progressMonitor != null) {
			progressMonitor.done();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO synchronized
	@Override
	public synchronized IModifiableResource getOrCreateResource(IResourceKey resourceKey) {

		//
		Resource resource = _resourceMap.get(resourceKey);

		// return result if != null
		if (resource != null) {
			return resource;
		}

		// create a new one if necessary
		resource = new Resource(resourceKey.getContentId(),
				resourceKey.getRoot(), resourceKey.getPath(), this);

		// store the Resource
		_resourceMap.put(new ResourceKey(resourceKey.getContentId(),
				resourceKey.getRoot(), resourceKey.getPath()), resource);

		// return the result
		return resource;
	}

	// TODO synchronized
	@Override
	public synchronized Type getOrCreateType(String fullyQualifiedName,
			TypeEnum typeEnum) {

		//
		Type type = _typeMap.get(fullyQualifiedName);

		// return result if != null
		if (type != null) {

			if (!type.getType().equals(typeEnum)) {

				// TODO
				throw new RuntimeException("Wrong type requested"
						+ fullyQualifiedName + " : " + typeEnum + " : " + type);
			}

			return type;
		}

		// create a new one if necessary
		type = new Type(fullyQualifiedName, typeEnum, _flyWeightCache);

		// store the Resource
		_typeMap.put(fullyQualifiedName, type);

		// return the result
		return type;
	}

	public Map<IResourceKey, Resource> getResourceMap() {
		return _resourceMap;
	}

	public FlyWeightCache getFlyWeightCache() {
		return _flyWeightCache;
	}

	public void resetTypeCache() {
		_typeMap.clear();
	}
}
