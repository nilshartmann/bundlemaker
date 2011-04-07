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
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Defines the common interface for resources. A resource is either a file (e.g. a java source file or a class file) or
 * an entry in an archive file. It contains 0 to n {@link IType ITypes}. It also contains 0 to n {@link IReference
 * IReferences}.
 * </p>
 * <p>
 * Note that both the {@link IResource} and the contained {@link IType ITypes} can contain {@link IReference
 * IReferences}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResource extends IResourceKey, Comparable<IResource> {

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
  Set<? extends IReference> getReferences();

  /**
   * <p>
   * Returns all the contained types in this resource. If the resource does not contain any type, an empty list will be
   * returned instead.
   * </p>
   * 
   * @return all the contained types in this resource. If the resource does not contain any type, an empty list will be
   *         returned instead.
   */
  Set<? extends IType> getContainedTypes();

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
   * Returns the {@link IResourceModule} that contains this {@link IResource}.
   * </p>
   * 
   * @param modularizedSystem
   * @return the {@link IResourceModule} that contains this {@link IResource}.
   */
  IResourceModule getAssociatedResourceModule(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<? extends IResource> getStickyResources();
}
