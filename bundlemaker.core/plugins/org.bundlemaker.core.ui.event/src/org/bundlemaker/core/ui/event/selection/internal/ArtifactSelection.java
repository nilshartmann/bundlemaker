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
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.eclipse.core.runtime.Assert;

/**
 * Implementation of {@link IArtifactSelection}
 * 
 * @author Nils Hartmann
 */
public class ArtifactSelection extends AbstractProviderSelection implements IArtifactSelection {

  /** the selected artifacts */
  private final List<IBundleMakerArtifact> _selectedArtifacts;

  /** - */
  private final boolean                    _useChildrenOfSelectedArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactSelection}.
   * </p>
   * 
   * @param selectionId
   * @param providerId
   */
  public ArtifactSelection(String selectionId, String providerId) {
    super(selectionId, providerId);
    _selectedArtifacts = Collections.emptyList();
    _useChildrenOfSelectedArtifacts = false;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactSelection}.
   * </p>
   * 
   * @param artifactSelectionId
   * @param providerId
   * @param selectedArtifacts
   * @param useChildrenOfSelectedArtifacts
   */
  public ArtifactSelection(String artifactSelectionId, String providerId, List<IBundleMakerArtifact> selectedArtifacts,
      boolean useChildrenOfSelectedArtifacts) {

    // call super
    super(artifactSelectionId, providerId);

    // assert not null
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // set the selected artifacts
    _selectedArtifacts = Collections.unmodifiableList(selectedArtifacts);

    //
    _useChildrenOfSelectedArtifacts = useChildrenOfSelectedArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IBundleMakerArtifact> getSelectedArtifacts() {
    return _selectedArtifacts;
  }

  @Override
  public List<IBundleMakerArtifact> getEffectiveSelectedArtifacts() {

    if (!useChildrenOfSelectedArtifacts()) {
      return _selectedArtifacts;
    }

    List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();
    for (IBundleMakerArtifact artifact : _selectedArtifacts) {
      result.addAll(artifact.getChildren());
    }

    return result;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedArtifacts() {
    return _selectedArtifacts != null && !_selectedArtifacts.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRootArtifact() {
    Assert.isTrue(hasSelectedArtifacts());

    return _selectedArtifacts.get(0).getRoot();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useChildrenOfSelectedArtifacts() {
    return _useChildrenOfSelectedArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((_selectedArtifacts == null) ? 0 : _selectedArtifacts.hashCode());
    result = prime * result + (_useChildrenOfSelectedArtifacts ? 1231 : 1237);
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
    ArtifactSelection other = (ArtifactSelection) obj;
    if (_selectedArtifacts == null) {
      if (other._selectedArtifacts != null)
        return false;
    } else if (!_selectedArtifacts.equals(other._selectedArtifacts))
      return false;
    if (_useChildrenOfSelectedArtifacts != other._useChildrenOfSelectedArtifacts)
      return false;
    return true;
  }
}
