package org.bundlemaker.core.resource;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * A resource standin represents a resource that should be analyzed.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceStandin extends IResourceKey,
		Comparable<IResourceStandin> {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResource getResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceModule getResourceModule();
}
