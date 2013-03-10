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

import org.bundlemaker.core.ui.event.selection.IArtifactSelection;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class StageSelection {

  public static final String STAGE_VIEW_SELECTION_PROVIDER_ID      = "org.bundlemaker.core.ui.stage.StageViewSelectionProvider";

  public static final String ANALYZE_COMMAND_SELECTION_PROVIDER_ID = "org.bundlemaker.core.ui.stage.AnalyzeCommandSelectionProvider";

  public static boolean isAnalyzeCommandSelectionProvider(IArtifactSelection selection) {
    if (selection == null) {
      return false;
    }

    return ANALYZE_COMMAND_SELECTION_PROVIDER_ID.equals(selection.getProviderId());
  }
}
