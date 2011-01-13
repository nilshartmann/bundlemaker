package org.bundlemaker.core.store;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.Resource;

/**
 * <p>
 * Extends the {@link IDependencyStore} and adds some methods that allows to add
 * and update {@link IResource IResources}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPersistentDependencyStore extends IDependencyStore {

	/**
	 * <p>
	 * Updates the {@link Resource}.
	 * </p>
	 * 
	 * @param resource
	 *            the {@link Resource} to add/update
	 */
	void updateResource(Resource resource);

	/**
	 * <p>
	 * Commits all updates to the database since the last call of the
	 * <code>commit</code> method.
	 * </p>
	 */
	void commit();
}
