package org.bundlemaker.core.modules;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystemChangedListener {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void movableUnitAdded(MovableUnitMovedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void movableUnitRemoved(MovableUnitMovedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void moduleAdded(ModuleMovedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void moduleRemoved(ModuleMovedEvent event);

  /**
   * <p>
   * </p>
   */
  void moduleClassificationChanged(ModuleClassificationChangedEvent event);
}
