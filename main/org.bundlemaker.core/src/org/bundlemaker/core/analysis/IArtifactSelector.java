package org.bundlemaker.core.analysis;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactSelector {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts();
}
