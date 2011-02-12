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
}
