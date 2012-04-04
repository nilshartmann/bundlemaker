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
package org.bundlemaker.core.ui.editor.xref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IRootArtifactSelection;
import org.bundlemaker.core.ui.selection.Selection;
import org.bundlemaker.core.ui.selection.workbench.editor.AbstractRootArtifactSelectionAwareEditorPart;
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
 * 
 * @author Frank Schlueter
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XRefView extends AbstractRootArtifactSelectionAwareEditorPart {

  /** the ID of the view as specified by the extension */
  public static final String         XREF_ID              = XRefView.class.getName();

  private TreeViewerPanel            leftTree;

  private TreeViewerPanel            middleTree;

  private TreeViewerPanel            rightTree;

  private IBundleMakerArtifact       rootArtifact;

  private List<IBundleMakerArtifact> middleSelectedArtifacts;

  private List<IBundleMakerArtifact> dependentSelectedArtifacts;

  private boolean                    showUsedDependencies = true;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    System.out.println("createPartControl");

    Composite panel = new Composite(parent, SWT.NONE);
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

    initRootArtifactSelection();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void artifactModelChanged() {
    leftTree.getTreeViewer().refresh();
    middleTree.getTreeViewer().refresh();
    rightTree.getTreeViewer().refresh();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    middleTree.getTreeViewer().getControl().setFocus();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    leftTree.getTreeViewer().getTree().dispose();
    middleTree.getTreeViewer().getTree().dispose();
    rightTree.getTreeViewer().getTree().dispose();
    super.dispose();
  }

  private void selectLeftTree(List<IBundleMakerArtifact> selectedArtifacts) {
    dependentSelectedArtifacts = selectedArtifacts;
    showUsedDependencies = true;
    rightTree.getTreeViewer().getTree().deselectAll();
    showDependencyDetails(selectedArtifacts, middleSelectedArtifacts);
  }

  private int getTypeCount(List<IBundleMakerArtifact> artifacts) {
    Set<IBundleMakerArtifact> types = new HashSet<IBundleMakerArtifact>();
    for (IBundleMakerArtifact artifact : artifacts) {
      if (artifact.getType().isContainer()) {
        types.addAll(artifact.getLeafs());
      } else {
        types.add(artifact);
      }
    }
    return types.size();
  }

  private void selectMiddleTree(List<IBundleMakerArtifact> selectedArtifacts) {
    middleSelectedArtifacts = selectedArtifacts;
    List<IBundleMakerArtifact> dependentArtifacts = getDependencies(selectedArtifacts);
    List<IBundleMakerArtifact> usedByArtifacts = getUsedByArtifacts(selectedArtifacts);
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

  private void selectRightTree(List<IBundleMakerArtifact> selectedArtifacts) {
    showUsedDependencies = false;
    leftTree.getTreeViewer().getTree().deselectAll();
    showDependencyDetails(middleSelectedArtifacts, selectedArtifacts);
  }

  private void showDependencyDetails(List<IBundleMakerArtifact> fromArtifacts, List<IBundleMakerArtifact> toArtifacts) {
    List<IDependency> dependencies = new ArrayList<IDependency>();
    if ((fromArtifacts != null) && (toArtifacts != null)) {
      for (IBundleMakerArtifact artifact : fromArtifacts) {
        dependencies.addAll(artifact.getDependencies(toArtifacts));
      }
      Selection.instance().getDependencySelectionService()
          .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, XREF_ID, dependencies);
    }
  }

  private List<IBundleMakerArtifact> getDependencies(List<IBundleMakerArtifact> selectedArtifacts) {
    List<IBundleMakerArtifact> dependentArtifacts = new ArrayList<IBundleMakerArtifact>();
    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      for (IDependency dependency : artifact.getDependencies()) {
        dependentArtifacts.add(dependency.getTo());
      }
    }
    return dependentArtifacts;
  }

  private List<IBundleMakerArtifact> getUsedByArtifacts(List<IBundleMakerArtifact> selectedArtifacts) {
    Collection<? extends IDependency> usedByDependencies = rootArtifact.getDependencies(selectedArtifacts);
    List<IBundleMakerArtifact> dependentArtifacts = new ArrayList<IBundleMakerArtifact>();
    for (IDependency dependency : usedByDependencies) {
      Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
      dependency.getLeafDependencies(leafDependencies);
      for (IDependency leafDependency : leafDependencies) {
        dependentArtifacts.add(leafDependency.getFrom());
      }
    }
    return dependentArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onRootArtifactSelectionChanged(IRootArtifactSelection rootArtifactSelection) {
    System.out.println("onRootArtifactSelectionChanged");
    //
    if (middleTree == null || leftTree == null || rightTree == null) {
      return;
    }

    //
    middleSelectedArtifacts = null;
    dependentSelectedArtifacts = null;

    //
    if (rootArtifactSelection != null && rootArtifactSelection.hasSelectedRootArtifact()) {
      rootArtifact = rootArtifactSelection.getSelectedRootArtifact();
      if (middleTree != null) {
        middleTree.getTreeViewer().setInput(rootArtifact);
      }
      if (leftTree != null) {
        leftTree.getTreeViewer().setInput(rootArtifact);
      }
      if (rightTree != null) {
        rightTree.getTreeViewer().setInput(rootArtifact);
      }
    } else {
      middleTree.getTreeViewer().setInput(new Object());
      leftTree.getTreeViewer().setInput(new Object());
      rightTree.getTreeViewer().setInput(new Object());
    }
  }
}