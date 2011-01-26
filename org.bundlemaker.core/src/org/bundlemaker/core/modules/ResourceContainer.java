package org.bundlemaker.core.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ResourceStandin;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceContainer extends TypeContainer implements
		IResourceContainer {

	/** the binary resources */
	private Set<IResourceStandin> _binaryResources;

	/** the source resources */
	private Set<IResourceStandin> _sourceResources;

	/** the containing resource module */
	private IResourceModule _resourceModule;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceContainer}.
	 * </p>
	 */
	public ResourceContainer() {

		// create the resource sets
		_binaryResources = new HashSet<IResourceStandin>();
		_sourceResources = new HashSet<IResourceStandin>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsResource(String resourceType, ContentType contentType) {
		return getResource(resourceType, contentType) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceStandin getResource(String path, ContentType contentType) {

		//
		for (IResourceStandin resourceStandin : getModifiableResourcesSet(contentType)) {

			//
			if (resourceStandin.getPath().equals(path)) {
				return resourceStandin;
			}
		}

		// return null
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IResourceStandin> getResources(ContentType contentType) {

		//
		return Collections
				.unmodifiableSet(getModifiableResourcesSet(contentType));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getReferencedTypes(boolean hideContainedTypes,
			boolean includeSourceReferences) {

		// return result
		return getReferences(hideContainedTypes, includeSourceReferences, false);
	}

	@Override
	public Set<IReference> getAllReferences(boolean hideContainedTypes,
			boolean includeSourceReferences) {

		// create the result
		Set<IReference> result = new HashSet<IReference>();

		//
		getIReferences(_binaryResources, hideContainedTypes, result);

		//
		if (includeSourceReferences) {
			getIReferences(_sourceResources, hideContainedTypes, result);
		}

		// return result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getReferencedPackages(boolean hideContainedTypes,
			boolean includeSourceReferences) {

		// return result
		return getReferences(hideContainedTypes, includeSourceReferences, true);
	}

	public IResourceModule getResourceModule() {
		return _resourceModule;
	}

	public void setResourceModule(IResourceModule resourceModule) {
		_resourceModule = resourceModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContainer
	 */
	public void initializeContainedTypes() {

		//
		for (IResourceStandin resourceStandin : _binaryResources) {

			// step 1: add all contained types
			for (IType type : resourceStandin.getResource().getContainedTypes()) {
				getModifiableContainedTypes().add(type.getFullyQualifiedName());
			}

			//
			((ResourceStandin) resourceStandin)
					.setResourceModule(_resourceModule);
		}

		//
		for (IResourceStandin resourceStandin : _sourceResources) {

			// step 1: add all contained types
			for (IType type : resourceStandin.getResource().getContainedTypes()) {
				getModifiableContainedTypes().add(type.getFullyQualifiedName());
			}

			//
			((ResourceStandin) resourceStandin)
					.setResourceModule(_resourceModule);
		}

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContainer
	 * @param contentType
	 * @return
	 */
	public Set<IResourceStandin> getModifiableResourcesSet(
			ContentType contentType) {

		Assert.isNotNull(contentType);

		// return the resource set
		return ContentType.BINARY.equals(contentType) ? _binaryResources
				: _sourceResources;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param hideContainedTypes
	 * @param includeSourceReferences
	 * @param collectPackages
	 * @return
	 */
	private Set<String> getReferences(boolean hideContainedTypes,
			boolean includeSourceReferences, boolean collectPackages) {

		// create the result
		Set<String> result = new HashSet<String>();

		//
		getReferences(_binaryResources, hideContainedTypes, result,
				collectPackages);

		//
		if (includeSourceReferences) {
			getReferences(_sourceResources, hideContainedTypes, result,
					collectPackages);
		}

		// return result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resources
	 * @param hideContainedTypes
	 * @param containedTypes
	 * @param result
	 */
	private void getReferences(Set<IResourceStandin> resources,
			boolean hideContainedTypes, Set<String> result,
			boolean collectPackages) {

		// iterate over all resources
		for (IResourceStandin resourceStandin : resources) {

			// get resource
			IResource resource = resourceStandin.getResource();

			// iterate over all resources
			for (IReference reference : resource.getReferences()) {

				if (!hideContainedTypes
						|| !getContainedTypes().contains(
								reference.getFullyQualifiedName())) {

					String entry;
					if (collectPackages) {

						if (reference.getFullyQualifiedName().indexOf('.') != -1) {
							entry = reference.getFullyQualifiedName()
									.substring(
											0,
											reference.getFullyQualifiedName()
													.lastIndexOf('.'));
						} else {
							entry = "";
						}

					} else {
						entry = reference.getFullyQualifiedName();
					}

					if (!result.contains(entry)) {
						result.add(entry);
					}

				}
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resources
	 * @param hideContainedTypes
	 * @param result
	 */
	private void getIReferences(Set<IResourceStandin> resources,
			boolean hideContainedTypes, Set<IReference> result) {

		// iterate over all resources
		for (IResourceStandin resourceStandin : resources) {

			// step 1: get resource
			IResource resource = resourceStandin.getResource();

			// iterate over all resources
			for (IReference reference : resource.getReferences()) {

				if (!hideContainedTypes
						|| !getContainedTypes().contains(
								reference.getFullyQualifiedName())) {

					result.add(reference);
				}
			}

			// step 2
			for (IType type : resource.getContainedTypes()) {

				for (IReference reference : type.getReferences()) {

					if (!hideContainedTypes
							|| !getContainedTypes().contains(
									reference.getFullyQualifiedName())) {

						result.add(reference);
					}
				}
			}
		}
	}
}
