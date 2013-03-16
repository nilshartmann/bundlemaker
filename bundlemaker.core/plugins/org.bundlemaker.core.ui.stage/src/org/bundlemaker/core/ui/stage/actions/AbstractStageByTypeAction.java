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

package org.bundlemaker.core.ui.stage.actions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractStageByTypeAction extends AbstractStageAction {

  private final Class<? extends IBundleMakerArtifact> _handledType;

  /**
   * @param title
   */
  public AbstractStageByTypeAction(String title, Class<? extends IBundleMakerArtifact> handledType) {
    super(title);
    this._handledType = checkNotNull(handledType);
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

    if (!(isManualAddMode() && artifactSelection.hasSelectedArtifacts())) {
      setEnabled(false);
      return;
    }

    // List<IBundleMakerArtifact> selectedArtifacts = artifactSelection.getSelectedArtifacts();
    // boolean e = true;
    // for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
    // if (iBundleMakerArtifact.isInstanceOf(ITypeArtifact.class)) {
    // e = false;
    // break;
    // }
    // }
    setEnabled(true);

  }

  @Override
  public void run() {
    List<IBundleMakerArtifact> resources = new LinkedList<IBundleMakerArtifact>();

    List<IBundleMakerArtifact> selectedArtifacts = getArtifactSelection().getSelectedArtifacts();

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {

      List<IBundleMakerArtifact> resourceChildren = (List<IBundleMakerArtifact>) AnalysisModelQueries.findChildren(
          iBundleMakerArtifact, _handledType);
      resources.addAll(resourceChildren);
    }

    addToStage(resources);

  }

}
