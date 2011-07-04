package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * Implemented by {@link IArtifact IArtifacts} that hold {@link IType} instances
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ITypeArtifact {

  /**
   * <p>
   * Returns the IType that is hold by this ITypeHolder
   * </p>
   * 
   * @return
   */
  IType getAssociatedType();
}
