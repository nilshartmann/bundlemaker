package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class ArtifactTreeDropAdapterAssistant extends CommonDropAdapterAssistant {

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

  @Override
  public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {

    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(
        aDropAdapter.getCurrentTransfer());

    IArtifact sourceArtifact = (IArtifact) treeSelection.getFirstElement();
    IArtifact targetArtifact = (IArtifact) aTarget;

    targetArtifact.addArtifact(sourceArtifact);

    // TODO
    CommonNavigatorUtils.refresh(
        "org.eclipse.ui.navigator.ProjectExplorer",
        targetArtifact.getType().equals(ArtifactType.Root) ? targetArtifact : targetArtifact
            .getParent(ArtifactType.Root));

    return Status.OK_STATUS;
  }

  @Override
  public boolean isSupportedType(TransferData transferData) {
    System.out.println(transferData.getClass());
    return true;
  }
}
