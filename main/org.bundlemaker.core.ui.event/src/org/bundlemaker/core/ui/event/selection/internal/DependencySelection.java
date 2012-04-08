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

import java.util.Collections;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencySelection extends AbstractProviderSelection implements IDependencySelection {

  /** the selected artifacts */
  private final List<IDependency> _selectedDependencies;

  /**
   * <p>
   * Creates a new instance of type {@link DependencySelection}.
   * </p>
   * 
   * @param dependencySelectionId
   * @param providerId
   * @param selectedDependencies
   */
  public DependencySelection(String dependencySelectionId, String providerId, List<IDependency> selectedDependencies) {

    //
    super(dependencySelectionId, providerId);

    //
    Assert.isNotNull(selectedDependencies, "The parameter 'selectedDependencies' must not be null");
    _selectedDependencies = Collections.unmodifiableList(selectedDependencies);
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
    int result = super.hashCode();
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
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    DependencySelection other = (DependencySelection) obj;
    if (_selectedDependencies == null) {
      if (other._selectedDependencies != null)
        return false;
    } else if (!_selectedDependencies.equals(other._selectedDependencies))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DependencySelection [selectionId=");

    builder.append(getSelectionId());
    builder.append(", providerId=");
    builder.append(getProviderId());
    builder.append(", selectedDependencies=");
    builder.append(_selectedDependencies);
    builder.append("]");
    return builder.toString();
  }
  
  
}
