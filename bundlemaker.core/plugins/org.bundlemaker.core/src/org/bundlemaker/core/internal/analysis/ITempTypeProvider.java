package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.core._type.IType;

@Deprecated
public interface ITempTypeProvider {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Deprecated
  boolean hasAssociatedTypes();

  /**
   * <p>
   * Returns the list of types that are associated with this movable unit.
   * </p>
   * 
   * @return the list of types that are associated with this movable unit.
   */
  @Deprecated
  List<IType> getAssociatedTypes();
}
