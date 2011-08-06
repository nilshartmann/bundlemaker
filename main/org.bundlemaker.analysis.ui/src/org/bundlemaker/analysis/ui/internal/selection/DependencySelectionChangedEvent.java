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

import org.bundlemaker.analysis.ui.selection.IDependencySelection;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionChangedEvent;
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

}
