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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.selection.IDependencySelection;
import org.bundlemaker.core.selection.IDependencySelectionListener;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.ErrorDialogUtil;
import org.bundlemaker.core.ui.artifact.tree.EditorHelper;
import org.bundlemaker.core.ui.event.selection.workbench.view.AbstractDependencySelectionAwareViewPart;
import org.bundlemaker.core.ui.operations.CreateModuleWithArtifactsOperation;
import org.bundlemaker.core.ui.view.dependencytable.internal.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTableView extends AbstractDependencySelectionAwareViewPart implements
    IDependencySelectionListener {

  /** - */
  public static String               ID                    = DependencyTableView.class.getName();

  public static String               PROVIDER_ID           = "org.bundlemaker.core.ui.view.dependencytable";

  private static final String        VIEW_SETTINGS_SECTION = "DependencyTableView";

  /** - */
  private TableViewer                _viewer;

  /** - */
  private ArtifactPathLabelGenerator _fromLabelGenerator   = new ArtifactPathLabelGenerator();

  /** - */
  private ArtifactPathLabelGenerator _toLabelGenerator     = new ArtifactPathLabelGenerator();

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
        | SWT.FULL_SELECTION | SWT.MULTI);
    final Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    _viewer.setContentProvider(new LazyDependencyProvider(_viewer));
    createColumns(tableComposite, _viewer);

    // open editor on double click
    _viewer.addDoubleClickListener(new IDoubleClickListener() {

      @Override
      public void doubleClick(DoubleClickEvent event) {
        openDependenciesInEditor();
      }
    });

    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

        //
        Collection<IDependency> dependencies = new LinkedList<IDependency>();

        //
        for (Iterator<?> iterator = structuredSelection.iterator(); iterator.hasNext();) {
          dependencies.add((IDependency) iterator.next());
        }

        //
        Selection.instance().getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, PROVIDER_ID, dependencies);
      }
    });

    loadViewSettings();

    // Popup menu
    MenuManager menuMgr = new MenuManager();
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager manager) {
        DependencyTableView.this.fillContextMenu(manager);
      }
    });
    Menu menu = menuMgr.createContextMenu(_viewer.getControl());
    _viewer.getControl().setMenu(menu);
//    getSite().registerContextMenu(menuMgr, _viewer);

    contributeToActionBars();

    // init the dependencies
    initDependencies();
  }

  /**
   * @param manager
   */
  protected void fillContextMenu(IMenuManager manager) {
    final List<IDependency> selectedDependencies = getSelectedDependencies();
    Action action = new Action("Open in Editor") {

      @Override
      public void run() {
        openDependenciesInEditor();
      }

    };
    action.setEnabled(selectedDependencies.isEmpty() == false);
    manager.add(action);

    action = new Action("Copy to Clipboard") {
      @Override
      public void run() {
        copyDependenciesToClipboard();
      }
    };
    action.setEnabled(selectedDependencies.isEmpty() == false);
    manager.add(action);
    
    action = new Action("Create Module from referenced Artifacts") {
      public void run() {
        List<IBundleMakerArtifact> artifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> currentSelectedDependencies = getSelectedDependencies();
        for (IDependency iDependency : currentSelectedDependencies) {
          IBundleMakerArtifact to = iDependency.getTo();
          if (to instanceof ITypeArtifact) {
            if (!artifacts.contains(to)) {
            artifacts.add(to);
            }
          }
        }
        createModuleFromSelectedTypes(artifacts);
        _viewer.refresh();
      }
    };
    manager.add(action);
    
    action = new Action("Create Module from referencing Artifacts") {
      public void run() {
        List<IBundleMakerArtifact> artifacts = new LinkedList<IBundleMakerArtifact>();
        List<IDependency> currentSelectedDependencies = getSelectedDependencies();
        for (IDependency iDependency : currentSelectedDependencies) {
          IBundleMakerArtifact from = iDependency.getFrom();
          if (from instanceof ITypeArtifact) {
            if (!artifacts.contains(from)) {
            artifacts.add(from);
            }
          }
        }
        createModuleFromSelectedTypes(artifacts);
        _viewer.refresh();
      }
    };
    manager.add(action);

  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown(bars.getMenuManager());
    // fillLocalToolBar(bars.getToolBarManager());
  }

  class UseShortLabelsAction extends Action {

    public UseShortLabelsAction() {
      super("Use Short Labels", IAction.AS_CHECK_BOX);
      setChecked(_fromLabelGenerator.isUseShortLabel());
    }

    @Override
    public void run() {
      _fromLabelGenerator.setUseShortLabel(isChecked());
      _toLabelGenerator.setUseShortLabel(isChecked());

      saveViewSettings();

      _viewer.refresh();
    }
  }

  private IDialogSettings getViewSettings() {
    IDialogSettings settings = Activator.getDefault().getDialogSettings();
    IDialogSettings section = settings.getSection(VIEW_SETTINGS_SECTION);
    if (section == null) {
      section = settings.addNewSection(VIEW_SETTINGS_SECTION);
    }
    return section;
  }

  private void saveViewSettings() {
    IDialogSettings dialogSettings = getViewSettings();

    dialogSettings.put("useShortLabel", _fromLabelGenerator.isUseShortLabel());
  }

  private void loadViewSettings() {
    IDialogSettings dialogSettings = getViewSettings();

    boolean useShortLabel = dialogSettings.getBoolean("useShortLabel");

    _fromLabelGenerator.setUseShortLabel(useShortLabel);
    _toLabelGenerator.setUseShortLabel(useShortLabel);
  }

  private void fillLocalPullDown(IMenuManager menuManager) {

    menuManager.add(new UseShortLabelsAction());

  }

  /**
   * Returns the dependencies that are currently selected inside the viewer. Returns an empty list if there are now
   * dependencies selected.
   * 
   * @return
   */
  public List<IDependency> getSelectedDependencies() {
    StructuredSelection structuredSelection = (StructuredSelection) _viewer.getSelection();
    List<IDependency> result = new LinkedList<IDependency>();

    Iterator<?> it = structuredSelection.iterator();
    while (it.hasNext()) {
      IDependency selectedDependency = (IDependency) it.next();
      result.add(selectedDependency);
    }

    return result;
  }

  /**
   * Open the selected dependency in the editor of the 'from' reference, marking the 'to' reference
   */
  protected void openDependenciesInEditor() {

    List<IDependency> selectedDependencies = getSelectedDependencies();

    for (IDependency dependency : selectedDependencies) {

      IBundleMakerArtifact artifact = (IBundleMakerArtifact) dependency.getFrom();
      if (artifact != null) {
        try {
          EditorHelper.openArtifactInEditor(artifact instanceof IResourceArtifact ? ((IResourceArtifact) artifact)
              : artifact.getParent(IResourceArtifact.class));
        } catch (Exception e) {
          MessageDialog.openError(getSite().getShell(), "Error", e.getMessage());
        }
      }
    }

  }

  /**
   * Copies the selected dependencies into the clipboard.
   * 
   * <p>
   * One line for each dependency with "from", "kind" and "to" in comma-separated columns
   * 
   */
  protected void copyDependenciesToClipboard() {
    List<IDependency> selectedDependencies = getSelectedDependencies();

    // Build the content to be copied
    StringBuilder builder = new StringBuilder();

    for (IDependency dependency : selectedDependencies) {
      String from = _fromLabelGenerator.getLabel(dependency.getFrom());
      String to = _toLabelGenerator.getLabel(dependency.getTo());
      builder
          .append(String.format("%s,%s,%s%n", from, String.valueOf(dependency.getDependencyKind()).toLowerCase(), to));
    }

    // copy to clipboard
    final Clipboard cb = new Clipboard(getSite().getShell().getDisplay());
    TextTransfer textTransfer = TextTransfer.getInstance();
    cb.setContents(new Object[] { builder.toString() }, new Transfer[] { textTransfer });
  }
  
  protected void createModuleFromSelectedTypes(List<IBundleMakerArtifact> types) {
    CreateModuleWithArtifactsOperation operation = new CreateModuleWithArtifactsOperation(getViewSite().getShell(), types);
    operation.run();
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
  protected void onSetDependencySelection(IDependencySelection selection) {

    // init dependencies
    initDependencies();
  }

  /**
   * <p>
   * </p>
   */
  private void initDependencies() {

    if (_viewer == null || _viewer.getTable().isDisposed()) {
      return;
    }

    if (getCurrentDependencySelection() == null || !getCurrentDependencySelection().hasDependencies()) {
      setColumnTitles("From", "To");
      _viewer.setInput(new IDependency[0]);
      _viewer.getTable().redraw();
      return;
    } else {

      // TODO
      IBundleMakerArtifact bundleMakerArtifact = getCurrentDependencySelection().getFirstDependency().getFrom()
          .getRoot();
      _fromLabelGenerator.setBaseArtifact(bundleMakerArtifact);
      _toLabelGenerator.setBaseArtifact(bundleMakerArtifact);
      //
      String fromColumnTitle = "From " + _fromLabelGenerator.getTitle();
      String toColumnTitle = "To " + _toLabelGenerator.getTitle();

      setColumnTitles(fromColumnTitle, toColumnTitle);

      List<IDependency> leafDependencies = AnalysisModelQueries.getCoreDependencies(getCurrentDependencySelection()
          .getSelectedDependencies());

      IDependency[] dependencies = leafDependencies.toArray(new IDependency[0]);
      _viewer.setInput(dependencies);
      _viewer.setItemCount(dependencies.length); // This is the difference when using a ILazyContentProvider
      _viewer.getTable().redraw();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void analysisModelModified() {
    // TODO Auto-generated method stub
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  protected String getSelectionId() {
    return Selection.MAIN_DEPENDENCY_SELECTION_ID;
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
