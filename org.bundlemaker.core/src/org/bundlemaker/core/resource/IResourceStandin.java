package org.bundlemaker.core.resource;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
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
