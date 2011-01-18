package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

public class Reference implements IReference {

	/** - */
	private FlyWeightString _fullyQualifiedName;

	/** - */
	private ReferenceType _referenceType;

	// /** - */
	// private boolean _indirectlyReferenced;
	//
	// /** - */
	// private boolean _directlyReferenced;

	/** - */
	private boolean _sourceCodeDependency;

	/** - */
	private boolean _byteCodeDependency;

	// /** - */
	// private boolean _isUses;

	/**
	 * <p>
	 * Creates a new instance of type {@link Reference}.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceType
	 * @param sourceCodeDependency
	 * @param byteCodeDependency
	 */
	public Reference(FlyWeightString fullyQualifiedName,
			ReferenceType referenceType, boolean sourceCodeDependency,
			boolean byteCodeDependency) {

		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(referenceType);

		_fullyQualifiedName = fullyQualifiedName;
		_referenceType = referenceType;
		_sourceCodeDependency = sourceCodeDependency;
		_byteCodeDependency = byteCodeDependency;
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
	public ReferenceType getReferenceType() {
		return _referenceType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBytecodeDependency() {
		return _byteCodeDependency;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSourcecodeDependency() {
		return _sourceCodeDependency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_byteCodeDependency ? 1231 : 1237);
		result = prime
				* result
				+ ((_fullyQualifiedName == null) ? 0 : _fullyQualifiedName
						.hashCode());
		result = prime * result
				+ ((_referenceType == null) ? 0 : _referenceType.hashCode());
		result = prime * result + (_sourceCodeDependency ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reference other = (Reference) obj;
		if (_byteCodeDependency != other._byteCodeDependency)
			return false;
		if (_fullyQualifiedName == null) {
			if (other._fullyQualifiedName != null)
				return false;
		} else if (!_fullyQualifiedName.equals(other._fullyQualifiedName))
			return false;
		if (_referenceType != other._referenceType)
			return false;
		if (_sourceCodeDependency != other._sourceCodeDependency)
			return false;
		return true;
	}
}
