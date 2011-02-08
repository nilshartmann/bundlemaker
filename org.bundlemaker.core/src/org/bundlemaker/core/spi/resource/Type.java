package org.bundlemaker.core.spi.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.resource.IModifiableType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.TypeEnum;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Type implements IType, IModifiableType {

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
	 * @param fullyQualifiedName
	 * @param typeEnum
	 */
	public Type(String fullyQualifiedName, TypeEnum typeEnum) {

		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(typeEnum);

		//
		_fullyQualifiedName = new FlyWeightString(fullyQualifiedName);

		// the type of the type
		_typeEnum = typeEnum;
	}

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
	public String getPackageName() {

		//
		String typeName = _fullyQualifiedName.toString();

		// get index of the last '.'
		int lastIndex = typeName.lastIndexOf('.');

		//
		return lastIndex == -1 ? "" : typeName.substring(0, lastIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {

		// get the fully qualified name
		String fullyQualifiedName = _fullyQualifiedName.toString();

		// get the index
		int index = fullyQualifiedName.lastIndexOf('.');

		// return the result
		return index != -1 ? fullyQualifiedName.substring(index + 1)
				: fullyQualifiedName;
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
	public TypeEnum getType() {
		return _typeEnum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getSourceResource() {
		return _sourceResource.getResourceStandin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getBinaryResource() {
		return _binaryResource.getResourceStandin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasSourceResource() {
		return _sourceResource != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasBinaryResource() {
		return _binaryResource != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bundlemaker.core.resource.IModifiableType#recordReference(java.lang
	 * .String, org.bundlemaker.core.resource.ReferenceType, boolean, boolean,
	 * boolean, boolean, boolean)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bundlemaker.core.resource.IModifiableType#getModifiableReferences()
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
