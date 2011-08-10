package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
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

    if (treeSelection.getFirstElement() instanceof IAdvancedArtifact && target instanceof IAdvancedArtifact) {

      IAdvancedArtifact sourceArtifact = (IAdvancedArtifact) treeSelection.getFirstElement();
      IAdvancedArtifact targetArtifact = (IAdvancedArtifact) target;

      if (targetArtifact.canAdd(sourceArtifact)) {
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

    final IArtifact sourceArtifact = (IArtifact) treeSelection.getFirstElement();
    IArtifact targetArtifact = (IArtifact) aTarget;

    targetArtifact.addArtifact(sourceArtifact);

    ArtifactUtils.dumpArtifact(((IAdvancedArtifact) sourceArtifact).getRoot());

    CommonNavigator commonNavigator = CommonNavigatorUtils
        .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");
    IRootArtifact root = ((IAdvancedArtifact) sourceArtifact).getRoot();
    commonNavigator.getCommonViewer().refresh(root, true);
    commonNavigator.getCommonViewer().refresh(targetArtifact, true);
    return Status.OK_STATUS;
  }

  @Override
  public boolean isSupportedType(TransferData transferData) {
    System.out.println(transferData.getClass());
    return true;
  }
}
