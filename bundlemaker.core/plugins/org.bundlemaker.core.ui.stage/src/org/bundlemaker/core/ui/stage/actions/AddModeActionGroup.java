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

import java.util.List;

import org.bundlemaker.core.ui.event.stage.ArtifactStageAddMode;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class AddModeActionGroup {

  private AddModeAction _autoAddModeAction;

  private AddModeAction _autoAddChildrenModeAction;

  private AddModeAction _dontAutoAddModeAction;

  public AddModeActionGroup() {
    _autoAddChildrenModeAction = new AddModeAction(ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts,
        StageIcons.ADD_CHILD_ARTIFACTS_ICON.getImageDescriptor());
    _autoAddModeAction = new AddModeAction(ArtifactStageAddMode.autoAddSelectedArtifacts,
        StageIcons.ADD_ARTIFACTS_ICON.getImageDescriptor());
    _dontAutoAddModeAction = new AddModeAction(ArtifactStageAddMode.doNotAutomaticallyAddArtifacts,
        StageIcons.ADD_MANUALLY_ICON.getImageDescriptor());
  }

  public void fill(IContributionManager menuManager) {
    menuManager.add(_autoAddModeAction);
    menuManager.add(_autoAddChildrenModeAction);
    menuManager.add(_dontAutoAddModeAction);

  }

  /**
   * 
   */
  public void update() {
    _autoAddChildrenModeAction.update();
    _autoAddModeAction.update();
    _dontAutoAddModeAction.update();
  }

  /**
   * @param contributionItems
   */
  public void fill(List<IContributionItem> contributionItems) {
    contributionItems.add(new ActionContributionItem(_autoAddModeAction));
    contributionItems.add(new ActionContributionItem(_autoAddChildrenModeAction));
    contributionItems.add(new ActionContributionItem(_dontAutoAddModeAction));
  }

}
