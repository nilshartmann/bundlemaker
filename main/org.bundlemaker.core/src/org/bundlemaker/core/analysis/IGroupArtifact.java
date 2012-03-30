package org.bundlemaker.core.analysis;

/**
 * <p>
 * Defines an {@link IArtifact} that represents a group.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IGroupArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  /**
   * <p>
   * Sets the name of this group artifact.
   * </p>
   * 
   * @param name
   */
  void setName(String name);
}
