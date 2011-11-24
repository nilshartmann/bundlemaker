package org.bundlemaker.core.transformations.selectors;

import java.util.List;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

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
   * Returns a list of all {@link IResourceModule IResourceModules} of the specified {@link IModularizedSystem} that
   * matches this selector.
   * </p>
   * 
   * @param modularizedSystem
   *          the modularized system.
   * @return a list of all {@link IMovableUnit IMovableUnits} of the specified {@link IResourceModule} that matches this
   *         selector.
   */
  public List<IResourceModule> getMatchingResourceModules(IModularizedSystem modularizedSystem);

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
