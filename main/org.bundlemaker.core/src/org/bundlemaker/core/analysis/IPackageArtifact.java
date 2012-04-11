package org.bundlemaker.core.analysis;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPackageArtifact extends IBundleMakerArtifact {

  /**
   * <p>
   * Returns <code>true</code>, if this {@link IPackageArtifact} has direct children of type
   * {@link ArtifactType#Package}.
   * </p>
   * 
   * @return
   */
  boolean containsPackages();
}
