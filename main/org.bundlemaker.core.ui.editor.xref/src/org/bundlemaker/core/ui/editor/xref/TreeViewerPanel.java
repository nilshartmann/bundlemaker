package org.bundlemaker.core.ui.editor.xref;

import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerSorter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TreeViewerPanel extends Composite {

  private Label      titleLabel;

  private TreeViewer treeViewer;

  public TreeViewerPanel(Composite parent, String title) {
    super(parent, SWT.NONE);
    setLayout(new GridLayout(1, true));
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    setLayoutData(layoutData);

    titleLabel = new Label(this, SWT.NONE);
    if (title != null) {
      titleLabel.setText(title);
    }
    treeViewer = new TreeViewer(this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    treeViewer.setContentProvider(new ArtifactTreeContentProvider(false));
    treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    treeViewer.setLabelProvider(new ArtifactTreeLabelProvider());
    treeViewer.setSorter(new ArtifactTreeViewerSorter());
  }

  public void setTitle(String title) {
    titleLabel.setText(title);
    layout();
  }

  public TreeViewer getTreeViewer() {
    return treeViewer;
  }
}
