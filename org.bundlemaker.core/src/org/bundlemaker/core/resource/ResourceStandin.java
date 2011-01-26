package org.bundlemaker.core.resource;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceStandin extends ResourceKey implements IResourceStandin {

	/** - */
	private Resource _resource;

	/** do not set transient! */
	private IResourceModule _resourceModule;

	public IResourceModule getResourceModule() {
		return _resourceModule;
	}

	public void setResourceModule(IResourceModule resourceModule) {
		_resourceModule = resourceModule;
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceStandin}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 * @param archiveFileCache
	 */
	public ResourceStandin(String contentId, String root, String path,
			ArchiveFileCache archiveFileCache) {
		super(contentId, root, path, archiveFileCache);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getResource() {
		return _resource;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource) {
		_resource = resource;
	}

	@Override
	public int compareTo(IResourceStandin other) {

		if (!getContentId().equals(other.getContentId())) {
			return getContentId().compareTo(other.getContentId());
		}
		if (!getRoot().equals(other.getRoot())) {
			return getRoot().compareTo(other.getRoot());
		}
		if (!getPath().equals(other.getPath())) {
			return getPath().compareTo(other.getPath());
		}

		return 0;
	}
}
