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

import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;

import com.google.common.collect.Lists;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ManipulateStageActionGroup {

  private ClearStageAction                _clearStageAction = new ClearStageAction();

  private final List<AbstractStageAction> _actions;

  public ManipulateStageActionGroup() {
    _actions = Lists.newArrayList(//
        new AddToStageAction(),//
        new StageGroupsAction(),//
        new StageModulesAction(), //
        new StagePackagesAction(),//
        new StageResourcesAction()//
        );
  }

  public void fill(List<IContributionItem> items) {

    IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
        .getSelection(Selection.PROJECT_EXLPORER_SELECTION_ID);

    for (AbstractStageAction action : _actions) {
      action.setArtifactSelection(selection);

      items.add(new ActionContributionItem(action));
    }
    items.add(new Separator());

    _clearStageAction.setArtifactSelection(selection);
    items.add(new ActionContributionItem(_clearStageAction));
  }

}
