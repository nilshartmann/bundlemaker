package org.bundlemaker.analysis.ui.selection;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;

/**
 * A selection of {@link IArtifact} objects
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelection {

  /**
   * @return the selection provider's Id
   */
  public String getProviderId();

  /**
   * The selected artifacts. Never null but might be empty.
   * 
   * @return an <b>unmodifiable</b> list of {@link IArtifact IArtifacts}
   */
  public List<IArtifact> getSelectedArtifacts();

}
