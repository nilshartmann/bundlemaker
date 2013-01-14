package org.bundlemaker.core.analysis;

/**
 * <p>
 * Defines an {@link IBundleMakerArtifact} that represents a package.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPackageArtifact extends IBundleMakerArtifact {

  /**
   * Returns the complete (Java) package name. Regardless of the selected AnalysisModelConfiguration this method always
   * returns the "whole" qualified package name
   * 
   * @return the complete (Java) package, an empty String for the default package. Never null.
   */
  String getPackageName();

}
