package org.bundlemaker.analysis.ui.view.table;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.CurrentContextInfos;
import org.bundlemaker.analysis.ui.DependencySelection;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.analysis.ui.view.table.actions.IgnoreDependencyContextMenuAction;
import org.bundlemaker.analysis.ui.view.table.actions.ShowUsageContextMenuAction;
import org.bundlemaker.analysis.ui.view.table.labelprovider.FromLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.IgnoreTaggedLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.ToLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.ViolationLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.WeightLabelProvider;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

/**
 * Diese Klasse realisiert eine View, die die Abhaengigkeiten des aktuellen Abhaengigkeitsgraphen darstellt.
 * 
 * @author Kai Lehmann
 * 
 */
public class DependencyTreeTableView extends ViewPart implements PropertyChangeListener {

  public static String                       ID = "org.bundlemaker.analysis.ui.view.table.DependencyTreeTableView";

  private TreeViewer                         treeViewer;

  private Tree                               tree;

  private DependencyTreeTableContentProvider dependencyContentProvider;

  private void copyToClipboard(String text) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(text);
    clipboard.setContents(stringSelection, null);
  }

  private void setupContextInfos(IDependency dependency) {
    IArtifact fromBundle = dependency.getFrom().getParent(ArtifactType.Module);
    IArtifact toBundle = dependency.getTo().getParent(ArtifactType.Module);

    CurrentContextInfos.clearContextDependencies();
    CurrentContextInfos.clearContextClassNames();
    CurrentContextInfos.clearContextBundleNames();
    CurrentContextInfos.addContextDependency(dependency);

    Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
    dependency.getLeafDependencies(leafDependencies);
    for (IDependency leafDependency : leafDependencies) {
      String fromClassName = leafDependency.getFrom().getQualifiedName();
      String toClassName = leafDependency.getTo().getQualifiedName();
      CurrentContextInfos.addContextClassName(fromClassName);
      CurrentContextInfos.addContextClassName(toClassName);
    }
    if (fromBundle != null) {
      CurrentContextInfos.addContextBundleName(fromBundle.getName());
    }
    if (toBundle != null) {
      CurrentContextInfos.addContextBundleName(toBundle.getName());
    }
  }

  @Override
  public void createPartControl(Composite parent) {

    treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
    dependencyContentProvider = new DependencyTreeTableContentProvider();
    treeViewer.setContentProvider(dependencyContentProvider);

    Menu menu = new Menu(parent);
    addContextMenuAction(menu, new IgnoreDependencyContextMenuAction());
    addContextMenuAction(menu, new ShowUsageContextMenuAction(true));
    addContextMenuAction(menu, new ShowUsageContextMenuAction(false));
    treeViewer.getTree().setMenu(menu);

    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
        TreeSelection treeSelection = (TreeSelection) selectionChangedEvent.getSelection();
        IDependency dependency = (IDependency) treeSelection.getFirstElement();

        if (dependency != null) {
          String fromClassName = dependency.getFrom().getQualifiedName();
          String toClassName = dependency.getTo().getQualifiedName();
          JavaEditor.openTypeInEditor(fromClassName, toClassName);
          StringBuilder info = new StringBuilder(300);
          info.append('"');
          info.append(fromClassName);
          info.append("\" => \"");
          info.append(toClassName);
          info.append('"');
          copyToClipboard(info.toString());
          setupContextInfos(dependency);
        }
      }
    });

    tree = treeViewer.getTree();
    tree.setHeaderVisible(true);
    tree.setLinesVisible(true);
    TreeViewerColumn fromColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    fromColumn.getColumn().setText("From");
    fromColumn.getColumn().setWidth(260);
    fromColumn.setLabelProvider(new FromLabelProvider());
    fromColumn.getColumn().addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("From selected");
        dependencyContentProvider.sort("From");
        treeViewer.refresh();
      }
    });
    TreeViewerColumn toColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    toColumn.getColumn().setText("To");
    toColumn.getColumn().setWidth(250);
    toColumn.setLabelProvider(new ToLabelProvider());
    toColumn.getColumn().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("To selected");
        dependencyContentProvider.sort("To");
        treeViewer.refresh();
      }
    });

    TreeViewerColumn weightColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    weightColumn.getColumn().setText("Weight");
    weightColumn.getColumn().setWidth(80);
    weightColumn.setLabelProvider(new WeightLabelProvider());
    weightColumn.getColumn().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Weight selected");
        dependencyContentProvider.sort("Weight");
        treeViewer.refresh();
      }
    });

    TreeViewerColumn violationColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    violationColumn.getColumn().setText("Violation");
    violationColumn.getColumn().setWidth(90);
    violationColumn.setLabelProvider(new ViolationLabelProvider());
    violationColumn.getColumn().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Violation selected");
        dependencyContentProvider.sort("Violations");
        treeViewer.refresh();
      }
    });

    TreeViewerColumn ignoreTaggedColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    ignoreTaggedColumn.getColumn().setText("Ignore");
    ignoreTaggedColumn.getColumn().setWidth(80);
    ignoreTaggedColumn.setLabelProvider(new IgnoreTaggedLabelProvider());
    ignoreTaggedColumn.getColumn().addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Ignore selected");
        dependencyContentProvider.sort("Ignore");
        treeViewer.refresh();
      }
    });

    ColumnViewerToolTipSupport.enableFor(treeViewer);
    getAnalysisContext().addPropertyChangeListener(this);
    DependencyGraph dependencyGraph = getAnalysisContext().getDependencyGraph();
    if (!dependencyGraph.getArtifacts().isEmpty()) {
      List<IDependency> dependencies = new ArrayList<IDependency>(dependencyGraph.getDependencies());
      this.changeTable(dependencies);
    }

    getSite().setSelectionProvider(treeViewer);
    getSite().getPage().addSelectionListener(new ISelectionListener() {

      @Override
      public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (selection instanceof DependencySelection) {
          DependencySelection dependencySelection = (DependencySelection) selection;
          changeTable(dependencySelection.getDependencies(), "From: " + dependencySelection.getFrom(), "To: "
              + dependencySelection.getTo(), "Weight: " + dependencySelection.getWeight());
        }

      }
    });

  }

  private IAnalysisContext getAnalysisContext() {
    return Analysis.instance().getContext();
  }

  private void addContextMenuAction(Menu contextMenu, final DependencyTreeTableContextMenuAction action) {

    // Create a new menu item for the action
    MenuItem menuItem = new MenuItem(contextMenu, SWT.PUSH);
    menuItem.setText(action.getText());

    // Register listener that will execute the action upon selection of the menu
    menuItem.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent selectionEvent) {
        // Get current context
        IAnalysisContext context = getAnalysisContext();

        // Get selection
        TreeItem[] selectedItems = tree.getSelection();
        try {

          // Inform the registered listeners about action execution
          executeBeforeActionListeners(action.getId());

          // Execute the action
          action.execute(context, selectedItems);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  @Override
  public void setFocus() {

  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (IAnalysisContext.GRAPH_CHANGED_PROPERTY_NAME.equals(evt.getPropertyName())) {
    DependencyGraph graph = (DependencyGraph) evt.getNewValue();
    final List<IDependency> dependencies = new ArrayList<IDependency>(graph.getDependencies());
      getSite().getShell().getDisplay().syncExec(new Runnable() {

          @Override
          public void run() {
          // set new table contents
            changeTable(dependencies);
          }
        });
      }
    }

  /**
   * Set the dependencies that are displayed in the table
   * 
   * <p>
   * Before the new depenencies are shown a {@link DependencyTreeTableContentChangeEvent} is fired
   * 
   * @param dependencies
   *          the new dependencies
   * @param columnNames
   *          the names of the columns
   */
  public void changeTable(List<IDependency> dependencies, String... columnNames) {

    // Fire event
    DependencyTreeTableContentChangeEvent e = new DependencyTreeTableContentChangeEvent(dependencies);
    fireContentChangeEvent(e);

    // Use dependencies from event as clients of the Event are allowed to change
    // the dependencies
    dependencies = e.getDependencies();

    if (columnNames.length != 0) {
      // Set custom table headers
      for (int i = 0; i < columnNames.length; i++) {
        treeViewer.getTree().getColumn(i).setText(columnNames[i]);
      }
    } else {

      // Set default table headers
      treeViewer.getTree().getColumn(0).setText("From");
      treeViewer.getTree().getColumn(1).setText("To");
      treeViewer.getTree().getColumn(2).setText("Weight");
      treeViewer.getTree().getColumn(3).setText("Violations: " + getViolationCount(dependencies));
    }

    treeViewer.setInput(dependencies);
  }

  private int getViolationCount(List<IDependency> dependencies) {
    int violationCount = 0;
    // count violations
    for (IDependency dependency : dependencies) {
      violationCount += dependency.getViolationWeight();
    }

    // return the count
    return violationCount;
  }

  @Override
  public void dispose() {
    super.dispose();

    // remove listener
    getAnalysisContext().removePropertyChangeListener(this);

    // Dispose the tree
    tree.dispose();
  }

  /**
   * Invokes the {@link DependencyTreeTableListener#onContentChange(DependencyTreeTableContentChangeEvent)} for all
   * registered {@link DependencyTreeTableListener DependencyTreeTableListener}
   * 
   * @param event
   *          the event that should be propagated
   */
  private void fireContentChangeEvent(DependencyTreeTableContentChangeEvent event) {
    List<DependencyTreeTableListener> listeners = getDependencyTreeTableViewListeners();
    for (DependencyTreeTableListener listener : listeners) {
      listener.onContentChange(event);
    }
  }

  /**
   * Executes the {@link DependencyTreeTableListener#beforeActionExecution(String)}-Method for all registered
   * {@link DependencyTreeTableListener DependencyTreeTableListener}
   * 
   * @param actionId
   *          the id of the action that is going to be executed
   */
  private void executeBeforeActionListeners(String actionId) {
    List<DependencyTreeTableListener> listeners = getDependencyTreeTableViewListeners();
    for (DependencyTreeTableListener listener : listeners) {
      listener.beforeActionExecution(actionId);
    }
  }

  private List<DependencyTreeTableListener> getDependencyTreeTableViewListeners() {
    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = extensionRegistry
        .getExtensionPoint("org.bundlemaker.analysis.ui.dependencyTreeTableListener");

    List<DependencyTreeTableListener> result = new LinkedList<DependencyTreeTableListener>();

    for (IExtension extension : extensionPoint.getExtensions()) {
      for (IConfigurationElement element : extension.getConfigurationElements()) {
        try {
          DependencyTreeTableListener listener = (DependencyTreeTableListener) element
              .createExecutableExtension("class");
          result.add(listener);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
}

    return result;
  }

}
