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
package org.bundlemaker.core.transformation.resourceset;

import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceSetProcessor {

  /**
   * <p>
   * </p>
   * 
   * @param originResourceModule
   * @param targetResourceModule
   * @param resourceSet
   */
  public void processResources(IModifiableResourceModule originResourceModule,
      IModifiableResourceModule targetResourceModule, ResourceSet resourceSet);
}
