package org.bundlemaker.analysis.ui.view.table;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.analysis.ui.view.table.labelprovider.FromLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.IgnoreTaggedLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.ToLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.ViolationLabelProvider;
import org.bundlemaker.analysis.ui.view.table.labelprovider.WeightLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;

/**
 * Diese Klasse realisiert eine View, die die Abhaengigkeiten des aktuellen Abhaengigkeitsgraphen darstellt.
 * 
 * @author Kai Lehmann
 * 
 */
public class DependencyTreeTableView extends ViewPart implements PropertyChangeListener {

  public static String                       ID = "org.bundlemaker.dependencyanalysis.ui.view.table.DependencyTreeTableView";

  private TreeViewer                         treeViewer;

  private Tree                               tree;

  private DependencyTreeTableContentProvider dependencyContentProvider;

  private void copyToClipboard(String text) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(text);
    clipboard.setContents(stringSelection, null);
  }

  @Override
  public void createPartControl(Composite parent) {

    treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
    dependencyContentProvider = new DependencyTreeTableContentProvider();
    treeViewer.setContentProvider(dependencyContentProvider);

    Menu menu = new Menu(parent);
    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
    menuItem.setText("Ignore");
    menuItem.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent selectionEvent) {
        System.out.println("Ignore selected");

        TreeItem[] selectedItems = tree.getSelection();
        for (TreeItem item : selectedItems) {

          IDependency dependency = (IDependency) item.getData();

          if (dependency != null) {
            dependency.setTaggedIgnore(true);
          }
        }

        Analysis.instance().getContext().getDependencyGraph().setInvalid(true);
      }

    });
    treeViewer.getTree().setMenu(menu);

    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
        TreeSelection treeSelection = (TreeSelection) selectionChangedEvent.getSelection();
        IDependency dependency = (IDependency) treeSelection.getFirstElement();
        TreeViewer treeView = (TreeViewer) selectionChangedEvent.getSource();

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

  }

  @Override
  public void setFocus() {

  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    DependencyGraph graph = (DependencyGraph) evt.getNewValue();
    final List<IDependency> dependencies = new ArrayList<IDependency>(graph.getDependencies());

    // Update table
    IWorkbenchPartSite site = getSite();
    if (site != null) {
      Shell shell = site.getShell();
      if (shell != null) {
        Display display = shell.getDisplay();
        display.syncExec(new Runnable() {

          @Override
          public void run() {
            changeTable(dependencies);
          }
        });
      }
    }

  }

  /**
   * Veraendert die Abhaengigkeiten, die in der Tabelle dargestellt werden
   * 
   * @param dependencies
   *          Die Abhaengigkeiten die dargestellt werdeen sollen
   * @param columnNames
   *          Die neuen ColumnNames
   */
  public void changeTable(List<IDependency> dependencies, String... columnNames) {
    int countViolations = 0;
    for (IDependency dependency : dependencies) {
      countViolations += dependency.getViolationWeight();
    }
    if (columnNames.length != 0) {
      for (int i = 0; i < columnNames.length; i++) {
        treeViewer.getTree().getColumn(i).setText(columnNames[i]);
      }
    } else {
      treeViewer.getTree().getColumn(0).setText("From");
      treeViewer.getTree().getColumn(1).setText("To");
      treeViewer.getTree().getColumn(2).setText("Weight");
      treeViewer.getTree().getColumn(3).setText("Violations: " + countViolations);
    }

    treeViewer.setInput(dependencies);
  }

  @Override
  public void dispose() {
    super.dispose();
    tree.dispose();

    getAnalysisContext().removePropertyChangeListener(this);

  }

  private IAnalysisContext getAnalysisContext() {
    return Analysis.instance().getContext();
  }

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
