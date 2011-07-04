package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.model.IDependencyModel;

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
