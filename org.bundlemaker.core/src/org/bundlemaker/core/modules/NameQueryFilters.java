package org.bundlemaker.core.modules;

public class NameQueryFilters {

	/** TRUE_QUERY_FILTER */
	public static IQueryFilter<String> TRUE_QUERY_FILTER = new IQueryFilter<String>() {

		@Override
		public boolean matches(String content) {
			return true;
		}
	};
}
