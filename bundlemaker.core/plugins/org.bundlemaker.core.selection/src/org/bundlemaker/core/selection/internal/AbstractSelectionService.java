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
package org.bundlemaker.core.selection.internal;

import java.util.concurrent.ConcurrentHashMap;

import org.bundlemaker.core.selection.IProviderBasedSelection;
import org.eclipse.core.runtime.Assert;

/**
 * Abstract base class for selection services
 * 
 * @author Nils Hartmann
 * @param <SELECTION>
 *          The type of the selection-object
 */
public abstract class AbstractSelectionService<SELECTION extends IProviderBasedSelection> {

  /**
   * A map containing all current selections
   */
  private final ConcurrentHashMap<String, SELECTION> _currentSelections = new ConcurrentHashMap<String, SELECTION>();

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @return
   */
  public SELECTION getSelection(String selectionId) {
    Assert.isNotNull(selectionId, "The parameter 'selectionId' must not be null");

    return _currentSelections.get(selectionId);
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @param providerId
   * @param newSelection
   */
  protected void setSelection(String selectionId, String providerId, SELECTION newSelection) {

    // filter selections that already have been set
    if (equals(newSelection, _currentSelections.get(selectionId))) {
      return;
    }

    // add selection
    _currentSelections.put(selectionId, newSelection);

    // notify listeners
    fireSelectionChanged(selectionId, providerId, newSelection);
  }

  /**
   * <p>
   * </p>
   * 
   * @return the currentSelections
   */
  protected final ConcurrentHashMap<String, SELECTION> getCurrentSelections() {
    return _currentSelections;
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @param providerId
   * @param newSelection
   */
  protected abstract void fireSelectionChanged(String selectionId, String providerId, SELECTION newSelection);

  /**
   * <p>
   * </p>
   * 
   * @param newSelection
   * @param selection
   * @return
   */
  protected abstract boolean equals(SELECTION newSelection, SELECTION selection);
}
