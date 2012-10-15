/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewEditor extends AbstractArtifactSelectionAwareEditorPart implements IZoomableWorkbenchPart {

  public final static String              DEPENDENCY_VIEW_EDITOR_ID = DependencyViewEditor.class.getName();

  private GraphViewer                     _graphViewer;

  private DependencyViewerContentProvider _contentProvider;

  private DependencyViewerModel           _model;

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactModelModifiedListener#artifactModelModified()
   */
  @Override
  public void artifactModelModified() {
    setCurrentArtifactSelection(getCurrentArtifactSelection());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart#
   * setCurrentArtifactSelection(org.bundlemaker.core.ui.event.selection.IArtifactSelection)
   */
  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    //
    List<IBundleMakerArtifact> selectedArtifacts = artifactSelection.getSelectedArtifacts();
    _model.setArtifacts(selectedArtifacts);
    _graphViewer.setInput(selectedArtifacts);
    _graphViewer.refresh();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart#getProviderId()
   */
  @Override
  protected String getProviderId() {
    return DEPENDENCY_VIEW_EDITOR_ID;
  }

  private ToolBar _toolBar;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {

    _model = new DependencyViewerModel(getCurrentArtifactSelection().getSelectedArtifacts());

    //
    GridLayout gridLayout = new GridLayout(1, true);
    parent.setLayout(gridLayout);

    _toolBar = new ToolBar(parent, SWT.HORIZONTAL);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(_toolBar);

    _graphViewer = new GraphViewer(parent, SWT.BORDER);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(_graphViewer.getControl());

    _contentProvider = new DependencyViewerContentProvider(_model);
    _graphViewer.setContentProvider(_contentProvider);
    _graphViewer.setLabelProvider(new DependencyViewerLabelProvider(_model));
    _graphViewer.setInput(null);
    // _graphViewer.setFilters(new ViewerFilter[] { new SelectedArtifactViewerFilter() });
    LayoutAlgorithm layout = setLayout();
    _graphViewer.setLayoutAlgorithm(layout, true);
    _graphViewer.applyLayout();
    _graphViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
        Object o = structuredSelection.getFirstElement();
        if (o instanceof IDependency) {
          IDependency dependency = (IDependency) o;

          Selection.instance().getDependencySelectionService()
              .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DEPENDENCY_VIEW_EDITOR_ID, dependency);
        }
      }
    });

    fillToolBar();
  }

  private LayoutAlgorithm setLayout() {
    LayoutAlgorithm layout;
    layout = new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
    // layout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
    // layout = new
    // GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
    // layout = new
    // HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
    // layout = new
    // RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
    return layout;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }

  private void fillToolBar() {

    ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(this);
    toolbarZoomContributionViewItem.fill(_toolBar, 0);

  }

  class SelectedArtifactViewerFilter extends ViewerFilter {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
      if (element instanceof IBundleMakerArtifact) {
        IBundleMakerArtifact artifact = (IBundleMakerArtifact) (element);
        return getCurrentArtifactSelection().getSelectedArtifacts().contains(artifact);
      }

      return true;
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IZoomableWorkbenchPart#getZoomableViewer()
   */
  @Override
  public AbstractZoomableViewer getZoomableViewer() {
    return _graphViewer;
  }

}
