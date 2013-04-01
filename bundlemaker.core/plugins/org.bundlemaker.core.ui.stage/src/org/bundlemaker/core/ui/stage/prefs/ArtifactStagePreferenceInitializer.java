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

package org.bundlemaker.core.ui.stage.prefs;

import org.bundlemaker.core.ui.stage.internal.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStagePreferenceInitializer extends AbstractPreferenceInitializer {

  /** - */
  public static final String PREF_SWITCH_TO_MANUAL_ADD_MODE = Activator.PLUGIN_ID + ".switch_to_manualaddmode";

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {
    Activator.getDefault().getPreferenceStore()
        .setDefault(PREF_SWITCH_TO_MANUAL_ADD_MODE, MessageDialogWithToggle.PROMPT);
  }

}
