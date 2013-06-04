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

import org.bundlemaker.core.IGenericAdaptable;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IModuleResource extends IProjectContentResource, Comparable<IModuleResource>, IGenericAdaptable {

  /**
   * <p>
   * Returns the {@link IResourceModule} that contains this {@link IModuleResource}.
   * </p>
   * 
   * @param modularizedSystem
   * @return the {@link IResourceModule} that contains this {@link IModuleResource}.
   */
  IModule getModule(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  IMovableUnit getMovableUnit(IModularizedSystem modularizedSystem);

  /**
   * <p>
   * Returns the sticky resources for this resource. A sticky resource is a resource that belongs somehow to this
   * resource and must be moved between modules as a unit (e.g. <code>XY.class</code> and
   * <code>XY$InnerClass.class</code>).
   * </p>
   * 
   * @return
   */
  Set<? extends IModuleResource> getStickyResources();
}
