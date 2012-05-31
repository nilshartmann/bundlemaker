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
package org.bundlemaker.core.ui.preferences;

import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerPreferences extends AbstractPreferenceInitializer {
  public static final String PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN = Activator.PLUGIN_ID
                                                                            + ".switch_to_perspective_on_open";

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {

    Activator.getDefault().getPreferenceStore().setDefault(PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN, "prompt");
  }

}
