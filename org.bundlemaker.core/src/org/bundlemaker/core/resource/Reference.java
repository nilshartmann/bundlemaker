package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Reference implements IReference {

	/** - */
	private FlyWeightString _fullyQualifiedName;

	/** - */
	private ReferenceAttributes _referenceAttributes;

	/** non-persistent back-reference: the containing resource */
	private Resource _resource;

	/** non-persistent back-reference: the containing type */
	private Type _type;

	/**
	 * <p>
	 * Creates a new instance of type {@link Reference}.
	 * </p>
	 * 
	 * @param reference
	 */
	public Reference(Reference reference) {

		Assert.isNotNull(reference);

		//
		_fullyQualifiedName = reference._fullyQualifiedName;
		_referenceAttributes = reference._referenceAttributes;
	}

	/**
	 * <p>
	 * Creates a new instance of type {@link Reference}.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceAttributes
	 */
	public Reference(FlyWeightString fullyQualifiedName,
			ReferenceAttributes referenceAttributes) {

		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(referenceAttributes);

		_fullyQualifiedName = fullyQualifiedName;
		_referenceAttributes = referenceAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullyQualifiedName() {
		return _fullyQualifiedName.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isImplements() {
		return _referenceAttributes.isImplements();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isExtends() {
		return _referenceAttributes.isExtends();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReferenceType getReferenceType() {
		return _referenceAttributes.getReferenceType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCompileTimeReference() {
		return _referenceAttributes.isCompileTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRuntimeReference() {
		return _referenceAttributes.isRuntimeTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IType getType() {
		return _type;
	}

	/**
	 * {@inheritDoc}
	 */
	public IResource getResource() {
		return _resource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAssociatedResource() {
		return _resource != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAssociatedType() {
		return _type != null;
	}

	/**
	 * <p>
	 * Set the back-reference.
	 * </p>
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource) {
		_resource = resource;
	}

	/**
	 * <p>
	 * Set the back-reference.
	 * </p>
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		_type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 31 + _fullyQualifiedName.hashCode();
		return 31 * result + _referenceAttributes.hashCode();
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
		Reference other = (Reference) obj;
		if (_fullyQualifiedName == null) {
			if (other._fullyQualifiedName != null)
				return false;
		} else if (!_fullyQualifiedName.equals(other._fullyQualifiedName))
			return false;
		if (_referenceAttributes == null) {
			if (other._referenceAttributes != null)
				return false;
		} else if (!_referenceAttributes.equals(other._referenceAttributes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reference [_fullyQualifiedName=" + _fullyQualifiedName
				+ ", _referenceAttributes=" + _referenceAttributes + "]";
	}
}
