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

import java.util.Collection;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModularizedSystem extends IModularizedSystem {

  // TODO IModifiableModule

  /**
   * <p>
   * Creates a new IModifiableResourceModule and adds it to this {@link IModularizedSystem}.
   * </p>
   * 
   * @param moduleIdentifier
   *          the module identifier
   * @return
   */
  IModifiableResourceModule createResourceModule(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * Adds the given {@link IModifiableResourceModule} to this {@link IModularizedSystem}.
   * </p>
   * 
   * @param identifier
   * @param resourceModule
   */
  void addModule(IModule resourceModule);

  /**
   * <p>
   * Removes the module with the given {@link IModuleIdentifier}.
   * </p>
   * 
   * @param identifier
   *          the {@link IModuleIdentifier}.
   */
  void removeModule(IModuleIdentifier identifier);

  /**
   * <p>
   * Removes the given {@link IModule} from this {@link IModularizedSystem}.
   * </p>
   * 
   * @param module
   */
  void removeModule(IModule module);

  /**
   * <p>
   * Returns the {@link IModifiableResourceModule} with the given {@link IModuleIdentifier}.
   * </p>
   * 
   * @param moduleIdentifier
   * @return
   */
  IModifiableResourceModule getModifiableResourceModule(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * Returns all {@link IModifiableResourceModule IModifiableResourceModules} for this {@link IModularizedSystem}.
   * </p>
   * 
   * @return
   */
  Collection<IModifiableResourceModule> getModifiableResourceModules();
}
