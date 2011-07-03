package org.bundlemaker.analysis.ui.internal;

import org.bundlemaker.analysis.ui.IDependencyModelProvider;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;

public class DependencyModelProvider implements IDependencyModelProvider {

  private IDependencyModel _model;

  @Override
  public void setModel(IDependencyModel model) {
    _model = model;
  }

  @Override
  public IDependencyModel getModel() {
    return _model;
  }

}
