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
package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider.VirtualRoot;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerSorter;
import org.bundlemaker.core.ui.event.selection.IDependencySelection;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractDependencySelectionAwareViewPart;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeView extends AbstractDependencySelectionAwareViewPart {

  public static final String                                    ID                 = DependencyTreeView.class.getName();

  /** - */
  private TreeViewer                                            _fromTreeViewer;

  /** - */
  private TreeViewer                                            _toTreeViewer;

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _targetArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _sourceArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  private List<IDependency>                                     _leafDependencies;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    //
    parent.setLayout(new GridLayout(2, true));

    //
    _fromTreeViewer = createTreeViewer(parent);

    //
    _toTreeViewer = createTreeViewer(parent);

    // add SelectionListeners
    _fromTreeViewer.addSelectionChangedListener(new FromArtifactsSelectionChangedListener());
    _toTreeViewer.addSelectionChangedListener(new ToArtifactSelectionChangedListener());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onDependencySelectionChanged(IDependencySelection selection) {

    // set the current dependencies
    setCurrentDependencies(selection.getSelectedDependencies());

    //
    _leafDependencies = Helper.getAllLeafDependencies(getCurrentDependencies());

    //
    _sourceArtifactMap.clear();
    _targetArtifactMap.clear();

    //
    for (IDependency dependency : _leafDependencies) {
      _sourceArtifactMap.getOrCreate(dependency.getFrom()).add(dependency);
      _targetArtifactMap.getOrCreate(dependency.getTo()).add(dependency);
    }

    // update 'from' and 'to' tree, no filtering
    setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
    setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());

    // auto expand
    for (IBundleMakerArtifact artifact : Helper.getChildrenOfCommonParent(_sourceArtifactMap.keySet())) {
      _fromTreeViewer.expandToLevel(artifact, 0);
    }
    for (IBundleMakerArtifact artifact : Helper.getChildrenOfCommonParent(_targetArtifactMap.keySet())) {
      _toTreeViewer.expandToLevel(artifact, 0);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param treeViewer
   * @param visibleArtifacts
   */
  private void setVisibleArtifacts(TreeViewer treeViewer, Collection<IBundleMakerArtifact> visibleArtifacts) {
    Assert.isNotNull(treeViewer);
    Assert.isNotNull(visibleArtifacts);

    if (visibleArtifacts.size() > 0) {

      // set the root if necessary
      IRootArtifact rootArtifact = visibleArtifacts.toArray(new IBundleMakerArtifact[0])[0].getRoot();
      if (!rootArtifact.equals(treeViewer.getInput())) {
        treeViewer.setInput(rootArtifact);
      }

      // set the filter
      treeViewer.setFilters(new ViewerFilter[] { new VisibleArtifactsFilter(visibleArtifacts) });
    }

    // set empty list
    else {
      treeViewer.setInput(Collections.emptyList());
    }
  }

  /**
   * <p>
   * Creates a new TreeViewer
   * </p>
   * 
   * @param parent
   * @return
   */
  private TreeViewer createTreeViewer(Composite parent) {
    TreeViewer treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    treeViewer.setContentProvider(new ArtifactTreeContentProvider(true));
    treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    treeViewer.setSorter(new ArtifactTreeViewerSorter());
    treeViewer.setLabelProvider(new ArtifactTreeLabelProvider());
    return treeViewer;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class FromArtifactsSelectionChangedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      // check for selected root element
      boolean containsRoot = false;
      for (Object object : structuredSelection.toList()) {
        if (object instanceof IRootArtifact || object instanceof VirtualRoot) {
          containsRoot = true;
          break;
        }
      }

      if (structuredSelection.size() > 0 && !containsRoot) {

        //
        List<IBundleMakerArtifact> visibleArtifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;
            for (IBundleMakerArtifact artifact : Helper.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_sourceArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _sourceArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getTo());
                }
              }
            }
          }
        }

        _toTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_toTreeViewer, visibleArtifacts);
        Selection.instance().getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, ID, selectedDetailDependencies);
      } else {
        setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());
        Selection.instance().getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, ID, _leafDependencies);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class ToArtifactSelectionChangedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      // check for selected root element
      boolean containsRoot = false;
      for (Object object : structuredSelection.toList()) {
        if (object instanceof IRootArtifact || object instanceof VirtualRoot) {
          containsRoot = true;
          break;
        }
      }

      if (structuredSelection.size() > 0 && !containsRoot) {

        //
        List<IBundleMakerArtifact> visibleArtifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;
            for (IBundleMakerArtifact artifact : Helper.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_targetArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _targetArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getFrom());
                }
              }
            }
          }
        }

        _fromTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_fromTreeViewer, visibleArtifacts);
        Selection.instance().getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, ID, selectedDetailDependencies);
      } else {
        setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
        Selection.instance().getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, ID, _leafDependencies);
      }
    }
  }
}
