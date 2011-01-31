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
	private boolean _extends;

	/** - */
	private boolean _implements;

	/** - */
	private boolean _classAnnotation;

	/** - */
	private boolean _isCompileTime;

	/** - */
	private boolean _isRuntimeTime;

	/**
	 * <p>
	 * Creates a new instance of type {@link ReferenceAttributes}.
	 * </p>
	 * 
	 * @param referenceType
	 * @param isExtends
	 * @param isImplements
	 * @param isClassAnnotation
	 * @param isCompileTime
	 * @param isRuntimeTime
	 */
	public ReferenceAttributes(ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompileTime, boolean isRuntimeTime) {

		Assert.isNotNull(referenceType);

		_referenceType = referenceType;
		_extends = isExtends;
		_implements = isImplements;
		_classAnnotation = isClassAnnotation;
		_isCompileTime = isCompileTime;
		_isRuntimeTime = isRuntimeTime;
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
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isClassAnnotation() {
		return _classAnnotation;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isCompileTime() {
		return _isCompileTime;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isRuntimeTime() {
		return _isRuntimeTime;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_classAnnotation ? 1231 : 1237);
		result = prime * result + (_extends ? 1231 : 1237);
		result = prime * result + (_implements ? 1231 : 1237);
		result = prime * result + (_isCompileTime ? 1231 : 1237);
		result = prime * result + (_isRuntimeTime ? 1231 : 1237);
		result = prime * result
				+ ((_referenceType == null) ? 0 : _referenceType.hashCode());
		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
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
		if (_classAnnotation != other._classAnnotation)
			return false;
		if (_extends != other._extends)
			return false;
		if (_implements != other._implements)
			return false;
		if (_isCompileTime != other._isCompileTime)
			return false;
		if (_isRuntimeTime != other._isRuntimeTime)
			return false;
		if (_referenceType != other._referenceType)
			return false;
		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "ReferenceAttributes [_referenceType=" + _referenceType
				+ ", _extends=" + _extends + ", _implements=" + _implements
				+ ", _classAnnotation=" + _classAnnotation
				+ ", _isCompileTime=" + _isCompileTime + ", _isRuntimeTime="
				+ _isRuntimeTime + "]";
	}
}
