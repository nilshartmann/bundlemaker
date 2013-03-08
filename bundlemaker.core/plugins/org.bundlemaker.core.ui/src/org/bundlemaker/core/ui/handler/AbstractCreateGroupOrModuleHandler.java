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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractCreateGroupOrModuleHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    if (selectedArtifacts.isEmpty()) {
      // nothing selected. Shouldn't happen anyway...
      return;
    }

    IBundleMakerArtifact artifact = selectedArtifacts.get(0);

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

    refreshProjectExplorer(newArtifact);
  }

  protected abstract IBundleMakerArtifact createArtifact(Shell shell, IGroupAndModuleContainer groupAndModuleContainer);

  /**
   * Returns a set of names of the artifacts that are already part of the given container
   * 
   * @param container
   * @return
   */
  public static Set<String> getExistingArtifactNames(IGroupAndModuleContainer container) {
    final Set<String> existingArtifactNames = new HashSet<String>();

    for (IBundleMakerArtifact iBundleMakerArtifact : container.getChildren()) {
      existingArtifactNames.add(iBundleMakerArtifact.getName());
    }

    return existingArtifactNames;
  }

  protected String getUniqueArtifactName(IGroupAndModuleContainer parentContainer, String prefix, String suffix) {

    if (suffix == null) {
      suffix = "";
    }

    Set<String> existingArtifacts = getExistingArtifactNames(parentContainer);

    String candidate = prefix + suffix;
    int ix = 0;
    while (existingArtifacts.contains(candidate)) {
      ix++;
      candidate = prefix + ix + suffix;
    }

    return candidate.substring(0, candidate.length() - suffix.length());

  }
}
