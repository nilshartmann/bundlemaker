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

import org.bundlemaker.core.selection.stage.ArtifactStage;
import org.bundlemaker.core.selection.stage.ArtifactStageAddMode;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;

public class AddModeAction extends Action {
  private final ArtifactStageAddMode _addMode;

  AddModeAction(ArtifactStageAddMode mode, ImageDescriptor descriptor) {
    super(mode.toString(), IAction.AS_CHECK_BOX);
    _addMode = mode;

    setImageDescriptor(descriptor);
    update();
  }

  @Override
  public void run() {
    ArtifactStage.instance().setAddMode(_addMode);
  }

  public void update() {
    setChecked(_addMode == ArtifactStage.instance().getAddMode());
  }

}