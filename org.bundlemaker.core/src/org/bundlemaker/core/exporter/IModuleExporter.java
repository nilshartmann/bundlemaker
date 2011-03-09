package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Defines the interface for module exporter. A module exporter can export a single {@link IResourceModule} to an
 * external format.
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
   * Returns <code>true</code> if this {@link IModuleExporter} can export the specified {@link IResourceModule}.
   * </p>
   * 
   * @param modularizedSystem
   *          the {@link IModularizedSystem}
   * @param module
   *          the {@link IResourceModule}
   * @param context
   *          the {@link IModuleExporterContext}
   * 
   * @return <code>true</code> if this {@link IModuleExporter} can export the specified {@link IResourceModule}.
   */
  boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context);

  /**
   * <p>
   * Exports the specified {@link IResourceModule} to an external format.
   * </p>
   * 
   * @param modularizedSystem
   *          the {@link IModularizedSystem}
   * @param module
   *          the {@link IResourceModule}
   * @param context
   *          the {@link IModuleExporterContext}
   * 
   * @throws CoreException
   */
  void export(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context)
      throws CoreException;
}
