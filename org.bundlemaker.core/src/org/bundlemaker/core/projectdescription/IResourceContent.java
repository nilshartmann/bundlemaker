package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.bundlemaker.core.resource.IResourceStandin;
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

	IResourceStandin getResource(IPath path, ContentType type);

	Set<? extends IResourceStandin> getResources(ContentType type);

	IResourceStandin getBinaryResource(IPath path);

	Set<? extends IResourceStandin> getBinaryResources();

	IResourceStandin getSourceResource(IPath path);

	Set<? extends IResourceStandin> getSourceResources();

}
