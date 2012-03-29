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

import java.util.Collections;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IDependencySelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 * 
 */
public class DependencySelection implements IDependencySelection {

  /**
   * The provider that provides this selection
   */
  private final String            _providerId;

  /**
   * the selected artifacts
   */
  private final List<IDependency> _selectedDependencies;

  public DependencySelection(String providerId, List<IDependency> selectedDependencies) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedDependencies, "The parameter 'selectedDependencies' must not be null");

    _providerId = providerId;
    _selectedDependencies = Collections.unmodifiableList(selectedDependencies);
  }

  @Override
  public String getProviderId() {
    return _providerId;
  }

  @Override
  public List<IDependency> getSelectedDependencies() {
    return _selectedDependencies;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.ui.selection.IDependencySelection#getFirstDependency()
   */
  @Override
  public IDependency getFirstDependency() {
    if (!hasDependencies()) {
      // empty selection
      return null;
    }

    // Return first element
    return _selectedDependencies.get(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.ui.selection.IDependencySelection#hasDependencies()
   */
  @Override
  public boolean hasDependencies() {
    return !_selectedDependencies.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_providerId == null) ? 0 : _providerId.hashCode());
    result = prime * result + ((_selectedDependencies == null) ? 0 : _selectedDependencies.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DependencySelection other = (DependencySelection) obj;
    if (_providerId == null) {
      if (other._providerId != null)
        return false;
    } else if (!_providerId.equals(other._providerId))
      return false;
    if (_selectedDependencies == null) {
      if (other._selectedDependencies != null)
        return false;
    } else if (!_selectedDependencies.equals(other._selectedDependencies))
      return false;
    return true;
  }
}
