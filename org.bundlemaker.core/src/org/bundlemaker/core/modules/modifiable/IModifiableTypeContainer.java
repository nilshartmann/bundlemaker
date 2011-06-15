package org.bundlemaker.core.modules.modifiable;

import java.util.Map;

import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public interface IModifiableTypeContainer {
  
  /**
   * <p>Returns a Map containing all types of this container.
   * </p>
   * <p>The <b>key</b> of the map is the fully qualified name of the associated IType
   *
   * @return
   */
  public Map<String, IType> getModifiableContainedTypesMap();

}
