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
 * 
 */
public class DependencySelectionService extends
    AbstractSelectionService<IDependencySelection, IDependencySelectionListener, IDependencySelectionChangedEvent>
    implements IDependencySelectionService {

  /**
   * {@inheritDoc}
   */
  @Override
  protected IDependencySelection getNullSelection(String providerId) {
    List<IDependency> emptyList = Collections.emptyList();
    return new DependencySelection(providerId, emptyList);
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

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.ui.selection.IDependencySelectionService#setSelection(java.lang.String,
   * java.util.Collection)
   */
  @Override
  public void setSelection(String providerId, Collection<IDependency> selectedArtifacts) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // Create selection
    DependencySelection dependencySelection = new DependencySelection(providerId, new LinkedList<IDependency>(
        selectedArtifacts));

    // register selection and inform listener
    setSelection(providerId, dependencySelection);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.ui.selection.IDependencySelectionService#setSelection(java.lang.String,
   * org.bundlemaker.analysis.model.IDependency)
   */
  @Override
  public void setSelection(String providerId, IDependency dependency) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");

    // Create list of dependencies
    List<IDependency> dependencies;
    if (dependency == null)
      dependencies = Collections.emptyList();
    else {
      dependencies = Arrays.asList(dependency);
    }

    // Create DependencySelection
    DependencySelection dependencySelection = new DependencySelection(providerId, dependencies);

    // register selection and inform listener
    setSelection(providerId, dependencySelection);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.selection.IDependencySelectionService#addDependencySelectionListener(java.lang.String,
   * org.bundlemaker.analysis.ui.selection.IDependencySelectionListener)
   */
  @Override
  public void addDependencySelectionListener(String providerId, IDependencySelectionListener listener) {
    addSelectionListener(providerId, listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.selection.IDependencySelectionService#addDependencySelectionListener(org.bundlemaker
   * .analysis.ui.selection.IDependencySelectionListener)
   */
  @Override
  public void addDependencySelectionListener(IDependencySelectionListener listener) {
    addSelectionListener(null, listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.selection.IDependencySelectionService#removeDependencySelectionListener(org.bundlemaker
   * .analysis.ui.selection.IDependencySelectionListener)
   */
  @Override
  public void removeDependencySelectionListener(IDependencySelectionListener listener) {
    removeSelectionListener(listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.internal.selection.AbstractSelectionService#createSelectionChangedEvent(java.lang.Object
   * )
   */
  @Override
  protected IDependencySelectionChangedEvent createSelectionChangedEvent(IDependencySelection newSelection) {
    return new DependencySelectionChangedEvent(newSelection);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.ui.internal.selection.AbstractSelectionService#invokeListener(java.lang.Object,
   * java.lang.Object)
   */
  @Override
  protected void invokeListener(IDependencySelectionListener listener, IDependencySelectionChangedEvent event) {
    listener.dependencySelectionChanged(event);
  }

}
