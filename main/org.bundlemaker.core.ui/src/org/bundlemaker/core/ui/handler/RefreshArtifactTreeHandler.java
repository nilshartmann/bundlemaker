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
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.commands.ExecutionEvent;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class RefreshArtifactTreeHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
      refreshProjectExplorer(iBundleMakerArtifact);
    }

  }

}
