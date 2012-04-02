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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.selection.IArtifactSelectionService;
import org.bundlemaker.core.ui.selection.IRootArtifactSelection;
import org.bundlemaker.core.ui.selection.IRootArtifactSelectionChangedEvent;
import org.bundlemaker.core.ui.selection.IRootArtifactSelectionListener;
import org.bundlemaker.core.ui.selection.IRootArtifactSelectionService;
import org.eclipse.core.runtime.Assert;

/**
 * The selection service implementation
 * 
 * @author Nils Hartmann
 */
public class ArtifactSelectionService extends AbstractSelectionService<IArtifactSelection> implements
    IArtifactSelectionService, IRootArtifactSelectionService {

  /** - */
  private SelectionListenerList<IArtifactSelectionListener, IArtifactSelection>         _artifactSelectionListenerContainer     = null;

  /** - */
  private SelectionListenerList<IRootArtifactSelectionListener, IRootArtifactSelectionChangedEvent> _rootArtifactSelectionListenerContainer = null;

  /** - */
  private boolean                                                                                   _useChildrenOfSelectedArtifacts         = true;

  public ArtifactSelectionService() {

    //
    _artifactSelectionListenerContainer = new SelectionListenerList<IArtifactSelectionListener, IArtifactSelection>() {
      @Override
      protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelection event) {
        listener.artifactSelectionChanged(event);
      }
    };

    //
    _rootArtifactSelectionListenerContainer = new SelectionListenerList<IRootArtifactSelectionListener, IRootArtifactSelectionChangedEvent>() {
      @Override
      protected void invokeListener(IRootArtifactSelectionListener listener, IRootArtifactSelectionChangedEvent event) {
        listener.rootArtifactSelectionChanged(event);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUseChildrenOfSelectedArtifacts(boolean useChildrenOfSelectedArtifacts) {

    //
    _useChildrenOfSelectedArtifacts = useChildrenOfSelectedArtifacts;

    //
    for (Entry<String, IArtifactSelection> entry : getCurrentSelections().entrySet()) {

      //
      IArtifactSelection oldArtifactSelection = entry.getValue();

      //
      setSelection(oldArtifactSelection.getSelectionId(), oldArtifactSelection.getProviderId(),
          oldArtifactSelection.getSelectedArtifacts());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getUseChildrenOfSelectedArtifacts() {
    return _useChildrenOfSelectedArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelection(String selectionId, String providerId, Collection<IBundleMakerArtifact> selectedArtifacts,
      boolean useChildrenOfSelectedArtifacts) {

    Assert.isNotNull(selectionId, "The parameter 'selectionId' must not be null");
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // create selection object for selected artifacts
    ArtifactSelection artifactSelection = new ArtifactSelection(selectionId, providerId,
        new LinkedList<IBundleMakerArtifact>(selectedArtifacts), useChildrenOfSelectedArtifacts);

    // set the selection
    setSelection(selectionId, providerId, artifactSelection);
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @param providerId
   * @param selectedArtifacts
   */
  public void setSelection(String selectionId, String providerId, Collection<IBundleMakerArtifact> selectedArtifacts) {
    setSelection(selectionId, providerId, selectedArtifacts, getUseChildrenOfSelectedArtifacts());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifactSelection getRootArtifactSelection(String selectionId) {

    //
    IArtifactSelection artifactSelection = getSelection(selectionId);

    //
    if (artifactSelection == null || artifactSelection.getSelectedArtifacts().size() == 0) {
      // TODO
      return new RootArtifactSelection(selectionId, "");
    }

    //
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) artifactSelection.getSelectedArtifacts().get(0);

    //
    return new RootArtifactSelection(selectionId, artifactSelection.getProviderId(), artifact.getRoot());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRootArtifactSelectionListener(String selectionId, IRootArtifactSelectionListener listener) {
    _rootArtifactSelectionListenerContainer.addSelectionListener(selectionId, listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeRootArtifactSelectionListener(IRootArtifactSelectionListener listener) {
    _rootArtifactSelectionListenerContainer.removeSelectionListener(listener);
  }

  @Override
  public void addArtifactSelectionListener(String selectionId, IArtifactSelectionListener listener) {
    _artifactSelectionListenerContainer.addSelectionListener(selectionId, listener);
  }

  @Override
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener) {
    _artifactSelectionListenerContainer.removeSelectionListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean equals(IArtifactSelection newSelection, IArtifactSelection selection) {
    if (newSelection == null) {
      return false;
    }
    return newSelection.equals(selection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireSelectionChanged(String selectionId, String providerId, IArtifactSelection newSelection) {
    _artifactSelectionListenerContainer.fireSelectionChanged(selectionId, newSelection);

    //
    RootArtifactSelection rootArtifactSelection = null;
    if (newSelection.getSelectedArtifacts().size() == 0) {
      rootArtifactSelection = new RootArtifactSelection(selectionId, providerId);
    } else {
      //
      IBundleMakerArtifact artifact = (IBundleMakerArtifact) newSelection.getSelectedArtifacts().get(0);
      rootArtifactSelection = new RootArtifactSelection(selectionId, newSelection.getProviderId(), artifact.getRoot());
    }

    IRootArtifactSelectionChangedEvent rootArtifactSelectionChangedEvent = new RootArtifactSelectionChangedEvent(
        rootArtifactSelection);
    _rootArtifactSelectionListenerContainer.fireSelectionChanged(selectionId, rootArtifactSelectionChangedEvent);
  }

}
