/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.ui.internal.selection;

import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.Assert;

/**
 * Abstract base class for selection services
 * 
 * @author Nils Hartmann
 * 
 * @param <LISTENER>
 *          The SelectionListener-Type
 * @param <EVENT>
 *          The SelectionEvent-Type
 */
public abstract class AbstractSelectionService<LISTENER, EVENT> {

  /**
   * A set containing the registered listeners
   */
  private final CopyOnWriteArraySet<SelectionListenerWrapper<LISTENER>> _listenerList = new CopyOnWriteArraySet<SelectionListenerWrapper<LISTENER>>();

  protected void addSelectionListener(String providerId, LISTENER listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    // Create wrapper
    SelectionListenerWrapper<LISTENER> wrapper = new SelectionListenerWrapper<LISTENER>(providerId, listener);

    // add to listener list
    _listenerList.add(wrapper);
  }

  protected void removeSelectionListener(LISTENER listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    for (SelectionListenerWrapper<LISTENER> wrapper : _listenerList) {
      if (listener.equals(wrapper.getListener())) {
        // Remove from listener list
        _listenerList.remove(wrapper);
        break;
      }
    }
  }

  protected void fireSelectionChanged(String providerId, EVENT event) {
    for (SelectionListenerWrapper<LISTENER> wrapper : _listenerList) {
      // check if listener is registered for the provider
      if (wrapper.matches(providerId)) {

        // invoke!
        invokeListener(wrapper.getListener(), event);
      }

    }
  }

  protected abstract void invokeListener(LISTENER listener, EVENT event);

  private static class SelectionListenerWrapper<LISTENER> {
    private final String   _providerId;

    private final LISTENER _listener;

    private SelectionListenerWrapper(String providerId, LISTENER listener) {
      super();
      _providerId = providerId;
      _listener = listener;
    }

    public boolean matches(String providerId) {
      return (_providerId == null // matches all
      || _providerId.equals(providerId));
    }

    public LISTENER getListener() {
      return _listener;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_listener == null) ? 0 : _listener.hashCode());
      result = prime * result + ((_providerId == null) ? 0 : _providerId.hashCode());
      return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      SelectionListenerWrapper other = (SelectionListenerWrapper) obj;
      if (_listener == null) {
        if (other._listener != null)
          return false;
      } else if (!_listener.equals(other._listener))
        return false;
      if (_providerId == null) {
        if (other._providerId != null)
          return false;
      } else if (!_providerId.equals(other._providerId))
        return false;
      return true;
    }
  }

}
