package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystemExporter {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param context
	 * @throws Exception
	 */
	void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception;
}
