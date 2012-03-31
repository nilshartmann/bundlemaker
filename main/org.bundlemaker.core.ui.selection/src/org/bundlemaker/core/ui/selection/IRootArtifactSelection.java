package org.bundlemaker.core.ui.selection;

import org.bundlemaker.core.analysis.IRootArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifactSelection extends IProviderSelection {

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
