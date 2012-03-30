package org.bundlemaker.core.ui.selection;

import org.bundlemaker.core.analysis.IRootArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifactSelection {

  /**
   * <p>
   * </p>
   * 
   * @return the selection provider's Id
   */
  public String getProviderId();

  /**
   * <p>
   * The selected {@link IRootArtifact}. Maybe null.
   * </p>
   * 
   * @return the selected {@link IRootArtifact}. Maybe null.
   */
  public IRootArtifact getSelectedRootArtifact();
  
  /**
   * <p>
   * </p>
   *
   * @return
   */
  public boolean hasSelectedRootArtifact();
}
