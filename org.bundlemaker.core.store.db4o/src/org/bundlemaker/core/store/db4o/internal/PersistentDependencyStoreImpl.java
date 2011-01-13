package org.bundlemaker.core.store.db4o.internal;

import java.util.List;

import org.bundlemaker.core.resource.Resource;

import com.db4o.osgi.Db4oService;
import com.db4o.query.Query;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreImpl extends
		AbstractPersistentDependencyStore {

	/**
	 * <p>
	 * Creates a new instance of type {@link PersistentDependencyStoreImpl}.
	 * </p>
	 * 
	 * @param db4oService
	 * @param fileName
	 */
	public PersistentDependencyStoreImpl(Db4oService db4oService,
			String fileName) {
		super(db4oService, fileName);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getResources() {

		Query query = getDatabase().query();
		query.constrain(Resource.class);
		return query.execute();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateResource(Resource bundleElement) {
		getDatabase().store(bundleElement);
	}
}
