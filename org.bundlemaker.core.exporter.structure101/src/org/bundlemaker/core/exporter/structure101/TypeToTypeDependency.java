package org.bundlemaker.core.exporter.structure101;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeToTypeDependency {

	/** - */
	private String _from;

	/** - */
	private String _to;

	/** - */
	private boolean _isImplements;

	/** - */
	private boolean _isExtents;

	/**
	 * <p>
	 * Creates a new instance of type {@link TypeToTypeDependency}.
	 * </p>
	 * 
	 * @param from
	 * @param to
	 * @param isImplements
	 * @param isExtents
	 */
	public TypeToTypeDependency(String from, String to, boolean isImplements,
			boolean isExtents) {

		Assert.isNotNull(from);
		Assert.isNotNull(to);

		_from = from;
		_to = to;
		_isImplements = isImplements;
		_isExtents = isExtents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public String getFrom() {
		return _from;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public String getTo() {
		return _to;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isImplements() {
		return _isImplements;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isExtents() {
		return _isExtents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_from == null) ? 0 : _from.hashCode());
		result = prime * result + (_isExtents ? 1231 : 1237);
		result = prime * result + (_isImplements ? 1231 : 1237);
		result = prime * result + ((_to == null) ? 0 : _to.hashCode());
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
		TypeToTypeDependency other = (TypeToTypeDependency) obj;
		if (_from == null) {
			if (other._from != null)
				return false;
		} else if (!_from.equals(other._from))
			return false;
		if (_isExtents != other._isExtents)
			return false;
		if (_isImplements != other._isImplements)
			return false;
		if (_to == null) {
			if (other._to != null)
				return false;
		} else if (!_to.equals(other._to))
			return false;
		return true;
	}
}
