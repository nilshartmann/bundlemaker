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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.common.IGenericAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystem extends IGenericAdaptable {

  /**
   * <p>
   * Returns the name of the modularized system.
   * </p>
   * 
   * @return the name of the modularized system.
   */
  String getName();

  /**
   * <p>
   * Returns the user attributes of this {@link IModularizedSystem}.
   * </p>
   * 
   * @return the user attributes of this {@link IModularizedSystem}.
   */
  Map<String, Object> getUserAttributes();

  /**
   * <p>
   * Returns the associated {@link IModuleAwareBundleMakerProject}.
   * </p>
   * 
   * @return the associated {@link IModuleAwareBundleMakerProject}.
   */
  IModuleAwareBundleMakerProject getBundleMakerProject();

  /**
   * <p>
   * Returns the unmodifiable list of all transformations.
   * </p>
   * 
   * @return the unmodifiable list of all transformations.
   */
  List<ITransformation> getTransformations();

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   */
  void undoTransformations(IProgressMonitor progressMonitor);

  /**
   * <p>
   * </p>
   * 
   */
  void undoLastTransformation();

  /**
   * Undos all transformation up to (but not including) the specified transformation
   * 
   * @param transformation
   */
  void undoUntilTransformation(IProgressMonitor progressMonitor, ITransformation transformation);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IPath> getGroups();

  /**
   * <p>
   * Returns the {@link IModule} that represents the execution environment.
   * </p>
   * 
   * @return the {@link IModule} that represents the execution environment.
   */
  IModule getExecutionEnvironment();

  /**
   * <p>
   * Returns all contained modules. The result contains both {@link IResourceModule IResourceModules} as well as
   * non-resource modules.
   * </p>
   * <p>
   * This is a convenience method and fully equivalent to:
   * 
   * <pre>
   * <code>
   * getAllModules(ModuleQueryFilters.TRUE_QUERY_FILTER);
   * </code>
   * </pre>
   * 
   * </p>
   * 
   * @return all contained modules.
   */
  Collection<IModule> getModules();

  /**
   * <p>
   * Returns the {@link IModule} with the given module identifier. If this {@link IModularizedSystem} doesn't contain a
   * module with the specified module identifier, <code>null</code> will be returned.
   * </p>
   * 
   * @param identifier
   *          the module identifier of the requested module
   * @return the {@link IModule} with the given module identifier or <code>null</code> if no such module exists.
   */
  IModule getModule(IModuleIdentifier identifier);

  /**
   * <p>
   * Returns the {@link IModule} with the given name and the given version. If this {@link IModularizedSystem} doesn't
   * contain a module with the specified name and version, <code>null</code> will be returned.
   * </p>
   * <p>
   * This is a convenience method and fully equivalent to: <code><pre>
   * getModule(new ModuleIdentifier(name, version));
   *  </pre></code>
   * </p>
   * 
   * @param name
   * @param version
   * @return
   */
  IModule getModule(String name, String version);

  /**
   * <p>
   * Returns a collection with all modules that have the specified name.
   * </p>
   * 
   * @param name
   *          the name
   * @return a collection with all modules that have the specified name.
   */
  Collection<IModule> getModules(String name);
}
