package org.bundlemaker.core._type;

import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.CoreException;

public interface ITypeResource {

  /**
   * <p>
   * Returns all {@link IReference IReferences} that are originated in this resource.
   * </p>
   * <p>
   * <b>Note:</b> The result set does <b>not</b> contain any references of the contained types. If you want to access
   * these references as well, you have explicitly to request them from the contained types. The reason why there is no
   * method that aggregates these dependencies is that references contain information that are specific to the
   * originator (e.g. {@link IReference#isExtends()} or {@link IReference#isImplements()}).
   * 
   * </p>
   * 
   * @return all {@link IReference IReferences} that are originated in this resource.
   */
  Set<IReference> getReferences();

  /**
   * <p>
   * Returns all the contained types in this resource. If the resource does not contain any type, an empty list will be
   * returned instead.
   * </p>
   * 
   * @return all the contained types in this resource. If the resource does not contain any type, an empty list will be
   *         returned instead.
   */
  Set<IType> getContainedTypes();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IType getContainedType() throws CoreException;

  /**
   * <p>
   * Returns <code>true</code>, if the resource contains one or more {@link IType ITypes}.
   * </p>
   * 
   * @return <code>true</code>, if the resource contains one or more {@link IType ITypes}.
   */
  boolean containsTypes();

  /**
   * <p>
   * Returns the primary type of this resource (that is, the type with the same name as the source resource, or the type
   * of a class file), or <code>null<code> if no such a type exists.
   * </p>
   * 
   * @return
   */
  IType getPrimaryType();

  /**
   * <p>
   * Returns <code>true</code> if the given type is the primary type of this {@link IModuleResource}, <code>false</code>
   * otherwise.
   * </p>
   * 
   * @param type
   *          the type to test
   * @return <code>true</code> if the given type is the primary type of this {@link IModuleResource}, <code>false</code>
   *         otherwise.
   */
  boolean isPrimaryType(IType type);

  /**
   * <p>
   * Returns <code>true</code> if this {@link IModuleResource} has a primary type, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this {@link IModuleResource} has a primary type, <code>false</code> otherwise.
   */
  boolean hasPrimaryType();
}
