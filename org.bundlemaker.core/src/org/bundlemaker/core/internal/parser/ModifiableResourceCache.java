package org.bundlemaker.core.internal.parser;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.resource.FlyWeightCache;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModifiableResourceCache implements IResourceCache {

	/** the element map */
	private Map<IResourceKey, Resource> _resourceMap;

	/** the dependency store */
	private IPersistentDependencyStore _dependencyStore;

	/** - */
	private FlyWeightCache _referenceCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link ModifiableResourceCache}.
	 * </p>
	 * 
	 * @param dependencyStore
	 */
	public ModifiableResourceCache(IPersistentDependencyStore dependencyStore) {

		Assert.isNotNull(dependencyStore);

		// set the dependency store
		_dependencyStore = dependencyStore;

		// set the element map
		_resourceMap = new HashMap<IResourceKey, Resource>();

		//
		_referenceCache = new FlyWeightCache();
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
		}

		// commit the store
		_dependencyStore.commit();
	}

	// /**
	// * <p>
	// * </p>
	// */
	// private void optimize() {
	//
	// System.out.println("Optimize start");
	//
	// // create the reference map
	// Map<Reference, Reference> referenceMap = new HashMap<Reference,
	// Reference>();
	//
	// // iterate over all resources
	// for (Resource resource : _resourceMap.values()) {
	//
	// // the references to replace
	// Set<Reference> referencesToReplace = new HashSet<Reference>();
	//
	// // get all references
	// for (Iterator<Reference> iterator = referencesToReplace.iterator();
	// iterator
	// .hasNext();) {
	//
	// // get the reference
	// Reference reference = (Reference) iterator.next();
	//
	// //
	// if (referenceMap.containsKey(reference)) {
	//
	// Reference flyWeightReference = referenceMap.get(reference);
	//
	// // TODO
	// if (flyWeightReference == reference) {
	// System.out.println("BOOM");
	// }
	//
	// //
	// iterator.remove();
	// referencesToReplace.add(referenceMap.get(reference));
	//
	// } else {
	//
	// //
	// referenceMap.put(reference, reference);
	// }
	//
	// }
	//
	// //
	// for (Reference reference : referencesToReplace) {
	//
	// resource.getModifiableReferences().add(reference);
	// }
	// }
	// }

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
				resourceKey.getRoot(), resourceKey.getPath(), _referenceCache);

		// store the Resource
		_resourceMap.put(new ResourceKey(resourceKey.getContentId(),
				resourceKey.getRoot(), resourceKey.getPath()), resource);

		// return the result
		return resource;
	}
}
