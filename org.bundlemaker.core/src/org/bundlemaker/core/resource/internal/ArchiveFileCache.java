package org.bundlemaker.core.resource.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArchiveFileCache {

	/** the internal ZipFile cache */
	private Map<String, ZipFile> _cache = new HashMap<String, ZipFile>();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param root
	 * @return
	 */
	public ZipFile getZipFile(String root) {

		//
		if (_cache.containsKey(root)) {
			return _cache.get(root);
		}

		// get the root file
		File rootFile = new File(root);

		if (rootFile.isFile()) {

			ZipFile zipFile;
			try {
				zipFile = new ZipFile(rootFile);
				_cache.put(root, zipFile);
				return _cache.get(root);

			} catch (ZipException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("FEHLER");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("FEHLER");
			}

		} else {
			// TODO
			throw new RuntimeException("FEHLER");
		}
	}
}
