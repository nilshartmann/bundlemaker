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

package org.bundlemaker.core.ui.stage;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStage {

  private static ArtifactStage                                    _instance;

  private boolean                                                 _stagePinned         = false;

  private List<IBundleMakerArtifact>                              _stagedArtifacts     = new LinkedList<IBundleMakerArtifact>();

  private final CopyOnWriteArraySet<IArtifactStageChangeListener> _stageChangeListener = new CopyOnWriteArraySet<IArtifactStageChangeListener>();

  public static ArtifactStage instance() {
    if (_instance == null) {
      _instance = new ArtifactStage();
    }

    return _instance;
  }

  private ArtifactStage() {
    Selection.instance().getArtifactSelectionService()
        .addArtifactSelectionListener(Selection.PROJECT_EXLPORER_SELECTION_ID, new IArtifactSelectionListener() {

          @Override
          public void artifactSelectionChanged(IArtifactSelection event) {
            projectExplorerSelectionChanged(event);
          }
        });

    IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
        .getSelection(Selection.PROJECT_EXLPORER_SELECTION_ID);
    projectExplorerSelectionChanged(selection);
  }

  public boolean hasStagedArtifacts() {
    return !_stagedArtifacts.isEmpty();
  }

  /**
   * @return the stagedArtifacts. Never null
   */
  public List<IBundleMakerArtifact> getStagedArtifacts() {
    return _stagedArtifacts;
  }

  /**
   * @param selectionPinnned
   *          the selectionPinnned to set
   */
  public void setStagePinned(boolean selectionPinnned) {
    _stagePinned = selectionPinnned;
  }

  /**
   * @return the selectionPinnned
   */
  public boolean isStagePinned() {
    return _stagePinned;
  }

  protected void projectExplorerSelectionChanged(IArtifactSelection newSelection) {
    // Selection in Project Explorer changed

    if (isStagePinned() && hasStagedArtifacts()) {
      // ignore
      return;
    }

    if (newSelection == null) {
      setStagedArtifacts(new LinkedList<IBundleMakerArtifact>());
    } else {
      setStagedArtifacts(new LinkedList(newSelection.getSelectedArtifacts()));
    }
  }

  void setStagedArtifacts(List<IBundleMakerArtifact> stagedArtifacts) {
    _stagedArtifacts = (stagedArtifacts == null ? new LinkedList<IBundleMakerArtifact>() : stagedArtifacts);

    fireArtifactStageChange();

    publishStagedArtifacts();

  }

  public void addArtifactStageChangeListener(IArtifactStageChangeListener listener) {
    checkNotNull(listener);

    _stageChangeListener.add(listener);
  }

  public void removeArtifactStageChangeListener(IArtifactStageChangeListener listener) {
    checkNotNull(listener);

    _stageChangeListener.remove(listener);
  }

  public void publishStagedArtifacts() {
    System.out.println("Set MAIN_SELECTION: " + _stagedArtifacts);
    Selection.instance().getArtifactSelectionService().setSelection(//
        Selection.MAIN_ARTIFACT_SELECTION_ID, //
        StageSelection.STAGE_VIEW_SELECTION_PROVIDER_ID, //
        _stagedArtifacts);

  }

  protected void fireArtifactStageChange() {
    for (IArtifactStageChangeListener listener : _stageChangeListener) {
      listener.artifactStateChanged();
    }
  }

  /**
   * @param selectedArtifacts
   */
  void addToStage(List<IBundleMakerArtifact> selectedArtifacts) {

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
      _stagedArtifacts.add(iBundleMakerArtifact);
    }

    fireArtifactStageChange();

    publishStagedArtifacts();

  }
}
