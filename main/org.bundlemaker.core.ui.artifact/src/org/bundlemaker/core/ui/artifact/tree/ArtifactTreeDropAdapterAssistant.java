package org.bundlemaker.core.ui.artifact.tree;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
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
 * The {@link ArtifactTreeDropAdapter} is used as a drop adapter for CNF based artifact trees (e.g. the one in the
 * project explorer).
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

    if (treeSelection != null && treeSelection.getFirstElement() instanceof IBundleMakerArtifact
        && target instanceof IBundleMakerArtifact) {

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

    // TODO
    CommonNavigator commonNavigator = CommonNavigatorUtils
        .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");

    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(
        aDropAdapter.getCurrentTransfer());

    IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) aTarget;

    //
    List<IBundleMakerArtifact> artifacts = new LinkedList<IBundleMakerArtifact>();
    for (Object selectedObject : treeSelection.toArray()) {
      artifacts.add((IBundleMakerArtifact) selectedObject);
    }
    targetArtifact.addArtifacts(artifacts);

    //
    targetArtifact.getRoot().invalidateDependencyCache();

    CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
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
