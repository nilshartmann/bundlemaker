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
package org.bundlemaker.core.ui.view.dependencytable;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.selection.view.AbstractDependencySelectionAwareViewPart;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyTableView extends AbstractDependencySelectionAwareViewPart implements
    IDependencySelectionListener {

  /** - */
  private TableViewer                _viewer;

  private ArtifactPathLabelGenerator _fromLabelGenerator = new ArtifactPathLabelGenerator();

  private ArtifactPathLabelGenerator _toLabelGenerator   = new ArtifactPathLabelGenerator();

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    FillLayout fillLayout = new FillLayout();
    fillLayout.type = SWT.VERTICAL;

    parent.setLayout(fillLayout);

    Composite tableComposite = new Composite(parent, SWT.NONE);
    tableComposite.setLayout(new TableColumnLayout());

    _viewer = new TableViewer(tableComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(ArrayContentProvider.getInstance());
    createColumns(tableComposite, _viewer);

    // open editor
    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        StructuredSelection structuredSelection = (StructuredSelection) event.getSelection();
        IDependency dependency = (IDependency) structuredSelection.getFirstElement();
        if (dependency != null) {
          IBundleMakerArtifact artifact = (IBundleMakerArtifact) dependency.getFrom();
          EditorHelper.open(artifact);
        }
      }
    });
  }

  private void createColumns(Composite parent, TableViewer viewer) {

    createTableViewerColumn(parent, viewer, "From", 45, new DependencyColumnLabelProvider(_fromLabelGenerator) {
      @Override
      protected IArtifact getArtifactElement(IDependency element) {
        return element.getFrom();
      }
    });

    //
    createTableViewerColumn(parent, viewer, "Usage", 10, new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        if (element instanceof IDependency) {
          IDependency dependency = (IDependency) element;
          return String.valueOf(dependency.getDependencyKind()).toLowerCase();
        }
        return super.getText(element);
      }

    });
    createTableViewerColumn(parent, viewer, "To", 45, new DependencyColumnLabelProvider(_toLabelGenerator) {

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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDependencies() {

    if (getCurrentDependency() == null) {
      setColumnTitles("From", "To");
      _viewer.setInput(new Object());
      _viewer.getTable().redraw();
      return;
    } else {

      _fromLabelGenerator.setBaseArtifact(getCurrentDependency().getFrom());
      _toLabelGenerator.setBaseArtifact(getCurrentDependency().getTo());

      // StringBuilder builder = new StringBuilder();
      // dumpDependencies(builder, 0, dependency);

      String fromColumnTitle = "From " + _fromLabelGenerator.getTitle();
      String toColumnTitle = "To " + _toLabelGenerator.getTitle();

      setColumnTitles(fromColumnTitle, toColumnTitle);

      _viewer.setInput(getCurrentDependency().getDependencies());
      _viewer.getTable().redraw();
    }
  }

  private void setColumnTitles(String fromColumnTitle, String toColumnTitle) {
    Table table = _viewer.getTable();

    table.getColumn(0).setText(fromColumnTitle);
    table.getColumn(2).setText(toColumnTitle);
  }

  // private void dumpDependencies(StringBuilder builder, int level, IDependency iDependency) {
  // for (int i = 0; i < level; i++) {
  // builder.append("  ");
  // }
  // builder.append(String.format("From: %s To: %s Type: %s%n", iDependency.getFrom().getQualifiedName(), iDependency
  // .getTo().getQualifiedName(), iDependency.getDependencyKind()));
  // Collection<IDependency> dependencies = iDependency.getDependencies();
  // if (dependencies != null) {
  // for (IDependency iDependency2 : dependencies) {
  // dumpDependencies(builder, level + 1, iDependency2);
  // }
  // }
  // }

}
