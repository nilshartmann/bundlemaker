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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IGenericAdaptable;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModule extends IGenericAdaptable {

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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Deprecated
  boolean isResourceModule();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean containsSources();

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @param contentType
   * @return
   */
  boolean containsResource(String path, ProjectContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @param conentType
   * @return
   */
  IModuleResource getResource(String path, ProjectContentType conentType);

  /**
   * <p>
   * </p>
   * 
   * @param conentType
   * @return
   */
  Set<? extends IModuleResource> getResources(ProjectContentType conentType);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IMovableUnit> getMovableUnits();
}
