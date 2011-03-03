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
	 * Returns the {@link IResource} with 
	 * </p>
	 * 
	 * @param path
	 * @param type
	 * @return
	 */
	IResource getResource(IPath path, ContentType type);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	Set<? extends IResource> getResources(ContentType type);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	IResource getBinaryResource(IPath path);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<? extends IResource> getBinaryResources();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	IResource getSourceResource(IPath path);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<? extends IResource> getSourceResources();

}
