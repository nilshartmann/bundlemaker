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

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeArtifact;
import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.impl.GroupSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.PackageSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ResourceSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.TypeSubCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractModularizedSystem;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

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
  private AbstractModularizedSystem     _modularizedSystem;

  /** - */
  protected IAnalysisModelConfiguration _modelConfiguration;

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

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ArtifactCache(IModifiableModularizedSystem modularizedSystem, IAnalysisModelConfiguration configuration) {

    // assert not null
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(configuration);

    // set the modularized system
    _modularizedSystem = (AbstractModularizedSystem) modularizedSystem;

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
  protected ArtifactCache(IModularizedSystem modularizedSystem, AbstractArtifactContainer rootArtifact) {

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
  public final IRootArtifact transform(IProgressMonitor progressMonitor) throws CoreException {

    // we may have empty groups - so it is necessary to create IGroupArtifact
    // entries explicitly
    for (Group group : _modularizedSystem.internalGroups()) {
      getGroupCache().getOrCreate(group);
    }

    // transform the modularized system
    return transform(_modularizedSystem.getModules().toArray(new IModule[0]), progressMonitor);
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
  public final ITypeArtifact getTypeArtifact(IType type, boolean createIfMissing) {
    Assert.isNotNull(type);

    //
    try {
      if (createIfMissing) {
        return _typeCache.getOrCreate(new TypeKey(type));
      } else {
        return _typeCache.get(new TypeKey(type));
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(type);
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
  public final AdapterResource2IArtifact getResourceArtifact(IModuleResource resource) {
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
  public final ITypeArtifact getTypeArtifact(String fullyQualifiedName, boolean createIfMissing) {

    //
    IType targetType = ((IModifiableModularizedSystem) getModularizedSystem()).adaptAs(ITypeModularizedSystem.class)
        .getType(fullyQualifiedName);

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
  public final IAnalysisModelConfiguration getConfiguration() {
    return _modelConfiguration;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  private IRootArtifact transform(IModule[] modules, IProgressMonitor progressMonitor) {

    //
    int count = 0;
    for (IModule module : modules) {
      count = count + module.adaptAs(ITypeModule.class).getContainedTypes().size();
      if (module instanceof IModifiableModule) {
        IModifiableModule resourceModule = (IModifiableModule) module;
        count = count + resourceModule.getResources(getConfiguration().getContentType()).size();
      }
    }

    //
    if (progressMonitor != null) {
      progressMonitor.beginTask("Creating analysis model...", count);
    }

    // MISSING TYPES: create virtual module for missing types
    if (getConfiguration().isIncludeVirtualModuleForMissingTypes()) {

      // add the
      for (IModule module : modules) {
        if (module instanceof IModifiableModule) {
          for (IReference iReference : ((IModifiableModularizedSystem) getModularizedSystem())
              .adaptAs(ITypeModularizedSystem.class).getUnsatisfiedReferences((IModifiableModule) module)) {
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
      for (IType type : ((ITypeModule) module.getAdapter(ITypeModule.class)).getContainedTypes()) {

        if (progressMonitor != null) {
          progressMonitor.worked(1);
        }

        // filter local or anonymous type names
        if (!type.isLocalOrAnonymousType()) {

          // create the artifact
          this.getTypeArtifact(type, true);
        }
      }

      // cast to 'IResourceModule'
      if (module instanceof IModifiableModule) {

        // get the resource module
        IModifiableModule resourceModule = (IModifiableModule) module;

        // iterate over all contained resources
        for (IModuleResource resource : resourceModule.getResources(getConfiguration().getContentType())) {

          if (progressMonitor != null) {
            progressMonitor.worked(1);
          }

          if (!resource.adaptAs(ITypeResource.class).containsTypes()) {
            // create the artifact
            this.getResourceArtifact(resource);
          }
        }
      }
    }

    //
    if (progressMonitor != null) {
      progressMonitor.done();
    }

    // return the root artifact
    return this.getRootArtifact();
  }
}
