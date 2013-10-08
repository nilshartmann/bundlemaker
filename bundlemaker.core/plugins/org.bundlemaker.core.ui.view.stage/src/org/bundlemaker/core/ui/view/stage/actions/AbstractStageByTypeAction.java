/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.view.stage.actions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.ui.view.ArtifactStageActionHelper;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractStageByTypeAction extends AbstractStageAction {

  private final List<Class<? extends IBundleMakerArtifact>> _disableForTyes;

  private final Class<? extends IBundleMakerArtifact>       _handledType;

  /**
   * @param title
   */
  public AbstractStageByTypeAction(String title, Class<? extends IBundleMakerArtifact> handledType,
      Class<? extends IBundleMakerArtifact>... disableFor) {
    super(title);
    this._handledType = checkNotNull(handledType);
    _disableForTyes = new LinkedList<Class<? extends IBundleMakerArtifact>>();

    for (Class<? extends IBundleMakerArtifact> clazz : disableFor) {
      _disableForTyes.add(clazz);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.stage.actions.AbstractStageAction#setArtifactSelection(org.bundlemaker.core.ui.event.selection
   * .IArtifactSelection)
   */
  @Override
  public void setArtifactSelection(IArtifactSelection artifactSelection) {
    super.setArtifactSelection(artifactSelection);

    if (!artifactSelection.hasSelectedArtifacts()) {
      setEnabled(false);
      return;
    }

    List<IBundleMakerArtifact> selectedArtifacts = artifactSelection.getSelectedArtifacts();
    boolean e = true;
    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {

      for (Class<? extends IBundleMakerArtifact> clazz : _disableForTyes) {
        if (iBundleMakerArtifact.isInstanceOf(clazz)) {
          e = false;
          break;
        }

        if (!e) {
          break;
        }

      }
    }
    setEnabled(e);

  }

  @Override
  public void run() {
    if (!ArtifactStageActionHelper.switchToManualAddModeIfRequired()) {
      return;
    }

    List<IBundleMakerArtifact> resources = new LinkedList<IBundleMakerArtifact>();

    List<IBundleMakerArtifact> selectedArtifacts = getArtifactSelection().getSelectedArtifacts();

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {

      List<IBundleMakerArtifact> resourceChildren = (List<IBundleMakerArtifact>) AnalysisModelQueries.findSuccessors(
          iBundleMakerArtifact, _handledType);
      resources.addAll(resourceChildren);
    }

    addToStage(resources);

  }

}
