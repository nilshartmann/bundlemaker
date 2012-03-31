package org.bundlemaker.core.ui.selection.internal;

import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.Assert;

public abstract class SelectionListenerList<LISTENER, EVENT> {

  /**
   * A set containing the registered listeners
   */
  private final CopyOnWriteArraySet<SelectionListenerWrapper<LISTENER>> _listenerList = new CopyOnWriteArraySet<SelectionListenerWrapper<LISTENER>>();

  /**
   * Add the specified listener to the list of listeners.
   * 
   * @param selectionId
   *          the provider or null, if this listener should react on changes from all providers
   * @param listener
   */
  public void addSelectionListener(String selectionId, LISTENER listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    // Create wrapper
    SelectionListenerWrapper<LISTENER> wrapper = new SelectionListenerWrapper<LISTENER>(selectionId, listener);

    // add to listener list
    _listenerList.add(wrapper);
  }

  public void removeSelectionListener(LISTENER listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    for (SelectionListenerWrapper<LISTENER> wrapper : _listenerList) {
      if (listener.equals(wrapper.getListener())) {
        // Remove from listener list
        _listenerList.remove(wrapper);
        break;
      }
    }
  }

  public void fireSelectionChanged(String selectionId, EVENT event) {
    for (SelectionListenerWrapper<LISTENER> wrapper : _listenerList) {
      // check if listener is registered for the provider
      if (wrapper.matches(selectionId)) {

        // invoke!
        invokeListener(wrapper.getListener(), event);
      }
    }
  }

  /**
   * Invoke the specified listener with the given event
   * 
   * <p>
   * Subclasses must implement this method to call the appropriate method on the LISTENER class
   * 
   * @param listener
   *          the listener to be invoked
   * @param event
   *          the event that should be passed to the listener method
   */
  protected abstract void invokeListener(LISTENER listener, EVENT event);

  protected static class SelectionListenerWrapper<LISTENER> {

    private final String   _selectionId;

    private final LISTENER _listener;

    protected SelectionListenerWrapper(String selectionId, LISTENER listener) {
      super();
      _selectionId = selectionId;
      _listener = listener;
    }

    public boolean matches(String selectionId) {
      return (_selectionId == null // matches all
      || _selectionId.equals(selectionId));
    }

    public LISTENER getListener() {
      return _listener;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_listener == null) ? 0 : _listener.hashCode());
      result = prime * result + ((_selectionId == null) ? 0 : _selectionId.hashCode());
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
      if (_selectionId == null) {
        if (other._selectionId != null)
          return false;
      } else if (!_selectionId.equals(other._selectionId))
        return false;
      return true;
    }
  }
}
