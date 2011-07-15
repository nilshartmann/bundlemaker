package org.bundlemaker.core.modules.modifiable;

import org.bundlemaker.core.modules.ITypeContainer;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IModifiableTypeContainer extends ITypeContainer {

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  void add(IType type);

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  void remove(IType type);
}
