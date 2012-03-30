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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

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
 * 
 */
public class ArtifactSelectionService extends
    AbstractSelectionService<IArtifactSelection, IArtifactSelectionListener, IArtifactSelectionChangedEvent> implements
    IArtifactSelectionService, IRootArtifactSelectionService {

  /** - */
  private final CopyOnWriteArraySet<SelectionListenerWrapper<IRootArtifactSelectionListener>> _rootSelectionlistenerList = new CopyOnWriteArraySet<SelectionListenerWrapper<IRootArtifactSelectionListener>>();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setSelection(String providerId, IArtifactSelection newSelection) {
    super.setSelection(providerId, newSelection);

    IRootArtifactSelection rootArtifactSelection = getRootArtifactSelection(providerId);
    fireRootArtifactSelectionChanged(providerId, new RootArtifactSelectionChangedEvent(rootArtifactSelection));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifactSelection getRootArtifactSelection(String selectionProviderId) {

    //
    IArtifactSelection artifactSelection = getSelection(selectionProviderId);

    //
    if (artifactSelection == null || artifactSelection.getSelectedArtifacts().size() == 0) {
      return new RootArtifactSelection(selectionProviderId);
    }

    //
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) artifactSelection.getSelectedArtifacts().get(0);

    //
    return new RootArtifactSelection(selectionProviderId, artifact.getRoot());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRootArtifactSelectionListener(String providerId, IRootArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    // Create wrapper
    SelectionListenerWrapper<IRootArtifactSelectionListener> wrapper = new SelectionListenerWrapper<IRootArtifactSelectionListener>(
        providerId, listener);

    // add to listener list
    _rootSelectionlistenerList.add(wrapper);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeRootArtifactSelectionListener(IRootArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    for (SelectionListenerWrapper<IRootArtifactSelectionListener> wrapper : _rootSelectionlistenerList) {
      if (listener.equals(wrapper.getListener())) {
        // Remove from listener list
        _rootSelectionlistenerList.remove(wrapper);
        break;
      }
    }
  }

  @Override
  public void setSelection(String providerId, Collection<IBundleMakerArtifact> selectedArtifacts) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");

    // create selection object for selected artifacts
    ArtifactSelection artifactSelection = new ArtifactSelection(providerId, new LinkedList<IBundleMakerArtifact>(
        selectedArtifacts));

    // set the selection
    setSelection(providerId, artifactSelection);
  }

  @Override
  public void addArtifactSelectionListener(String providerId, IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    addSelectionListener(providerId, listener);
  }

  @Override
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    removeSelectionListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IArtifactSelectionChangedEvent createSelectionChangedEvent(IArtifactSelection newSelection) {
    return new ArtifactSelectionChangedEvent(newSelection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelectionChangedEvent event) {
    listener.artifactSelectionChanged(event);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IArtifactSelection getNullSelection(String providerId) {
    List<IBundleMakerArtifact> emptyList = Collections.emptyList();
    return new ArtifactSelection(providerId, emptyList);
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
   * <p>
   * </p>
   * 
   * @param providerId
   * @param event
   */
  protected void fireRootArtifactSelectionChanged(String providerId, IRootArtifactSelectionChangedEvent event) {
    for (SelectionListenerWrapper<IRootArtifactSelectionListener> wrapper : _rootSelectionlistenerList) {
      // check if listener is registered for the provider
      if (wrapper.matches(providerId)) {
        wrapper.getListener().rootArtifactSelectionChanged(event);
      }
    }
  }
}
