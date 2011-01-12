package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

public class Reference implements IReference {

	/** - */
	private int _fullyQualifiedNameIndex;

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

	/** - */
	private StringCache _stringCache;

	public Reference(String fullyQualifiedName, ReferenceType referenceType,
			StringCache cache) {
		Assert.isNotNull(fullyQualifiedName);
		Assert.isNotNull(referenceType);
		Assert.isNotNull(cache);

		_stringCache = cache;

		_fullyQualifiedNameIndex = cache.storeString(fullyQualifiedName);
		_referenceType = referenceType;
	}

	@Override
	public String getFullyQualifiedName() {
		return _stringCache.getString(_fullyQualifiedNameIndex);
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
