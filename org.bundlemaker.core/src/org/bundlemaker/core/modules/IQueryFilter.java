package org.bundlemaker.core.modules;

public interface IQueryFilter {

	/** QUERY_FILTER_FALSE */
	public static IQueryFilter TRUE_QUERY_FILTER = new IQueryFilter() {

		@Override
		public boolean matches(String content) {
			return true;
		}
	};

	/**
	 * <p>
	 * </p>
	 * 
	 * @param content
	 * @return
	 */
	boolean matches(String content);
}
