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
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class CreateModuleFromPackageSelectionHandler extends AbstractArtifactBasedHandler {

  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    if (selectedArtifacts.isEmpty()) {
      // nothing selected. Shouldn't happen anyway...
      return;
    }

    // todo make sure only packages, types or resources are selected

    IRootArtifact rootArtifact = null;

    String preselectedModuleName = null;

    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      if (rootArtifact == null) {
        rootArtifact = artifact.getRoot();
      }

        String packageName = (artifact instanceof IPackageArtifact) ? artifact.getQualifiedName() : artifact.getParent(
          IPackageArtifact.class).getQualifiedName();

        if (preselectedModuleName == null) {
          preselectedModuleName = packageName;
        } else {
          StringBuilder b = new StringBuilder();
          for (int i = 0, j = 0; i < preselectedModuleName.length() && j < packageName.length(); i++, j++) {
            if (preselectedModuleName.charAt(i) == packageName.charAt(j)) {
              b.append(preselectedModuleName.charAt(i));
          } else {
            break;
            }
          }
          preselectedModuleName = b.toString();
      }
    }

    if (preselectedModuleName.endsWith(".")) {
      if (preselectedModuleName.length() > 1) {
        preselectedModuleName = preselectedModuleName.substring(0, preselectedModuleName.length() - 1);
      } else
        preselectedModuleName = "";
    }

    if (preselectedModuleName.isEmpty()) {
      preselectedModuleName = "NewModule";
    }

    IBundleMakerArtifact artifact = selectedArtifacts.get(0);

    // Retrieve shell from Event
    Shell shell = HandlerUtil.getActiveShell(event);

    CreateModuleFromPackageSelectionDialog dialog = new CreateModuleFromPackageSelectionDialog(shell,
        artifact.getRoot(), preselectedModuleName, "1.0.0");
    if (dialog.open() == Window.OK) {

      IGroupAndModuleContainer groupAndModuleContainer = dialog.getParent();
      IModuleArtifact newModuleArtifact = groupAndModuleContainer.getOrCreateModule(dialog.getModuleName(),
          dialog.getModuleVersion());

      newModuleArtifact.addArtifacts(selectedArtifacts);

    }

  }

}
