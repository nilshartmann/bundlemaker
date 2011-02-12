package org.bundlemaker.core.internal.resource;

import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ResourceKey;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceStandin extends ResourceKey implements IResource {

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
	public int compareTo(IResource other) {

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

	@Override
	public Set<? extends IReference> getResourceAndTypeReferences() {

		//
		if (_resource == null) {
			// TODO
			throw new RuntimeException();
		}

		return _resource.getResourceAndTypeReferences();
	}

	@Override
	public Set<? extends IReference> getReferences() {

		//
		if (_resource == null) {
			// TODO
			throw new RuntimeException();
		}

		return _resource.getReferences();
	}

	@Override
	public Set<? extends IType> getContainedTypes() {

		//
		if (_resource == null) {
			// TODO
			throw new RuntimeException();
		}

		return _resource.getContainedTypes();
	}

	@Override
	public boolean containsTypes() {

		//
		if (_resource == null) {
			// TODO
			throw new RuntimeException();
		}

		return _resource.containsTypes();
	}

}
