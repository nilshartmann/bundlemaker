package org.bundlemaker.core.internal.resource;

import org.bundlemaker.core.resource.ReferenceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class ReferenceKey {

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
	public ReferenceKey(String fullyQualifiedName, ReferenceType referenceType) {
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