package org.bundlemaker.core.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResourceStandin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceModule extends
		AbstractModule<IResourceContainer, ResourceContainer> implements
		IResourceModule, ITypeModule {

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
	public IResourceStandin getResource(final String path,
			final ContentType contentType) {

		// create the result set
		final IResourceStandin[] result = new IResourceStandin[1];

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
	public Set<IResourceStandin> getResources(final ContentType contentType) {

		// create the result set
		final Set<IResourceStandin> result = new HashSet<IResourceStandin>();

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
			final boolean includeSourceReferences) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getReferencedTypes(
						hideContainedTypes, includeSourceReferences));
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
	public Set<String> getReferencedPackages(final boolean hideContainedTypes,
			final boolean includeSourceReferences) {

		// create the result set
		final Set<String> result = new HashSet<String>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getReferencedPackages(
						hideContainedTypes, includeSourceReferences));
				return false;
			}
		});

		// return the result
		return Collections.unmodifiableSet(result);
	}

	@Override
	public Set<IReference> getAllReferences(final boolean hideContainedTypes,
			final boolean includeSourceReferences) {

		// create the result set
		final Set<IReference> result = new HashSet<IReference>();

		//
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				result.addAll(resourceContainer.getAllReferences(
						hideContainedTypes, includeSourceReferences));
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

	@Override
	public List<String> getDuplicateResources(ContentType contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDuplicateResources(ContentType contentType) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResourceModule getResourceModule() {
		return this;
	}

	public void initializeContainedTypes() {
		doWithAllContainers(new ContainerClosure<ResourceContainer>() {
			@Override
			public boolean doWithContainer(ResourceContainer resourceContainer) {
				resourceContainer.initializeContainedTypes();
				return false;
			}
		});
	}
}
