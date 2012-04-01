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
  private SelectionListenerList<IArtifactSelectionListener, IArtifactSelectionChangedEvent>         _artifactSelectionListenerContainer     = null;

  /** - */
  private SelectionListenerList<IRootArtifactSelectionListener, IRootArtifactSelectionChangedEvent> _rootArtifactSelectionListenerContainer = null;

  public ArtifactSelectionService() {

    //
    _artifactSelectionListenerContainer = new SelectionListenerList<IArtifactSelectionListener, IArtifactSelectionChangedEvent>() {
      @Override
      protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelectionChangedEvent event) {
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

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected IArtifactSelectionChangedEvent createSelectionChangedEvent(IArtifactSelection newSelection) {
  // return new ArtifactSelectionChangedEvent(newSelection);
  // }
  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelectionChangedEvent event) {
  // listener.artifactSelectionChanged(event);
  // }

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

  // /**
  // * <p>
  // * </p>
  // *
  // * @param providerId
  // * @param event
  // */
  // protected void fireRootArtifactSelectionChanged(String providerId, IRootArtifactSelectionChangedEvent event) {
  // for (SelectionListenerWrapper<IRootArtifactSelectionListener> wrapper : _rootSelectionlistenerList) {
  // // check if listener is registered for the provider
  // if (wrapper.matches(providerId)) {
  // wrapper.getListener().rootArtifactSelectionChanged(event);
  // }
  // }
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireSelectionChanged(String selectionId, String providerId, IArtifactSelection newSelection) {
    _artifactSelectionListenerContainer.fireSelectionChanged(selectionId, new ArtifactSelectionChangedEvent(
        newSelection));

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
