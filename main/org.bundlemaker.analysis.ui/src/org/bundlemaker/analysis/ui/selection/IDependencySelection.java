package org.bundlemaker.analysis.ui.selection;

import java.util.List;

import org.bundlemaker.analysis.model.IDependency;

/**
 * A selection of {@link IDependency} instances
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencySelection {

  /**
   * @return the selection provider's Id
   */
  public String getProviderId();

  /**
   * The selected artifacts. Never null but might be empty.
   * 
   * @return an <b>unmodifiable</b> list of {@link IDependency IDependency-Objects}
   */
  public List<IDependency> getSelectedDependencies();

}
