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
package org.bundlemaker.core.modules.modifiable;

import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModularizedSystem extends IModularizedSystem {

  /**
   * <p>
   * Returns a map with all contained {@link IModifiableResourceModule IModifiableResourceModules}.
   * </p>
   * 
   * @return
   */
  Map<IModuleIdentifier, IModifiableResourceModule> getModifiableResourceModulesMap();

  /**
   * <p>
   * </p>
   * 
   * @param moduleIdentifier
   * @return
   */
  IModifiableResourceModule getModifiableResourceModule(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * </p>
   * 
   * @param moduleIdentifier
   * @return
   */
  IModifiableResourceModule createResourceModule(IModuleIdentifier moduleIdentifier);

  // TODO
  void initializeResourceModules();

}
