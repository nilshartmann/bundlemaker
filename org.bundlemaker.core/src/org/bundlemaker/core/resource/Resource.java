package org.bundlemaker.core.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.resource.internal.FlyWeightCache;
import org.bundlemaker.core.resource.internal.ReferenceContainer;
import org.eclipse.core.runtime.Assert;

public class Resource extends ResourceKey implements IResource {

	/** - */
	private Set<Reference> _references;

	/** - */
	private Set<Type> _containedTypes;

	/** do not set transient! */
	private IResourceStandin _resourceStandin;

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
			FlyWeightCache cache, ResourceCache resourceCache) {
		super(contentId, root, path, cache);

		Assert.isNotNull(resourceCache);

		_resourceCache = resourceCache;

		_referenceContainer = new ReferenceContainer(cache) {
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

	@Override
	public Set<? extends IReference> getReferences() {
		return Collections.unmodifiableSet(references());
	}

	@Override
	public Set<? extends IType> getContainedTypes() {
		return Collections.unmodifiableSet(containedTypes());
	}

	@Override
	public IResourceStandin getResourceStandin() {
		return _resourceStandin;
	}

	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, Boolean isExtends,
			Boolean isImplements, Boolean isCompiletime, Boolean isRuntime) {

		_referenceContainer.recordReference(fullyQualifiedName, referenceType,
				isExtends, isImplements, isCompiletime, isRuntime);
	}

	public Type getOrCreateType(String fullyQualifiedName) {

		//
		Type type = _resourceCache.getOrCreateType(fullyQualifiedName);

		containedTypes().add(type);

		return type;
	}

	/**
	 * @param resourceStandin
	 */
	public void setResourceStandin(IResourceStandin resourceStandin) {
		_resourceStandin = resourceStandin;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<Type> getModifiableContainedTypes() {
		return containedTypes();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<Reference> getModifiableReferences() {
		return references();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Reference> references() {

		if (_references == null) {
			_references = new HashSet<Reference>();
		}

		return _references;
	}

	/**
	 * @return
	 */
	private Set<Type> containedTypes() {

		if (_containedTypes == null) {
			_containedTypes = new HashSet<Type>();
		}

		return _containedTypes;
	}
}
