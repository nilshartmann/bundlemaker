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
package org.bundlemaker.core.analysis.internal.transformer;

import org.bundlemaker.core.analysis.internal.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.internal.AdapterGroup2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterModularizedSystem2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterModule2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterPackage2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterResource2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterType2IArtifact;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Implements an cache for the base artifacts.
 * </p>
 */
public class ArtifactCache {

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

  /** - */
  private IModularizedSystem                                         _modularizedSystem;

  /** - */
  private AbstractArtifactContainer                                  _rootArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ArtifactCache(IModularizedSystem modularizedSystem) {
    this(modularizedSystem, new AdapterModularizedSystem2IArtifact(modularizedSystem));
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifact
   */
  public ArtifactCache(IModularizedSystem modularizedSystem, AbstractArtifactContainer artifact) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(artifact);

    // set the modularized system
    _modularizedSystem = modularizedSystem;

    // create the root artifact
    _rootArtifact = artifact;

    // initialize the caches
    initCaches();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifact getRootArtifact() {
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
  public IArtifact getArtifact(IType type) {

    try {

      //
      return _typeCache.getOrCreate(type);

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
  public IArtifact getArtifact(String fullyQualifiedName) {

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
      return getArtifact(targetType);

    } catch (AmbiguousElementException e) {
      System.err.println("AmbExc " + fullyQualifiedName);
      return null;
    }

  }

  /**
   * <p>
   * </p>
   */
  private void initCaches() {

    // STEP 1: GROUP CACHE
    _groupCache = new GenericCache<IPath, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IPath classification) {

        //
        if (classification == null || classification.isEmpty()) {
          return (AbstractArtifactContainer) ArtifactCache.this._rootArtifact;
        }

        //
        IArtifact parent = _groupCache.getOrCreate(classification.removeLastSegments(1));

        //
        AdapterGroup2IArtifact result = new AdapterGroup2IArtifact(classification.lastSegment(), parent);

        //
        return result;
      }
    };

    // STEP 2: MODULE CACHE
    _moduleCache = new GenericCache<IModule, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IModule module) {

        // get the parent
        IArtifact parent = module.hasClassification() ? _groupCache.getOrCreate(module.getClassification())
            : ArtifactCache.this._rootArtifact;

        //
        AbstractArtifactContainer artifactContainer = module instanceof IResourceModule ? new AdapterResourceModule2IArtifact(
            (IResourceModule) module, parent) : new AdapterModule2IArtifact(module, parent);

        //
        return artifactContainer;
      }
    };

    // STEP 4: PACKAGE CACHE
    _packageCache = new GenericCache<ModulePackageKey, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(ModulePackageKey modulePackageKey) {

        // get the parent
        IArtifact parent = _moduleCache.getOrCreate(modulePackageKey.getModule());

        //
        AbstractArtifactContainer artifactContainer = new AdapterPackage2IArtifact(modulePackageKey.getPackageName(),
            parent);

        //
        return artifactContainer;
      }
    };

    // SETP 5: RESOURCE CACHE
    _resourceCache = new GenericCache<ModuleResourceKey, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(ModuleResourceKey key) {

        // compute the package name
        String packageName = key.getResource().getPackageName();

        // get the module package
        ModulePackageKey modulePackageKey = new ModulePackageKey(key.getResourceModule(), packageName);

        // get the parent
        AbstractArtifactContainer parent = _packageCache.getOrCreate(modulePackageKey);

        //
        AbstractArtifactContainer artifact = new AdapterResource2IArtifact(key.getResource(), false, parent);

        //
        return artifact;
      }
    };

    // STEP 6: TYPE CACHE
    _typeCache = new GenericCache<IType, IArtifact>() {

      @Override
      protected IArtifact create(IType type) {

        //
        IResource resource = type.hasSourceResource() ? type.getSourceResource() : type.getBinaryResource();

        //
        IModule module = resource != null ? resource.getAssociatedResourceModule(_modularizedSystem) : type
            .getModule(_modularizedSystem);

        // get the parent
        AbstractArtifactContainer parent = null;

        if (module instanceof IResourceModule) {

          // get the module package
          ModuleResourceKey resourceKey = new ModuleResourceKey((IResourceModule) module, resource);

          //
          parent = _resourceCache.getOrCreate(resourceKey);

        } else {

          // get the module package
          ModulePackageKey modulePackageKey = new ModulePackageKey(module, type.getPackageName());

          // get the parent
          parent = _packageCache.getOrCreate(modulePackageKey);
        }

        //
        IArtifact artifact = new AdapterType2IArtifact(type, ArtifactCache.this, parent);

        //
        return artifact;
      }
    };
  }
}
