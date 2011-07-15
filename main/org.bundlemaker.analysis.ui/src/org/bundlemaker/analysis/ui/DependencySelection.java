package org.bundlemaker.analysis.ui;

import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.jface.viewers.ISelection;

/**
 * Represents selected dependency between two artefacts
 * 
 * @author Nils Hartmann
 * 
 */
public class DependencySelection implements ISelection {

  /**
   * The name of the from-artefact
   */
  private final String            _from;

  /**
   * The name of the to-artefact
   */
  private final String            _to;

  /**
   * the weight of this dependency
   */
  private final int               _weight;

  /**
   * The dependencies
   */
  private final List<IDependency> _dependencies;

  public DependencySelection(String from, String to, int weight, List<IDependency> dependencies) {
    super();
    _from = from;
    _to = to;
    _weight = weight;
    _dependencies = dependencies;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  public String getFrom() {
    return _from;
  }

  public String getTo() {
    return _to;
  }

  public int getWeight() {
    return _weight;
  }

  public List<IDependency> getDependencies() {
    return _dependencies;
  }

}
