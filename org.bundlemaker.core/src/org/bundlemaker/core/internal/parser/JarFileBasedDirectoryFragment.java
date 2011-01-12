package org.bundlemaker.core.internal.parser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.parser.IJarFileBasedDirectoryFragment;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarFileBasedDirectoryFragment extends AbstractDirectoryFragment
		implements IJarFileBasedDirectoryFragment {

	/** the jar file */
	private JarFile _jarFile;

	/** the jar file entries for this package */
	private List<JarEntry> _jarEntries;

	/**
	 * <p>
	 * Creates a new instance of type {@link JarFileBasedDirectoryFragment}.
	 * </p>
	 * 
	 * @param jarFile
	 */
	public JarFileBasedDirectoryFragment(File file, JarFile jarFile) {
		super(file);

		Assert.isNotNull(jarFile);

		_jarFile = jarFile;
		_jarEntries = new LinkedList<JarEntry>();
	}

	@Override
	public List<String> getContent() {

		List<String> result = new LinkedList<String>();

		for (JarEntry jarEntry : _jarEntries) {
			result.add(jarEntry.getName());
		}

		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public int getResourceCount() {
		return _jarEntries.size();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public JarFile getJarFile() {
		return _jarFile;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public List<JarEntry> getJarEntries() {
		return _jarEntries;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param jarEntry
	 */
	void addJarEntry(JarEntry jarEntry) {
		_jarEntries.add(jarEntry);
	}

	@Override
	public String toString() {
		return "JarFilePackageContent [_jarEntries=" + _jarEntries
				+ ", _jarFile=" + _jarFile + ", getDirectoryRoot()="
				+ getDirectoryFragmentRoot() + ", getWorkspaceRelativeDirectoryRoot()="
				+ getWorkspaceRelativeRoot() + "]";
	}
}
