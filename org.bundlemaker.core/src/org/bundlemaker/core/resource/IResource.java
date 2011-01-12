package org.bundlemaker.core.resource;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResource extends IResourceKey {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	List<? extends IReference> getReferences();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	List<? extends IResource> getAssociatedResources();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	List<String> getContainedTypes();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceStandin getResourceStandin();
}
