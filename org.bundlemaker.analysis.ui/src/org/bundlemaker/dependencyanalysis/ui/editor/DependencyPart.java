package org.bundlemaker.dependencyanalysis.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * Abstrakte Oberklasse fuer alle Dependency Views, die den Abhaengigkeitsgrapehn darstellen.
 * 
 * <p>
 * Eine Dependency View muss von dieser Klasse erben und den Extension-Point dependency-view erweitern.
 * 
 * 
 * @author Kai Lehmann
 * 
 */
public abstract class DependencyPart implements PropertyChangeListener {

  private Composite composite;

  private TabFolder tabFolder;

  private TabItem   tabItem;

  public DependencyPart() {
  }

  public void setTabFolder(TabFolder tabFolder) {
    this.tabFolder = tabFolder;
  }

  public TabFolder getTabFolder() {
    return tabFolder;
  }

  public void selectViewTab() {
    tabFolder.setSelection(tabItem);
  }

  public void setTabItem(TabItem tabItem) {
    this.tabItem = tabItem;
  }

  public TabItem getTabItem() {
    return tabItem;
  }

  /**
   * <p>
   * Initialisiert die Dependency View.
   * 
   * <p>
   * Dies beinhaltet die Anmeldung als PropertyChangeListener am DependencyModeln sowie der Initialisierung der
   * graphischen Oberflaeche der konkreten Unterklasse
   * 
   * @param parent
   *          Das Composite, welcher die komponenten hinzugefuegt werden
   */
  public void init(Composite parent) {
    composite = new Composite(parent, SWT.None);
    composite.setLayout(new FillLayout());

    doInit(composite);

    getAnalysisContext().addPropertyChangeListener(this);
    DependencyGraph graph = getAnalysisContext().getDependencyGraph();
    if (!graph.getArtifacts().isEmpty()) {
      this.useDependencyGraph(graph);
    }
  }

  protected IAnalysisContext getAnalysisContext() {
    return Analysis.instance().getContext();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (IAnalysisContext.GRAPH_CHANGED_PROPERTY_NAME.equals(evt.getPropertyName())) {
      DependencyGraph graph = (DependencyGraph) evt.getNewValue();
      this.useDependencyGraph(graph);
    }
  }

  public Composite getComposite() {
    return composite;
  }

  public void dispose() {
    getAnalysisContext().removePropertyChangeListener(this);
    this.doDispose();
  }

  public void changeTable(List<IDependency> dependencies, String... columnNames) {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
    // DependencyTreeTableView treeView = (DependencyTreeTableView) workbenchPage.findView(DependencyTreeTableView.ID);
    // treeView.changeTable(dependencies, columnNames);
  }

  public void addDependencySelectionListener(ISelectionListener selectionListener) {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
    // workbenchPage.addSelectionListener(DependencyTreeTableView.ID, (ISelectionListener) this);
  }

  public void setFocus() {

  }

  /**
   * Explizite Initialisierung der konkreten Dependency View
   * 
   * @param composite
   */
  protected abstract void doInit(Composite composite);

  /**
   * Diese Methode wird immer dann aufgerufen, wenn sich der Abhaengigkeitsgraph geaendert hat. Die Aenderung erfolgt
   * durch das Dependency Model
   * 
   * @param graph
   *          Der aktuelle Abhaengigkeitsgraph
   */
  protected abstract void useDependencyGraph(DependencyGraph graph);

  /**
   * Konkrete Implentierung das Dispose, wenn das Fenster geschlossen wird
   */
  protected abstract void doDispose();

  /**
   * Might return null!
   * 
   * @return
   */
  protected IDependencyModel getDependencyModel() {
    return getAnalysisContext().getDependencyModel();
  }
}
