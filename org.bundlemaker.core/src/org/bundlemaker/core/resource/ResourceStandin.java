package org.bundlemaker.core.resource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceStandin extends ResourceKey implements IResourceStandin {

	/** - */
	private Resource _resource;

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
