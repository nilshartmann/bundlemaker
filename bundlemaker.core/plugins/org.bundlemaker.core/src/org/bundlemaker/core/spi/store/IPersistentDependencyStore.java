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
package org.bundlemaker.core.spi.store;

import org.bundlemaker.core.spi.parser.IParsableResource;

/**
 * <p>
 * Extends the {@link IDependencyStore} and adds some methods that allows to add and update {@link IParsableResource
 * IParsableResources} .
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPersistentDependencyStore extends IDependencyStore {

  /**
   * <p>
   * Updates the {@link IParsableResource}.
   * </p>
   * 
   * @param resource
   *          the {@link IParsableResource} to add/update
   */
  void updateResource(IParsableResource resource);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  void delete(IParsableResource resource);

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
