package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Defines the interface for project content that contains resources that should
 * be parsed and analyzed.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceContent {

	/**
	 * <p>
	 * Returns the set of all defined source paths for this content entry.
	 * </p>
	 * 
	 * @return
	 */
	Set<IPath> getSourcePaths();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isAnalyzeSourceResources();

	/**
	 * <p>
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
