package org.bundlemaker.core.resource;

import java.util.Set;

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
	Set<? extends IReference> getReferences();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<? extends IResource> getAssociatedResources();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<String> getContainedTypes();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceStandin getResourceStandin();
}
