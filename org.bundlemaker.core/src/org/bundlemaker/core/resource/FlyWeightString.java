package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FlyWeightString {

	/** - */
	private String _content;

	/**
	 * <p>
	 * Creates a new instance of type {@link FlyWeightString}.
	 * </p>
	 * 
	 * @param content
	 */
	public FlyWeightString(String content) {
		Assert.isNotNull(content);

		_content = content;
	}

	@Override
	public String toString() {
		return _content;
	}

	@Override
	public int hashCode() {
		return _content.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj.getClass().equals(FlyWeightString.class) || obj.getClass()
				.equals(String.class)))
			return false;
		return _content.equals(obj.toString());
	}
}
