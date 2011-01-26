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
	 * <b>Note:</b> This reference is set after the model is loaded from the
	 * database. It is <b>not</b> part of the stored model. </p>
	 * 
	 * @return
	 */
	IResourceStandin getResourceStandin();
}
