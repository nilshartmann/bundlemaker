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
package org.bundlemaker.core.ui.event.selection.internal;

import java.util.List;

import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionChangedEvent;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann
 * 
 */
public class DependencySelectionChangedEvent implements IDependencySelectionChangedEvent {

  /**
   * the new selection
   */
  private final IDependencySelection _selection;

  public DependencySelectionChangedEvent(IDependencySelection selection) {
    Assert.isNotNull(selection, "The parameter 'selection' must not be null");

    _selection = selection;
  }

  @Override
  public IDependencySelection getSelection() {
    return _selection;
  }

  /**
   * <p>
   * </p>
   *
   * @return
   * @see org.bundlemaker.core.ui.event.selection.IProviderSelection#getSelectionId()
   */
  public String getSelectionId() {
    return _selection.getSelectionId();
  }

  /**
   * <p>
   * </p>
   *
   * @return
   * @see org.bundlemaker.core.ui.event.selection.IProviderSelection#getProviderId()
   */
  public String getProviderId() {
    return _selection.getProviderId();
  }

  /**
   * <p>
   * </p>
   *
   * @return
   * @see org.bundlemaker.core.ui.event.selection.IDependencySelection#getSelectedDependencies()
   */
  public List<IDependency> getSelectedDependencies() {
    return _selection.getSelectedDependencies();
  }

  /**
   * <p>
   * </p>
   *
   * @return
   * @see org.bundlemaker.core.ui.event.selection.IDependencySelection#getFirstDependency()
   */
  public IDependency getFirstDependency() {
    return _selection.getFirstDependency();
  }

  /**
   * <p>
   * </p>
   *
   * @return
   * @see org.bundlemaker.core.ui.event.selection.IDependencySelection#hasDependencies()
   */
  public boolean hasDependencies() {
    return _selection.hasDependencies();
  }
}
