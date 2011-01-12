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
	 */
	public ResourceStandin(String contentId, String root, String path,
			StringCache cache) {
		super(contentId, root, path, cache);
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
}
