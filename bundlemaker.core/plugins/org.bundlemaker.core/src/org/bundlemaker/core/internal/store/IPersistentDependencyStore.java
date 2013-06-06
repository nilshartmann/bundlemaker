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
package org.bundlemaker.core.internal.store;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.parser.IParsableResource;
import org.bundlemaker.core.resource.IModuleResource;

/**
 * <p>
 * Extends the {@link IDependencyStore} and adds some methods that allows to add and update {@link IModuleResource IResources}
 * .
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPersistentDependencyStore extends IDependencyStore {

  /**
   * <p>
   * Updates the {@link Resource}.
   * </p>
   * 
   * @param resource
   *          the {@link Resource} to add/update
   */
  void updateResource(IParsableResource resource);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  void delete(Resource resource);

  /**
   * <p>
   * Commits all updates to the database since the last call of the <code>commit</code> method.
   * </p>
   */
  void commit();

  /**
   * <p>
   * </p>
   */
  void init();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isInitialized();

  /**
   * <p>
   * </p>
   * 
   */
  void dispose();
}
