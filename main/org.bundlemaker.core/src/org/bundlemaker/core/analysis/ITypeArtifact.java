package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * Defines an {@link IArtifact IArtifacts} that holds an {@link IType} instance.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ITypeArtifact {

  /**
   * <p>
   * Returns the IType that is hold by this {@link ITypeArtifact}.
   * </p>
   * 
   * @return the IType that is hold by this {@link ITypeArtifact}.
   */
  IType getAssociatedType();
}
