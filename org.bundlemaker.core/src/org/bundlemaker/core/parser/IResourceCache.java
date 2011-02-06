package org.bundlemaker.core.parser;

import org.bundlemaker.core.resource.IModifiableResource;
import org.bundlemaker.core.resource.IModifiableType;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.TypeEnum;

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
	IModifiableResource getOrCreateResource(IResourceKey resourceKey);

	/**
	 * @return
	 */
	IModifiableType getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum);
}
