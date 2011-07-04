package org.bundlemaker.analysis.ui;

import java.beans.PropertyChangeListener;

import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;

/**
 * Repraesentiert den aktuellen Zustand der Auswahl, Modelle etc in der Workbench
 * 
 */
public interface IAnalysisContext {

  /**
   * Name des Properties, unter dem Änderungen am DependencyGraph notifiziert werden.
   * 
   * <p>
   * Dieses Property wird auch verwendet, wenn der Graph insgesamt ausgetauscht wird (sich also nicht nur ein Property
   * des Graphen ändert)
   */
  public final static String GRAPH_CHANGED_PROPERTY_NAME = "dependencyGraph";

  public IDependencyModel getDependencyModel();

  public void setDependencyModel(IDependencyModel dependencyModel);

  public DependencyGraph getDependencyGraph();

  public void setDependencyGraph(DependencyGraph dependencyGraph);

  public void addPropertyChangeListener(PropertyChangeListener listener);

  void removePropertyChangeListener(PropertyChangeListener listener);

  /**
   * Notify listeners that the current {@link IDependencyModel} has been changed.
   * 
   * <p>
   * Note:
   * </p>
   * this method will be removed when IDependencyModel get support for listeners itself.
   */
  void dependencyModelChanged();

}
