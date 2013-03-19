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

package org.bundlemaker.core.selection.internal.stage;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.IArtifactSelectionListener;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.selection.stage.ArtifactStageAddMode;
import org.bundlemaker.core.selection.stage.ArtifactStageChangedEvent;
import org.bundlemaker.core.selection.stage.IArtifactStage;
import org.bundlemaker.core.selection.stage.IArtifactStageChangeListener;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStage implements IArtifactStage {

  private ArtifactStageAddMode                                    _addMode             = ArtifactStageAddMode.autoAddSelectedArtifacts;

  private List<IBundleMakerArtifact>                              _stagedArtifacts     = new LinkedList<IBundleMakerArtifact>();

  private final CopyOnWriteArraySet<IArtifactStageChangeListener> _stageChangeListener = new CopyOnWriteArraySet<IArtifactStageChangeListener>();

  private boolean                                                 _init;

  public void init() {
    if (!_init) {
      Selection.instance().getArtifactSelectionService()
          .addArtifactSelectionListener(Selection.PROJECT_EXLPORER_SELECTION_ID, new IArtifactSelectionListener() {

            @Override
            public void artifactSelectionChanged(IArtifactSelection event) {
              projectExplorerSelectionChanged(event);
            }
          });
      boolean useChildrenOfSelectedArtifacts = Selection.instance().getArtifactSelectionService()
          .getUseChildrenOfSelectedArtifacts();
      if (useChildrenOfSelectedArtifacts) {
        _addMode = ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts;
      }
      IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
          .getSelection(Selection.PROJECT_EXLPORER_SELECTION_ID);
      projectExplorerSelectionChanged(selection);

      _init = true;
    }
  }

  public boolean hasStagedArtifacts() {
    return !_stagedArtifacts.isEmpty();
  }

  public void setAddMode(ArtifactStageAddMode addMode) {
    ArtifactStageAddMode oldMode = _addMode;
    this._addMode = checkNotNull(addMode);

    if (!_addMode.equals(oldMode)) {

      Selection.instance().getArtifactSelectionService()
          .setUseChildrenOfSelectedArtifacts(addMode == ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts);

      fireArtifactStageChange();
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
    setStagedArtifacts(selectedArtifacts);

    // List<IBundleMakerArtifact> stagedArtifacts = new LinkedList<IBundleMakerArtifact>();
    // if (_addMode == ArtifactStageAddMode.autoAddSelectedArtifacts) {
    // stagedArtifacts.addAll(selectedArtifacts);
    //
    // } else if (_addMode == ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts) {
    // // add children of selected Artifacts
    //
    // System.out.println("STAGED ARTIFACTS: " + stagedArtifacts);
    //
    // for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
    // Collection<IBundleMakerArtifact> children = iBundleMakerArtifact.getChildren();
    //
    // System.out.println("  ADD CHILDREN: " + children);
    // stagedArtifacts.addAll(children);
    // }
    // }
    //
    // setStagedArtifacts(stagedArtifacts);
  }

  /**
   * Set a complete new list of staged artifacts
   * 
   * @param stagedArtifacts
   *          the new staged artifacts. Might be null
   */
  public void setStagedArtifacts(List<IBundleMakerArtifact> stagedArtifacts) {
    _stagedArtifacts = (stagedArtifacts == null ? new LinkedList<IBundleMakerArtifact>()
        : new LinkedList<IBundleMakerArtifact>(stagedArtifacts));

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
    Selection.instance().getArtifactSelectionService().setSelection(//
        Selection.ARTIFACT_STAGE_SELECTION_ID, //
        StageSelection.STAGE_VIEW_SELECTION_PROVIDER_ID, //
        _stagedArtifacts);

  }

  protected void fireArtifactStageChange() {

    final ArtifactStageChangedEvent event = new ArtifactStageChangedEvent();

    for (IArtifactStageChangeListener listener : _stageChangeListener) {
      listener.artifactStateChanged(event);
    }
  }

  /**
   * @param selectedArtifacts
   */
  public void addToStage(List<IBundleMakerArtifact> selectedArtifacts) {

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
      _stagedArtifacts.add(iBundleMakerArtifact);
    }

    // fireArtifactStageChange(ArtifactStageChangeReason.contentChanged);

    publishStagedArtifacts();
  }

  /**
   * @param artifacts
   */
  public void removeStagedArtifacts(Collection<IBundleMakerArtifact> artifacts) {
    _stagedArtifacts.removeAll(artifacts);

    // fireArtifactStageChange(ArtifactStageChangeReason.contentChanged);

    publishStagedArtifacts();
  }

}
