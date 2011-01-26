package org.bundlemaker.core.parser;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.Type;

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
	Resource getOrCreateResource(IResourceKey resourceKey);

	/**
	 * @return
	 */
	Type getOrCreateType(String fullyQualifiedName);
}
