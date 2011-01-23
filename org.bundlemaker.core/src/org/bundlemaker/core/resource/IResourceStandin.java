package org.bundlemaker.core.resource;

import java.io.InputStream;

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
	InputStream getInputStream();
}
