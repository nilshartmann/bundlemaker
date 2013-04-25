/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core._type;

import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * Represents a reference from an {@link IResource} to a type or a package.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IReference extends Comparable<IReference> {

  /**
   * <p>
   * Returns the fully qualified name of the referenced type, e.g. <code>'de.example.YXY'</code>.
   * </p>
   * 
   * @return the fully qualified name.
   */
  String getFullyQualifiedName();

  /**
   * <p>
   * Returns the type of this {@link IReference}. A reference can either be a <i>package</i> reference or <i>type</i>
   * reference.
   * </p>
   * 
   * @return the {@link ReferenceType} of this {@link IReference}.
   */
  ReferenceType getReferenceType();

  /**
   * <p>
   * Returns <code>true</code> if the origin of this reference is a <b>source</b> resources.
   * </p>
   * 
   * @return <code>true</code> if the origin of this reference is a <b>source</b> resources.
   */
  boolean isSourceReference();

  /**
   * <p>
   * Returns <code>true</code> if the origin of this reference is a <b>binary</b> resources.
   * </p>
   * 
   * @return <code>true</code> if the origin of this reference is a <b>binary</b> resources.
   */
  boolean isBinaryReference();

  /**
   * <p>
   * Returns <code>true</code>, if the this {@link IReference} represents an extends relationship. This is the case if
   * the originating {@link IResource} contains a java type that extends the type described by this dependency (e.g.
   * <code>public class XY extends Z</code>).
   * </p>
   * 
   * @return <code>true</code>, if the this {@link IReference} represents an extends relationship, <code>false</code>
   *         otherwise.
   */
  boolean isExtends();

  /**
   * <p>
   * Returns <code>true</code>, if the this {@link IReference} represents an implements relationship. This is the case
   * if the originating {@link IResource} contains a java type that implements the type described by this dependency
   * (e.g. <code>public class XY implements Z</code>).
   * </p>
   * 
   * @return <code>true</code>, if the this {@link IReference} represents an implements relationship, <code>false</code>
   *         otherwise.
   */
  boolean isImplements();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isClassAnnotation();

  boolean isDirectlyReferenced();

  boolean isIndirectlyReferenced();

  /**
   * <p>
   * Returns the originating {@link IResource} or <code>null</code>, if the reference does not belong to a resource. In
   * this case, the reference belongs to a type that is return by method {@link IReference#getType()}.
   * </p>
   * <p>
   * <b>Note:</b> This back reference is set after the model is loaded from the database. It is <b>not</b> part of the
   * stored model.
   * </p>
   * 
   * @return the originating {@link IResource}.
   */
  IResource getResource();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasAssociatedResource();

  /**
   * <p>
   * Returns the originating {@link IType}.
   * </p>
   * <p>
   * <b>Note:</b> This back reference is set after the model is loaded from the database. It is <b>not</b> part of the
   * stored model.
   * </p>
   * 
   * @return the originating {@link IType}.
   */
  IType getType();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasAssociatedType();
}
