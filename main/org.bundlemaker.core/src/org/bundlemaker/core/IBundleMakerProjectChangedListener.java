package org.bundlemaker.core;

/**
 * <p>
 * Listener to track changes of the project state <i>or</i> the project description.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerProjectChangedListener {

  /**
   * <p>
   * Called if the project state <i>or</i> the project description has changed.
   * </p>
   * 
   * @param event
   *          the {@link BundleMakerProjectChangedEvent}
   */
  void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event);
}
