package org.bundlemaker.core.spi.store;

import java.util.List;

import org.bundlemaker.core.spi.resource.Resource;

/**
 * <p>
 * Defines an interface to retrieve {@link IReferencingElement IBundleElements}
 * from an underlying store.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDependencyStore {

	/**
	 * <p>
	 * Returns the list of all {@link ReferencingResource IBundleElements}.
	 * </p>
	 * 
	 * @return the list of all {@link ReferencingResource IBundleElements}.
	 */
	List<Resource> getResources();
}