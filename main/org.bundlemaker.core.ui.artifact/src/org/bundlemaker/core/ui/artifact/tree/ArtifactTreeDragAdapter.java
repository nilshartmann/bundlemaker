package org.bundlemaker.core.ui.artifact.tree;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeDragAdapter implements DragSourceListener {

  /** - */
  private TreeViewer _treeViewer;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeDragAdapter}.
   * </p>
   * 
   * @param treeViewer
   */
  public ArtifactTreeDragAdapter(TreeViewer treeViewer) {
    Assert.isNotNull(treeViewer);

    //
    _treeViewer = treeViewer;
  }

  @Override
  public void dragStart(DragSourceEvent event) {
    // ...
  }

  @Override
  public void dragSetData(DragSourceEvent event) {
    LocalSelectionTransfer.getTransfer().setSelection(_treeViewer.getSelection());
  }

  @Override
  public void dragFinished(DragSourceEvent event) {
    // ...
  }
}
