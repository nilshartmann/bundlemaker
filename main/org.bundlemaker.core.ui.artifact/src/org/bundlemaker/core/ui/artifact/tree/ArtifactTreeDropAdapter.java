package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
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
    int location = this.determineLocation(event);
    String target = (String) determineTarget(event);
    String translatedLocation = "";
    switch (location) {
    case 1:
      translatedLocation = "Dropped before the target ";
      break;
    case 2:
      translatedLocation = "Dropped after the target ";
      break;
    case 3:
      translatedLocation = "Dropped on the target ";
      break;
    case 4:
      translatedLocation = "Dropped into nothing ";
      break;
    }
    System.out.println(translatedLocation);
    System.out.println("The drop was done on the element: " + target);
    super.drop(event);
  }

  // This method performs the actual drop
  // We simply add the String we receive to the model and trigger a refresh of the
  // viewer by calling its setInput method.
  @Override
  public boolean performDrop(Object data) {
    System.out.println("Data: " + data);
    System.out.println("Data: " + data.getClass());
    // TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(
    // data.getCurrentTransfer());
    //
    // IRootArtifact root = null;
    // for (Object selectedObject : treeSelection.toArray()) {
    //
    // final IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) selectedObject;
    // IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) aTarget;
    // targetArtifact.addArtifact(sourceArtifact);
    //
    // //
    // if (root == null) {
    // root = targetArtifact.getRoot();
    // }
    // }
    // root.invalidateDependencyCache();
    return false;
  }

  @Override
  public boolean validateDrop(Object target, int operation,
      TransferData transferData) {

    TreeSelection treeSelection = (TreeSelection) LocalSelectionTransfer.getTransfer().nativeToJava(transferData);

    if (treeSelection.getFirstElement() instanceof IBundleMakerArtifact && target instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) treeSelection.getFirstElement();
      IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) target;

      if (targetArtifact.canAdd(sourceArtifact) && sourceArtifact.isMovable()) {
        return true;
      }
    }

    return false;
  }
}
