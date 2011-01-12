package org.bundlemaker.core.parser;

import java.util.List;

import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDirectory {

	/**
	 * <p>
	 * Returns the {@link IFileBasedContent} that defines this directory entry.
	 * </p>
	 * 
	 * @return
	 */
	public IFileBasedContent getFileBasedContent();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IPath getDirectoryName();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<IDirectoryFragment> getBinaryDirectoryFragments();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public int getBinaryContentCount();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean hasSourceContent();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<IDirectoryFragment> getSourceDirectoryFragments();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public int getSourceContentCount();
}
