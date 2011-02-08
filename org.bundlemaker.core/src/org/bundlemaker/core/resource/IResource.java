package org.bundlemaker.core.resource;

import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * Defines the common interface for resources. A resource is either a file (e.g.
 * a java source file or a class file) or an entry in an archive file. It
 * contains 0 to n {@link IType ITypes}. It also contains 0 to n
 * {@link IReference IReferences}.
 * </p>
 * <p>
 * Note that both the {@link IResource} and the contained {@link IType ITypes}
 * can contain {@link IReference IReferences}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResource extends IResourceKey, Comparable<IResource> {

	/**
	 * <p>
	 * Returns all {@link IReference IReferences} that are originated in this
	 * resource.
	 * </p>
	 * <p>
	 * <b>Note:</b> The result set does <b>not</b> contain any references of the
	 * contained types. You explicitly have to request them by iterating over
	 * the contained types and calling {@link IType#getReferences()} on each
	 * type.
	 * </p>
	 * 
	 * @return all {@link IReference IReferences} that are originated in this
	 *         resource.
	 */
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
	 * Returns the {@link IResourceModule} that contains this {@link IResource}.
	 * </p>
	 * 
	 * @return the {@link IResourceModule} that contains this {@link IResource}.
	 */
	IResourceModule getResourceModule();
}
