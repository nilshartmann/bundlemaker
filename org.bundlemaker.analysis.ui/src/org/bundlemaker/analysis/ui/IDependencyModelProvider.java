package org.bundlemaker.analysis.ui;

import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;

/**
 * Provides access to the one and only instance of the IDependencyModel in the workbench
 * 
 * 
 * TODO
 * 
 */
public interface IDependencyModelProvider {

  public void setModel(IDependencyModel model);

  public IDependencyModel getModel();

}
