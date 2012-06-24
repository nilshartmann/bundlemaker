package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

/**
 * <p>
 * The {@link ArtifactTreeDropAdapter} is used for programmatically created artifact trees.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeDropAdapter extends ViewerDropAdapter {

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeDropAdapter}.
   * </p>
   * 
   * @param viewer
   */
  public ArtifactTreeDropAdapter(TreeViewer viewer) {
    super(viewer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drop(DropTargetEvent event) {

    //
    ISelection selection = (ISelection) LocalSelectionTransfer.getTransfer().nativeToJava(event.currentDataType);

    if (!(selection instanceof IStructuredSelection)) {
      return;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;

    IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) determineTarget(event);

    // TODO: Identify action

    IRootArtifact root = null;
    for (Object selectedObject : structuredSelection.toArray()) {

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
    ISelection selection = (ISelection) LocalSelectionTransfer.getTransfer().nativeToJava(transferData);

    if (!(selection instanceof IStructuredSelection)) {
      return false;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;

    //
    if (structuredSelection.getFirstElement() instanceof IBundleMakerArtifact
        && target instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();
      IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) target;

      if (targetArtifact.canAdd(sourceArtifact) && sourceArtifact.isMovable()) {
        return true;
      }
    }

    //
    return true;
  }

  @Override
  public boolean performDrop(Object data) {

    return false;
  }
}
