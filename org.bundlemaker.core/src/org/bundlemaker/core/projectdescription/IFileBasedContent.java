package org.bundlemaker.core.projectdescription;

import java.util.Set;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Describes a file based content entry in an
 * {@link IBundleMakerProjectDescription}. A file base content entry can contain
 * one or many directories or archive files (*.zip or *.jar).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IFileBasedContent {

	/**
	 * <p>
	 * Returns the internal identifier of this content entry.
	 * </p>
	 * 
	 * @return the internal identifier of this content entry.
	 */
	String getId();

	/**
	 * <p>
	 * Returns the name of this content entry.
	 * </p>
	 * 
	 * @return the name of this content entry.
	 */
	String getName();

	/**
	 * <p>
	 * Returns the version of this content entry.
	 * </p>
	 * 
	 * @return the version of this content entry.
	 */
	String getVersion();

	/**
	 * <p>
	 * Returns the set of all binary paths that belongs to this
	 * {@link IFileBasedContent}.
	 * </p>
	 * 
	 * @return the set of all binary paths that belongs to this
	 *         {@link IFileBasedContent}.
	 */
	Set<IPath> getBinaryPaths();

	/**
	 * <p>
	 * Return <code>true</code> if this content entry is a resource entry that
	 * should be parsed and analyzed, <code>false</code> otherwise.
	 * </p>
	 * 
	 * @return <code>true</code> if this content entry is a resource entry that
	 *         should be parsed and analyzed, <code>false</code> otherwise.
	 */
	boolean isResourceContent();

	/**
	 * <p>
	 * Returns the {@link IResourceContent} instance if this content entry is an
	 * resource content entry (<code>isResourceContent()</code> returns
	 * <code>true</code>).
	 * </p>
	 * 
	 * @return the {@link IResourceContent} instance if this content entry is an
	 *         resource content entry, <code>null</code> otherwise.
	 */
	IResourceContent getResourceContent();
}
