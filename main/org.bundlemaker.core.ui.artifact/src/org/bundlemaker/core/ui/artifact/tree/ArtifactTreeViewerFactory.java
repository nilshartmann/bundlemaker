package org.bundlemaker.core.ui.artifact.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeViewerFactory {

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @return
   */
  public static TreeViewer createDefaultArtifactTreeViewer(Composite parent) {
    TreeViewer treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    treeViewer.setContentProvider(new ArtifactTreeContentProvider(true));
    treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    treeViewer.setSorter(new ArtifactTreeViewerSorter());
    treeViewer.setLabelProvider(new ArtifactTreeLabelProvider());
    return treeViewer;
  }
}
