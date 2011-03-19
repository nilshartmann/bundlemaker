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

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModule extends ITypeContainer {

  /**
   * <p>
   * Returns the module identifier of this module.
   * </p>
   * 
   * @return the module identifier of this module.
   */
  IModuleIdentifier getModuleIdentifier();

  /**
   * <p>
   * Returns the classification of this module.
   * </p>
   * 
   * @return the classification
   */
  IPath getClassification();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IModularizedSystem getModularizedSystem();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasClassification();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, Object> getUserAttributes();
}