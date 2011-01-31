package org.bundlemaker.core.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Type implements IType {

	/** the fully qualified name */
	private FlyWeightString _fullyQualifiedName;

	/** the references set */
	private Set<Reference> _references;

	/** the type of this type (enum, class, interface, annotation) **/
	private TypeEnum _typeEnum;

	/** non-persistent: the source resource */
	private Resource _sourceResource;

	/** non-persistent: the binary resource */
	private Resource _binaryResource;

	/** transient: the reference container */
	private transient ReferenceContainer _referenceContainer;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param flyWeightCache
	 */
	public Type(String fullyQualifiedName, TypeEnum typeEnum,
			FlyWeightCache flyWeightCache) {

		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(typeEnum);
		Assert.isNotNull(flyWeightCache);

		//
		_fullyQualifiedName = flyWeightCache
				.getFlyWeightString(fullyQualifiedName);

		// the type of the type
		_typeEnum = typeEnum;

		//
		_referenceContainer = new ReferenceContainer(flyWeightCache) {
			@Override
			protected Set<Reference> createReferencesSet() {
				return references();
			}
		};
	}

	@Override
	public String getFullyQualifiedName() {
		return _fullyQualifiedName.toString();
	}

	@Override
	public Set<? extends IReference> getReferences() {
		return Collections.unmodifiableSet(references());
	}

	@Override
	public TypeEnum getType() {
		return _typeEnum;
	}

	@Override
	public IResource getSourceResource() {
		return _sourceResource;
	}

	@Override
	public IResource getBinaryResource() {
		return _binaryResource;
	}

	@Override
	public boolean hasSourceResource() {
		return _sourceResource != null;
	}

	@Override
	public boolean hasBinaryResource() {
		return _binaryResource != null;
	}

	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompiletime, boolean isRuntime) {

		_referenceContainer.recordReference(fullyQualifiedName, referenceType,
				isExtends, isImplements, isClassAnnotation, isCompiletime,
				isRuntime);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param sourceResource
	 */
	public void setSourceResource(Resource sourceResource) {
		_sourceResource = sourceResource;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param binaryResource
	 */
	public void setBinaryResource(Resource binaryResource) {
		_binaryResource = binaryResource;
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

		// create if necessary
		if (_references == null) {
			_references = new HashSet<Reference>();
		}

		// return the result
		return _references;
	}
}
