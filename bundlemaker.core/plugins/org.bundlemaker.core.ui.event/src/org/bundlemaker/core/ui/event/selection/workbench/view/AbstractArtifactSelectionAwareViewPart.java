/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.event.selection.workbench.view;

import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.internal.ArtifactSelection;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractArtifactSelectionAwareViewPart extends AbstractPartLifecycleAwareViewPart implements
    IArtifactSelectionListener, IAnalysisModelModifiedListener {

  /**
   * The current artifacts (contents) of this dependency part
   */
  private IArtifactSelection      _currentArtifactSelection;

  /** - */
  public final IArtifactSelection EMPTY_ARTIFACT_SELECTION = new ArtifactSelection(getArtifactSelectionId(),
                                                               getProviderId());

  public AbstractArtifactSelectionAwareViewPart() {
    _currentArtifactSelection = EMPTY_ARTIFACT_SELECTION;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactSelection getCurrentArtifactSelection() {
    return _currentArtifactSelection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // Remove ourself from the list of listeners
    Selection.instance().getArtifactSelectionService().removeArtifactSelectionListener(this);

    //
    unregisterArtifactModelChangedListener();

    // invoke super
    super.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.event.selection.workbench.view.AbstractPartLifecycleAwareViewPart#init(org.eclipse.ui.IViewSite
   * )
   */
  @Override
  public void init(IViewSite site) throws PartInitException {
    // call super
    super.init(site);

    // add listener
    Selection.instance().getArtifactSelectionService().addArtifactSelectionListener(getArtifactSelectionId(), this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void artifactSelectionChanged(IArtifactSelection selection) {

    //
    // if (!isActive()) {
    // return;
    // }

    // // skip self
    // if (selection.getProviderId().equals(getProviderId())) {
    // return;
    // }

    // skip already set
    if (getCurrentArtifactSelection() != null && getCurrentArtifactSelection().equals(selection)) {
      return;
    }

    setCurrentArtifactSelection(selection);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactModelModifiedListener#artifactModelModified()
   */
  @Override
  public void analysisModelModified() {
    // implement in subclasses if needed
  }

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {

    // remove ArtifactModelChangedListener from 'old' model
    unregisterArtifactModelChangedListener();

    _currentArtifactSelection = artifactSelection;

    registerArtifactModelChangedListener();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPartActivated() {

    //
    if (_currentArtifactSelection == null || !_currentArtifactSelection.hasSelectedArtifacts()) {

      // initialize view with current selection from Artifact tree
      initFromArtifactSelectionService();
    }
  }

  private void unregisterArtifactModelChangedListener() {
    if (_currentArtifactSelection != null && _currentArtifactSelection.hasSelectedArtifacts()) {
      _currentArtifactSelection.getRootArtifact().removeAnalysisModelModifiedListener(this);
    }
  }

  private void registerArtifactModelChangedListener() {
    if (_currentArtifactSelection != null && _currentArtifactSelection.hasSelectedArtifacts()) {
      _currentArtifactSelection.getRootArtifact().addAnalysisModelModifiedListener(this);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getArtifactSelectionId() {
    return Selection.MAIN_ARTIFACT_SELECTION_ID;
  }

  protected abstract String getProviderId();

  /**
   * <p>
   * </p>
   * 
   */
  private void initFromArtifactSelectionService() {
    IArtifactSelection currentArtifactSelection = Selection.instance().getArtifactSelectionService()
        .getSelection(getArtifactSelectionId());

    if (currentArtifactSelection != null) {
      setCurrentArtifactSelection(currentArtifactSelection);
    }
  }
}
