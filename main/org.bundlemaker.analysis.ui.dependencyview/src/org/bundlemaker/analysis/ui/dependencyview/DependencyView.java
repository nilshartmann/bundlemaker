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
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyView extends ViewPart implements IDependencySelectionListener {

  private TableViewer _viewer;

  private IDependency _currentDependency;

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
    createColumns(tableComposite, _viewer);

    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        StructuredSelection structuredSelection = (StructuredSelection) event.getSelection();
        IDependency dependency = (IDependency) structuredSelection.getFirstElement();
        if (dependency != null) {
          IBundleMakerArtifact artifact = (IBundleMakerArtifact) dependency.getFrom();
          IResourceArtifact resourceArtifact = (IResourceArtifact) artifact.getParent(ArtifactType.Resource);
          IResource resource = resourceArtifact.getAssociatedResource();
          IMovableUnit movableUnit = MovableUnit.createFromResource(resource, resourceArtifact.getModularizedSystem());
          IResource sourceResource = movableUnit.hasAssociatedSourceResource() ? movableUnit
              .getAssociatedSourceResource() : resource;

          try {
            IBundleMakerProject bundleMakerProject = resourceArtifact.getRoot().getModularizedSystem()
                .getBundleMakerProject();
            IJavaProject javaProject = getAssociatedJavaProject(bundleMakerProject.getProject());
            IJavaElement javaElement = javaProject.findElement(new Path(sourceResource.getPath()));
            JavaUI.openInEditor(javaElement);
          } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (JavaModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

        // IWorkbenchPage page = getViewSite().getPage();
        // IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(""));
        // IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
        // page.openEditor(new FileEditorInput(file), desc.getId());

        // FileEditorInput input = new FileEditorInput(file)
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

    _currentDependency = dependency;

    _fromLabelGenerator.setBaseArtifact(_currentDependency.getFrom());
    _toLabelGenerator.setBaseArtifact(_currentDependency.getTo());

    StringBuilder builder = new StringBuilder();
    dumpDependencies(builder, 0, dependency);

    String fromColumnTitle = "From " + _fromLabelGenerator.getTitle();
    String toColumnTitle = "To " + _toLabelGenerator.getTitle();

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

  private ArtifactPathLabelGenerator _fromLabelGenerator = new ArtifactPathLabelGenerator();

  private ArtifactPathLabelGenerator _toLabelGenerator   = new ArtifactPathLabelGenerator();

  public static IJavaProject getAssociatedJavaProject(IProject bundleMakerProject) {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    IJavaProject javaProject = JavaCore.create(associatedProject);

    try {
      javaProject.open(null);
    } catch (JavaModelException e) {
      throw new RuntimeException(e.getMessage());
    }

    return javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  private static String getAssociatedJavaProjectName(IProject project) {
    return project.getName() + "$bundlemakerJdt";
  }

}
