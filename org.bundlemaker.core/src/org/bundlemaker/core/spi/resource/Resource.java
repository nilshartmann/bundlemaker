package org.bundlemaker.core.spi.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IModifiableResource;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.TypeEnum;
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

	@Override
	public Set<? extends IReference> getReferences() {
		return Collections.unmodifiableSet(references());
	}

	@Override
	public Set<? extends IType> getContainedTypes() {
		return Collections.unmodifiableSet(containedTypes());
	}

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.resource.IModifiableResource#recordReference(java.lang.String, org.bundlemaker.core.resource.ReferenceType, boolean, boolean, boolean, boolean, boolean)
	 */
	@Override
	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompiletime, boolean isRuntime) {

		_referenceContainer.recordReference(fullyQualifiedName, referenceType,
				isExtends, isImplements, isClassAnnotation, isCompiletime,
				isRuntime);
	}

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.resource.IModifiableResource#getOrCreateType(java.lang.String, org.bundlemaker.core.resource.TypeEnum)
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
	 * @param resourceStandin
	 */
	public void setResourceStandin(ResourceStandin resourceStandin) {
		_resourceStandin = resourceStandin;
	}
	
	public ResourceStandin getResourceStandin() {
		return _resourceStandin;
	}

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableContainedTypes()
	 */
	public Set<Type> getModifiableContainedTypes() {
		return containedTypes();
	}

	/* (non-Javadoc)
	 * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableReferences()
	 */
	public Set<Reference> getModifiableReferences() {
		return references();
	}
	
	

	@Override
	public IResourceModule getResourceModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(IResource arg0) {
		// TODO Auto-generated method stub
		return 0;
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
}
