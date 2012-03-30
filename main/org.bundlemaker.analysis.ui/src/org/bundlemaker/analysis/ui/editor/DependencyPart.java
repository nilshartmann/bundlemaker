package org.bundlemaker.analysis.ui.editor;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

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
public abstract class DependencyPart {

  private Composite                  composite;

  private ISelectionProvider         _selectionProvider;

  /**
   * The current artifacts (contents) of this dependency part
   */
  private List<IBundleMakerArtifact> _currentArtifacts;

  public DependencyPart() {
    _currentArtifacts = Collections.emptyList();
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

  /**
   * <p>
   * Initialisiert die Dependency View.
   * 
   * <p>
   * Dies beinhaltet die Anmeldung als PropertyChangeListener am DependencyModeln sowie der Initialisierung der
   * graphischen Oberflaeche der konkreten Unterklasse
   * 
   * <p>
   * Clients should implement {@link #doInit(Composite)} to create their widgets
   * 
   * @param parent
   *          Das Composite, welcher die komponenten hinzugefuegt werden
   */
  void init(Composite parent) {
    composite = new Composite(parent, SWT.None);
    composite.setLayout(new FillLayout());

    doInit(composite);

  }

  // protected IAnalysisContext getAnalysisContext() {
  // return Analysis.instance().getContext();
  // }

  // protected IDependencyGraph getDependencyGraphForCurrentArtifacts() {
  // IDependencyGraphService dependencyGraphService = Analysis.instance().getDependencyGraphService();
  //
  // return dependencyGraphService.getDependencyGraph(getCurrentArtifacts());
  //
  // }

  // /**
  // * Returns the calculated {@link IDependencyGraph} for the specified artifacts
  // * <p>
  // * </p>
  // *
  // * @param artifacts
  // * @return
  // */
  // protected IDependencyGraph getDependencyGraph(List<IArtifact> artifacts) {
  // IDependencyGraphService dependencyGraphService = Analysis.instance().getDependencyGraphService();
  //
  // return dependencyGraphService.getDependencyGraph(artifacts);
  // }

  public Composite getComposite() {
    return composite;
  }

  void dispose() {
    this.doDispose();
  }

  public void setFocus() {
    composite.setFocus();
  }

  // /**
  // * <p>
  // * Will be invoked when this DependencyPart is going to be opened
  // * </p>
  // *
  // * <p>
  // * Subclasses can implemented this method to update their displays
  // *
  // */
  // public void onShow() {
  // }

  /**
   * Explizite Initialisierung der konkreten Dependency View
   * 
   * @param composite
   */
  protected abstract void doInit(Composite composite);

  /**
   * This method is invoked to set the artifacts that should be visualized when this editor is visible
   * <p>
   * </p>
   * 
   * @param artifacts
   *          The new artifacts. Must not be null but might be empty
   */
  protected void useArtifacts(List<IBundleMakerArtifact> artifacts) {
    _currentArtifacts = artifacts;
  }

  /**
   * <p>
   * Returns the {@link IArtifact} instances that should be visualized
   * </p>
   * 
   * @return
   */
  public List<IBundleMakerArtifact> getCurrentArtifacts() {
    return _currentArtifacts;

  }

  /**
   * Konkrete Implentierung das Dispose, wenn das Fenster geschlossen wird
   */
  protected abstract void doDispose();

  // /**
  // * Might return null!
  // *
  // * @return
  // */
  // protected IDependencyModel getDependencyModel() {
  // return getAnalysisContext().getDependencyModel();
  // }

}
