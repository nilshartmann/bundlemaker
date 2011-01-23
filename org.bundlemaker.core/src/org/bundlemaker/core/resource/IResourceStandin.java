package org.bundlemaker.core.resource;

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
}
