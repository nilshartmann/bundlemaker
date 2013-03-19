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

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractArtifactSelectionAwareViewPart;
import org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.internal.TransformationHistoryImages;
import org.bundlemaker.core.ui.transformations.history.labelprovider.AddArtifactsTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.CreateGroupTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.CreateModuleTransformationLabelProvider;
import org.bundlemaker.core.ui.transformations.history.labelprovider.RenameModuleTransformationLabelProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;

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

  private Action                       _resetAction;

  private Action                       _pinSelectionAction;

  private Action                       _undoLastTransformationAction;

  protected boolean                    _selectionPinnned       = false;

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
    _viewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION);
    _viewer.setContentProvider(new HistoryViewContentProvider());
    createTreeColumns();
    createActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();
    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        HistoryView.this.refreshEnablement();
      }
    });

    refreshEnablement();
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
    column.getColumn().setText("Transformation");
    column.setLabelProvider(new HistoryViewTitleColumnLabelProvider(registeredTransformationLabelProvider));

    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(false);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(20));

    column = new TreeViewerColumn(_viewer, SWT.NONE);
    column.setLabelProvider(new HistoryViewDetailsColumnLabelProvider(registeredTransformationLabelProvider));
    column.getColumn().setText("Details");
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(false);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(80));

    tree.getParent().setLayout(layout);
    tree.setLinesVisible(true);
    tree.setHeaderVisible(true);
    tree.layout(true);

  }

  private void refreshEnablement() {
    IStructuredSelection structuredSelection = (IStructuredSelection) _viewer.getSelection();

    boolean hasSelection = !structuredSelection.isEmpty();

    if (hasSelection) {

      IModularizedSystem modularizedSystem = getSelectedModularizedSystem();

      // Actions are enabled if a transformation or an modularized system with at least one transformation is selected
      boolean actionsEnabled = (modularizedSystem != null && !modularizedSystem.getTransformations().isEmpty());

      _undoLastTransformationAction.setEnabled(actionsEnabled);
      _resetAction.setEnabled(actionsEnabled);

      //
      return;
    }

    // No selection.
    // Reset action is always disabled in this case
    _resetAction.setEnabled(false);

    // Undo action is enabled if there is exactly one Modularized System that has at least one transformation
    List<IRootArtifact> viewerContent = getViewerContent();

    if (viewerContent.size() != 1) {
      _undoLastTransformationAction.setEnabled(false);

      return;
    }

    _undoLastTransformationAction.setEnabled(!viewerContent.get(0).getModularizedSystem().getTransformations()
        .isEmpty());
  }

  /**
   * @return
   */
  private IModularizedSystem getSelectedModularizedSystem() {
    IStructuredSelection structuredSelection = (IStructuredSelection) _viewer.getSelection();
    if (structuredSelection.isEmpty()) {
      return null;
    }

    Object o = structuredSelection.getFirstElement();

    if (o instanceof IRootArtifact) {
      return ((IRootArtifact) o).getModularizedSystem();
    }

    if (o instanceof ITransformation) {
      return getModularizedSystem((ITransformation) o);
    }

    return null;

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
    // manager.add(action2);
  }

  private void fillContextMenu(IMenuManager manager) {
    manager.add(_resetAction);
    manager.add(_undoLastTransformationAction);
    manager.add(new Separator());
    // Other plug-ins can contribute there actions here
    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void fillLocalToolBar(IToolBarManager manager) {
    manager.add(_pinSelectionAction);
    manager.add(_undoLastTransformationAction);
    // manager.add(_resetAction);
    // manager.add(action2);
    // manager.add(new Separator());
  }

  private void createActions() {
    _resetAction = new Action() {
      @Override
      public void run() {
        resetHistory();
      }
    };
    _resetAction.setText("Reset to this Transformation");
    _resetAction.setToolTipText("Resets the History to this Transformation by undoing all later Transformations");
    _resetAction.setImageDescriptor(TransformationHistoryImages.RESET_ICON.getImageDescriptor());

    _undoLastTransformationAction = new Action() {
      @Override
      public void run() {
        undoLastTransformation();
      }
    };
    _undoLastTransformationAction.setText("Undo last Transformation");
    _undoLastTransformationAction.setToolTipText("Reverts the last executed Transformation on the selected System");
    _undoLastTransformationAction.setImageDescriptor(TransformationHistoryImages.UNDO_TRANSFORMATION
        .getImageDescriptor());

    _pinSelectionAction = new PinSelectionAction();
  }

  private void hookDoubleClickAction() {
    //
  }

  private void resetHistory() {
    IStructuredSelection selection = (IStructuredSelection) _viewer.getSelection();

    if (selection.isEmpty()) {
      return;
    }

    Object firstElement = selection.getFirstElement();

    // If no transformation is selected undo complete history
    final ITransformation transformation = (firstElement instanceof ITransformation) ? (ITransformation) firstElement
        : null;

    final IModularizedSystem modularizedSystem = (firstElement instanceof IRootArtifact) ? ((IRootArtifact) firstElement)
        .getModularizedSystem() : getModularizedSystem(transformation);

    ProgressMonitorDialog dialog = new ProgressMonitorDialog(getViewSite().getShell());
    try {
      dialog.run(true, true, new IRunnableWithProgress() {

        @Override
        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
          modularizedSystem.undoUntilTransformation(monitor, transformation);

        }
      });

      CommonNavigatorUtils.refresh(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    } catch (InvocationTargetException e) {
      // TODO
      e.printStackTrace();
    } catch (InterruptedException e) {
      // doesn't matter
    }

    refreshEnablement();

  }

  private void undoLastTransformation() {
    IStructuredSelection selection = (IStructuredSelection) _viewer.getSelection();

    IModularizedSystem modularizedSystem;

    if (selection.isEmpty()) {

      List<IRootArtifact> viewerContent = getViewerContent();
      if (viewerContent.size() < 1) {
        return;
      }
      IRootArtifact rootArtifact = viewerContent.get(0);
      modularizedSystem = rootArtifact.getModularizedSystem();

    } else {

      Object firstElement = selection.getFirstElement();
      modularizedSystem = (firstElement instanceof IRootArtifact) ? ((IRootArtifact) firstElement)
          .getModularizedSystem() : getModularizedSystem((ITransformation) firstElement);
    }
    modularizedSystem.undoLastTransformation();

    CommonNavigatorUtils.refresh(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    refreshEnablement();

  }

  /**
   * @param transformation
   * @return
   */
  private IModularizedSystem getModularizedSystem(ITransformation transformation) {

    List<IRootArtifact> systems = getViewerContent();

    for (IRootArtifact rootArtifact : systems) {
      IModularizedSystem modularizedSystem = rootArtifact.getModularizedSystem();
      if (modularizedSystem.getTransformations().contains(transformation)) {
        return modularizedSystem;
      }
    }

    throw new IllegalStateException("Transformation without Modularized System detected: " + transformation);
  }

  private void showMessage(String message) {
    MessageDialog.openInformation(_viewer.getControl().getShell(), "Transformation History", message);
  }

  private List<IRootArtifact> getViewerContent() {
    List<IRootArtifact> systems = (List<IRootArtifact>) _viewer.getInput();
    if (systems == null) {
      systems = Collections.emptyList();
    }
    return systems;
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

    if (isSelectionPinnned() && getCurrentArtifactSelection().hasSelectedArtifacts()) {
      // artifacts are already selected and selection is pinned: ignore new selection
      return;
    }

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

    refreshEnablement();

  }

  /**
   * @return the selectionPinnned
   */
  public boolean isSelectionPinnned() {
    return _selectionPinnned;
  }

  /**
   * @param selectionPinnned
   *          the selectionPinnned to set
   */
  public void setSelectionPinnned(boolean selectionPinnned) {
    _selectionPinnned = selectionPinnned;
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

  class PinSelectionAction extends Action {
    public PinSelectionAction() {
      super("Pin Selection", IAction.AS_CHECK_BOX);
      setToolTipText("Pin Selection");
      setImageDescriptor(TransformationHistoryImages.PIN_SELECTION.getImageDescriptor());
      update();
    }

    @Override
    public void run() {
      setSelectionPinnned(isChecked());
    }

    public void update() {
      setChecked(isSelectionPinnned());
    }

  }

}