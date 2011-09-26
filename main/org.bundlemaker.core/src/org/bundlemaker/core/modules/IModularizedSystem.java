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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystem {

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
   * Returns the associated {@link IBundleMakerProject}.
   * </p>
   * 
   * @return the associated {@link IBundleMakerProject}.
   */
  IBundleMakerProject getBundleMakerProject();

  /**
   * <p>
   * Returns the {@link IBundleMakerProjectDescription}.
   * </p>
   * 
   * @return the {@link IBundleMakerProjectDescription}
   */
  IBundleMakerProjectDescription getProjectDescription();

  /**
   * <p>
   * Returns the (modifiable) transformation list.
   * </p>
   * 
   * @return the (modifiable) transformation list.
   * 
   * @deprecated use applyTransformations(List<ITransformation>, IProgressMonitor monitor) instead
   */
  @Deprecated
  List<ITransformation> getTransformations();

  /**
   * <p>
   * Applies the transformations.
   * </p>
   * 
   * @param monitor
   *          the progress monitor to use for reporting progress to the user. It is the caller's responsibility to call
   *          done() on the given monitor. Accepts <code>null</code>, indicating that no progress should be reported and
   *          that the operation cannot be canceled.
   * 
   * @deprecated use applyTransformations(IProgressMonitor monitor, List<ITransformation> transformations) instead
   */
  @Deprecated
  void applyTransformations(IProgressMonitor monitor);

  /**
   * <p>
   * </p>
   * 
   * @param monitor
   * @param transformations
   */
  void applyTransformations(IProgressMonitor monitor, List<ITransformation> transformations);

  /**
   * <p>
   * </p>
   * 
   * @param monitor
   * @param transformation
   */
  void applyTransformations(IProgressMonitor monitor, ITransformation... transformation);

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
  Collection<IModule> getAllModules();

  /**
   * <p>
   * Returns all contained modules that match the specified filter. The result contains both {@link IResourceModule
   * IResourceModules} as well as non-resource modules.
   * </p>
   * 
   * @param filter
   *          the {@link IQueryFilter}
   * @return all contained modules that match the specified filter.
   */
  Collection<IModule> getAllModules(IQueryFilter<IModule> filter);

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

  /**
   * <p>
   * Returns the {@link IResourceModule} with the given identifier. This method is a convenience method and fully
   * equivalent to:
   * 
   * <code>
   * <pre>
   * IModule module = getModule(...);
   * 
   * if (module instanceof IResourceModule) {
   * 	 
   *    IResourceModule resourceModule = (IResourceModule) module;
   *    
   *    ...
   * 
   * }
   * </pre>
   * </code>
   * 
   * </p>
   * 
   * @param identifier
   * @return
   */
  IResourceModule getResourceModule(IModuleIdentifier identifier);

  /**
   * <p>
   * Returns the {@link IResourceModule} with the given name and the given version. If this {@link IModularizedSystem}
   * doesn't contain a {@link IResourceModule} with the specified name and version, <code>null</code> will be returned.
   * </p>
   * <p>
   * This is a convenience method and fully equivalent to: <code><pre>
   * getResourceModule(new ModuleIdentifier(name, version));
   *  </pre></code>
   * </p>
   * 
   * @param name
   * @param version
   * @return
   */
  IResourceModule getResourceModule(String name, String version);

  /**
   * <p>
   * Returns all contained {@link IModule ITypeModules}.
   * </p>
   * 
   * @return
   */
  Collection<IModule> getNonResourceModules();

  /**
   * <p>
   * </p>
   * 
   * @param filter
   * @return
   */
  Collection<IModule> getNonResourceModules(IQueryFilter<IModule> filter);

  /**
   * <p>
   * Returns all contained {@link IResourceModule IResourceModules}.
   * </p>
   * 
   * @return
   */
  Collection<IResourceModule> getResourceModules();

  /**
   * <p>
   * </p>
   * 
   * @param filter
   * @return
   */
  Collection<IResourceModule> getResourceModules(IQueryFilter<IResourceModule> filter);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Set<IType> getTypes();

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  Set<IType> getTypes(String fullyQualifiedName);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @param referencingModule
   * @return
   */
  Set<IType> getTypes(String fullyQualifiedName, IResourceModule referencingModule);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   * @throws AmbiguousElementException
   */
  IType getType(String fullyQualifiedName) throws AmbiguousElementException;

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @param referencingModule
   * @return
   * @throws AmbiguousElementException
   */
  IType getType(String fullyQualifiedName, IResourceModule referencingModule) throws AmbiguousElementException;

  /**
   * <p>
   * Returns a set of {@link IModule IModules} that contain a type with the specified name.
   * </p>
   * 
   * @param fullyQualifiedName
   *          the fully qualified name.
   * @return a set of {@link IModule IModules} that contain a type with the specified name.
   */
  Set<IModule> getTypeContainingModules(String fullyQualifiedName);

  /**
   * <p>
   * Returns a set of {@link IModule IModules} that contain a type with the specified name.
   * </p>
   * 
   * @param fullyQualifiedName
   *          the fully qualified name.
   * @return a set of {@link IModule IModules} that contain a type with the specified name.
   */
  Set<IModule> getTypeContainingModules(String fullyQualifiedName, IResourceModule referencingModule);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   * @throws AmbiguousElementException
   */
  IModule getTypeContainingModule(String fullyQualifiedName) throws AmbiguousElementException;

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   * @throws AmbiguousElementException
   */
  IModule getTypeContainingModule(String fullyQualifiedName, IResourceModule referencingModule)
      throws AmbiguousElementException;

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param referencesFilter
   * @return
   */
  IReferencedModulesQueryResult getReferencedModules(IResourceModule resourceModule);

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param referencesFilter
   * @return
   */
  IReferencedModulesQueryResult getTransitiveReferencedModules(IResourceModule resourceModule);

  /******************************************************************************/

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Deprecated
  public List<ITypeSelector> getTypeSelectors();

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedPackageName
   * @return
   */
  @Deprecated
  Set<IModule> getPackageContainingModules(String fullyQualifiedPackageName);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedPackageName
   * @return
   * @throws AmbiguousElementException
   */
  @Deprecated
  IModule getPackageContainingModule(String fullyQualifiedPackageName) throws AmbiguousElementException;

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  @Deprecated
  Set<IType> getReferencingTypes(String fullyQualifiedName);

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // @Deprecated
  // Map<String, Set<IModule>> getAmbiguousPackages();
  //
  // @Deprecated
  // Map<String, Set<IType>> getAmbiguousTypes();

  // TODO
  @Deprecated
  Collection<IType> getTypeReferencesTransitiveClosure(String typeName, IQueryFilter<IType> filter);

  // TODO
  @Deprecated
  Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName, IQueryFilter<IType> filter);

  // TODO
  @Deprecated
  Collection<IResource> getResourceReferencesTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IType> queryFilter);

  // TODO
  @Deprecated
  Collection<IResource> getResourceIsReferencedTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IResource> queryFilter);

  Set<IReference> getUnsatisfiedReferences(IResourceModule resourceModule);
}
