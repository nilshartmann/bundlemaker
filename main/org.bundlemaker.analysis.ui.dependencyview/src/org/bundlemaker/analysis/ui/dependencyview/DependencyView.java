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
import org.bundlemaker.analysis.ui.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionListener;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
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

    Composite tableComposite = new Composite(parent, SWT.NONE);
    tableComposite.setLayout(new TableColumnLayout());

    _viewer = new TableViewer(tableComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(ArrayContentProvider.getInstance());
    _viewer.setLabelProvider(new DependencyTableLabelProvider());
    createColumns(tableComposite, _viewer);

  }

  private void createColumns(Composite parent, TableViewer viewer) {

    createTableViewerColumn(parent, viewer, "From", 45, new DependencyColumnLabelProvider() {

      @Override
      protected IArtifact getArtifactElement(IDependency element) {
        return element.getFrom();
      }

      @Override
      protected String getArtifactLabel(IArtifact artifact) {
        return ArtifactHelper.getArtifactPath(_currentDependency.getFrom(), artifact);
      }

    });
    createTableViewerColumn(parent, viewer, "Usage", 10, new ColumnLabelProvider() {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
       */
      @Override
      public String getText(Object element) {
        if (element instanceof IDependency) {
          IDependency dependency = (IDependency) element;
          return String.valueOf(dependency.getDependencyKind()).toLowerCase();
        }
        return super.getText(element);
      }

    });
    createTableViewerColumn(parent, viewer, "To", 45, new DependencyColumnLabelProvider() {
      @Override
      protected String getArtifactLabel(IArtifact artifact) {
        return ArtifactHelper.getArtifactPath(_currentDependency.getTo(), artifact);
      }

      @Override
      public IArtifact getArtifactElement(IDependency element) {
        return element.getTo();
      }
    });

  }

  private TableViewerColumn createTableViewerColumn(Composite tableComposite, TableViewer viewer, String title,
      int weight, CellLabelProvider labelProvider) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setResizable(true);
    column.setMoveable(false);

    TableColumnLayout tableLayout = (TableColumnLayout) tableComposite.getLayout();
    ColumnLayoutData columnLayoutData = new ColumnWeightData(weight);
    tableLayout.setColumnData(column, columnLayoutData);
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

  private IDependency _currentDependency;

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

    _currentDependency = dependency;

    StringBuilder builder = new StringBuilder();
    dumpDependencies(builder, 0, dependency);

    String fromColumnTitle = "From "
        + ArtifactHelper.getArtifactPath(dependency.getFrom().getParent(ArtifactType.Root), dependency.getFrom());
    String toColumnTitle = "To "
        + ArtifactHelper.getArtifactPath(dependency.getTo().getParent(ArtifactType.Root), dependency.getTo());

    setColumnTitles(fromColumnTitle, toColumnTitle);
    _viewer.setInput(dependency.getDependencies());
    _viewer.getTable().redraw();

  }

  private void setColumnTitles(String fromColumnTitle, String toColumnTitle) {
    Table table = _viewer.getTable();

    table.getColumn(0).setText(fromColumnTitle);
    table.getColumn(2).setText(toColumnTitle);
  }

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

}
