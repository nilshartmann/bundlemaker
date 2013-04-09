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

package org.bundlemaker.core.ui.view;

import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.selection.stage.ArtifactStageAddMode;
import org.bundlemaker.core.selection.stage.IArtifactStage;
import org.bundlemaker.core.ui.view.stage.internal.Activator;
import org.bundlemaker.core.ui.view.stage.prefs.ArtifactStagePreferenceInitializer;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStageActionHelper {

  /**
   * 
   * @return true if Artifact Stage has been set to 'manual add mode' or false if it remains in auto add mode
   */
  public static boolean switchToManualAddModeIfRequired() {
    IArtifactStage artifactStage = Selection.instance().getArtifactStage();

    if (!artifactStage.getAddMode().isAutoAddMode()) {
      // already in manual mode
      return true;
    }

    String switchPerspective = Activator.getDefault().getPreferenceStore()
        .getString(ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE);
    if (MessageDialogWithToggle.ALWAYS.equals(switchPerspective)) {
      // turn into manual add mode
      artifactStage.setAddMode(ArtifactStageAddMode.doNotAutomaticallyAddArtifacts);
      return true;
    } else if (MessageDialogWithToggle.NEVER.equals(switchPerspective)) {
      return false;
    }

    Shell shell = Display.getCurrent().getActiveShell();

    MessageDialogWithToggle dialog = MessageDialogWithToggle
        .openYesNoQuestion(
            shell,
            "Switch to manual Add mode", //
            "The artifact stage currently is in 'automatic add mode', but this Action requires it to be in 'manual add mode'. Do you want the stage to be set to 'manual mode' now?",
            null, true, Activator.getDefault().getPreferenceStore(),
            ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE);
    boolean answer = (dialog.getReturnCode() == IDialogConstants.YES_ID);
    if (!answer) {
      return false;
    }

    artifactStage.setAddMode(ArtifactStageAddMode.doNotAutomaticallyAddArtifacts);

    return true;
  }

}
