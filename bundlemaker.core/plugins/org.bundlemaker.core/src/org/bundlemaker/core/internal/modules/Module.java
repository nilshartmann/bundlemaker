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
package org.bundlemaker.core.internal.modules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.internal.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModule;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Abstract base class for all modules.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Module implements IModifiableModule {

  /** the module identifier */
  private IModuleIdentifier     _moduleIdentifier;

  /** the classification */
  private Group                 _classification;

  /** the user attributes */
  private Map<String, Object>   _userAttributes;

  /** the self container */
  private TypeContainer         _typeContainer;

  /** the modularized system the module belongs to */
  private IModularizedSystem    _modularizedSystem;

  /** specified whether or not the module is attached to a modularized system */
  private boolean               _isDetached;

  /** the binary resources */
  private Set<IResourceStandin> _binaryResources;

  /** the source resources */
  private Set<IResourceStandin> _sourceResources;

  /** - */
  private boolean               _isResourceModule;

  /**
   * <p>
   * Creates a new instance of type {@link Module}.
   * </p>
   * 
   * @param moduleIdentifier
   * @param modularizedSystem
   *          TODO
   * @param selfContainer
   */
  public Module(IModuleIdentifier moduleIdentifier, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(moduleIdentifier);
    Assert.isNotNull(modularizedSystem);

    // set the parameters
    _moduleIdentifier = moduleIdentifier;
    _modularizedSystem = modularizedSystem;

    // create the hash map
    _userAttributes = new HashMap<String, Object>();

    // create the resource sets
    _binaryResources = new HashSet<IResourceStandin>();
    _sourceResources = new HashSet<IResourceStandin>();

    //
    _typeContainer = new TypeContainer(this);
    _isResourceModule = true;
  }

  @Override
  public Object getAdapter(Class adapter) {
    return adaptAs(adapter);
  }

  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    if (clazz == ITypeModule.class) {
      return (T) _typeContainer;
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResourceModule() {
    return _isResourceModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @param isResourceModule
   */
  public void setResourceModule(boolean isResourceModule) {
    _isResourceModule = isResourceModule;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleIdentifier getModuleIdentifier() {
    return _moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getClassification() {
    return _classification != null ? _classification.getPath() : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasClassification() {
    return _classification != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return _isDetached ? null : _modularizedSystem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasModularizedSystem() {
    return !_isDetached;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getUserAttributes() {
    return _userAttributes;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public Set<String> getContainedTypeNames() {
  // return getContainedTypeNames(new IQueryFilter<String>() {
  // @Override
  // public boolean matches(String content) {
  // return true;
  // }
  // });
  // }

  public Group getClassificationGroup() {
    return _classification;
  }

  public final void setModuleIdentifier(String name, String version) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);

    setModuleIdentifier(new ModuleIdentifier(name, version));
  }

  public final void setModuleIdentifier(IModuleIdentifier moduleIdentifier) {
    Assert.isNotNull(moduleIdentifier);

    _moduleIdentifier = moduleIdentifier;

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem()).fireModuleIdentifierChanged(this);
    }
  }

  // public IType getType(String fullyQualifiedName) {
  // return _typeContainer.getType(fullyQualifiedName);
  // }
  //
  // public boolean containsType(String fullyQualifiedName) {
  // return _typeContainer.containsType(fullyQualifiedName);
  // }
  //
  // public boolean containsAll(Set<String> typeNames) {
  // return _typeContainer.containsAll(typeNames);
  // }
  //
  // public Collection<IType> getContainedTypes() {
  // return _typeContainer.getContainedTypes();
  // }
  //
  // public Collection<IType> getContainedTypes(IQueryFilter<IType> filter) {
  // return _typeContainer.getContainedTypes(filter);
  // }
  //
  // public Set<String> getContainedTypeNames(IQueryFilter filter) {
  // return _typeContainer.getContainedTypeNames(filter);
  // }
  //
  // public void add(IType type) {
  // _typeContainer.add(type);
  // }
  //
  // public void remove(IType type) {
  // _typeContainer.remove(type);
  // }

  /**
   * <p>
   * </p>
   * 
   * @param classificationPath
   */
  public void setClassification(IPath classificationPath) {

    //
    if (classificationPath == null || classificationPath.isEmpty()) {
      _classification = null;
    }

    //
    else {

      _classification = ((AbstractTransformationAwareModularizedSystem) getModularizedSystem())
          .getOrCreateGroup(classificationPath);
    }

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem())
          .fireModuleClassificationChanged(new ModuleClassificationChangedEvent(this));
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void detach() {
    _isDetached = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  public void attach(IModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    Assert.isTrue(modularizedSystem.equals(_modularizedSystem),
        "You can only add a module to the modularized system you specified when creating the module.");

    //
    _isDetached = false;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + " [_moduleIdentifier=" + _moduleIdentifier + ", _classification="
        + _classification + "]";
  }

  // TODO
  public void validate() {

    //
    Map<String, IModuleResource> entries = new HashMap<String, IModuleResource>();

    //
    for (IModuleResource resource : getResources(ProjectContentType.SOURCE)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }

    //
    entries.clear();
    for (IModuleResource resource : getResources(ProjectContentType.BINARY)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }
  }

  /********************************************************/
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResource(String resourceType, ProjectContentType contentType) {
    return getResource(resourceType, contentType) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceStandin getResource(String path, ProjectContentType contentType) {

    //
    for (IResourceStandin resourceStandin : getModifiableResourcesSet(contentType)) {

      //
      if (resourceStandin.getPath().equalsIgnoreCase(path)) {
        return resourceStandin;
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IResourceStandin> getResources(ProjectContentType contentType) {

    //
    Set<IResourceStandin> result = getModifiableResourcesSet(contentType);
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences() {

    //
    Set<IReference> result = new HashSet<IReference>();

    // iterate over all resources
    for (IModuleResource resource : getResources(ProjectContentType.BINARY)) {
      for (IReference reference : resource.getReferences()) {
        result.add(reference);
      }
    }

    for (IModuleResource resource : getResources(ProjectContentType.SOURCE)) {
      for (IReference reference : resource.getReferences()) {
        result.add(reference);
      }
    }

    //
    for (IType type : _typeContainer.getContainedTypes()) {
      for (IReference reference : type.getReferences()) {
        result.add(reference);
      }
    }

    // return result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IMovableUnit> getMovableUnits() {

    // the result
    List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    // iterate over all types
    for (IType type : _typeContainer.getContainedTypes()) {

      //
      IMovableUnit movableUnit = MovableUnit.createFromType(type, getModularizedSystem());

      //
      if (!result.contains(movableUnit)) {
        result.add(movableUnit);
      }
    }

    // iterate over all resources
    for (IModuleResource resource : getResources(ProjectContentType.BINARY)) {
      if (!resource.containsTypes()) {

        //
        IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());

        //
        if (!result.contains(movableUnit)) {
          result.add(movableUnit);
        }
      }
    }

    // iterate over all resources
    for (IModuleResource resource : getResources(ProjectContentType.SOURCE)) {
      if (!resource.containsTypes()) {

        //
        IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());

        //
        if (!result.contains(movableUnit)) {
          result.add(movableUnit);
        }
      }
    }

    // return the result
    return Collections.unmodifiableList(result);
  }

  @Override
  public boolean containsSources() {
    return !getResources(ProjectContentType.SOURCE).isEmpty();
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public Set<String> getReferencedTypeNames(IQueryFilter<IReference> filter) {
  //
  // //
  // Set<IReference> references = getReferences(filter);
  //
  // //
  // Set<String> result = new HashSet<String>();
  // for (IReference reference : references) {
  // result.add(reference.getFullyQualifiedName());
  // }
  //
  // //
  // return Collections.unmodifiableSet(result);
  // }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public Set<String> getReferencedPackageNames(IQueryFilter<IReference> filter) {
  //
  // //
  // Set<IReference> references = getReferences(filter);
  //
  // //
  // Set<String> result = new HashSet<String>();
  // for (IReference reference : references) {
  //
  // if (reference.getFullyQualifiedName().indexOf('.') != -1) {
  // result.add(reference.getFullyQualifiedName().substring(0, reference.getFullyQualifiedName().lastIndexOf('.')));
  // } else {
  // // TODO: brauchen wir das ?
  // // result.add("");
  // }
  // }
  //
  // //
  // return result;
  // }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public IModule getResourceModule() {
  // return (IModule) getModule();
  // }

  /**
   * {@inheritDoc}
   */
  private void add(IResourceStandin resource, ProjectContentType contentType) {

    Assert.isNotNull(resource);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).add(resource);

    // ... and add all contained types to the cache
    for (IType type : resource.getContainedTypes()) {
      _typeContainer.add(type);
    }

    // notify
    if (hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getModularizedSystem()).resourceChanged(resource,
          this, ChangeAction.ADDED);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Deprecated
  @Override
  public void addAll(Set<IResourceStandin> resources, ProjectContentType contentType) {

    Assert.isNotNull(resources);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).addAll(resources);

    // ... and add all contained types to the cache
    for (IModuleResource resource : resources) {
      for (IType type : resource.getContainedTypes()) {
        _typeContainer.add(type);
      }
    }

    // notify
    if (hasModularizedSystem()) {
      ((IModifiableModularizedSystem) getModularizedSystem()).resourcesChanged(resources,
          this, ChangeAction.ADDED);
    }
  }

  /**
   * {@inheritDoc}
   */
  private void remove(IModuleResource resource, ProjectContentType contentType) {

    Assert.isNotNull(resource);
    Assert.isNotNull(contentType);

    //
    if (getModifiableResourcesSet(contentType).contains(resource)) {

      // add the resource to the resource set...
      getModifiableResourcesSet(contentType).remove(resource);

      // notify
      if (hasModularizedSystem()) {
        ((AbstractCachingModularizedSystem) getModularizedSystem()).resourceChanged(resource,
            this, ChangeAction.REMOVED);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  private void removeAll(Collection<? extends IModuleResource> resources, ProjectContentType contentType) {

    Assert.isNotNull(resources);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).removeAll(resources);

    // // ... and add all contained types to the cache
    // try {
    // for (IResource resource : resources) {
    // for (IType type : resource.getContainedTypes()) {
    // remove(type);
    // }
    // }
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // notify
    if (hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getModularizedSystem()).resourcesChanged(resources,
          this, ChangeAction.REMOVED);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    // add all types
    for (IType type : movableUnit.getAssociatedTypes()) {
      _typeContainer.add(type);
    }

    // add binary resources
    @SuppressWarnings("unchecked")
    Set<IResourceStandin> resourceStandins = new HashSet<IResourceStandin>(
        (List<IResourceStandin>) movableUnit.getAssociatedBinaryResources());
    addAll(resourceStandins, ProjectContentType.BINARY);

    // add source resources
    if (movableUnit.hasAssociatedSourceResource()) {
      add((IResourceStandin) movableUnit.getAssociatedSourceResource(), ProjectContentType.SOURCE);
    }

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, this, ChangeAction.ADDED);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    // add all types
    for (IType type : movableUnit.getAssociatedTypes()) {
      _typeContainer.remove(type);
    }

    // add binary resources
    removeAll(movableUnit.getAssociatedBinaryResources(), ProjectContentType.BINARY);

    // add source resources
    if (movableUnit.hasAssociatedSourceResource()) {
      remove(movableUnit.getAssociatedSourceResource(), ProjectContentType.SOURCE);
    }

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, this, ChangeAction.REMOVED);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentType
   * @return
   */
  private Set<IResourceStandin> getModifiableResourcesSet(ProjectContentType contentType) {
    Assert.isNotNull(contentType);

    // return the resource set
    return ProjectContentType.BINARY.equals(contentType) ? _binaryResources : _sourceResources;
  }
}
