package org.bundlemaker.core.exporter;

import java.util.Set;

import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IResource;

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
  Set<IResource> getAdditionalResources(IModule currentModule,
      IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext);
}
