package org.bundlemaker.core.transformations.selectors;

import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * Defines the common interface for resource module selectors.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceModuleSelector {

  /**
   * <p>
   * Returns <code>true</code>, if the given {@link IResourceModule} matches this selector.
   * </p>
   * 
   * @param resourceModule
   *          <code>true</code>, if the given {@link IResourceModule} matches this selector.
   * @return <code>true</code>, if the given {@link IResourceModule} matches this selector.
   */
  public boolean matches(IResourceModule resourceModule);
}
