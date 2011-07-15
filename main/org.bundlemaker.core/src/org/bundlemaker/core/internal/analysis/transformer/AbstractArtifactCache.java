/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.analysis.transformer;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.internal.analysis.AdapterModularizedSystem2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Implements an cache for artifacts.
 * </p>
 */
public abstract class AbstractArtifactCache {

  /** - */
  private IAdvancedArtifact                                          _rootArtifact;

  /** - */
  private IModularizedSystem                                         _modularizedSystem;

  /** - */
  private GenericCache<IPath, AbstractArtifactContainer>             _groupCache;

  /** - */
  private GenericCache<IModule, AbstractArtifactContainer>           _moduleCache;

  /** - */
  private GenericCache<ModulePackageKey, AbstractArtifactContainer>  _packageCache;

  /** - */
  private GenericCache<ModuleResourceKey, AbstractArtifactContainer> _resourceCache;

  /** - */
  private GenericCache<IType, IArtifact>                             _typeCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AbstractArtifactCache(IModifiableModularizedSystem modularizedSystem) {
    this(modularizedSystem, new AdapterModularizedSystem2IArtifact(modularizedSystem));
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifact
   */
  protected AbstractArtifactCache(IModularizedSystem modularizedSystem, IAdvancedArtifact artifact) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(artifact);

    // set the modularized system
    _modularizedSystem = modularizedSystem;

    // create the root artifact
    _rootArtifact = artifact;

    // initialize the caches
    _groupCache = createGroupCache();
    _moduleCache = createModuleCache();
    _packageCache = createPackageCache();
    _resourceCache = createResourceCache();
    _typeCache = createTypeCache();
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws Exception
   */
  public IAdvancedArtifact transform() throws CoreException {
    return transform(_modularizedSystem, _modularizedSystem.getAllModules().toArray(new IModule[0]));
  }

  public GenericCache<IPath, AbstractArtifactContainer> getGroupCache() {
    return _groupCache;
  }

  public GenericCache<IModule, AbstractArtifactContainer> getModuleCache() {
    return _moduleCache;
  }

  public GenericCache<ModulePackageKey, AbstractArtifactContainer> getPackageCache() {
    return _packageCache;
  }

  public GenericCache<ModuleResourceKey, AbstractArtifactContainer> getResourceCache() {
    return _resourceCache;
  }

  public GenericCache<IType, IArtifact> getTypeCache() {
    return _typeCache;
  }

  public IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IAdvancedArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @return
   */
  public IArtifact getModuleArtifact(IModule module) {

    try {

      //
      return _moduleCache.getOrCreate(module);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public IArtifact getTypeArtifact(IType type, boolean createIfMissing) {

    try {

      if (createIfMissing) {
        return _typeCache.getOrCreate(type);
      } else {
        return _typeCache.get(type);
      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public AdapterResource2IArtifact getResourceArtifact(IResource resource) {

    Assert.isNotNull(resource);

    //
    ModuleResourceKey key = new ModuleResourceKey(resource.getAssociatedResourceModule(_modularizedSystem), resource);

    //
    try {

      //
      return (AdapterResource2IArtifact) _resourceCache.getOrCreate(key);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @throws Exception
   */
  public IArtifact getTypeArtifact(String fullyQualifiedName, boolean createIfMissing) {

    //
    try {

      //
      IType targetType = _modularizedSystem.getType(fullyQualifiedName);

      //
      if (targetType == null) {
        // System.out.println("NO TYPE FOR '" + fullyQualifiedName +
        // "'.");
        return null;
      }

      //
      return getTypeArtifact(targetType, createIfMissing);

    } catch (AmbiguousElementException e) {
      System.err.println("AmbExc " + fullyQualifiedName);
      return null;
    }
  }

  protected abstract GenericCache<IModule, AbstractArtifactContainer> createModuleCache();

  protected abstract GenericCache<IPath, AbstractArtifactContainer> createGroupCache();

  protected abstract GenericCache<IType, IArtifact> createTypeCache();

  protected abstract GenericCache<ModuleResourceKey, AbstractArtifactContainer> createResourceCache();

  protected abstract GenericCache<ModulePackageKey, AbstractArtifactContainer> createPackageCache();

  protected abstract IAdvancedArtifact transform(IModularizedSystem modularizedSystem, IModule[] modules);
}
