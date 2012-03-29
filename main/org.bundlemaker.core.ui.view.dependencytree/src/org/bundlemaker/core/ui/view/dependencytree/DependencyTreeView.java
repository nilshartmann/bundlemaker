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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerSorter;
import org.bundlemaker.core.ui.selection.view.AbstractDependencySelectionAwareViewPart;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeView extends AbstractDependencySelectionAwareViewPart {

  private TreeViewer                _fromTreeViewer;

  private TreeViewer                _toTreeViewer;

  private Set<IBundleMakerArtifact> _visibleFromArtifacts = new HashSet<IBundleMakerArtifact>();

  private Set<IBundleMakerArtifact> _fromArtifacts        = new HashSet<IBundleMakerArtifact>();

  private Set<IBundleMakerArtifact> _visibleToArtifacts   = new HashSet<IBundleMakerArtifact>();

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {

    parent.setLayout(new GridLayout(2, true));

    _fromTreeViewer = createTreeViewer(parent);
    _fromTreeViewer.setLabelProvider(new ArtifactTreeLabelProvider());

    _toTreeViewer = createTreeViewer(parent);
    // _toTreeViewer.setLabelProvider(new MyLabelProvider(_toArtifacts));
    _toTreeViewer.setLabelProvider(new ArtifactTreeLabelProvider());

    // add SelectionListener
    _fromTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        //
        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

        //
        _visibleToArtifacts.clear();

        //
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            Collection<IDependency> dependencies = ((IBundleMakerArtifact) selectedObject).getDependencies();
            for (IDependency iDependency : dependencies) {
              _visibleToArtifacts.add(((IBundleMakerArtifact) iDependency.getTo()));
              _visibleToArtifacts.add(((IBundleMakerArtifact) iDependency.getTo().getParent(ArtifactType.Resource)));
            }
          }
        }

        if (structuredSelection.getFirstElement() != null) {
          updateDependencies(false, true, false, true);
          _toTreeViewer.setSelection(new StructuredSelection());
        } else {
          updateDependencies(false, true, false, false);
        }
      }
    });

    // add SelectionListener
    _toTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        //
        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

        //
        _visibleFromArtifacts.clear();

        //
        Collection<IBundleMakerArtifact> selection = structuredSelection.toList();
        for (IBundleMakerArtifact artifact : _fromArtifacts) {

          Collection<? extends IDependency> deps = artifact.getDependencies(selection);

          if (!deps.isEmpty()) {
            _visibleFromArtifacts.add(((IBundleMakerArtifact) artifact));
            _visibleFromArtifacts.add(((IBundleMakerArtifact) artifact.getParent(ArtifactType.Resource)));
          }
        }

        if (structuredSelection.getFirstElement() != null) {
          updateDependencies(true, false, true, false);
          _fromTreeViewer.setSelection(new StructuredSelection());
        } else {
          updateDependencies(true, false, false, false);
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDependencies() {
    updateDependencies(true, true, false, false);
  }

  /**
   * {@inheritDoc}
   */
  protected void updateDependencies(boolean updateFromArtifacts, boolean updateToArtifacts, boolean filterFrom,
      boolean filterTo) {

    if (getCurrentDependency() == null) {
      if (updateFromArtifacts) {
        _fromTreeViewer.setInput(new Object());
      }
      if (updateToArtifacts) {
        _toTreeViewer.setInput(new Object());
      }
      return;
    }

    //
    List<IBundleMakerArtifact> fromArtifacts = null;
    if (updateFromArtifacts) {
      fromArtifacts = getFromArtifacts(getCurrentDependency(), filterFrom);
    }
    List<IBundleMakerArtifact> toArtifacts = null;
    if (updateToArtifacts) {
      toArtifacts = getToArtifacts(getCurrentDependency(), filterTo);
    }

    //
    if (updateFromArtifacts) {
      setTreeViewer(_fromTreeViewer, fromArtifacts);
    }
    if (updateToArtifacts) {
      setTreeViewer(_toTreeViewer, toArtifacts);
    }

    //
    if (updateFromArtifacts) {
      _fromTreeViewer.expandToLevel(getCurrentDependency().getFrom(), 1);
    }
    if (updateToArtifacts) {
      _toTreeViewer.expandToLevel(getCurrentDependency().getTo(), 1);
    }
  }

  private void setTreeViewer(TreeViewer treeViewer, List<IBundleMakerArtifact> artifacts) {
    if (artifacts.size() > 0) {
      if (!artifacts.get(0).getRoot().equals(treeViewer.getInput())) {
        treeViewer.setInput(artifacts.get(0).getRoot());
      }
      treeViewer.setFilters(new ViewerFilter[] { new DependentArtifactsFilter(artifacts) });
    } else {
      treeViewer.setInput(Collections.emptyList());
    }
  }

  private List<IBundleMakerArtifact> getFromArtifacts(IDependency dependency, boolean filter) {

    List<IBundleMakerArtifact> fromArtifacts = new ArrayList<IBundleMakerArtifact>();
    Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
    dependency.getLeafDependencies(leafDependencies);
    for (IDependency leafDependency : leafDependencies) {
      if (!filter || _visibleFromArtifacts.contains((IBundleMakerArtifact) leafDependency.getFrom())) {
        fromArtifacts.add((IBundleMakerArtifact) leafDependency.getFrom());
      }
      _fromArtifacts.add((IBundleMakerArtifact) leafDependency.getFrom());
    }

    return fromArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   * @return
   */
  private List<IBundleMakerArtifact> getToArtifacts(IDependency dependency, boolean filter) {

    List<IBundleMakerArtifact> toArtifacts = new ArrayList<IBundleMakerArtifact>();
    Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
    dependency.getLeafDependencies(leafDependencies);
    for (IDependency leafDependency : leafDependencies) {
      if (!filter || _visibleToArtifacts.contains((IBundleMakerArtifact) leafDependency.getTo())) {
        toArtifacts.add((IBundleMakerArtifact) leafDependency.getTo());
      }
    }

    return toArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @return
   */
  private TreeViewer createTreeViewer(Composite parent) {

    //
    TreeViewer treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    treeViewer.setContentProvider(new ArtifactTreeContentProvider());
    treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

    treeViewer.setSorter(new ArtifactTreeViewerSorter());

    //
    return treeViewer;
  }

  class MyLabelProvider extends StyledCellLabelProvider {

    final private Set<IBundleMakerArtifact> _bundleMakerArtifacts;

    private ArtifactTreeLabelProvider       _artifactTreeLabelProvider = new ArtifactTreeLabelProvider();

    public MyLabelProvider(Set<IBundleMakerArtifact> bundleMakerArtifacts) {
      super();
      _bundleMakerArtifacts = bundleMakerArtifacts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ViewerCell cell) {
      Object element = cell.getElement();
      StyledString text = new StyledString();
      text.append(_artifactTreeLabelProvider.getText(element));
      if (_bundleMakerArtifacts.contains(element)) {
        cell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
      } else {
        cell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
      }
      cell.setStyleRanges(text.getStyleRanges());
      cell.setImage(_artifactTreeLabelProvider.getImage(element));
      cell.setText(text.toString());
      cell.setStyleRanges(text.getStyleRanges());
      super.update(cell);
    }
  }
}
