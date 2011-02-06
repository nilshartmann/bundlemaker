package org.bundlemaker.core.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.internal.resource.ArchiveFileCache;
import org.bundlemaker.core.internal.resource.FlyWeightCache;
import org.bundlemaker.core.internal.resource.FlyWeightString;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceKey implements IResourceKey {

	/** - */
	private FlyWeightString _contentId;

	/** - */
	private FlyWeightString _root;

	/** - */
	private String _path;

	/** - */
	private ArchiveFileCache _archiveFileCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceKey}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 */
	public ResourceKey(String contentId, String root, String path) {
		this(contentId, root, path, new ArchiveFileCache());
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceKey}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 * @param archiveFileCache
	 */
	protected ResourceKey(String contentId, String root, String path,
			ArchiveFileCache archiveFileCache) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);
		Assert.isNotNull(archiveFileCache);

		_contentId = new FlyWeightString(contentId);
		_root = new FlyWeightString(root);
		_path = path;
		_archiveFileCache = archiveFileCache;
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceKey}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 * @param cache
	 */
	protected ResourceKey(String contentId, String root, String path,
			FlyWeightCache cache) {

		//
		this(contentId, root, path, cache, new ArchiveFileCache());
	}

	protected ResourceKey(String contentId, String root, String path,
			FlyWeightCache cache, ArchiveFileCache archiveFileCache) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);
		Assert.isNotNull(cache);

		_contentId = cache.getFlyWeightString(contentId);
		_root = cache.getFlyWeightString(root);
		_path = path;
		_archiveFileCache = archiveFileCache;

	}

	@Override
	public String getContentId() {
		return _contentId.toString();
	}

	@Override
	public String getRoot() {
		return _root.toString();
	}

	@Override
	public String getPath() {
		return _path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream getInputStream() {

		// jar file?
		if (getRoot().endsWith(".jar") || getRoot().endsWith(".zip")) {

			ZipFile zipFile = _archiveFileCache.getZipFile(getRoot());

			ZipEntry zipEntry = zipFile.getEntry(getPath());

			try {
				return new BufferedInputStream(zipFile.getInputStream(zipEntry));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// get the root file
		File rootFile = new File(getRoot());

		//
		if (rootFile.isDirectory()) {

			try {

				// TODO
				return new BufferedInputStream(new FileInputStream(new File(
						rootFile, getPath())));

			} catch (FileNotFoundException e) {
				throw new RuntimeException("FEHLER");
			}

		} else {
			throw new RuntimeException("FEHLER");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _contentId.hashCode();
		result = prime * result + _path.hashCode();
		result = prime * result + _root.hashCode();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(ResourceKey.class.isAssignableFrom(obj.getClass())))
			return false;
		ResourceKey other = (ResourceKey) obj;
		if (!_contentId.equals(other.getContentId()))
			return false;
		if (!_path.equals(other.getPath()))
			return false;
		if (!_root.equals(other.getRoot()))
			return false;
		return true;
	}
	
	
}
