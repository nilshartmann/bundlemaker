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
package org.bundlemaker.core.internal.api.store;

import java.util.List;

import org.bundlemaker.core.internal.resource.Resource;

/**
 * <p>
 * Defines an interface to retrieve {@link IReferencingElement IBundleElements} from an underlying store.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDependencyStore {

  /**
   * <p>
   * Returns the list of all {@link ReferencingResource IBundleElements}.
   * </p>
   * 
   * @return the list of all {@link ReferencingResource IBundleElements}.
   */
  List<Resource> getResources();
}
