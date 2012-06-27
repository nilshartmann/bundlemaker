package org.bundlemaker.core.analysis.cmd;

import java.util.List;

import org.bundlemaker.core.analysis.IResourceArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceArtifactSelector {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IResourceArtifact> getResourceArtifacts();
}
