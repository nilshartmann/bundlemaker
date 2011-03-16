package org.bundlemaker.core.modules;

import java.util.Set;

import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeSelector {

  /**
   * <p>
   * Must return <code>true</code> if (and only if) this module selector matches all existing {@link IResourceModule
   * IResourceModules}. The result of this method is used for consistency checks only. It does not affect the result of
   * method {@link ITypeSelector#selectType(IModule, String, Set)}.
   * </p>
   * <p>
   * In case that this method returns <code>true</code> the result of method {@link ITypeSelector#getSourceModules()}
   * is ignored.
   * </p>
   * 
   * @return <code>true</code> if (and only if) this module selector matches all existing {@link IResourceModule
   *         IResourceModules}.
   */
  boolean matchesAllModules();

  /**
   * <p>
   * Must return the {@link IModuleIdentifier IModuleIdentifiers} of the modules that matches this module selector. The
   * result of this method is used for consistency checks only. It does not affect the result of method
   * {@link ITypeSelector#selectType(IModule, String, Set)}.
   * </p>
   * <p>
   * In case that the method {@link ITypeSelector#matchesAllModules()} returns <code>true</code> the result of this
   * method is ignored.
   * </p>
   * 
   * @return the {@link IModuleIdentifier IModuleIdentifiers} of the modules that matches this module selector.
   */
  Set<IModuleIdentifier> getSourceModules();

  /**
   * <p>
   * Selects a {@link IType} for the given {@link IModule} from the set of possible implementations. The set of possible
   * types contains all types in the system with the given fully qualified name.
   * </p>
   * 
   * @param module
   *          the module to select the type for
   * @param fullyQualifiedName
   *          the fully qualified name of the type to select
   * @param types
   *          the set of all implementations of the specified type
   * @return the selected type
   */
  IType selectType(IResourceModule module, String fullyQualifiedName, Set<IType> types);
}
