package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IFileBasedContent {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	String getId();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	String getName();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<IPath> getBinaryPaths();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isResourceContent();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceContent getResourceContent();
}
