/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.history.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart;
import org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.AddArtifactsTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.CreateGroupTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.CreateModuleTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.RenameModuleTransformationLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HistoryView extends AbstractArtifactSelectionAwareViewPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String           ID                      = "org.bundlemaker.core.ui.transformations.history.view.HistoryView";

  private TreeViewer                   _viewer;

  private DrillDownAdapter             drillDownAdapter;

  private Action                       action1;

  private Action                       action2;

  private Action                       doubleClickAction;

  /**
   * Comparator to make sure ordering in the tree (top-level elements) always remain the same
   */
  private final RootArtifactComparator _rootArtifactComparator = new RootArtifactComparator();

  /**
   * The constructor.
   */
  public HistoryView() {
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(Composite parent) {
    _viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    drillDownAdapter = new DrillDownAdapter(_viewer);
    _viewer.setContentProvider(new HistoryViewContentProvider());

    _viewer.setContentProvider(new HistoryViewContentProvider());
    createTreeColumns();
    // viewer.setSorter(new NameSorter());
    // _viewer.setInput(getViewSite());
    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();
  }

  protected List<ITransformationLabelProvider> getRegisteredTransformationLabelProvider() {

    // TODO use extension points
    List<ITransformationLabelProvider> registeredTransformationLabelProviders = new LinkedList<ITransformationLabelProvider>();

    registeredTransformationLabelProviders.add(new CreateGroupTransformationLabelProvider());
    registeredTransformationLabelProviders.add(new AddArtifactsTransformationLabelProvider());
    registeredTransformationLabelProviders.add(new CreateModuleTransformationLabelProvider());
    registeredTransformationLabelProviders.add(new RenameModuleTransformationLabelProvider());

    return registeredTransformationLabelProviders;
  }

  private void createTreeColumns() {
    List<ITransformationLabelProvider> registeredTransformationLabelProvider = getRegisteredTransformationLabelProvider();

    Tree tree = _viewer.getTree();
    TreeColumnLayout layout = new TreeColumnLayout();
    TreeViewerColumn column = new TreeViewerColumn(_viewer, SWT.NONE);
    column.setLabelProvider(new HistoryViewTitleColumnLabelProvider(registeredTransformationLabelProvider));

    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(false);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(20));

    column = new TreeViewerColumn(_viewer, SWT.NONE);
    column.setLabelProvider(new HistoryViewDetailsColumnLabelProvider(registeredTransformationLabelProvider));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(false);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(80));

    tree.getParent().setLayout(layout);
    tree.setLinesVisible(true);
    tree.layout(true);

  }

  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager("#PopupMenu");
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager manager) {
        HistoryView.this.fillContextMenu(manager);
      }
    });
    Menu menu = menuMgr.createContextMenu(_viewer.getControl());
    _viewer.getControl().setMenu(menu);
    getSite().registerContextMenu(menuMgr, _viewer);
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown(bars.getMenuManager());
    fillLocalToolBar(bars.getToolBarManager());
  }

  private void fillLocalPullDown(IMenuManager manager) {
    manager.add(action1);
    manager.add(new Separator());
    manager.add(action2);
  }

  private void fillContextMenu(IMenuManager manager) {
    manager.add(action1);
    manager.add(action2);
    manager.add(new Separator());
    drillDownAdapter.addNavigationActions(manager);
    // Other plug-ins can contribute there actions here
    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void fillLocalToolBar(IToolBarManager manager) {
    manager.add(action1);
    manager.add(action2);
    manager.add(new Separator());
    drillDownAdapter.addNavigationActions(manager);
  }

  private void makeActions() {
    action1 = new Action() {
      @Override
      public void run() {
        showMessage("Action 1 executed");
      }
    };
    action1.setText("Action 1");
    action1.setToolTipText("Action 1 tooltip");
    action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
        .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

    action2 = new Action() {
      @Override
      public void run() {
        showMessage("Action 2 executed");
      }
    };
    action2.setText("Action 2");
    action2.setToolTipText("Action 2 tooltip");
    action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
        .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
    doubleClickAction = new Action() {
      @Override
      public void run() {
        ISelection selection = _viewer.getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        showMessage("Double-click detected on " + obj.toString());
      }
    };
  }

  private void hookDoubleClickAction() {
    _viewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {
        doubleClickAction.run();
      }
    });
  }

  private void showMessage(String message) {
    MessageDialog.openInformation(_viewer.getControl().getShell(), "Transformation History", message);
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    _viewer.getControl().setFocus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart#
   * setCurrentArtifactSelection(org.bundlemaker.core.ui.event.selection.IArtifactSelection)
   */
  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    List<IBundleMakerArtifact> selectedArtifacts = artifactSelection.getSelectedArtifacts();
    setContentFromArtifacts(selectedArtifacts);
  }

  public void setContentFromArtifacts(List<IBundleMakerArtifact> artifacts) {

    final List<IRootArtifact> newModularizedSystems = new LinkedList<IRootArtifact>();

    // Determine new list of modularized systems from selected IArtifacts
    for (IBundleMakerArtifact artifact : artifacts) {
      IRootArtifact root = artifact.getRoot();
      // IModularizedSystem modularizedSystem = root.getModularizedSystem();
      if (!newModularizedSystems.contains(root)) {
        newModularizedSystems.add(root);
      }
    }

    // Sort
    Collections.sort(newModularizedSystems, _rootArtifactComparator);

    @SuppressWarnings("unchecked")
    List<IRootArtifact> currentModularizedSystems = (List<IRootArtifact>) _viewer.getInput();
    if (!newModularizedSystems.equals(currentModularizedSystems)) {
      _viewer.setInput(newModularizedSystems);

      if (newModularizedSystems.size() == 1) {
        // if only one system is select expand by default
        _viewer.expandAll();
      }

    }

  }

  class RootArtifactComparator implements Comparator<IRootArtifact> {

    private final ModularizedSystemComparator _modularizedSystemComparator = new ModularizedSystemComparator();

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IRootArtifact mod1, IRootArtifact mod2) {

      return _modularizedSystemComparator.compare(mod1.getModularizedSystem(), mod2.getModularizedSystem());
    }
  }

  class ModularizedSystemComparator implements Comparator<IModularizedSystem> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IModularizedSystem mod1, IModularizedSystem mod2) {

      int result = mod1.getBundleMakerProject().getName().compareTo(mod2.getBundleMakerProject().getName());

      if (result == 0) {
        // projects are equal, compare modularized system name
        result = mod1.getName().compareTo(mod2.getName());
      }

      return 0;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart#getProviderId()
   */
  @Override
  protected String getProviderId() {
    return ID;
  }
}