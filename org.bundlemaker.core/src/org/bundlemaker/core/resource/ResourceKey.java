package org.bundlemaker.core.resource;

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
