package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * Defines an {@link IArtifact IArtifacts} that represented an {@link IType} instance.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ITypeArtifact extends IBundleMakerArtifact, IMovableUnit {

  /**
   * <p>
   * Returns the {@link IType} that is represented by this {@link ITypeArtifact}.
   * </p>
   * 
   * @return the {@link IType} that is represented by this {@link ITypeArtifact}.
   */
  IType getAssociatedType();
}
