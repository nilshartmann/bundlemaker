package org.bundlemaker.core.parser;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IResource;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDirectoryFragment {

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public IDirectory getDirectory();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public File getDirectoryFragmentRoot();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isWorkspaceRelative();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IResource getWorkspaceRelativeRoot();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public int getResourceCount();

	/**
	 * <p>
	 * Returns the paths of the contained content, e.g.
	 * "com/wuetherich/test/MyClass.class" or "com/wuetherich/test/MyClass.java"
	 * </p>
	 * 
	 * @return
	 */
	public List<String> getContent();
}
