package org.bundlemaker.core.db4o.internal;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.parser.store.IPersistentDependencyStore;
import org.bundlemaker.core.parser.store.IPersistentDependencyStoreFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.db4o.osgi.Db4oService;

/**
 * <p>
 * Implementation of the {@link IPersistentDependencyStoreFactory} interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreFactoryImpl implements
		IPersistentDependencyStoreFactory {

	/** the cache */
	private Map<IBundleMakerProject, PersistentDependencyStoreImpl> _cache;

	/** the db4o service */
	private Db4oService _db4oService;

	/**
	 * <p>
	 * Creates a new instance of type
	 * {@link PersistentDependencyStoreFactoryImpl}.
	 * </p>
	 * 
	 * @param db4oService
	 */
	public PersistentDependencyStoreFactoryImpl(Db4oService db4oService) {
		Assert.isNotNull(db4oService, "Parameter '" + db4oService
				+ "' has to be set!");

		// set the db4o service
		_db4oService = db4oService;

		// create the cache
		_cache = new HashMap<IBundleMakerProject, PersistentDependencyStoreImpl>();
	}

	/**
	 * @see org.bundlemaker.core.parser.store.IPersistentDependencyStoreFactory#resetPersistentDependencyStore(org.bundlemaker.core.IBundleMakerProject)
	 */
	public void resetPersistentDependencyStore(IBundleMakerProject project)
			throws CoreException {

		// step 1: dispose the cache if necessary
		if (_cache.containsKey(project)) {

			// get the store
			PersistentDependencyStoreImpl infoStore = _cache.get(project);

			// dispose the store if necessary
			if (infoStore.isInitialized()) {
				infoStore.dispose();
			}
		}

		// step 2: delete the existing '.bundlemaker/db4o.store' file
		IFile file = project.getProject().getFile(".bundlemaker/db4o.store");
		file.delete(true, null);

		// step 3: re-init the dependency store
		if (_cache.containsKey(project)) {

			// get the store
			PersistentDependencyStoreImpl dependencyStore = _cache.get(project);

			// initialize the dependency store
			dependencyStore.init();
		}
	}

	/**
	 * @see org.bundlemaker.core.parser.store.IPersistentDependencyStoreFactory#getPersistentDependencyStore(org.bundlemaker.core.IBundleMakerProject)
	 */
	public IPersistentDependencyStore getPersistentDependencyStore(
			IBundleMakerProject project) {

		// step 1: return the cached version if one exists
		if (_cache.containsKey(project)) {
			return _cache.get(project);
		}

		// step 2: create a new store
		IFile file = project.getProject().getFile(".bundlemaker/db4o.store");
		PersistentDependencyStoreImpl store = new PersistentDependencyStoreImpl(
				_db4oService, file.getRawLocation().toOSString());

		// step 3: initialize the store
		store.init();

		// step 4: cache it
		_cache.put(project, store);

		// return the store
		return store;
	}
}
