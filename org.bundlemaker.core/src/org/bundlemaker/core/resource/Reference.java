package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

public class Reference implements IReference {

	/** - */
	private String _fullyQualifiedName;

	/** - */
	private ReferenceType _referenceType;

	/** - */
	private boolean _indirectlyReferenced;

	/** - */
	private boolean _directlyReferenced;

	/** - */
	private boolean _sourceCodeDependency;

	/** - */
	private boolean _byteCodeDependency;

	/** - */
	private boolean _isUses;

	/**
	 * <p>
	 * Creates a new instance of type {@link Reference}.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceType
	 * @param cache
	 */
	public Reference(String fullyQualifiedName, ReferenceType referenceType) {
		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(referenceType);

		_referenceType = referenceType;
		_fullyQualifiedName = fullyQualifiedName;
	}

	@Override
	public String getFullyQualifiedName() {
		return _fullyQualifiedName;
	}

	@Override
	public ReferenceType getReferenceType() {
		return _referenceType;
	}

	@Override
	public boolean isBytecodeDependency() {
		return _byteCodeDependency;
	}

	@Override
	public boolean isSourcecodeDependency() {
		return _sourceCodeDependency;
	}

	@Override
	public boolean isIndirectlyReferenced() {
		return _indirectlyReferenced;
	}

	@Override
	public boolean isDirectlyReferenced() {
		return _directlyReferenced;
	}

	@Override
	public boolean isUses() {
		return _isUses;
	}

	public void setIndirectlyReferenced(boolean indirectlyReferenced) {
		_indirectlyReferenced = indirectlyReferenced;
	}

	public void setDirectlyReferenced(boolean directlyReferenced) {
		_directlyReferenced = directlyReferenced;
	}

	public void setSourceCodeDependency(boolean sourceCodeDependency) {
		_sourceCodeDependency = sourceCodeDependency;
	}

	public void setByteCodeDependency(boolean byteCodeDependency) {
		_byteCodeDependency = byteCodeDependency;
	}

	public void setUses(boolean isUses) {
		_isUses = isUses;
	}
}
