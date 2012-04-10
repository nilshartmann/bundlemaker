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

import java.util.Collection;

import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.IDependencySelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractDependencySelectionAwareViewPart;
import org.bundlemaker.core.ui.utils.EditorHelper;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyTableView extends AbstractDependencySelectionAwareViewPart implements
    IDependencySelectionListener {

  public static String               ID                  = DependencyTableView.class.getName();

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

    _viewer = new TableViewer(tableComposite, SWT.VIRTUAL | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
        | SWT.FULL_SELECTION);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(new LazyDependencyProvider(_viewer));
    createColumns(tableComposite, _viewer);

    // open editor
    // TODO : DOUBLECLICK
    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        StructuredSelection structuredSelection = (StructuredSelection) event.getSelection();
        IDependency dependency = (IDependency) structuredSelection.getFirstElement();
        if (dependency != null) {
          IBundleMakerArtifact artifact = (IBundleMakerArtifact) dependency.getFrom();
          if (artifact != null) {
            try {
              EditorHelper.open(artifact);
            } catch (Exception e) {
              MessageDialog.openError(getSite().getShell(), "Error", e.getMessage());
            }
          }
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onDependencySelectionChanged(IDependencySelection selection) {

    // set current dependencies
    setCurrentDependencies(selection.getSelectedDependencies());

    if (getCurrentDependencies() == null || getCurrentDependencies().size() == 0) {
      setColumnTitles("From", "To");
      _viewer.setInput(new IDependency[0]);
      _viewer.getTable().redraw();
      return;
    } else {

      // TODO
      IBundleMakerArtifact bundleMakerArtifact = getCurrentDependencies().get(0).getFrom().getRoot();
      _fromLabelGenerator.setBaseArtifact(bundleMakerArtifact);
      _toLabelGenerator.setBaseArtifact(bundleMakerArtifact);
      //
      String fromColumnTitle = "From " + _fromLabelGenerator.getTitle();
      String toColumnTitle = "To " + _toLabelGenerator.getTitle();

      setColumnTitles(fromColumnTitle, toColumnTitle);

      IDependency[] dependencies = getCurrentDependencies().toArray(new IDependency[0]);
      _viewer.setInput(dependencies);
      _viewer.setItemCount(dependencies.length); // This is the difference when using a ILazyContentProvider
      _viewer.getTable().redraw();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSelectionId() {
    return Selection.DETAIL_DEPENDENCY_SELECTION_ID;
  }

  private void createColumns(Composite parent, TableViewer viewer) {

    createTableViewerColumn(parent, viewer, "From", 45, new DependencyColumnLabelProvider(_fromLabelGenerator) {
      @Override
      protected IBundleMakerArtifact getArtifactElement(IDependency element) {
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
      public IBundleMakerArtifact getArtifactElement(IDependency element) {
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

  private void setColumnTitles(String fromColumnTitle, String toColumnTitle) {
    Table table = _viewer.getTable();

    table.getColumn(0).setText(fromColumnTitle);
    table.getColumn(2).setText(toColumnTitle);
  }
}
