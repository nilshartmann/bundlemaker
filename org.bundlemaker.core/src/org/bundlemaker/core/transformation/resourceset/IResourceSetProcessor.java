package org.bundlemaker.core.transformation.resourceset;

import org.bundlemaker.core.internal.modules.ResourceModule;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceSetProcessor {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param originResourceModule
	 * @param targetResourceModule
	 * @param resourceSet
	 */
	public void processResources(ResourceModule originResourceModule,
			ResourceModule targetResourceModule, ResourceSet resourceSet);
}
