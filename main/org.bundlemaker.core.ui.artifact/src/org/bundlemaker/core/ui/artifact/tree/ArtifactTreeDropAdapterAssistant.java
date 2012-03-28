package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.navigator.CommonNavigator;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeDropAdapterAssistant extends CommonDropAdapterAssistant {

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus validateDrop(Object target, int operation, TransferData transferData) {

    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(transferData);

    if (treeSelection.getFirstElement() instanceof IBundleMakerArtifact && target instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) treeSelection.getFirstElement();
      IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) target;

      if (targetArtifact.canAdd(sourceArtifact) && sourceArtifact.isMovable()) {
        return Status.OK_STATUS;
      }
    }

    return Status.CANCEL_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {

    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(
        aDropAdapter.getCurrentTransfer());

    IRootArtifact root = null;
    for (Object selectedObject : treeSelection.toArray()) {

      final IArtifact sourceArtifact = (IArtifact) selectedObject;
      IArtifact targetArtifact = (IArtifact) aTarget;
      targetArtifact.addArtifact(sourceArtifact);

      //
      if (root == null) {
        root = ((IBundleMakerArtifact) targetArtifact).getRoot();
      }
    }
    root.invalidateDependencyCache();

    // TODO
    CommonNavigator commonNavigator = CommonNavigatorUtils
        .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");

    TreePath[] expanedTreePath = commonNavigator.getCommonViewer().getExpandedTreePaths();
    commonNavigator.getCommonViewer().refresh();
    commonNavigator.getCommonViewer().setExpandedTreePaths(expanedTreePath);

    return Status.OK_STATUS;
  }

  @Override
  public boolean isSupportedType(TransferData transferData) {
    return true;
  }
}
