package org.bundlemaker.core.internal.api.resource;

import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModule extends IModule {

  /**
   * <p>
   * </p>
   * 
   * @param classification
   */
  void setClassification(IPath classification);

  void setModuleIdentifier(String name, String version);

  void setModuleIdentifier(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void addMovableUnit(IMovableUnit movableUnit);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void removeMovableUnit(IMovableUnit movableUnit);
}
