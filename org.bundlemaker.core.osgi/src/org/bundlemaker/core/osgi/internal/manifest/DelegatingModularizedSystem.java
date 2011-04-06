package org.bundlemaker.core.osgi.internal.manifest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.ITransformation;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DelegatingModularizedSystem implements IModularizedSystem {

  private IModularizedSystem _modularizedSystem;

  public DelegatingModularizedSystem(IModularizedSystem modularizedSystem) {
    super();
    _modularizedSystem = modularizedSystem;
  }

  public Map<String, Object> getUserAttributes() {
    return _modularizedSystem.getUserAttributes();
  }

  public Collection<IModule> getAllModules() {
    return _modularizedSystem.getAllModules();
  }

  public Set<IType> getTypes(String fullyQualifiedName, IResourceModule referencingModule) {
    return _modularizedSystem.getTypes(fullyQualifiedName, referencingModule);
  }

  public IType getType(String fullyQualifiedName, IResourceModule referencingModule) throws AmbiguousElementException {
    return _modularizedSystem.getType(fullyQualifiedName, referencingModule);
  }

  public Set<IModule> getTypeContainingModules(String fullyQualifiedName, IResourceModule referencingModule) {
    return _modularizedSystem.getTypeContainingModules(fullyQualifiedName, referencingModule);
  }

  public IModule getTypeContainingModule(String fullyQualifiedName, IResourceModule referencingModule)
      throws AmbiguousElementException {
    return _modularizedSystem.getTypeContainingModule(fullyQualifiedName, referencingModule);
  }

  public List<ITypeSelector> getTypeSelectors() {
    return _modularizedSystem.getTypeSelectors();
  }



  public Collection<IModule> getModules(String name) {
    return _modularizedSystem.getModules(name);
  }

  public Collection<IModule> getAllModules(IQueryFilter<IModule> filter) {
    return _modularizedSystem.getAllModules(filter);
  }

  public IModule getModule(String name, String version) {
    return _modularizedSystem.getModule(name, version);
  }

  public IResourceModule getResourceModule(String name, String version) {
    return _modularizedSystem.getResourceModule(name, version);
  }

  public Collection<IModule> getNonResourceModules(IQueryFilter<IModule> filter) {
    return _modularizedSystem.getNonResourceModules(filter);
  }

  public Collection<IResourceModule> getResourceModules(IQueryFilter<IResourceModule> filter) {
    return _modularizedSystem.getResourceModules(filter);
  }

  public Set<IType> getReferencingTypes(String fullyQualifiedName) {
    return _modularizedSystem.getReferencingTypes(fullyQualifiedName);
  }

  public String getName() {
    return _modularizedSystem.getName();
  }

  public IReferencedModulesQueryResult getReferencedModules(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {
    return _modularizedSystem.getReferencedModules(module, hideContainedTypes, includeSourceReferences);
  }

  public IBundleMakerProject getBundleMakerProject() {
    return _modularizedSystem.getBundleMakerProject();
  }

  public IBundleMakerProjectDescription getProjectDescription() {
    return _modularizedSystem.getProjectDescription();
  }

  public IReferencedModulesQueryResult getReferencedModules(IResource resource) {
    return _modularizedSystem.getReferencedModules(resource);
  }

  public List<ITransformation> getTransformations() {
    return _modularizedSystem.getTransformations();
  }

  public Set<String> getUnsatisfiedReferencedTypes(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {
    return _modularizedSystem.getUnsatisfiedReferencedTypes(module, hideContainedTypes, includeSourceReferences);
  }

  public void applyTransformations() {
    _modularizedSystem.applyTransformations();
  }

  public IModule getExecutionEnvironment() {
    return _modularizedSystem.getExecutionEnvironment();
  }

  public Set<String> getUnsatisfiedReferencedPackages(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {
    return _modularizedSystem.getUnsatisfiedReferencedPackages(module, hideContainedTypes, includeSourceReferences);
  }

  public Collection<IModule> getNonResourceModules() {
    return _modularizedSystem.getNonResourceModules();
  }

  public Map<String, Set<IModule>> getAmbiguousPackages() {
    return _modularizedSystem.getAmbiguousPackages();
  }

  public Map<String, Set<IType>> getAmbiguousTypes() {
    return _modularizedSystem.getAmbiguousTypes();
  }

  public IModule getModule(IModuleIdentifier identifier) {
    return _modularizedSystem.getModule(identifier);
  }

  public Collection<IType> getTypeReferencesTransitiveClosure(String typeName, IQueryFilter<IType> filter) {
    return _modularizedSystem.getTypeReferencesTransitiveClosure(typeName, filter);
  }

  public Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName, IQueryFilter<IType> filter) {
    return _modularizedSystem.getTypeIsReferencedTransitiveClosure(typeName, filter);
  }

  public Collection<IResourceModule> getResourceModules() {
    return _modularizedSystem.getResourceModules();
  }

  public Collection<IResource> getResourceReferencesTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IType> queryFilter) {
    return _modularizedSystem.getResourceReferencesTransitiveClosure(resource, contentType, queryFilter);
  }

  public IResourceModule getResourceModule(IModuleIdentifier identifier) {
    return _modularizedSystem.getResourceModule(identifier);
  }

  public Collection<IResource> getResourceIsReferencedTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IResource> queryFilter) {
    return _modularizedSystem.getResourceIsReferencedTransitiveClosure(resource, contentType, queryFilter);
  }

  public Set<IType> getTypes(String fullyQualifiedName) {
    return _modularizedSystem.getTypes(fullyQualifiedName);
  }

  public IType getType(String fullyQualifiedName) throws AmbiguousElementException {
    return _modularizedSystem.getType(fullyQualifiedName);
  }

  public Set<IModule> getTypeContainingModules(String fullyQualifiedName) {
    return _modularizedSystem.getTypeContainingModules(fullyQualifiedName);
  }

  public IModule getTypeContainingModule(String fullyQualifiedName) throws AmbiguousElementException {
    return _modularizedSystem.getTypeContainingModule(fullyQualifiedName);
  }

  public Set<IModule> getPackageContainingModules(String fullyQualifiedPackageName) {
    return _modularizedSystem.getPackageContainingModules(fullyQualifiedPackageName);
  }

  public IModule getPackageContainingModule(String fullyQualifiedPackageName) throws AmbiguousElementException {
    return _modularizedSystem.getPackageContainingModule(fullyQualifiedPackageName);
  }

}
