package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * Defines an {@link IArtifact} that holds an {@link IResource} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceArtifact {

  /**
   * <p>
   * Returns the associated resource.
   * </p>
   * 
   * @return the associated resource.
   */
  IResource getAssociatedResource();
}
