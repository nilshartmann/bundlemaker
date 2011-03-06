package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Defines the interface for project content that contains resources that should
 * be parsed and analyzed.
 * </p>
 * <p>
 * To define a resource content entry you have to call one of the
 * <code>addResourceContent()</code> methods on the interface
 * {@link IBundleMakerProjectDescription}.
 * </p>
 * <p>
 * <p>
 * <b>Paths and Resources</b>
 * <p>
 * A resource content contains paths and resources. A <b>path</b> is a a simple
 * pointer to a location somewhere on the file system. A <b>resource</b>
 * represents the (parsed) content, that is available from that location.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResourceContent {

	/**
	 * <p>
	 * Returns the set of all defined source paths for this content entry. The
	 * result set is never <code>null</code>, but maybe empty.
	 * </p>
	 * 
	 * <p>
	 * Note: to get a list of the binary paths of this IResourceContent, use
	 * <tt>getBinaryPaths() on its owning IFileBasedContent instance
	 * 
	 * @return the set of all defined source paths for this content entry.
	 */
	Set<IPath> getSourcePaths();

	/**
	 * <p>
	 * Returns <code>true</code> if specified source files should be parsed.
	 * E.g. if you just want to create an eclipse-specific source bundle for a
	 * given source archive, you set this value to <code>false</code>.
	 * </p>
	 * 
	 * @return <code>true</code> if specified source files should be parsed.
	 */
	boolean isAnalyzeSourceResources();

	/**
	 * <p>
	 * Returns the {@link IResource} for the specified path and type
	 * </p>
	 * 
	 * @param path
	 *            The path of the resource that should be returned
	 * @param type
	 *            The type of the resource, either binary or source
	 * @return
	 */
	IResource getResource(IPath path, ContentType type);

	/**
	 * <p>
	 * Returns a {@link Set} of all resources of the specified type
	 * </p>
	 * 
	 * @param type
	 * @return a Set of resources, never null.
	 */
	Set<? extends IResource> getResources(ContentType type);

	/**
	 * <p>
	 * Returns the binary {@link IResource resource} for the specified path
	 * </p>
	 * 
	 * @param path
	 * @return the resource the path points to or null if no such resource is
	 *         available
	 */
	IResource getBinaryResource(IPath path);

	/**
	 * <p>
	 * Returns a {@link Set} of all binary resources
	 * </p>
	 * <p>
	 * This is a convenience method for {@link #getResources(ContentType)
	 * getResources(ContentType.BINARY)}
	 * </p>
	 * 
	 * @return a Set of resources, never null.
	 */
	Set<? extends IResource> getBinaryResources();

	/**
	 * <p>
	 * Returns the {@link IResource Resource} for the specified path
	 * </p>
	 * 
	 * @param path
	 * @return The resource for the given path or null if there is no resource
	 *         for the given path
	 */
	IResource getSourceResource(IPath path);

	/**
	 * Returns all source resources
	 * <p>
	 * This is a convenience method for {@link #getResources(ContentType)
	 * getResources(ContentType.SOURCE)}
	 * </p>
	 * 
	 * @return a Set of resources, never null.
	 */
	Set<? extends IResource> getSourceResources();

}
