package org.bundlemaker.core.parser;

import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IJarFileBasedDirectoryFragment extends IDirectoryFragment {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public JarFile getJarFile();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<JarEntry> getJarEntries();
}
