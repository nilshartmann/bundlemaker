package org.bundlemaker.core.ui.artifact.tree;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * @see org.eclipse.debug.internal.ui.views.variables.SelectionDragAdapter
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
    ISelection selection = _treeViewer.getSelection();
    LocalSelectionTransfer.getTransfer().setSelection(selection);
    LocalSelectionTransfer.getTransfer().setSelectionSetTime(event.time & 0xFFFFFFFFL);
    event.doit = !selection.isEmpty();
  }

  @Override
  public void dragSetData(DragSourceEvent event) {

    // For consistency set the data to the selection even though
    // the selection is provided by the LocalSelectionTransfer
    // to the drop target adapter.
    event.data = LocalSelectionTransfer.getTransfer().getSelection();
  }

  @Override
  public void dragFinished(DragSourceEvent event) {

    LocalSelectionTransfer.getTransfer().setSelection(null);
    LocalSelectionTransfer.getTransfer().setSelectionSetTime(0);
  }
}
