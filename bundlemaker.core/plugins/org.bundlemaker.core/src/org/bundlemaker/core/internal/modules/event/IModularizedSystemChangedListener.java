package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.internal.modules.ChangeAction;

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
  void classificationChanged(ClassificationChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @param changeAction
   */
  void movableUnitChanged(MovableUnitMovedEvent event, ChangeAction changeAction);

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @param changeAction
   */
  void groupChanged(GroupChangedEvent event, ChangeAction changeAction);

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @param changeAction
   */
  void moduleChanged(ModuleMovedEvent event, ChangeAction changeAction);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void moduleClassificationChanged(ModuleClassificationChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void moduleIdentifierChanged(ModuleIdentifierChangedEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param isDisabled
   */
  // TODO: UNIFY WITH 'modelModifiedNotificationDisabled'
  void modelModifiedNotificationDisabled(boolean isDisabled);

  // TODO: UNIFY WITH 'modelModifiedNotificationDisabled'
  void handleModelModification();
}
