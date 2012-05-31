package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class ArtifactTreeDropAdapter extends ViewerDropAdapter {

  /** - */
  private TreeViewer _treeViewer;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeDropAdapter}.
   * </p>
   * 
   * @param viewer
   */
  public ArtifactTreeDropAdapter(TreeViewer viewer) {
    super(viewer);

    //
    this._treeViewer = viewer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drop(DropTargetEvent event) {

    //
    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(
        event.currentDataType);

    IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) determineTarget(event);

    IRootArtifact root = null;
    for (Object selectedObject : treeSelection.toArray()) {

      //
      final IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) selectedObject;

      targetArtifact.addArtifact(sourceArtifact);

      //
      if (root == null) {
        root = targetArtifact.getRoot();
      }
    }

    //
    root.invalidateDependencyCache();
  }

  @Override
  public boolean validateDrop(Object target, int operation,
      TransferData transferData) {

    //
    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(transferData);

    //
    if (treeSelection.getFirstElement() instanceof IBundleMakerArtifact && target instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) treeSelection.getFirstElement();
      IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) target;

      if (targetArtifact.canAdd(sourceArtifact) && sourceArtifact.isMovable()) {
        return true;
      }
    }

    //
    return false;
  }

  @Override
  public boolean performDrop(Object data) {
    // TODO Auto-generated method stub
    return false;
  }
}
