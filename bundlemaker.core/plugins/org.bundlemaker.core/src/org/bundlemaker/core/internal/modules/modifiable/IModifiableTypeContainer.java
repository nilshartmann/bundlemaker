package org.bundlemaker.core.internal.modules.modifiable;

import org.bundlemaker.core.modules.IMovableUnit;
import org.bundlemaker.core.modules.ITypeContainer;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface IModifiableTypeContainer extends ITypeContainer {

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
