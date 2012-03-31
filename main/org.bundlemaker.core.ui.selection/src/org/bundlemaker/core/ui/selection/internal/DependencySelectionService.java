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
package org.bundlemaker.core.ui.selection.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.selection.IDependencySelection;
import org.bundlemaker.core.ui.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.selection.IDependencySelectionService;
import org.bundlemaker.core.ui.selection.Selection;
import org.eclipse.core.runtime.Assert;

/**
 * The implementation of the {@link IDependencySelectionListener}.
 * 
 * <p>
 * Instances of the {@link IDependencySelectionListener} can be received via the {@link Selection} factory class
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class DependencySelectionService extends AbstractSelectionService<IDependencySelection> implements
    IDependencySelectionService {

  /** - */
  private SelectionListenerList<IDependencySelectionListener, IDependencySelectionChangedEvent> _listenerContainer = null;

  /**
   * <p>
   * Creates a new instance of type {@link DependencySelectionService}.
   * </p>
   */
  public DependencySelectionService() {

    //
    _listenerContainer = new SelectionListenerList<IDependencySelectionListener, IDependencySelectionChangedEvent>() {
      @Override
      protected void invokeListener(IDependencySelectionListener listener, IDependencySelectionChangedEvent event) {
        listener.dependencySelectionChanged(event);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelection(String selectionId, String providerId, Collection<IDependency> selectedArtifacts) {
    Assert.isNotNull(selectionId, "The parameter 'selectionId' must not be null");
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // Create selection
    DependencySelection dependencySelection = new DependencySelection(selectionId, providerId,
        new LinkedList<IDependency>(selectedArtifacts));

    // register selection and inform listener
    setSelection(selectionId, providerId, dependencySelection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelection(String selectionId, String providerId, IDependency dependency) {

    // Create list of dependencies
    List<IDependency> dependencies;
    if (dependency == null)
      dependencies = Collections.emptyList();
    else {
      dependencies = Arrays.asList(dependency);
    }

    // Create DependencySelection
    DependencySelection dependencySelection = new DependencySelection(selectionId, providerId, dependencies);

    // register selection and inform listener
    setSelection(selectionId, providerId, dependencySelection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addDependencySelectionListener(String selectionId, IDependencySelectionListener listener) {
    _listenerContainer.addSelectionListener(selectionId, listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeDependencySelectionListener(IDependencySelectionListener listener) {
    _listenerContainer.removeSelectionListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean equals(IDependencySelection newSelection, IDependencySelection selection) {
    if (newSelection == null) {
      return false;
    }
    return newSelection.equals(selection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireSelectionChanged(String selectionId, String providerId, IDependencySelection newSelection) {
    IDependencySelectionChangedEvent event = new DependencySelectionChangedEvent(newSelection);
    _listenerContainer.fireSelectionChanged(selectionId, event);
  }
}
