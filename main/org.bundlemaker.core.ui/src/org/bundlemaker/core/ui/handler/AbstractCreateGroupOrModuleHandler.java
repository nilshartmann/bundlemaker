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

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractCreateGroupOrModuleHandler extends AbstractBundleMakerHandler {

  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    // Extract selected artifact
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;

    if (structuredSelection.isEmpty()) {
      // nothing selected. Shouldn't happen anyway...
      return;
    }

    IBundleMakerArtifact artifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();

    // Retrieve shell from Event
    Shell shell = HandlerUtil.getActiveShell(event);

    // Make sure an IGroupAndModuleContainer is selected
    if (!(artifact instanceof IGroupAndModuleContainer)) {
      MessageDialog.openError(shell, "Unsupported artifact selected",
          "Please select another Group or the Root artifact to create a new artifact.");

      // cannot handle
      return;
    }

    IGroupAndModuleContainer groupAndModuleContainer = (IGroupAndModuleContainer) artifact;

    // Let subclasses create the actual artifact
    IBundleMakerArtifact newArtifact = createArtifact(shell, groupAndModuleContainer);

    if (newArtifact != null) {
      // update navigator
      CommonNavigatorUtils.refresh(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID,
          (artifact instanceof IRootArtifact) ? artifact : artifact.getParent(IRootArtifact.class));
    }
  }

  protected abstract IBundleMakerArtifact createArtifact(Shell shell, IGroupAndModuleContainer groupAndModuleContainer);

}
