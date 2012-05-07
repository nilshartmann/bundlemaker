package org.bundlemaker.core.transformations.selectors;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByNameResourceModuleSelector implements IResourceModuleSelector {

  /** - */
  private String _moduleName;

  /**
   * <p>
   * Creates a new instance of type {@link ByNameResourceModuleSelector}.
   * </p>
   * 
   * @param moduleName
   */
  public ByNameResourceModuleSelector(String moduleName) {
    Assert.isNotNull(moduleName);

    _moduleName = moduleName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IResourceModule resourceModule) {

    //
    return _moduleName.equalsIgnoreCase(resourceModule.getModuleIdentifier().getName());
  }
}
