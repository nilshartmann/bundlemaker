package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceContainer;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceModule extends
		AbstractModule<IResourceContainer, ResourceContainer> implements
		IResourceModule, IModule {

	/**
	 * <p>
	 * Creates a new instance of type {@link ResourceModule}.
	 * </p>
	 * 
	 * @param moduleIdentifier
	 */
	public ResourceModule(IModuleIdentifier moduleIdentifier) {
		super(moduleIdentifier, new ResourceContainer());
		((ResourceContainer) getSelfResourceContainer())
				.setResourceModule(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsResource(String path, ContentType contentType) {
		return getResource(path, contentType) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getResource(final String path,
			final ContentType contentType) {

		// create the result set
		final IResource[] result = new IResource[1];

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result[0] = resourceContainer.getResource(path, contentType);
				return result[0] != null;
			}
		});

		// return the result
		return result[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IResource> getResources(final ContentType contentType) {

		// create the result set
		final Set<IResource> result = new HashSet<IResource>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getResources(contentType));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getReferencedTypes(final boolean hideContainedTypes,
			final boolean includeSourceReferences,
			final boolean includeIndirectReferences) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getReferencedTypes(
						hideContainedTypes, includeSourceReferences,
						includeIndirectReferences));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getReferencedPackageNames(
			final boolean hideContainedTypes,
			final boolean includeSourceReferences,
			final boolean includeIndirectReferences) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getReferencedPackageNames(
						hideContainedTypes, includeSourceReferences,
						includeIndirectReferences));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	@Override
	public Set<IReference> getAllReferences(final boolean hideContainedTypes,
			final boolean includeSourceReferences,
			final boolean includeIndirectReferences) {

		// create the result set
		final Set<IReference> result = new HashSet<IReference>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getAllReferences(
						hideContainedTypes, includeSourceReferences,
						includeIndirectReferences));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceContainer getSelfResourceContainer() {
		return getSelfContainer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, ? extends IResourceContainer> getContainedResourceContainers() {
		return getEmbeddedContainers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceModule getResourceModule() {
		return this;
	}

	/**
	 * <p>
	 * </p>
	 */
	public void initializeContainedTypes() {

		// iterate over all containers and initialize the contained types
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {

			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {

				// initialize the contained types of the resource container
				resourceContainer.initialize();

				// return false to indicate that all containers should be
				// processed
				return false;
			}
		});
	}
}
