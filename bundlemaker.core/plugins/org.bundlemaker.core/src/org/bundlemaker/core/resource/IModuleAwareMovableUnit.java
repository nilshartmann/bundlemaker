package org.bundlemaker.core.resource;

import org.bundlemaker.core.project.IMovableUnit;

/**
 * <p>
 * A movable unit defines a list of types together with a list of (binary and source) resources that must be moved as
 * <i>one</i> unit. Normally this is necessary because they all are associated with the same source file.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModuleAwareMovableUnit extends IMovableUnit {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IModule getAssoicatedModule(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasModule(IModularizedSystem modularizedSystem);
}
