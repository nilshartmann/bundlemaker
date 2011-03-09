package org.bundlemaker.core.modules.query;

import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeQueryFilters {

	/** TRUE_QUERY_FILTER */
	public static IQueryFilter<IType> TRUE_QUERY_FILTER = new IQueryFilter<IType>() {

		@Override
		public boolean matches(IType type) {
			return true;
		}
	};

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public static IQueryFilter<IType> newPatternBasedTypeFilter(
			final String[] includes, final String[] excludes) {

		//
		IQueryFilter<IType> result = new IQueryFilter<IType>() {

			public boolean matches(IType type) {

				//
				boolean included = false;

				//
				for (String include : includes) {
					if (type.getFullyQualifiedName().matches(include)) {
						included = true;
					}
				}

				//
				if (!included) {
					return false;
				}

				//
				for (String exclude : excludes) {
					if (type.getFullyQualifiedName().matches(exclude)) {
						return false;
					}
				}

				//
				return true;
			}
		};

		//
		return result;
	}
}
