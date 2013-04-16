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

package org.bundlemaker.core.ui.operations;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonNavigator;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class MoveArtifactsOperation extends AbstractUiOperation {

  /**
   * @param shell
   */
  public MoveArtifactsOperation(Shell shell, List<IBundleMakerArtifact> artifacts) {
    super(shell, artifacts);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.operations.AbstractUiOperation#doRun()
   */
  @Override
  protected void doRun() {

    if (!hasArtifacts()) {
      return;
    }
    // create the dialog
    SelectArtifactParentDialog dialog = new SelectArtifactParentDialog(getShell(), getArtifacts().get(0).getRoot(),
        getArtifacts());

    //
    if (dialog.open() == Window.OK) {

      IBundleMakerArtifact targetArtifact = dialog.getSelectedArtifact();

      // TODO
      CommonNavigator commonNavigator = CommonNavigatorUtils
          .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");

      targetArtifact.addArtifacts(getArtifacts());

      CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
      TreePath[] expanedTreePath = commonNavigator.getCommonViewer().getExpandedTreePaths();
      commonNavigator.getCommonViewer().refresh();
      commonNavigator.getCommonViewer().setExpandedTreePaths(expanedTreePath);
    }
  }

}
