package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Defines the root {@link IArtifact IArtifacts} that holds the {@link IModularizedSystem} instance. The root artifact
 * can contain group and modules ad therefore extends the interface {@link IGroupAndModuleContainer}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  void addArtifactModelChangedListener(IArtifactModelModifiedListener listener);

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  void removeArtifactModelChangedListener(IArtifactModelModifiedListener listener);
}
