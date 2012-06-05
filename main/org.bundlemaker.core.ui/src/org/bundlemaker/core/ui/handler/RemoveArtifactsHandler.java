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
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class RemoveArtifactsHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    if (selectedArtifacts.isEmpty()) {
      return;
    }

    // make sure the user really wants to remove the selected artifacts
    String message = null;
    if (selectedArtifacts.size() > 1) {
      message = "Are you sure you want to remove these " + selectedArtifacts.size() + " artifacts?";
    } else {
      IBundleMakerArtifact artifact = selectedArtifacts.get(0);
      message = "Are you soure your want to remove " + artifact.getName() + "?";
    }

    message += " (cannot be undone!)";
    if (!MessageDialog.openConfirm(HandlerUtil.getActiveShell(event), "Confirm delete", message)) {
      return;
    }

    // remember the root artifact, needed for refreshing
    IBundleMakerArtifact rootArtifact = null;

    // remove the artifacts
    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      if (rootArtifact == null) {
        rootArtifact = artifact.getParent(IRootArtifact.class);
      }
      IBundleMakerArtifact parent = artifact.getParent();
      parent.removeArtifact(artifact);
    }

    // refresh project explorer
    refreshProjectExplorer(rootArtifact);

  }
}
