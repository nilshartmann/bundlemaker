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

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelection implements IArtifactSelection {

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_providerId == null) ? 0 : _providerId.hashCode());
    result = prime * result + ((_selectedArtifacts == null) ? 0 : _selectedArtifacts.hashCode());
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
    ArtifactSelection other = (ArtifactSelection) obj;
    if (_providerId == null) {
      if (other._providerId != null)
        return false;
    } else if (!_providerId.equals(other._providerId))
      return false;
    if (_selectedArtifacts == null) {
      if (other._selectedArtifacts != null)
        return false;
    } else if (!_selectedArtifacts.equals(other._selectedArtifacts))
      return false;
    return true;
  }

  /**
   * The provider that provides this selection
   */
  private final String          _providerId;

  /**
   * the selected artifacts
   */
  private final List<IBundleMakerArtifact> _selectedArtifacts;

  public ArtifactSelection(String providerId, List<IBundleMakerArtifact> selectedArtifacts) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    _providerId = providerId;
    _selectedArtifacts = Collections.unmodifiableList(selectedArtifacts);
  }

  @Override
  public String getProviderId() {
    return _providerId;
  }

  @Override
  public List<IBundleMakerArtifact> getSelectedArtifacts() {
    return _selectedArtifacts;
  }

}
