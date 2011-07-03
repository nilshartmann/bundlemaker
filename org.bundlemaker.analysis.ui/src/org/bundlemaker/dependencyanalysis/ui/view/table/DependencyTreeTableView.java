/*--- (C) 1999-2010 Techniker Krankenkasse ---*/

package org.bundlemaker.dependencyanalysis.ui.view.table;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.dependencyanalysis.CurrentContextInfos;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.bundlemaker.dependencyanalysis.rules.IRule;
import org.bundlemaker.dependencyanalysis.rules.impl.OrdinalRule;
import org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider.FromLabelProvider;
import org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider.IgnoreTaggedLabelProvider;
import org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider.ToLabelProvider;
import org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider.ViolationLabelProvider;
import org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider.WeightLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
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

  private IDependencyModel                   model;

  private IRule                              ordinalRule;

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

    model = Analysis.instance().getContext().getDependencyModel();
    ordinalRule = new OrdinalRule();

    treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
    dependencyContentProvider = new DependencyTreeTableContentProvider();
    treeViewer.setContentProvider(dependencyContentProvider);

    Menu menu = new Menu(parent);
    addIgnoreMenu(menu);
    addShowClassMenu(menu, true);
    addShowClassMenu(menu, false);
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
    fromColumn.getColumn().addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("From selected");
        dependencyContentProvider.sort("From");
        treeViewer.refresh();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    TreeViewerColumn toColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    toColumn.getColumn().setText("To");
    toColumn.getColumn().setWidth(250);
    toColumn.setLabelProvider(new ToLabelProvider());
    toColumn.getColumn().addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("To selected");
        dependencyContentProvider.sort("To");
        treeViewer.refresh();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });

    TreeViewerColumn weightColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    weightColumn.getColumn().setText("Weight");
    weightColumn.getColumn().setWidth(80);
    weightColumn.setLabelProvider(new WeightLabelProvider());
    weightColumn.getColumn().addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Weight selected");
        dependencyContentProvider.sort("Weight");
        treeViewer.refresh();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });

    TreeViewerColumn violationColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    violationColumn.getColumn().setText("Violation");
    violationColumn.getColumn().setWidth(90);
    violationColumn.setLabelProvider(new ViolationLabelProvider());
    violationColumn.getColumn().addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Violation selected");
        dependencyContentProvider.sort("Violations");
        treeViewer.refresh();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });

    TreeViewerColumn ignoreTaggedColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
    ignoreTaggedColumn.getColumn().setText("Ignore");
    ignoreTaggedColumn.getColumn().setWidth(80);
    ignoreTaggedColumn.setLabelProvider(new IgnoreTaggedLabelProvider());
    ignoreTaggedColumn.getColumn().addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        System.out.println("Ignore selected");
        dependencyContentProvider.sort("Ignore");
        treeViewer.refresh();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
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

  private IAnalysisContext getAnalysisContext() {
    return Analysis.instance().getContext();
  }

  private void addIgnoreMenu(Menu menu) {
    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
    menuItem.setText("Ignore");
    menuItem.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent selectionEvent) {
        System.out.println("Ignore selected");

        // Das DSL Model neu einlesen falls Aenderungen am Text gemacht werden,
        // ansonsten gehen die Aenderunegn verolren, da das aktuelle Modell im Speicher neu
        // zurueck geschrieben wird.
        // TODO ###REFACTORING
        // AssignClassVisitors.reload();

        TreeItem[] selectedItems = tree.getSelection();
        for (TreeItem item : selectedItems) {

          IDependency dependency = (IDependency) item.getData();

          if (dependency != null) {
            dependency.setTaggedIgnore(true);
          }
        }
        getAnalysisContext().getDependencyGraph().setInvalid(true);
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

  }

  private void addShowClassMenu(Menu menu, final boolean showFrom) {
    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
    menuItem.setText(showFrom ? "Show From Usage" : "Show To Usage");
    menuItem.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent selectionEvent) {
        TreeItem[] selectedItems = tree.getSelection();
        IDependency dependency = (IDependency) selectedItems[0].getData();
        IArtifact singleClassArtifact = showFrom ? dependency.getFrom() : dependency.getTo();
        Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
        IArtifact root = singleClassArtifact.getParent(ArtifactType.Root);
        if ((singleClassArtifact != null) && (root != null)) {
          artifacts.add(singleClassArtifact);
          artifacts.add(root);

          DependencyGraph newGraph = DependencyGraph.calculateDependencyGraph(artifacts);
          getAnalysisContext().setDependencyGraph(newGraph);
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

  }

  @Override
  public void setFocus() {

  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    DependencyGraph graph = (DependencyGraph) evt.getNewValue();
    List<IDependency> dependencies = new ArrayList<IDependency>(graph.getDependencies());

    this.changeTable(dependencies);

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
    boolean isGroupLevel = false;

    for (IDependency dependency : dependencies) {
      if ((dependency.getFrom().getType() == ArtifactType.Group)
          || (dependency.getTo().getType() == ArtifactType.Group)) {
        isGroupLevel = true;
      }
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

    if (isGroupLevel) {
      List<IDependency> transformedDependencies = getDependenciesWithBundleContext(dependencies);
      treeViewer.setInput(transformedDependencies);
    } else {
      treeViewer.setInput(dependencies);
    }
  }

  private List<IDependency> getDependenciesWithBundleContext(List<IDependency> dependencies) {
    List<IDependency> leafDependencies = new ArrayList<IDependency>();
    List<IDependency> transformedDependencies = new ArrayList<IDependency>();

    for (IDependency dependency : dependencies) {
      dependency.getLeafDependencies(leafDependencies);
    }
    Map<String, IDependency> bundleDependencies = new HashMap<String, IDependency>();
    for (IDependency leafDependency : leafDependencies) {
      IArtifact toBundle = leafDependency.getTo().getParent(ArtifactType.Module);
      IArtifact fromBundle = leafDependency.getFrom().getParent(ArtifactType.Module);
      String pathName = fromBundle.getQualifiedName() + toBundle.getQualifiedName();
      IDependency parentDependency = bundleDependencies.get(pathName);
      if (parentDependency == null) {
        // TODO ###REFACTORING
        throw new UnsupportedOperationException("TODO");
        // IArtifact fromArtifact = new ArtifactContainer( ArtifactType.Module, fromBundle.getQualifiedName());
        // IArtifact toArtifact = new ArtifactContainer( ArtifactType.Module, toBundle.getQualifiedName());
        // parentDependency = new Dependency(fromArtifact, toArtifact);
        // transformedDependencies.add(parentDependency);
        // bundleDependencies.put(pathName, parentDependency);
      }
      parentDependency.addDependency(leafDependency);
    }
    return transformedDependencies;
  }

  @Override
  public void dispose() {
    super.dispose();
    tree.dispose();
    // TODO ###REFACTORING

    getAnalysisContext().removePropertyChangeListener(this);
    // DependencyRoot.get().removePropertyChangeListener(this);

  }

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
