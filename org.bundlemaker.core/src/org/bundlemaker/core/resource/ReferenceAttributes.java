package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceAttributes {

	/** - */
	private ReferenceType _referenceType;

	// /** - */
	// private boolean _directlyReferenced;
	//
	// /** - */
	// private boolean _indirectlyReferenced;

	// /** - */
	// private boolean _uses;

	/** - */
	private boolean _sourceCodeDependency;

	/** - */
	private boolean _byteCodeDependency;

	/** - */
	private boolean _extends;

	/** - */
	private boolean _implements;

	/**
	 * <p>
	 * Creates a new instance of type {@link ReferenceAttributes}.
	 * </p>
	 * 
	 * @param referenceType
	 * @param sourceCodeDependency
	 * @param byteCodeDependency
	 * @param isExtends
	 * @param isImplements
	 */
	public ReferenceAttributes(ReferenceType referenceType,
			boolean sourceCodeDependency, boolean byteCodeDependency,
			boolean isExtends, boolean isImplements) {

		Assert.isNotNull(referenceType);

		_referenceType = referenceType;
		_sourceCodeDependency = sourceCodeDependency;
		_byteCodeDependency = byteCodeDependency;
		_extends = isExtends;
		_implements = isImplements;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public ReferenceType getReferenceType() {
		return _referenceType;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isSourceCodeDependency() {
		return _sourceCodeDependency;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isByteCodeDependency() {
		return _byteCodeDependency;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isExtends() {
		return _extends;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isImplements() {
		return _implements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_byteCodeDependency ? 1231 : 1237);
		result = prime * result + (_extends ? 1231 : 1237);
		result = prime * result + (_implements ? 1231 : 1237);
		result = prime * result
				+ ((_referenceType == null) ? 0 : _referenceType.hashCode());
		result = prime * result + (_sourceCodeDependency ? 1231 : 1237);
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
		ReferenceAttributes other = (ReferenceAttributes) obj;
		if (_byteCodeDependency != other._byteCodeDependency)
			return false;
		if (_extends != other._extends)
			return false;
		if (_implements != other._implements)
			return false;
		if (_referenceType != other._referenceType)
			return false;
		if (_sourceCodeDependency != other._sourceCodeDependency)
			return false;
		return true;
	}
}
