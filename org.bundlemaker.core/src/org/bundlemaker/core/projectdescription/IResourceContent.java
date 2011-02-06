package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceContent {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<IPath> getSourcePaths();

	boolean isAnalyzeSourceResources();

	IResource getResource(IPath path, ContentType type);

	Set<? extends IResource> getResources(ContentType type);

	IResource getBinaryResource(IPath path);

	Set<? extends IResource> getBinaryResources();

	IResource getSourceResource(IPath path);

	Set<? extends IResource> getSourceResources();

}
