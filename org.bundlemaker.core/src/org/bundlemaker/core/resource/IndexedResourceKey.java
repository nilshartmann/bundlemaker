package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IndexedResourceKey implements IResourceKey {

	/** - */
	private String _contentId;

	/** - */
	private int _rootIndex;

	/** - */
	private String _path;

	/** - */
	private StringCache _stringCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link IndexedResourceKey}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 */
	public IndexedResourceKey(String contentId, String root, String path,
			StringCache stringCache) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);
		Assert.isNotNull(path);

		_stringCache = stringCache;

		_contentId = contentId;
		_rootIndex = _stringCache.storeString(root);
		_path = path;
	}

	@Override
	public String getContentId() {
		return _contentId;
	}

	@Override
	public String getRoot() {
		return _stringCache.getString(_rootIndex);
	}

	@Override
	public String getPath() {
		return _path;
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
		result = prime * result + getRoot().hashCode();
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
		if (!(obj instanceof IResourceKey))
			return false;
		IResourceKey other = (IResourceKey) obj;
		if (!_contentId.equals(other.getContentId()))
			return false;
		if (!_path.equals(other.getPath()))
			return false;
		if (!getRoot().equals(other.getRoot()))
			return false;
		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public StringCache getResourceCache() {
		return _stringCache;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean hasResourceCache() {
		return _stringCache != null;
	}
}
