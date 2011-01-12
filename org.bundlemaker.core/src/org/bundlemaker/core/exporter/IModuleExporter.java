package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModuleExporter {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * 
	 * @return
	 */
	boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * 
	 * @throws Exception
	 */
	void export(IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) throws Exception;
}
