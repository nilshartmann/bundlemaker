package org.bundlemaker.core.modules.query;

/**
 * @param <T>
 */
public interface IQueryFilter<T> {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param content
	 * @return
	 */
	public abstract boolean matches(T content);
}
