package org.bundlemaker.core.analysis;

/**
 * <p>
 * Listener for {@link ArtifactTreeChangedEvent ArtifactTreeChangedEvents}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactTreeChangedListener {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  void artifactTreeChanged(ArtifactTreeChangedEvent event);
}
