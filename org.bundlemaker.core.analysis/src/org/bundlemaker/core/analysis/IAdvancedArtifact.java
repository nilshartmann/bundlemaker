package org.bundlemaker.core.analysis;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAdvancedArtifact extends IArtifact {

  boolean canAdd(IArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @return
   */
  IArtifact getChildByName(String name);

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  IArtifact getChild(IPath path);
}
