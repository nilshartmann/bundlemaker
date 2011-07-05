package org.bundlemaker.analysis.ui.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.IAnalysisContext;
import org.eclipse.core.runtime.Assert;

public class AnalysisContext implements IAnalysisContext {

  private final PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

  private IDependencyModel            _dependencyModel;

  private DependencyGraph             _dependencyGraph       = new DependencyGraph(new HashSet<IArtifact>());

  @Override
  public IDependencyModel getDependencyModel() {
    return this._dependencyModel;
  }

  @Override
  public void setDependencyModel(IDependencyModel dependencyModel) {
    Object oldModel = _dependencyModel;
    _dependencyModel = dependencyModel;
    firePropertyChange("dependencyModel", oldModel, _dependencyModel);

  }

  @Override
  public DependencyGraph getDependencyGraph() {
    return _dependencyGraph;
  }

  @Override
  public void setDependencyGraph(DependencyGraph dependencyGraph) {
    Assert.isNotNull(dependencyGraph);
    Object oldGraph = _dependencyGraph;
    _dependencyGraph = dependencyGraph;
    firePropertyChange(GRAPH_CHANGED_PROPERTY_NAME, oldGraph, _dependencyGraph);
  }

  @Override
  public void dependencyModelChanged() {
    firePropertyChange("dependencyModel", null, _dependencyModel);
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    _propertyChangeSupport.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    _propertyChangeSupport.removePropertyChangeListener(listener);
  }

  private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    _propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }

}
