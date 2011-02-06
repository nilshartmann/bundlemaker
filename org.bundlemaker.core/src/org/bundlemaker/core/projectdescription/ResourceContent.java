package org.bundlemaker.core.projectdescription;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.spi.resource.ResourceStandin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceContent implements IResourceContent {

	/** - */
	private Set<IPath> _sourcePaths;

	/** - */
	private Set<ResourceStandin> _binaryResources;

	/** - */
	private Set<ResourceStandin> _sourceResources;

	/** - */
	private boolean _analyzeSourceResources;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceContent}.
	 * </p>
	 */
	public ResourceContent() {

		//
		_sourcePaths = new HashSet<IPath>();
		_binaryResources = new HashSet<ResourceStandin>();
		_sourceResources = new HashSet<ResourceStandin>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IPath> getSourcePaths() {
		return Collections.unmodifiableSet(_sourcePaths);
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAnalyzeSourceResources() {
		return _analyzeSourceResources;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getResource(IPath path, ContentType type) {

		switch (type) {
		case BINARY: {
			return getBinaryResource(path);
		}
		case SOURCE: {
			return getSourceResource(path);
		}
		default: {
			return null;
		}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends IResource> getResources(ContentType type) {

		switch (type) {
		case BINARY: {
			return getBinaryResources();
		}
		case SOURCE: {
			return getSourceResources();
		}
		default: {
			return null;
		}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getBinaryResource(IPath path) {

		//
		for (ResourceStandin resourceStandin : _binaryResources) {

			if (new Path(resourceStandin.getPath()).equals(path)) {
				return resourceStandin;
			}
		}

		return null;
	}

	@Override
	public Set<? extends IResource> getBinaryResources() {

		//
		return Collections.unmodifiableSet(_binaryResources);
	}

	@Override
	public IResource getSourceResource(IPath path) {

		//
		for (ResourceStandin resourceStandin : _sourceResources) {

			if (new Path(resourceStandin.getPath()).equals(path)) {
				return resourceStandin;
			}
		}

		//
		return null;
	}

	@Override
	public Set<? extends IResource> getSourceResources() {

		//
		return Collections.unmodifiableSet(_sourceResources);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final Set<ResourceStandin> getModifiableSourceResources() {

		//
		return _sourceResources;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final Collection<ResourceStandin> getModifiableBinaryResources() {

		//
		return _binaryResources;
	}

	public Set<IPath> getModifiableSourcePaths() {
		return _sourcePaths;
	}

	public void setAnalyzeSourceResources(boolean analyzeSourceResources) {
		_analyzeSourceResources = analyzeSourceResources;
	}

}
