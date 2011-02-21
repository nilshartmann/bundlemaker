package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Resource extends ResourceKey implements IModifiableResource {

	/** - */
	private Set<Reference> _references;

	/** - */
	private Set<Type> _containedTypes;

	/** - */
	private Set<IModifiableResource> _stickyResources;

	/** do not set transient! */
	private ResourceStandin _resourceStandin;

	/** - */
	private transient ReferenceContainer _referenceContainer;

	/** - */
	private transient ResourceCache _resourceCache;

	/**
	 * <p>
	 * Creates a new instance of type {@link Resource}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 */
	public Resource(String contentId, String root, String path,
			ResourceCache resourceCache) {
		super(contentId, root, path, resourceCache.getFlyWeightCache());

		Assert.isNotNull(resourceCache);

		_resourceCache = resourceCache;

		_referenceContainer = new ReferenceContainer(
				resourceCache.getFlyWeightCache()) {
			@Override
			protected Set<Reference> createReferencesSet() {
				return references();
			}
		};

	}

	/**
	 * <p>
	 * Creates a new instance of type {@link Resource}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 */
	public Resource(String contentId, String root, String path) {
		super(contentId, root, path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends IReference> getReferences() {
		return Collections.unmodifiableSet(references());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends IType> getContainedTypes() {
		return Collections.unmodifiableSet(containedTypes());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<? extends IResource> getStickyResources() {
		return Collections.unmodifiableSet(stickyResources());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsTypes() {
		return _containedTypes != null && !_containedTypes.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordReference(String fullyQualifiedName,
			ReferenceAttributes referenceAttributes) {

		//
		_referenceContainer.recordReference(fullyQualifiedName,
				referenceAttributes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum) {

		//
		Type type = _resourceCache
				.getOrCreateType(fullyQualifiedName, typeEnum);

		//
		containedTypes().add(type);

		//
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Type getType(String fullyQualifiedName) {

		for (Type containedType : containedTypes()) {
			if (containedType.getFullyQualifiedName()
					.equals(fullyQualifiedName)) {
				return containedType;
			}
		}

		return null;
	}

	@Override
	public void addStickyResource(IModifiableResource stickyResource) {
		stickyResources().add(stickyResource);
	}

	/**
	 * @param resourceStandin
	 */
	public void setResourceStandin(ResourceStandin resourceStandin) {
		_resourceStandin = resourceStandin;
	}

	public ResourceStandin getResourceStandin() {
		return _resourceStandin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bundlemaker.core.resource.IModifiableResource#getModifiableContainedTypes
	 * ()
	 */
	public Set<Type> getModifiableContainedTypes() {
		return containedTypes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bundlemaker.core.resource.IModifiableResource#getModifiableReferences
	 * ()
	 */
	public Set<Reference> getModifiableReferences() {
		return references();
	}

	@Override
	public IResourceModule getResourceModule() {

		//
		if (_resourceStandin == null) {
			throw new RuntimeException();
		}

		return _resourceStandin.getResourceModule();
	}

	@Override
	public int compareTo(IResource arg0) {

		//
		if (_resourceStandin == null) {
			throw new RuntimeException();
		}

		return _resourceStandin.compareTo(arg0);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Reference> references() {

		//
		if (_references == null) {
			_references = new HashSet<Reference>();
		}

		//
		return _references;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Type> containedTypes() {

		if (_containedTypes == null) {
			_containedTypes = new HashSet<Type>();
		}

		return _containedTypes;
	}

	private Set<IModifiableResource> stickyResources() {

		if (_stickyResources == null) {
			_stickyResources = new HashSet<IModifiableResource>();
		}

		return _stickyResources;
	}
}
