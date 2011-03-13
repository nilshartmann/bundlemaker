package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.ITransformation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystem extends ICrossReferencer {

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
   */
  List<ITransformation> getTransformations();

  /**
   * <p>
   * Applies the transformations.
   * </p>
   */
  void applyTransformations();

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
   * @param fullyQualifiedName
   * @return
   */
  Set<IType> getTypes(String fullyQualifiedName);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   * @throws AmbiguousDependencyException
   */
  IType getType(String fullyQualifiedName) throws AmbiguousDependencyException;

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
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   * @throws AmbiguousDependencyException
   */
  IModule getTypeContainingModule(String fullyQualifiedName) throws AmbiguousDependencyException;

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedPackageName
   * @return
   */
  Set<IModule> getPackageContainingModules(String fullyQualifiedPackageName);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedPackageName
   * @return
   * @throws AmbiguousDependencyException
   */
  IModule getPackageContainingModule(String fullyQualifiedPackageName) throws AmbiguousDependencyException;
}