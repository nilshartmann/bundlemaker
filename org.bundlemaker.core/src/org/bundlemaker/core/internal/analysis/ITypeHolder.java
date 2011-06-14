package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.resource.IType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

/**
 * <p>
 * Implemented by {@link IArtifact IArtifacts} that hold {@link IType} instances
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public interface ITypeHolder {
  
  /**
   * <p>Returns the IType that is hold by this ITypeHolder
   * </p>
   *
   * @return
   */
  IType getAssociatedType();

}
