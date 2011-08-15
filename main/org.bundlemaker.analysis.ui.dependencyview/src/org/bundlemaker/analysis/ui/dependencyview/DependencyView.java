/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.ui.dependencyview;

import java.util.Collection;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.DefaultArtifactLabelProvider;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyView extends ViewPart implements IDependencySelectionListener {

  private TableViewer _viewer;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {

    FillLayout fillLayout = new FillLayout();
    fillLayout.type = SWT.VERTICAL;

    parent.setLayout(fillLayout);

    Analysis.instance().getDependencySelectionService().addDependencySelectionListener(this);

    _viewer = new TableViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(ArrayContentProvider.getInstance());
    _viewer.setLabelProvider(new DependencyTableLabelProvider());
    createColumns(parent, _viewer);

  }

  private void createColumns(Composite parent, TableViewer viewer) {

    createTableViewerColumn(viewer, "From", 120, new DelegatingDependencyColumnLabelProvider() {

      @Override
      public Object getArtifactElement(IDependency element) {
        return element.getFrom();
      }
    });
    createTableViewerColumn(viewer, "Usage", 30, new ColumnLabelProvider() {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
       */
      @Override
      public String getText(Object element) {
        if (element instanceof IDependency) {
          IDependency dependency = (IDependency) element;
          return String.valueOf(dependency.getDependencyKind());
        }
        return super.getText(element);
      }

    });
    createTableViewerColumn(viewer, "To", 120, new DelegatingDependencyColumnLabelProvider() {

      @Override
      public Object getArtifactElement(IDependency element) {
        return element.getTo();
      }
    });

  }

  private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int width,
      CellLabelProvider labelProvider) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setWidth(width);
    column.setResizable(true);
    column.setMoveable(true);

    if (labelProvider != null) {
      viewerColumn.setLabelProvider(labelProvider);
    }
    return viewerColumn;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    _viewer.getControl().setFocus();

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.selection.IDependencySelectionListener#dependencySelectionChanged(org.bundlemaker.analysis
   * .ui.selection.IDependencySelectionChangedEvent)
   */
  @Override
  public void dependencySelectionChanged(IDependencySelectionChangedEvent event) {

    IDependency dependency = event.getSelection().getFirstDependency();
    if (dependency == null) {
      return;
    }

    StringBuilder builder = new StringBuilder();
    dumpDependencies(builder, 0, dependency);

    String fromColumnTitle = _columnTitleLabelProvider.getText(dependency.getFrom());
    String toColumnTitle = _columnTitleLabelProvider.getText(dependency.getTo());

    setColumnTitles(fromColumnTitle, toColumnTitle);
    _viewer.setInput(dependency.getDependencies());

  }

  private void setColumnTitles(String fromColumnTitle, String toColumnTitle) {
    Table table = _viewer.getTable();

    table.getColumn(0).setText(fromColumnTitle);
    table.getColumn(2).setText(toColumnTitle);
  }

  private final DefaultArtifactLabelProvider _columnTitleLabelProvider = new DefaultArtifactLabelProvider();

  private void dumpDependencies(StringBuilder builder, int level, IDependency iDependency) {
    for (int i = 0; i < level; i++) {
      builder.append("  ");
    }
    builder.append(String.format("From: %s To: %s Type: %s%n", iDependency.getFrom().getQualifiedName(), iDependency
        .getTo().getQualifiedName(), iDependency.getDependencyKind()));
    Collection<IDependency> dependencies = iDependency.getDependencies();
    if (dependencies != null) {
      for (IDependency iDependency2 : dependencies) {
        dumpDependencies(builder, level + 1, iDependency2);
      }
    }
  }

  abstract class DelegatingDependencyColumnLabelProvider extends DelegatingArtifactColumnLabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.analysis.ui.dependencyview.DelegatingArtifactColumnLabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
      element = preprocessElement(element);
      if (element instanceof IArtifact) {
        String absoluteName = "";
        IArtifact artifact = (IArtifact) element;
        while (artifact != null && artifact.getType() != ArtifactType.Root) {
          absoluteName = artifact.getName() + "/" + absoluteName;
          artifact = artifact.getParent();
        }
        return absoluteName;
      }
      return super.getText(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bundlemaker.analysis.ui.dependencyview.DelegatingArtifactColumnLabelProvider#preprocessElement(java.lang.
     * Object)
     */
    @Override
    protected Object preprocessElement(Object element) {
      if (element instanceof IDependency) {
        return getArtifactElement((IDependency) element);
      }
      return super.preprocessElement(element);
    }

    /**
     * @param element
     * @return
     */
    public abstract Object getArtifactElement(IDependency element);

  }

}
