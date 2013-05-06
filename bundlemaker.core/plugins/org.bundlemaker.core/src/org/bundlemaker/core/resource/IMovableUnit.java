package org.bundlemaker.core.resource;

import java.util.List;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * A movable unit defines a list of types together with a list of (binary and source) resources that must be moved as
 * <i>one</i> unit. Normally this is necessary because they all are associated with the same source file.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IMovableUnit {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IModule getAssoicatedModule();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasModule();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasAssociatedBinaryResources();

  /**
   * <p>
   * Returns the list of binary resources that are associated with this movable unit.
   * </p>
   * 
   * @return the list of binary resources that are associated with this movable unit.
   */
  List<? extends IModuleResource> getAssociatedBinaryResources();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasAssociatedSourceResource();

  /**
   * <p>
   * Returns the source resource that is associated with this movable type.
   * </p>
   * 
   * @return the source resource that is associated with this movable type.
   */
  IModuleResource getAssociatedSourceResource();

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
