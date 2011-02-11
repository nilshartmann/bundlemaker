package org.bundlemaker.core.modules;

import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceModule extends IResourceContainer, IModule {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceContainer getSelfResourceContainer();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<String, ? extends IResourceContainer> getContainedResourceContainers();
}