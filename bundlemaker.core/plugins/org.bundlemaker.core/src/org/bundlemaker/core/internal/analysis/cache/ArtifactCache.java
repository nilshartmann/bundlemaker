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

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.impl.GroupSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.PackageSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.ResourceSubCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modelext.ModelExtFactory;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractModularizedSystem;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.modext.IAnalysisModelContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Implements an cache for artifacts.
 * </p>
 */
public class ArtifactCache implements IAnalysisModelContext {

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
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceArtifact getOrCreateResource(IModuleResource resource) {
    Assert.isNotNull(resource);
    return _resourceCache.getOrCreate(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPackageArtifact getOrCreatePackage(String moduleName, String packageName) {
    Assert.isNotNull(moduleName);
    Assert.isNotNull(packageName);
    return (IPackageArtifact) _packageCache.getOrCreate(new ModulePackageKey(new ModuleKey(moduleName), packageName));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPackageArtifact getOrCreatePackage(IModule module, String packageName) {
    Assert.isNotNull(module);
    Assert.isNotNull(packageName);
    return (IPackageArtifact) _packageCache.getOrCreate(new ModulePackageKey(new ModuleKey(module), packageName));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleArtifact getOrCreateModuleArtifact(IModule module) {
    Assert.isNotNull(module);
    return (IModuleArtifact) _moduleCache.getOrCreate(new ModuleKey(module));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleArtifact getOrCreateModuleArtifact(String moduleName) {
    Assert.isNotNull(moduleName);
    return (IModuleArtifact) _moduleCache.getOrCreate(new ModuleKey(moduleName));
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
      if (module instanceof IModifiableModule) {
        IModifiableModule resourceModule = (IModifiableModule) module;
        count = count + resourceModule.getResources(getConfiguration().getContentType()).size();
      }
    }

    //
    if (progressMonitor != null) {
      progressMonitor.beginTask("Creating analysis model...", count);
    }

    // prepare analysis model
    ModelExtFactory.getModelExtensionFactory().prepareAnalysisModel(modules, this);

    // iterate over all the type modules
    for (IModule module : modules) {

      // create the module artifact
      this.getModuleArtifact(module);

      // iterate over all contained resources
      for (IMovableUnit movableUnit : module.getMovableUnits()) {

        //
        if (progressMonitor != null) {
          progressMonitor.worked(1);
        }

        // TODO!!
        if (getConfiguration().isSourceContent()) {

          if (movableUnit.hasAssociatedSourceResource()) {
            setupIt(movableUnit.getAssociatedSourceResource());
          } else {
            for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
              setupIt(moduleResource);
            }
          }
        }
        else if (getConfiguration().isBinaryContent()) {

          if (movableUnit.hasAssociatedBinaryResources()) {
            for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
              setupIt(moduleResource);
            }
          } else {
            setupIt(movableUnit.getAssociatedSourceResource());
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

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   * @param resource
   */
  private void setupIt(IModuleResource resource) {

    //
    if (ModelExtFactory.getModelExtensionFactory().shouldAddResourceArtifact(resource)) {
      IResourceArtifact resourceArtifact = this.getResourceArtifact(resource);
      ModelExtFactory.getModelExtensionFactory().setupResourceArtifact(resourceArtifact, resource);
    }
  }
}
