package org.bundlemaker.core.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

public class Resource extends ResourceKey implements IResource {

	/** - */
	private Set<Reference> _references;

	/** - */
	private Set<String> _containedTypes;

	/** do not set transient! */
	private Set<Resource> _associatedResource;

	/** do not set transient! */
	private IResourceStandin _resourceStandin;

	/** - */
	private transient FlyWeightCache _flyWeightCache;

	/** - */
	private transient Map<ReferenceKey, Reference> _referenceMap;

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
			FlyWeightCache cache) {
		super(contentId, root, path, cache);

		_flyWeightCache = cache;
		_referenceMap = new HashMap<ReferenceKey, Reference>();
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
	public Set<? extends IResource> getAssociatedResources() {
		return Collections.unmodifiableSet(associatedResources());
	}

	@Override
	public Set<String> getContainedTypes() {
		return Collections.unmodifiableSet(containedTypes());
	}

	@Override
	public IResourceStandin getResourceStandin() {
		return _resourceStandin;
	}

	public void setResourceStandin(IResourceStandin resourceStandin) {
		_resourceStandin = resourceStandin;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<String> getModifiableContainedTypes() {
		return containedTypes();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public void createReference(String fullyQualifiedName,
			ReferenceType referenceType, Boolean isExtends,
			Boolean isImplements, Boolean isSourceCodeDependency,
			Boolean isByteCodeDependency) {

		// create the key
		Assert.isNotNull(referenceType);
		ReferenceKey key = new ReferenceKey(fullyQualifiedName, referenceType);

		// get the reference
		Assert.isNotNull(_referenceMap, "Reference Map is null");
		Reference reference = _referenceMap.get(key);

		Assert.isNotNull(_flyWeightCache, "_flyWeightCache is not set!");

		// create completely new one
		if (reference == null) {

			reference = _flyWeightCache
					.getReference(
							fullyQualifiedName,
							referenceType,
							isExtends != null ? isExtends : false,
							isImplements != null ? isImplements : false,
							isSourceCodeDependency != null ? isSourceCodeDependency
									: false,
							isByteCodeDependency != null ? isByteCodeDependency
									: false);

			references().add(reference);
			_referenceMap.put(key, reference);

			return;
		}

		// return if current dependency matches the requested one
		if (equals(
				isSourceCodeDependency,
				reference.isSourcecodeDependency()
						&& equals(isByteCodeDependency,
								reference.isBytecodeDependency()))) {
			return;
		}

		// if current dependency does not match the requested one, we have to
		// request a new one
		references().remove(reference);

		reference = _flyWeightCache.getReference(
				fullyQualifiedName,
				referenceType,
				chooseValue(isExtends, reference.isExtends()),
				chooseValue(isImplements, reference.isImplements()),
				chooseValue(isSourceCodeDependency,
						reference.isSourcecodeDependency()),
				chooseValue(isByteCodeDependency,
						reference.isBytecodeDependency()));

		references().add(reference);
		_referenceMap.put(key, reference);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param associatedResource
	 */
	public void addAssociatedResource(Resource associatedResource) {

		// add associated resource
		associatedResources().add(associatedResource);

		if (associatedResource._references != null) {

			// add references
			for (Reference reference : associatedResource._references) {

				//
				createReference(reference.getFullyQualifiedName(),
						reference.getReferenceType(), null, null, null, true);
			}
		}
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
	 * @param b1
	 * @param b2
	 * @return
	 */
	private boolean equals(Boolean b1, boolean b2) {

		//
		return b1 != null && b1.booleanValue() == b2;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private boolean chooseValue(Boolean b1, boolean b2) {

		//
		return b2 || b1 != null && b1.booleanValue();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	private Set<Resource> associatedResources() {

		if (_associatedResource == null) {
			_associatedResource = new HashSet<Resource>();
		}

		return _associatedResource;
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

	private Set<String> containedTypes() {

		if (_containedTypes == null) {
			_containedTypes = new HashSet<String>();
		}

		return _containedTypes;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
	 * 
	 */
	public static class ReferenceKey {

		/** - */
		private String fullyQualifiedName;

		/** - */
		private ReferenceType referenceType;

		/**
		 * <p>
		 * Creates a new instance of type {@link ReferenceKey}.
		 * </p>
		 * 
		 * @param fullyQualifiedName
		 * @param referenceType
		 */
		public ReferenceKey(String fullyQualifiedName,
				ReferenceType referenceType) {
			this.fullyQualifiedName = fullyQualifiedName;
			this.referenceType = referenceType;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((fullyQualifiedName == null) ? 0 : fullyQualifiedName
							.hashCode());
			result = prime * result
					+ ((referenceType == null) ? 0 : referenceType.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ReferenceKey other = (ReferenceKey) obj;
			if (fullyQualifiedName == null) {
				if (other.fullyQualifiedName != null)
					return false;
			} else if (!fullyQualifiedName.equals(other.fullyQualifiedName))
				return false;
			if (referenceType != other.referenceType)
				return false;
			return true;
		}

	}
}
