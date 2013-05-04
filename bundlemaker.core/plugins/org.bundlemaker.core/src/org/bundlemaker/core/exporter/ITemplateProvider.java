package org.bundlemaker.core.exporter;

import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IReadableResource;

/**
 * <p>
 * </p>
 * 
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITemplateProvider<T> {

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @param modularizedSystem
   * @param context
   * @return
   */
  T getTemplate(IModule module, IModularizedSystem modularizedSystem,
      IModuleExporterContext context);

  /**
   * <p>
   * </p>
   * 
   * @param currentModule
   * @param currentModularizedSystem
   * @param currentContext
   * @return
   */
  Set<IReadableResource> getAdditionalResources(IModule currentModule,
      IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext);
}
