package org.bundlemaker.core.osgi.exporter.helper;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * @author wuetherich
 * 
 */
public class AbstractExporterHelper {

  /** the modularized system */
  private IModularizedSystem     _modularizedSystem;

  /** the current module */
  private IResourceModule        _module;

  /** the current context */
  private IModuleExporterContext _context;

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   */
  public AbstractExporterHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(module);
    Assert.isNotNull(context);

    // set attributes
    _modularizedSystem = modularizedSystem;
    _context = context;
    _module = module;
  }

  /**
   * <p>
   * Returns the {@link IModularizedSystem}.
   * </p>
   * 
   * @return the {@link IModularizedSystem}
   */
  protected IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * Returns the {@link IResourceModule}.
   * </p>
   * 
   * @return the {@link IResourceModule}
   */
  protected IResourceModule getModule() {
    return _module;
  }

  /**
   * <p>
   * Returns the current {@link IModuleExporterContext}.
   * </p>
   * 
   * @return the current {@link IModuleExporterContext}
   */
  protected IModuleExporterContext getContext() {
    return _context;
  }
}
