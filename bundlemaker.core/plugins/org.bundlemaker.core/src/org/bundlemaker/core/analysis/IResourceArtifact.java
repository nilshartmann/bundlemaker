package org.bundlemaker.core.analysis;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;

/**
 * <p>
 * Defines an {@link IBundleMakerArtifact} that holds an {@link IModuleResource} instance.
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
  IModuleResource getAssociatedResource();

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

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static interface IResourceArtifactContent extends IBundleMakerArtifact {

  }
}
