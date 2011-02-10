package org.bundlemaker.core.modules;

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
