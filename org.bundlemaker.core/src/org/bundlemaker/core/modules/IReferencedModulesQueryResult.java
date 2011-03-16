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

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IReference;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReferencedModulesQueryResult {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasErrors();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IReference, IModule> getReferencedModulesMap();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<IModule> getReferencedModules();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasReferencesWithAmbiguousModules();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IReference, Set<IModule>> getReferencesWithAmbiguousModules();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, Set<IModule>> getReferencedTypesWithAmbiguousModules();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasUnsatisfiedReferences();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<IReference> getUnsatisfiedReferences();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<String> getUnsatisfiedReferencedTypes();
}
