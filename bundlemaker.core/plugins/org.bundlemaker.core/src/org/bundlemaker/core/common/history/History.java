/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.common.history;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * A "add-only" history of things, that can be browsed forward and backward
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class History<T> {

  private final List<IHistoryChangedListener<T>> _listeners     = new LinkedList<IHistoryChangedListener<T>>();

  private final List<T>                          _objects       = new LinkedList<T>();

  private final Stack<Integer>                   _backStack     = new Stack<Integer>();

  private final Stack<Integer>                   _forwardStack  = new Stack<Integer>();

  /**
   * The current object index. The index is not contained in back or forward stack
   */
  private int                                    _currentObject = -1;

  public void add(T t) {
    if (t == null) {
      return;
    }

    if (_currentObject != -1) {
      _backStack.push(_currentObject);
    }

    _objects.add(t);

    _currentObject = _objects.size() - 1;

    while (!_forwardStack.isEmpty()) {
      _backStack.add(0, _forwardStack.pop());
    }

    fireHistoryChangedListener();
  }

  public boolean canGoBack() {
    return _backStack.size() > 0;
  }

  public void goBack() {
    if (!canGoBack()) {
      throw new IllegalStateException("Cannot move Back: already at first position in history");
    }

    if (_currentObject != -1) {
      _forwardStack.add(_currentObject);
    }

    Integer newItem = _backStack.pop();

    _currentObject = newItem;

    fireHistoryChangedListener();
  }

  public boolean canGoForward() {
    return !_forwardStack.isEmpty();
  }

  public void dumpHistory() {

    System.out.println("CURRENT OBJECT: " + _currentObject);
    System.out.println("BACK STACK:");
    for (int i = 0; i < _backStack.size(); i++) {
      System.out.printf("%d (%s)%n", _backStack.get(i), _objects.get(_backStack.get(i)));
    }

    System.out.println("FORWARD STACK:");
    for (int i = 0; i < _forwardStack.size(); i++) {
      System.out.printf("%d (%s)%n", _forwardStack.get(i), _objects.get(_forwardStack.get(i)));
    }

    // for (int i = 0; i < _history.size(); i++) {
    // System.out.printf("[%d %s] -> %d (%s)%n", i, (i == _currentItem ? "*" : ""), _history.get(i),
    // _objects.get(_history.get(i)));
    // }

  }

  public void goForward() {
    if (!canGoForward()) {
      throw new IllegalStateException("Cannot move forward in history: already at last position");
    }

    if (_currentObject != -1) {
      _backStack.add(_currentObject);
    }

    Integer newItem = _forwardStack.pop();
    System.out.println(" -> New Item after pop: " + newItem);
    _currentObject = newItem;

    fireHistoryChangedListener();
  }

  /**
   * @return
   */
  public T getCurrent() {
    T item = _objects.get(_currentObject);

    return item;

  }

  protected void fireHistoryChangedListener() {
    for (IHistoryChangedListener<T> listener : _listeners) {
      listener.historyChanged(this);
    }
  }

  public void addHistoryChangedListener(IHistoryChangedListener<T> listener) {
    if (listener != null && !_listeners.contains(listener)) {
      _listeners.add(listener);
    }
  }

  public void removeHistoryChangedListener(IHistoryChangedListener<T> listener) {
    if (listener != null) {
      _listeners.remove(listener);
    }
  }

}
