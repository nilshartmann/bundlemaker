package org.bundlemaker.core.transformations.selectors;

import java.util.List;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceModuleSelector {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  public List<IResourceModule> getMatchingResourceModules(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  public boolean matches(IResourceModule modularizedSystem);
}
