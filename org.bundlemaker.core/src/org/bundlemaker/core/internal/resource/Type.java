package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
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

	/** transient: the source resource */
	private transient Resource _sourceResource;

	/** transient: the binary resource */
	private transient Resource _binaryResource;

	/** transient: the reference container */
	private transient ReferenceContainer _referenceContainer;

	/** non-persistent: the type module */
	private IModule _typeModule;
	
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
	 * Creates a new instance of type {@link Type}.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public Type(String fullyQualifiedName) {

		Assert.isNotNull(fullyQualifiedName);

		//
		_fullyQualifiedName = new FlyWeightString(fullyQualifiedName);
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
	public IReference getReference(String fullyQualifiedName) {

		//
		Assert.isNotNull(fullyQualifiedName);

		//
		for (Reference reference : _references) {

			//
			if (fullyQualifiedName.equals(reference.getFullyQualifiedName())) {
				return reference;
			}
		}

		//
		return null;
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
		return _sourceResource != null ? _sourceResource.getResourceStandin()
				: null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IResource getBinaryResource() {
		return _binaryResource != null ? _binaryResource.getResourceStandin()
				: null;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IModule getModule() {

		if (_typeModule != null) {
			return _typeModule;
		} else if (_binaryResource != null) {
			return _binaryResource.getResourceModule();
		} else if (_sourceResource != null) {
			return _sourceResource.getResourceModule();
		}

		throw new RuntimeException("Type has no module " + this.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordReference(String fullyQualifiedName,
			ReferenceAttributes referenceAttributes) {

		_referenceContainer.recordReference(fullyQualifiedName,
				referenceAttributes);
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
	 * @param binaryResource
	 */
	public void setTypeModule(TypeModule typeModule) {
		_typeModule = typeModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeEnum
	 */
	public void setTypeEnum(TypeEnum typeEnum) {
		_typeEnum = typeEnum;
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

	@Override
	public String toString() {
		return "Type [_fullyQualifiedName=" + _fullyQualifiedName
				+ ", _typeEnum=" + _typeEnum + "]";
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
