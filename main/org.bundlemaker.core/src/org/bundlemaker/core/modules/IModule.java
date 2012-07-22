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
   * Returns <code>true</code> if this module is attached to a {@link IModularizedSystem}, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this module is attached to a {@link IModularizedSystem}, <code>false</code>
   *         otherwise.S
   */
  boolean hasModularizedSystem();

  /**
   * <p>
   * Returns the {@link IModularizedSystem} this module belongs to or <code>null</code> if this module does not belong
   * to an {@link IModularizedSystem}.
   * </p>
   * 
   * @return the {@link IModularizedSystem}.
   */
  IModularizedSystem getModularizedSystem();

  /**
   * <p>
   * Returns <code>true</code> if a classification is set.
   * </p>
   * 
   * @return <code>true</code> if a classification is set.
   */
  boolean hasClassification();

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
   * Returns the user attributes of this module.
   * </p>
   * 
   * @return the user attributes.
   */
  Map<String, Object> getUserAttributes();
}
