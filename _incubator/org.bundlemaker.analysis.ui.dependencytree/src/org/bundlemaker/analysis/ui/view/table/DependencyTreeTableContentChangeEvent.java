package org.bundlemaker.analysis.ui.view.table;

import java.util.List;

import org.bundlemaker.analysis.model.IDependency;

/**
 * An event that is fired <b>before</b> the {@link DependencyTreeTableView} displays its new content
 * 
 * @author Nils Hartmann
 * 
 */
public class DependencyTreeTableContentChangeEvent {

  /**
   * The dependencies that should be displayed
   */
  private List<IDependency> _dependencies;

  public DependencyTreeTableContentChangeEvent(List<IDependency> dependencies) {
    _dependencies = dependencies;
  }

  /**
   * @return the dependencies that should be displayed
   */
  public List<IDependency> getDependencies() {
    return _dependencies;
  }

  /**
   * Set the dependencies that should be displayed.
   * 
   * <p>
   * Clients can invoke this method to modify the list of dependencies before they are displayed
   */
  public void setDependencies(List<IDependency> dependencies) {
    _dependencies = dependencies;
  }
}
