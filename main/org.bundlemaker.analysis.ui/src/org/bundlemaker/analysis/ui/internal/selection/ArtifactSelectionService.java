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

import java.util.Collection;
import java.util.LinkedList;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.eclipse.core.runtime.Assert;

/**
 * The selection service implementation
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionService extends
    AbstractSelectionService<IArtifactSelection, IArtifactSelectionListener, IArtifactSelectionChangedEvent> implements
    IArtifactSelectionService {

  @Override
  public void setSelection(String providerId, Collection<IArtifact> selectedArtifacts) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // create selection object for selected artifacts
    ArtifactSelection artifactSelection = new ArtifactSelection(providerId,
        new LinkedList<IArtifact>(selectedArtifacts));

    // set the selection
    setSelection(providerId, artifactSelection);
  }

  @Override
  public void addArtifactSelectionListener(String providerId, IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    addSelectionListener(providerId, listener);
  }

  @Override
  public void addArtifactSelectionListener(IArtifactSelectionListener listener) {
    addArtifactSelectionListener(null, listener);
  }

  @Override
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

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
  protected IArtifactSelectionChangedEvent createSelectionChangedEvent(IArtifactSelection newSelection) {
    return new ArtifactSelectionChangedEvent(newSelection);
  }

  @Override
  protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelectionChangedEvent event) {
    listener.artifactSelectionChanged(event);
  }

}
