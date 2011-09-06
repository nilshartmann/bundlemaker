package org.bundlemaker.analysis.ui.editor;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.bundlemaker.analysis.ui.dependencies.IDependencyGraphService;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.eclipse.jface.viewers.ISelectionProvider;
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
public abstract class DependencyPart implements IArtifactSelectionListener {

  private Composite composite;

  private TabFolder tabFolder;

  private TabItem   tabItem;

  private ISelectionProvider _selectionProvider;

  public DependencyPart() {
  }

  public ISelectionProvider getSelectionProvider() {
    return _selectionProvider;
  }

  /**
   * Sets the {@link ISelectionProvider} that can be used by this {@link DependencyPart} to propagate selection changes
   * 
   * <p>
   * This method will be invoked <b>before</b> {@link #init(Composite)} is invoked.
   * 
   * @param selectionProvider
   */
  public void setSelectionProvider(ISelectionProvider selectionProvider) {
    _selectionProvider = selectionProvider;
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

  @Override
  public void artifactSelectionChanged(IArtifactSelectionChangedEvent event) {
    List<IArtifact> selectedArtifacts = event.getSelection().getSelectedArtifacts();
    useArtifacts(selectedArtifacts);
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

    Analysis.instance().getArtifactSelectionService().addArtifactSelectionListener(this);

    // initialize view with current selection from Artifact tree
    IArtifactSelection currentArtifactSelection = getArtifactSelectionService().getSelection(
        Analysis.PROJECT_EXPLORER_ARTIFACT_SELECTION_PROVIDER_ID);

    if (currentArtifactSelection != null) {
      useArtifacts(currentArtifactSelection.getSelectedArtifacts());
    }
  }

  protected IAnalysisContext getAnalysisContext() {
    return Analysis.instance().getContext();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the IArtifactSelectionService
   */
  protected IArtifactSelectionService getArtifactSelectionService() {
    return Analysis.instance().getArtifactSelectionService();
    }

  /**
   * Returns the calculated {@link DependencyGraph} for the specified artifacts
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  protected DependencyGraph getDependencyGraph(List<IArtifact> artifacts) {
    IDependencyGraphService dependencyGraphService = Analysis.instance().getDependencyGraphService();

    return dependencyGraphService.getDependencyGraph(artifacts);
  }

  public Composite getComposite() {
    return composite;
  }

  public void dispose() {
    Analysis.instance().getArtifactSelectionService().removeArtifactSelectionListener(this);
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
    composite.setFocus();
  }

  /**
   * Explizite Initialisierung der konkreten Dependency View
   * 
   * @param composite
   */
  protected abstract void doInit(Composite composite);

  /**
   * This method is invoked when the artifacts that should be visualized change
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected abstract void useArtifacts(List<IArtifact> artifacts);

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
