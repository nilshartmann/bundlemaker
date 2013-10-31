/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.modules.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemChangedListenerList {

  /** - */
  private List<IModularizedSystemChangedListener> _changedListeners;

  /** - */
  private boolean                                 _isModelModifiedNotificationDisabled = false;

  /** - */
  private boolean                                 _handleModelModification             = true;

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystemChangedListenerList}.
   * </p>
   */
  public ModularizedSystemChangedListenerList() {

    //
    _changedListeners = new CopyOnWriteArrayList<IModularizedSystemChangedListener>();
  }

  /**
   * {@inheritDoc}
   */
  public void addModularizedSystemChangedListener(IModularizedSystemChangedListener listener) {

    //
    if (listener != null && !_changedListeners.contains(listener)) {
      _changedListeners.add(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeModularizedSystemChangedListener(IModularizedSystemChangedListener listener) {

    //
    if (_changedListeners.contains(listener)) {
      _changedListeners.remove(listener);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param itemChanged
   * @param module
   * @param changeAction
   */
  public void fireMovableUnitEvent(IModuleAwareMovableUnit movableUnit, IModule module, ChangeAction changeAction) {

    MovableUnitMovedEvent event = new MovableUnitMovedEvent(movableUnit, module, changeAction);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.movableUnitChanged(event, changeAction);
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireModuleChanged(IModule module, ChangeAction changeAction) {

    //
    ModuleMovedEvent event = new ModuleMovedEvent(module, changeAction);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.moduleChanged(event, changeAction);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   */
  public void fireModuleIdentifierChanged(IModule module) {

    //
    ModuleIdentifierChangedEvent event = new ModuleIdentifierChangedEvent(module);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.moduleIdentifierChanged(event);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param group
   * @param changeAction
   */
  public void fireGroupChanged(Group group, ChangeAction changeAction) {
    Assert.isNotNull(group);
    Assert.isNotNull(changeAction);

    //
    GroupChangedEvent event = new GroupChangedEvent(group, changeAction);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.groupChanged(event, changeAction);
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void fireModuleClassificationChanged(ModuleClassificationChangedEvent event) {
    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.moduleClassificationChanged(event);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void fireClassificationChanged(ClassificationChangedEvent event) {
    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.classificationChanged(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void disableModelModifiedNotification(boolean isDisabled) {

    //
    boolean fireImmediately = !isDisabled && _isModelModifiedNotificationDisabled;

    //
    _isModelModifiedNotificationDisabled = isDisabled;

    //
    for (IModularizedSystemChangedListener modularizedSystemChangedListener : _changedListeners) {
      modularizedSystemChangedListener.modelModifiedNotificationDisabled(isDisabled);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isModelModifiedNotificationDisabled() {
    return _isModelModifiedNotificationDisabled;
  }

  public boolean isHandleModelModification() {
    return _handleModelModification;
  }

  /**
   * <p>
   * </p>
   * 
   * @param handleModelModification
   */
  public void setHandleModelModification(boolean handleModelModification) {

    if (!_handleModelModification && handleModelModification) {
      _handleModelModification = handleModelModification;

      //
      for (IModularizedSystemChangedListener modularizedSystemChangedListener : _changedListeners) {
        modularizedSystemChangedListener.handleModelModification();
      }

    } else {
      _handleModelModification = handleModelModification;
    }
  }
}
