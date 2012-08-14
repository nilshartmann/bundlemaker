package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.CommonNavigator;

/**
 * <p>
 * Handler that implements the "Move..." command in an artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MoveArtifactsHandler extends AbstractArtifactBasedHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // retrieve shell from event
    Shell shell = HandlerUtil.getActiveShell(event);

    // create the dialog
    SelectArtifactParentDialog dialog = new SelectArtifactParentDialog(shell, selectedArtifacts.get(0).getRoot(),
        selectedArtifacts);

    //
    if (dialog.open() == Window.OK) {

      IBundleMakerArtifact targetArtifact = dialog.getSelectedArtifact();

      // TODO
      CommonNavigator commonNavigator = CommonNavigatorUtils
          .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");

      targetArtifact.addArtifacts(selectedArtifacts);

      //
      targetArtifact.getRoot().invalidateDependencyCache();

      CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
      TreePath[] expanedTreePath = commonNavigator.getCommonViewer().getExpandedTreePaths();
      commonNavigator.getCommonViewer().refresh();
      commonNavigator.getCommonViewer().setExpandedTreePaths(expanedTreePath);
    }
  }
}