package org.bundlemaker.core.analysis;

import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * Defines an {@link IBundleMakerArtifact} that holds an {@link IResource} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceArtifact extends IBundleMakerArtifact, IMovableUnit {

  /**
   * <p>
   * Returns the associated resource.
   * </p>
   * 
   * @return the associated resource.
   */
  IResource getAssociatedResource();

  /**
   * <p>
   * Returns the simple resource name, e.g. {@code MyClass.class}, never {@code null}.
   * </p>
   * 
   * @return the simple type name, e.g. {@code MyClass.class}, never {@code null}.
   */
  String getName();

  /**
   * <p>
   * Returns the fully qualified resource name, e.g. {@code com/example/MyClass.class}, never {@code null}.
   * </p>
   * 
   * @return the fully qualified resource name, e.g. {@code com/example/MyClass}, never {@code null}.
   */
  String getQualifiedName();
}
