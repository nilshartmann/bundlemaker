package org.bundlemaker.core.modules.query;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceModuleQueryFilters {
	
	/** TRUE_QUERY_FILTER */
	public static IQueryFilter<IResourceModule> TRUE_QUERY_FILTER = new IQueryFilter<IResourceModule>() {

		@Override
		public boolean matches(IResourceModule type) {
			return true;
		}
	};
}
