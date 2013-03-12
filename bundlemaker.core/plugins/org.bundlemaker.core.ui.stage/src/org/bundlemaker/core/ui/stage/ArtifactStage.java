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

import java.util.Collection;
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

  private ArtifactStageAddMode                                    _addMode             = ArtifactStageAddMode.autoAddSelectedArtifacts;

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

  public void setAddMode(ArtifactStageAddMode addMode) {
    ArtifactStageAddMode oldMode = _addMode;
    this._addMode = checkNotNull(addMode);

    if (!_addMode.equals(oldMode)) {
      fireArtifactStageChange(ArtifactStageChangeReason.configurationChanged);
    }
  }

  /**
   * @return the addMode
   */
  public ArtifactStageAddMode getAddMode() {
    return _addMode;
  }

  protected void projectExplorerSelectionChanged(IArtifactSelection newSelection) {
    // Selection in Project Explorer changed

    if (_addMode == ArtifactStageAddMode.doNotAutomaticallyAddArtifacts) {
      return;
    }

    if (newSelection == null) {
      setStagedArtifacts(new LinkedList<IBundleMakerArtifact>());
      return;
    }

    final List<IBundleMakerArtifact> selectedArtifacts = newSelection.getSelectedArtifacts();
    List<IBundleMakerArtifact> stagedArtifacts = new LinkedList<IBundleMakerArtifact>(selectedArtifacts);

    if (_addMode == ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts) {
      // add children of selected Artifacts

      System.out.println("STAGED ARTIFACTS: " + stagedArtifacts);

      for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
        Collection<IBundleMakerArtifact> children = iBundleMakerArtifact.getChildren();

        System.out.println("  ADD CHILDREN: " + children);
        stagedArtifacts.addAll(children);
      }
    }

    setStagedArtifacts(stagedArtifacts);
  }

  void setStagedArtifacts(List<IBundleMakerArtifact> stagedArtifacts) {
    _stagedArtifacts = (stagedArtifacts == null ? new LinkedList<IBundleMakerArtifact>() : stagedArtifacts);

    fireArtifactStageChange(ArtifactStageChangeReason.contentChanged);

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

  protected void fireArtifactStageChange(ArtifactStageChangeReason reason) {

    final ArtifactStageChangedEvent event = new ArtifactStageChangedEvent(reason);

    for (IArtifactStageChangeListener listener : _stageChangeListener) {
      listener.artifactStateChanged(event);
    }
  }

  /**
   * @param selectedArtifacts
   */
  void addToStage(List<IBundleMakerArtifact> selectedArtifacts) {

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
      _stagedArtifacts.add(iBundleMakerArtifact);
    }

    fireArtifactStageChange(ArtifactStageChangeReason.contentChanged);

    publishStagedArtifacts();
  }

}
