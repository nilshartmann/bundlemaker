package org.bundlemaker.core.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.spi.resource.ResourceStandin;
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
	private Set<IResource> _binaryResources;

	/** the source resources */
	private Set<IResource> _sourceResources;

	/** the containing resource module */
	private IResourceModule _resourceModule;

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceContainer}.
	 * </p>
	 */
	public ResourceContainer() {

		// create the resource sets
		_binaryResources = new HashSet<IResource>();
		_sourceResources = new HashSet<IResource>();
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
	public IResource getResource(String path, ContentType contentType) {

		//
		for (IResource resourceStandin : getModifiableResourcesSet(contentType)) {

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
	public Set<IResource> getResources(ContentType contentType) {

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
	 * Initializes the contained types of this resource container.
	 * </p>
	 */
	public void initialize() {
		
		getModifiableContainedTypesMap().clear();

		// step 1: iterate over all binary resources...
		for (IResource resourceStandin : _binaryResources) {

			// ... and add all contained types
			for (IType type : resourceStandin.getContainedTypes()) {

				getModifiableContainedTypesMap().put(
						type.getFullyQualifiedName(), type);
			}

			// set the back-reference
			((ResourceStandin) resourceStandin)
					.setResourceModule(_resourceModule);
		}

		// step 2: iterate over all source resources...
		for (IResource resourceStandin : _sourceResources) {

			// ... and add all contained types
			for (IType type : resourceStandin.getContainedTypes()) {

				// TODO
				getModifiableContainedTypesMap().put(
						type.getFullyQualifiedName(), type);
			}

			// set the back-reference
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
	public Set<IResource> getModifiableResourcesSet(
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
	private void getReferences(Set<IResource> resources,
			boolean hideContainedTypes, Set<String> result,
			boolean collectPackages) {

		// iterate over all resources
		for (IResource resource : resources) {

			// iterate over all resources
			for (IReference reference : resource.getReferences()) {

				if (!hideContainedTypes
						|| !getContainedTypeNames().contains(
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
	private void getIReferences(Set<IResource> resources,
			boolean hideContainedTypes, Set<IReference> result) {

		// iterate over all resources
		for (IResource resource : resources) {

			// iterate over all resources
			for (IReference reference : resource.getReferences()) {

				if (!hideContainedTypes
						|| !getContainedTypeNames().contains(
								reference.getFullyQualifiedName())) {

					result.add(reference);
				}
			}

			// step 2
			for (IType type : resource.getContainedTypes()) {

				for (IReference reference : type.getReferences()) {

					if (!hideContainedTypes
							|| !getContainedTypeNames().contains(
									reference.getFullyQualifiedName())) {

						result.add(reference);
					}
				}
			}
		}
	}
}
