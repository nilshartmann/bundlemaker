package org.bundlemaker.core.parser;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.Resource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceCache {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceKey
	 * @return
	 */
	Resource getOrCreateModifiableResource(IResourceKey resourceKey);
}
