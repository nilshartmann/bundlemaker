package org.bundlemaker.core.parser;

import java.io.File;
import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IFolderBasedDirectoryFragment extends IDirectoryFragment {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<File> getFileEntries();
}
