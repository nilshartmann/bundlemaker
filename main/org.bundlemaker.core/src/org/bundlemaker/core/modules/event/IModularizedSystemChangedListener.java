package org.bundlemaker.core.modules.event;

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

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void groupAdded(GroupChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void groupRemoved(GroupChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void classificationChanged(ClassificationChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param isDisabled
   */
  void modelModifiedNotificationDisabled(boolean isDisabled);
}
