package org.bundlemaker.core.resource;

import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResource extends IResourceKey, Comparable<IResource> {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	// GET REFERENCES OF ASSOCIATED RESOURCES: getAllReferences() ?
	Set<? extends IReference> getReferences();

	/**
	 * <p>
	 * Returns all the contained types in this resource. If the resource does
	 * not contain any type, an empty list will be returned instead.
	 * </p>
	 * 
	 * @return all the contained types in this resource. If the resource does
	 *         not contain any type, an empty list will be returned instead.
	 */
	Set<? extends IType> getContainedTypes();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceModule getResourceModule();
}
