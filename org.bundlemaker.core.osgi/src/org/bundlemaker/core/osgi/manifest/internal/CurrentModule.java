package org.bundlemaker.core.osgi.manifest.internal;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CurrentModule {

  /** - */
  private IModularizedSystem     _modularizedSystem;

  /** - */
  private IResourceModule        _resourceModule;

  /** - */
  private ManifestContents       _manifestTemplate;

  /** - */
  private IModuleExporterContext _context;

  /**
   * <p>
   * Creates a new instance of type {@link CurrentModule}.
   * </p>
   * 
   * @param modularizedSystem
   * @param resourceModule
   * @param manifestTemplate
   */
  public CurrentModule(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      IModuleExporterContext context, ManifestContents manifestTemplate) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(context);
    Assert.isNotNull(manifestTemplate);

    _modularizedSystem = modularizedSystem;
    _resourceModule = resourceModule;
    _manifestTemplate = manifestTemplate;
    _context = context;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IResourceModule getResourceModule() {
    return _resourceModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ManifestContents getManifestTemplate() {
    return _manifestTemplate;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModuleExporterContext getContext() {
    return _context;
  }
}
