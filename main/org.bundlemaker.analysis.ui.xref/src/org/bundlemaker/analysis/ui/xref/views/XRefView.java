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
package org.bundlemaker.analysis.ui.xref.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * View for analysing IArtifact usedBy and uses dependencies. This view consists of three trees. In the middle tree the
 * subtree of the current artifact selection is displayed. Therefore this class registers an IArtifactSelectionListener
 * at the class<br>
 * 
 * <pre>
 * org.bundlemaker.analysis.ui.Analysis
 * </pre>
 * 
 * The left tree shows the artifacts which uses the artifacts in the middle. The right tree shows the artifacts which
 * are used by the middle. When the tree selection is changed the dependencies between the middle selection and the last
 * left or right selection is displayed in detail in the tree dependency view.
 * 
 * @see org.bundlemaker.analysis.ui.Analysis
 * @author Frank Schlueter
 */
public class XRefView extends DependencyPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String ID                   = "org.bundlemaker.analysis.ui.xref.views.BundlemakerXRefView";

  private TreeViewerPanel    leftTree;

  private TreeViewerPanel    middleTree;

  private TreeViewerPanel    rightTree;

  private IArtifact          rootArtifact;

  private List<IArtifact>    middleSelectedArtifacts;

  private List<IArtifact>    dependentSelectedArtifacts;

  private boolean            showUsedDependencies = true;

  /**
   * The constructor.
   */
  public XRefView() {
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    middleTree.getTreeViewer().getControl().setFocus();
  }

  @Override
  protected void doInit(Composite composite) {
    Composite panel = new Composite(composite, SWT.NONE);
    panel.setLayout(new GridLayout(3, true));

    leftTree = new TreeViewerPanel(panel, "Using");
    leftTree.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {

      @SuppressWarnings("unchecked")
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        TreeSelection treeSelection = (TreeSelection) event.getSelection();
        selectLeftTree(treeSelection.toList());
      }
    });

    middleTree = new TreeViewerPanel(panel, null);
    middleTree.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {

      @SuppressWarnings("unchecked")
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        TreeSelection treeSelection = (TreeSelection) event.getSelection();
        selectMiddleTree(treeSelection.toList());
      }
    });

    rightTree = new TreeViewerPanel(panel, "Used by");
    rightTree.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {

      @SuppressWarnings("unchecked")
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        TreeSelection treeSelection = (TreeSelection) event.getSelection();
        selectRightTree(treeSelection.toList());
      }
    });
  }

  private void selectLeftTree(List<IArtifact> selectedArtifacts) {
    dependentSelectedArtifacts = selectedArtifacts;
    showUsedDependencies = true;
    rightTree.getTreeViewer().getTree().deselectAll();
    showDependencyDetails(selectedArtifacts, middleSelectedArtifacts);
  }

  private int getTypeCount(List<IArtifact> artifacts) {
    Set<IArtifact> types = new HashSet<IArtifact>();
    for (IArtifact artifact : artifacts) {
      if (artifact.getType().isContainer()) {
        types.addAll(artifact.getLeafs());
      } else {
        types.add(artifact);
      }
    }
    return types.size();
  }

  private void selectMiddleTree(List<IArtifact> selectedArtifacts) {
    middleSelectedArtifacts = selectedArtifacts;
    List<IArtifact> dependentArtifacts = getDependencies(selectedArtifacts);
    List<IArtifact> usedByArtifacts = getUsedByArtifacts(selectedArtifacts);
    leftTree.setTitle("Used By: " + getTypeCount(usedByArtifacts));
    middleTree.setTitle("Artifacts: " + getTypeCount(selectedArtifacts));
    rightTree.setTitle("Using: " + getTypeCount(dependentArtifacts));
    leftTree.getTreeViewer().setFilters(new ViewerFilter[] { new DependentArtifactsFilter(usedByArtifacts) });
    rightTree.getTreeViewer().setFilters(new ViewerFilter[] { new DependentArtifactsFilter(dependentArtifacts) });
    if (showUsedDependencies) {
      showDependencyDetails(middleSelectedArtifacts, dependentSelectedArtifacts);
    } else {
      showDependencyDetails(dependentSelectedArtifacts, middleSelectedArtifacts);
    }
  }

  private void selectRightTree(List<IArtifact> selectedArtifacts) {
    showUsedDependencies = false;
    leftTree.getTreeViewer().getTree().deselectAll();
    showDependencyDetails(middleSelectedArtifacts, selectedArtifacts);
  }

  private void showDependencyDetails(List<IArtifact> fromArtifacts, List<IArtifact> toArtifacts) {
    List<IDependency> dependencies = new ArrayList<IDependency>();
    if ((fromArtifacts != null) && (toArtifacts != null)) {
      for (IArtifact artifact : fromArtifacts) {
        dependencies.addAll(artifact.getDependencies(toArtifacts));
      }
      Analysis.instance().getDependencySelectionService().setSelection(ID, dependencies);
      Analysis.instance().openDependencyTreeTableView();
    }
  }

  private List<IArtifact> getDependencies(List<IArtifact> selectedArtifacts) {
    List<IArtifact> dependentArtifacts = new ArrayList<IArtifact>();
    for (IArtifact artifact : selectedArtifacts) {
      for (IDependency dependency : artifact.getDependencies()) {
        dependentArtifacts.add(dependency.getTo());
      }
    }
    return dependentArtifacts;
  }

  private List<IArtifact> getUsedByArtifacts(List<IArtifact> selectedArtifacts) {
    Collection<IDependency> usedByDependencies = rootArtifact.getDependencies(selectedArtifacts);
    List<IArtifact> dependentArtifacts = new ArrayList<IArtifact>();
    for (IDependency dependency : usedByDependencies) {
      Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
      dependency.getLeafDependencies(leafDependencies);
      for (IDependency leafDependency : leafDependencies) {
        dependentArtifacts.add(leafDependency.getFrom());
      }
    }
    return dependentArtifacts;
  }

  @Override
  protected void doDispose() {
    leftTree.getTreeViewer().getTree().dispose();
    middleTree.getTreeViewer().getTree().dispose();
    rightTree.getTreeViewer().getTree().dispose();
  }

  @Override
  protected void useArtifacts(List<IArtifact> artifacts) {
    middleSelectedArtifacts = null;
    dependentSelectedArtifacts = null;
    middleTree.getTreeViewer().setInput(artifacts);
      rootArtifact = artifacts.get(0);
      if (rootArtifact.getType() != ArtifactType.Root) {
        rootArtifact = rootArtifact.getParent(ArtifactType.Root);
      }
    leftTree.getTreeViewer().setInput(rootArtifact);
    rightTree.getTreeViewer().setInput(rootArtifact);
    selectMiddleTree(artifacts);
  }
}