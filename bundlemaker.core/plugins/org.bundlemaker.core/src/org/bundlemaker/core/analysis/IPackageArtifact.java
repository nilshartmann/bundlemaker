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
   * Returns the complete (Java) package name. Regardless of the selected {@link AnalysisModelConfiguration} this method
   * always returns the "whole" qualified package name
   * 
   * @return the complete (Java) package, an empty String for the default package. Never null.
   */
  String getPackageName();

  /**
   * <p>
   * Returns the simple package name, e.g. {@code example}, never {@code null}.
   * </p>
   * 
   * @return the simple type name, e.g. {@code example}, never {@code null}.
   */
  String getName();

  /**
   * <p>
   * Returns the fully qualified package name, e.g. {@code com.example}, never {@code null}.
   * </p>
   * 
   * @return the fully qualified package name, e.g. {@code com.example}, never {@code null}.
   */
  String getQualifiedName();
}
