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
package org.bundlemaker.core.internal.analysis.cache;

import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AbstractBundleMakerArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.impl.GroupSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.PackageSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ResourceSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.TypeSubCache;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implements an cache for artifacts.
 * </p>
 */
public class ArtifactCache {

  /** - */
  // private static final String MISSING_TYPES = "<< Missing Types >>";

  /** the root artifact */
  private IRootArtifact                 _rootArtifact;

  /** the modularized system */
  private IModularizedSystem            _modularizedSystem;

  /** - */
  protected GroupSubCache               _groupCache;

  /** - */
  protected ModuleSubCache              _moduleCache;

  /** - */
  protected PackageSubCache             _packageCache;

  /** - */
  protected ResourceSubCache            _resourceCache;

  /** - */
  protected TypeSubCache                _typeCache;

  /** - */
  protected IArtifactModelConfiguration _modelConfiguration;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ArtifactCache(IModifiableModularizedSystem modularizedSystem, IArtifactModelConfiguration configuration) {

    // assert not null
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(configuration);

    // set the modularized system
    _modularizedSystem = modularizedSystem;

    // create the root artifact
    _rootArtifact = new AdapterRoot2IArtifact(modularizedSystem, configuration, this);

    //
    _modelConfiguration = configuration;

    // initialize the caches
    _groupCache = new GroupSubCache(this);
    _moduleCache = new ModuleSubCache(this);
    _packageCache = new PackageSubCache(this);
    _resourceCache = new ResourceSubCache(this);
    _typeCache = new TypeSubCache(this);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   *          the modularized system
   * @param rootArtifact
   *          the root artifact
   */
  protected ArtifactCache(IModularizedSystem modularizedSystem, AbstractBundleMakerArtifactContainer rootArtifact) {

  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public final IRootArtifact transform() throws CoreException {
    return transform(_modularizedSystem.getAllModules().toArray(new IModule[0]));
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @return
   */
  public final IModuleArtifact getModuleArtifact(IModule module) {

    //
    try {
      return (IModuleArtifact) _moduleCache.getOrCreate(new ModuleKey(module));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public final IBundleMakerArtifact getTypeArtifact(IType type, boolean createIfMissing) {
    Assert.isNotNull(type);

    //
    try {
      if (createIfMissing) {
        return _typeCache.getOrCreate(new TypeKey(type));
      } else {
        return _typeCache.get(new TypeKey(type));
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public final AdapterResource2IArtifact getResourceArtifact(IResource resource) {
    Assert.isNotNull(resource);

    //
    try {
      return (AdapterResource2IArtifact) _resourceCache.getOrCreate(resource);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @throws Exception
   */
  public final IBundleMakerArtifact getTypeArtifact(String fullyQualifiedName, boolean createIfMissing) {

    //
    try {

      //
      IType targetType = getModularizedSystem().getType(fullyQualifiedName);

      //
      if (targetType == null) {
        if (createIfMissing) {
          return _typeCache.getOrCreate(new TypeKey(fullyQualifiedName));
        } else {
          return _typeCache.get(new TypeKey(fullyQualifiedName));
        }
      } else {
        return getTypeArtifact(targetType, createIfMissing);
      }
    } catch (AmbiguousElementException e) {
      System.err.println("AmbExc " + fullyQualifiedName);
      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final GroupSubCache getGroupCache() {
    return _groupCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ModuleSubCache getModuleCache() {
    return _moduleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final PackageSubCache getPackageCache() {
    return _packageCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ResourceSubCache getResourceCache() {
    return _resourceCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final TypeSubCache getTypeCache() {
    return _typeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IArtifactModelConfiguration getConfiguration() {
    return _modelConfiguration;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  protected IRootArtifact transform(IModule[] modules) {

    // create virtual module for missing types
    if (getConfiguration().isIncludeVirtualModuleForMissingTypes()) {

      // // initialize the types caches
      // initializeMissingTypesCaches();

      // add the
      for (IModule module : modules) {
        if (module instanceof IResourceModule) {
          for (IReference iReference : getModularizedSystem().getUnsatisfiedReferences((IResourceModule) module)) {
            getTypeCache().getOrCreate(new TypeKey(iReference.getFullyQualifiedName()));
          }
        }
      }
    }

    // iterate over all the type modules
    for (IModule module : modules) {

      //
      this.getModuleArtifact(module);

      // add all types
      for (IType type : module.getContainedTypes()) {

        // filter local or anonymous type names
        if ((!getConfiguration().isAggregateInnerTypes() && !type.isLocalOrAnonymousType())
            || (getConfiguration().isAggregateInnerTypes() && !type.isInnerType() && type.handleAsPrimaryType())) {

          // create the artifact
          this.getTypeArtifact(type, true);
        }
      }

      // add all resources
      if (getConfiguration().getResourcePresentation().equals(ResourcePresentation.ALL_RESOURCES)
          || getConfiguration().getResourcePresentation().equals(ResourcePresentation.ONLY_NON_TYPE_RESOURCES)) {

        // cast to 'IResourceModule'
        if (module instanceof IResourceModule) {

          // get the resource module
          IResourceModule resourceModule = (IResourceModule) module;

          // iterate over all contained resources
          for (IResource resource : resourceModule.getResources(getConfiguration().getContentType())) {
            if (!resource.containsTypes()) {
              // create the artifact
              this.getResourceArtifact(resource);
            }
          }
        }
      }
    }

    // return the root artifact
    return this.getRootArtifact();
  }
}
