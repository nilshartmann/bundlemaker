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

import java.util.Collections;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelection implements IArtifactSelection {

  /**
   * The provider that provides this selection
   */
  private final String          _providerId;

  /**
   * the selected artifacts
   */
  private final List<IArtifact> _selectedArtifacts;

  public ArtifactSelection(String providerId, List<IArtifact> selectedArtifacts) {
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
  public List<IArtifact> getSelectedArtifacts() {
    return _selectedArtifacts;
  }

}
