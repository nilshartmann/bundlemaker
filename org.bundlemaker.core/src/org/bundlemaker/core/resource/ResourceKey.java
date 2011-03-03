package org.bundlemaker.core.resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.internal.resource.FlyWeightCache;
import org.bundlemaker.core.internal.resource.FlyWeightString;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Default implementation of the interface {@link IResourceKey}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be extended by clients.
 */
public class ResourceKey implements IResourceKey {

	/** the content id */
	private FlyWeightString _contentId;

	/** the root of the resource */
	private FlyWeightString _root;

	/** the path of the resource */
	private String _path;

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
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);

		_contentId = new FlyWeightString(contentId);
		_root = new FlyWeightString(root);
		_path = path;
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

		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);
		Assert.isNotNull(cache);

		_contentId = cache.getFlyWeightString(contentId);
		_root = cache.getFlyWeightString(root);
		_path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContentId() {
		return _contentId.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRoot() {
		return _root.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return _path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDirectory() {
		int lastIndex = _path.lastIndexOf('/');
		return lastIndex != -1 ? _path.substring(0, lastIndex) : "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidJavaPackage() {

		//
		String[] elements = getPath().split("/");

		//
		for (int i = 0; i < elements.length - 1; i++) {

			String element = elements[i];

			if (!isValidJavaIdentifier(element)) {
				return false;
			}
		}

		//
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPackageName() {
		return getDirectory().replace('/', '.');
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		int lastIndex = _path.lastIndexOf('/');
		return lastIndex != -1 ? _path.substring(lastIndex + 1) : _path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getContent() {

		// jar file?
		if (getRoot().endsWith(".jar") || getRoot().endsWith(".zip")) {

			try {
				ZipFile zipFile = new ZipFile(new File(getRoot()));
				ZipEntry zipEntry = zipFile.getEntry(getPath());

				InputStream is = zipFile.getInputStream(zipEntry);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();

				byte[] result = buffer.toByteArray();

				is.close();
				zipFile.close();

				return result;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("");
			}
		}

		// get the root file
		File rootFile = new File(getRoot());

		//
		if (rootFile.isDirectory()) {

			try {

				InputStream is = new BufferedInputStream(new FileInputStream(
						new File(rootFile, getPath())));
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();

				byte[] result = buffer.toByteArray();

				is.close();
				buffer.close();

				return result;

			} catch (Exception e) {
				throw new RuntimeException("FEHLER");
			}

		} else {
			throw new RuntimeException("FEHLER");
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isValidJavaIdentifier(String s) {

		// an empty or null string cannot be a valid identifier
		if (s == null || s.length() == 0) {
			return false;
		}

		char[] c = s.toCharArray();
		if (!Character.isJavaIdentifierStart(c[0])) {
			return false;
		}

		for (int i = 1; i < c.length; i++) {
			if (!Character.isJavaIdentifierPart(c[i])) {
				return false;
			}
		}

		return true;
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

	@Override
	public String toString() {
		return "ResourceKey [_contentId=" + _contentId + ", _root=" + _root
				+ ", _path=" + _path + "]";
	}
}
