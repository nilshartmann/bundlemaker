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
package org.bundlemaker.core.resource;

import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * Defines a (java) type. A type can be a {@link TypeEnum#CLASS}, an {@link TypeEnum#INTERFACE}, an
 * {@link TypeEnum#ANNOTATION} or an {@link TypeEnum#ENUM} and has a fully qualified name.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IType {

  /**
   * <p>
   * Returns the type of the this type, e.g. {@link TypeEnum#CLASS}, {@link TypeEnum#INTERFACE},
   * {@link TypeEnum#ANNOTATION} or {@link TypeEnum#ENUM}.
   * </p>
   * 
   * @return the type of this type.
   */
  TypeEnum getType();

  /**
   * <p>
   * Returns the fully qualified name of the referenced type, e.g. <code>'de.example.YXY'</code>.
   * </p>
   * 
   * @return the fully qualified name of this type.
   */
  String getFullyQualifiedName();

  /**
   * <p>
   * Returns the name of the referenced type, e.g. <code>'YXY'</code>.
   * </p>
   * 
   * @return the simple name of this type.
   */
  String getName();

  /**
   * <p>
   * Returns the fully qualified name of the package that contains this type, e.g. <code>'de.example'</code>.
   * </p>
   * 
   * @return the fully qualified package name of this type.
   */
  String getPackageName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getContentId();

  /**
   * <p>
   * Returns a set with all {@link IReference IReferences} of this type.
   * </p>
   * 
   * @return a set with all {@link IReference IReferences} of this type.
   */
  Set<? extends IReference> getReferences();

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  IReference getReference(String fullyQualifiedName);

  /**
   * <p>
   * Returns the associated source resource.
   * </p>
   * 
   * @return the associated source resource.
   */
  IResource getSourceResource();

  /**
   * <p>
   * Returns <code>true</code>, if a associated source resource is set.
   * </p>
   * 
   * @return <code>true</code>, if a associated source resource is set.
   */
  boolean hasSourceResource();

  /**
   * <p>
   * Returns the associated binary resource.
   * </p>
   * 
   * @return the associated binary resource.
   */
  IResource getBinaryResource();

  /**
   * <p>
   * Returns <code>true</code>, if a associated binary resource is set.
   * </p>
   * 
   * @return <code>true</code>, if a associated binary resource is set.
   */
  boolean hasBinaryResource();

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  IModule getModule(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isLocalOrAnonymousType();

  boolean isInnerType();

  boolean isPrimaryType();
}
