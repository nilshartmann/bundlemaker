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
package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.Set;

import org.bundlemaker.core._type.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeModule {

  /**
   * <p>
   * Returns the {@link IType} with the specified fully qualified name or <code>null</code> if no {@link IType} with the
   * specified name exists.
   * </p>
   * 
   * @param fullyQualifiedName
   * @return the {@link IType} with the specified fully qualified name or <code>null</code> if no {@link IType} with the
   *         specified name exists.
   */
  IType getType(String fullyQualifiedName);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  boolean containsType(String fullyQualifiedName);

  /**
   * <p>
   * Returns a collection with all contained {@link IType ITypes}.
   * </p>
   * 
   * @return a collection with all contained {@link IType ITypes}.
   */
  Collection<IType> getContainedTypes();

  /**
   * <p>
   * Returns a collection with the names of all contained types.
   * </p>
   * 
   * @return a collection with the names of all contained types.
   */
  Collection<String> getContainedTypeNames();

  /**
   * <p>
   * Returns <code>true</code>, if this container contains all specified types.
   * </p>
   * 
   * @param typeNames
   * @return
   */
  boolean containsAll(Set<String> typeNames);

  void add(IType type2);
}
