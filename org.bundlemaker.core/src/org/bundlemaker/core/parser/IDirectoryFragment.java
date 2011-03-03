package org.bundlemaker.core.parser;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.resource.IResourceKey;
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
	public Set<IResourceKey> getResourceKeys();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Deprecated
	public IResource getWorkspaceRelativeRoot();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Deprecated
	public int getResourceCount();

	/**
	 * <p>
	 * Returns the paths of the contained content, e.g.
	 * "com/wuetherich/test/MyClass.class" or "com/wuetherich/test/MyClass.java"
	 * </p>
	 * 
	 * @return
	 */
	@Deprecated
	public List<String> getContent();
}
