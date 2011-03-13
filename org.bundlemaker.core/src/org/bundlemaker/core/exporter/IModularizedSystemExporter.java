package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Common interface for all exporters that export an entire {@link IModularizedSystem}.
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
   * Exports the specified {@link IModularizedSystem}.
   * </p>
   * 
   * @param modularizedSystem
   *          the system to export.
   * @param context
   *          the context
   * @throws Exception
   */
  void export(IModularizedSystem modularizedSystem, IModuleExporterContext context) throws Exception;
}
