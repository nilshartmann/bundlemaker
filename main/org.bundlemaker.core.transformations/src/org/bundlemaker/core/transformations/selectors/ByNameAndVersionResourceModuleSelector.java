package org.bundlemaker.core.transformations.selectors;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByNameAndVersionResourceModuleSelector implements IResourceModuleSelector {

  /** the module identifier */
  private ModuleIdentifier _moduleIdentifier;

  /**
   * <p>
   * Creates a new instance of type {@link ByNameAndVersionResourceModuleSelector}.
   * </p>
   * 
   * @param moduleName
   */
  public ByNameAndVersionResourceModuleSelector(String moduleName, String version) {
    this(new ModuleIdentifier(moduleName, version));
  }

  /**
   * <p>
   * Creates a new instance of type {@link ByNameAndVersionResourceModuleSelector}.
   * </p>
   * 
   * @param moduleIdentifier
   */
  public ByNameAndVersionResourceModuleSelector(ModuleIdentifier moduleIdentifier) {
    Assert.isNotNull(moduleIdentifier);

    _moduleIdentifier = moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IResourceModule resourceModule) {
    return _moduleIdentifier.equals(resourceModule.getModuleIdentifier());
  }
}
