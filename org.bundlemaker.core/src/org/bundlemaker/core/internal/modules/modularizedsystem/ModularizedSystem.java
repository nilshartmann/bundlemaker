package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.internal.modules.ReferencedModulesQueryResult;
import org.bundlemaker.core.internal.modules.algorithm.ResourceIsReferencedTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.ResourceReferencesTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.TypeIsReferencedTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.TypeReferencesTransitiveClosure;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractValidatingModularizedSystem {

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {

    //
    super(name, projectDescription);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Set<IType>> getAmbiguousTypes() {

    // create the result
    Map<String, Set<IType>> result = new HashMap<String, Set<IType>>();

    //
    for (Entry<String, Set<IType>> entry : getTypeNameToTypeCache().getMap().entrySet()) {

      if (entry.getValue().size() > 1) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IType> getTypeReferencesTransitiveClosure(String typeName, IQueryFilter<IType> filter) {

    TypeReferencesTransitiveClosure closure = new TypeReferencesTransitiveClosure(this);
    closure.resolveType(typeName, filter);
    return closure.getTypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IResource> getResourceReferencesTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IType> queryFilter) {

    ResourceReferencesTransitiveClosure closure = new ResourceReferencesTransitiveClosure(this);
    closure.resolveResource(resource, contentType, queryFilter);
    return closure.getResources();
  }

  @Override
  public Collection<IResource> getResourceIsReferencedTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IResource> queryFilter) {

    ResourceIsReferencedTransitiveClosure closure = new ResourceIsReferencedTransitiveClosure(this);
    closure.resolveResource(resource, contentType, queryFilter);
    return closure.getResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName, IQueryFilter<IType> filter) {

    TypeIsReferencedTransitiveClosure closure = new TypeIsReferencedTransitiveClosure(this);
    closure.resolveType(typeName, filter);
    return closure.getTypes();
  }

  @Override
  public Set<IType> getReferencingTypes(String fullyQualifiedName) {

    Set<IType> result = getTypeNameToReferringCache().get(fullyQualifiedName);

    //
    if (result == null) {
      result = Collections.emptySet();
    }

    return result;
  }

  @Override
  public IReferencedModulesQueryResult getReferencedModules(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {

    // create the result list
    ReferencedModulesQueryResult result = new ReferencedModulesQueryResult(module);

    // TODO: getReferencedTypes(???, ???)
    for (IReference reference : module.getAllReferences(hideContainedTypes, includeSourceReferences,
        includeSourceReferences)) {
      _resolveReferencedModules(result, reference);
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IReferencedModulesQueryResult getReferencedModules(IResource resource) {

    // create the result list
    ReferencedModulesQueryResult result = new ReferencedModulesQueryResult();

    //
    for (IReference reference : resource.getReferences()) {
      _resolveReferencedModules(result, reference);
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getUnsatisfiedReferencedTypes(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {

    // create the result
    Set<String> result = new HashSet<String>();

    // iterate over the referenced types
    for (String referencedType : module.getReferencedTypeNames(hideContainedTypes, includeSourceReferences, false)) {

      // get the module list
      Set<IType> typeList = getTypeNameToTypeCache().get(referencedType);

      // unsatisfied?
      if (typeList == null || typeList.isEmpty()) {
        result.add(referencedType);
      }
    }

    // return the result
    return result;
  }

  @Override
  public Set<String> getUnsatisfiedReferencedPackages(IResourceModule module, boolean hideContainedTypes,
      boolean includeSourceReferences) {

    // create the result
    Set<String> result = new HashSet<String>();

    //
    Set<String> unsatisfiedTypes = getUnsatisfiedReferencedTypes(module, hideContainedTypes, includeSourceReferences);

    for (String unsatisfiedType : unsatisfiedTypes) {

      if (unsatisfiedType.indexOf('.') != -1) {

        //
        String packageName = unsatisfiedType.substring(0, unsatisfiedType.lastIndexOf('.'));

        //
        if (!result.contains(packageName)) {
          result.add(packageName);
        }
      }
    }

    // // iterate over the packages
    // for (String referencedPackage : module.getReferencedPackages(
    // hideContainedTypes, includeSourceReferences)) {
    //
    // //
    // boolean contained = false;
    //
    // //
    // for (ITypeModule iTypeModule : modularizedSystem.getAllModules()) {
    //
    // System.out.println("bam");
    // if (iTypeModule.getContainedPackages().contains(
    // referencedPackage)) {
    // contained = true;
    // break;
    // }
    // }
    //
    // if (!contained) {
    // result.add(referencedPackage);
    // }
    // }

    // return the result
    return result;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Set<IModule>> getAmbiguousPackages() {

    // create the result map
    Map<String, Set<IModule>> result = new HashMap<String, Set<IModule>>();

    // create the temp map
    Map<String, IModule> tempMap = new HashMap<String, IModule>();

    // iterate over all modules
    for (IModule typeModule : getAllModules()) {

      // iterate over contained packages
      for (String containedPackage : typeModule.getContainedPackageNames()) {

        // add
        if (result.containsKey(containedPackage)) {

          result.get(containedPackage).add(typeModule);
        }

        //
        else if (tempMap.containsKey(containedPackage)) {

          //
          Set<IModule> newSet = new HashSet<IModule>();

          //
          result.put(containedPackage, newSet);

          // add module to module list
          newSet.add(typeModule);
          newSet.add(tempMap.remove(containedPackage));
        }

        // put in the temp map
        else {
          tempMap.put(containedPackage, typeModule);
        }
      }
    }

    // return the result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param result
   * @param fullyQualifiedType
   */
  private void _resolveReferencedModules(ReferencedModulesQueryResult result, IReference reference) {

    Assert.isNotNull(result);
    Assert.isNotNull(reference);

    //
    Set<IModule> containingModules = _getContainingModules(reference.getFullyQualifiedName());

    //
    if (containingModules.isEmpty()) {

      //
      result.getUnsatisfiedReferences().add(reference);

    } else if (containingModules.size() > 1) {

      if (!result.getReferencesWithAmbiguousModules().containsKey(reference)) {

        result.getReferencesWithAmbiguousModules().put(reference, new HashSet<IModule>());
      }

      result.getReferencesWithAmbiguousModules().get(reference).addAll(containingModules);

    } else {

      result.getReferencedModulesMap().put(reference, containingModules.toArray(new IModule[0])[0]);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param fullyQualifiedName
   * @return
   */
  private Set<IModule> _getContainingModules(String fullyQualifiedName) {

    //
    if (getTypeNameToTypeCache().containsKey(fullyQualifiedName)) {

      //
      Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
      Set<IModule> result = new HashSet<IModule>();

      for (IType type : types) {
        // TODO: direct call
        result.add(type.getModule(this));
      }

      return result;

    } else {
      return Collections.emptySet();
    }
  }
}
