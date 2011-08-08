package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Defines an {@link IArtifact IArtifacts} that holds an {@link IModularizedSystem} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifact {

  /**
   * <p>
   * Adds the specified {@link IArtifactTreeChangedListener} to the listener list.
   * </p>
   * 
   * @param listener
   *          the {@link IArtifactTreeChangedListener}.
   */
  void addArtifactTreeChangedListener(IArtifactTreeChangedListener listener);

  /**
   * <p>
   * Removes the specified {@link IArtifactTreeChangedListener} from the listener list.
   * </p>
   * 
   * @param listener
   *          the {@link IArtifactTreeChangedListener}.
   */
  void removeArtifactTreeChangedListener(IArtifactTreeChangedListener listener);
}
