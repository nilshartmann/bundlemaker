/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResetTransformationsHandler extends AbstractArtifactBasedHandler {

  /**
   */
  @Override
  protected void execute(ExecutionEvent event, final List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // //
    // final Shell shell = HandlerUtil.getActiveShell(event);
    //
    // // Run the script
    // ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
    // progressMonitorDialog.run(true, true, new IRunnableWithProgress() {
    //
    // @Override
    // public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

    // the root artifacts
    Set<IRootArtifact> rootArtifacts = new HashSet<IRootArtifact>();

    //
    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      rootArtifacts.add(artifact.getRoot());
    }

    //
    for (IRootArtifact rootArtifact : rootArtifacts) {
      rootArtifact.resetTransformations();
    }
    // }
    // });
  }
}
