package org.bundlemaker.core.internal.parser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.parser.IFolderBasedDirectoryFragment;
import org.eclipse.core.resources.IFile;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FolderBasedDirectoryFragment extends AbstractDirectoryFragment implements
		IFolderBasedDirectoryFragment {

	/** - */
	private List<String> _entries = new LinkedList<String>();

	/**
	 * <p>
	 * Creates a new instance of type {@link FolderBasedDirectoryFragment}.
	 * </p>
	 * 
	 * @param file
	 */
	public FolderBasedDirectoryFragment(File file) {
		super(file);
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link FolderBasedDirectoryFragment}.
	 * </p>
	 * 
	 * @param ifile
	 */
	public FolderBasedDirectoryFragment(IFile ifile) {
		super(ifile);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	public void addEntry(String entry) {
		_entries.add(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getContent() {
		return _entries;
	}

	@Override
	public List<File> getFileEntries() {

		List<File> result = new LinkedList<File>();

		for (String entry : _entries) {
			result.add(new File(getDirectoryFragmentRoot(), entry));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getResourceCount() {
		return _entries.size();
	}

	@Override
	public String toString() {
		return "DirectoryPackageContent [getPackageRoot()="
				+ getDirectoryFragmentRoot() + ", _entries=" + _entries + "]";
	}
}
