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

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.internal.analysis.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterGroup2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterModularizedSystem2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterModule2IArtifact;
import org.bundlemaker.core.internal.analysis.model.ArtifactContainer;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
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
  private GenericCache<IPath, AbstractArtifactContainer>    _groupCache;

  /** - */
  private GenericCache<IModule, AbstractArtifactContainer>  _moduleCache;

  /** - */
  private GenericCache<ModulePackageKey, ArtifactContainer> _packageCache;

  /** - */
  private GenericCache<ModuleResourceKey, IArtifact>        _resourceCache;

  /** - */
  private GenericCache<IType, IArtifact>                    _typeCache;

  /** - */
  private IModularizedSystem                                _modularizedSystem;

  /** - */
  private AbstractArtifactContainer                         _rootArtifact;

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
    ModuleResourceKey key = new ModuleResourceKey(resource.getAssociatedResourceModule(_modularizedSystem),
        resource.getPath());

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

    // STEP 2: GROUP CACHE
    _groupCache = new GenericCache<IPath, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IPath classification) {

        //
        if (classification == null || classification.isEmpty()) {
          return (AbstractArtifactContainer) ArtifactCache.this._rootArtifact;
        }

        //
        IArtifact parent = _groupCache.getOrCreate(classification.removeLastSegments(1));

        System.out.println("Create " + classification + " : " + parent);

        //
        AdapterGroup2IArtifact result = new AdapterGroup2IArtifact(classification.lastSegment(), parent);

        //
        return result;
      }
    };

    // STEP 3: MODULE CACHE
    _moduleCache = new GenericCache<IModule, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IModule module) {

        // get the parent
        IArtifact parent = module.hasClassification() ? _groupCache.getOrCreate(module.getClassification())
            : ArtifactCache.this._rootArtifact;

        System.out.println("Create " + module.getModuleIdentifier() + " : " + parent);

        //
        AbstractArtifactContainer artifactContainer = module instanceof IResourceModule ? new AdapterResourceModule2IArtifact(
            (IResourceModule) module, parent) : new AdapterModule2IArtifact(module, parent);

        //
        return artifactContainer;
      }
    };

    // // STEP 4: PACKAGE CACHE
    // _packageCache = new GenericCache<ModulePackageKey, ArtifactContainer>() {
    //
    // @Override
    // protected ArtifactContainer create(ModulePackageKey modulePackageKey) {
    //
    // // get the parent
    // IArtifact parent = _moduleCache.getOrCreate(modulePackageKey.getModule());
    //
    // //
    // ArtifactContainer artifactContainer = new ArtifactContainer(ArtifactType.Package,
    // modulePackageKey.getPackageName());
    //
    // //
    // artifactContainer.setParent(parent);
    // parent.getChildren().add(artifactContainer);
    //
    // //
    // return artifactContainer;
    // }
    // };
    //
    // // SETP 5: RESOURCE CACHE
    // _resourceCache = new GenericCache<ModuleResourceKey, IArtifact>() {
    //
    // @Override
    // protected IArtifact create(ModuleResourceKey key) {
    //
    // // compute the package name
    // int lastIndex = key.getResourcePath().lastIndexOf('/');
    // String packageName = lastIndex != -1 ? key.getResourcePath().substring(0, lastIndex) : "";
    // packageName = packageName.replace('/', '.');
    //
    // // get the module package
    // ModulePackageKey modulePackageKey = new ModulePackageKey(key.getResourceModule(), packageName);
    //
    // // get the parent
    // ArtifactContainer parent = _packageCache.getOrCreate(modulePackageKey);
    //
    // //
    // IArtifact artifact = new AdapterResource2IArtifact(key.getResourcePath(), ArtifactCache.this, parent);
    // parent.getChildren().add(artifact);
    //
    // //
    // return artifact;
    // }
    // };
    //
    // // STEP 6: TYPE CACHE
    // _typeCache = new GenericCache<IType, IArtifact>() {
    //
    // @Override
    // protected IArtifact create(IType type) {
    //
    // //
    // IResource resource = type.hasBinaryResource() ? type.getBinaryResource() : type.getSourceResource();
    //
    // //
    // IModule module = resource != null ? resource.getAssociatedResourceModule(_modularizedSystem) : type
    // .getModule(_modularizedSystem);
    //
    // // get the module package
    // ModulePackageKey modulePackageKey = new ModulePackageKey(module, type.getPackageName());
    //
    // // get the parent
    // ArtifactContainer parent = _packageCache.getOrCreate(modulePackageKey);
    //
    // //
    // IArtifact artifact = new AdapterType2IArtifact(type, ArtifactCache.this, parent);
    // parent.getChildren().add(artifact);
    //
    // //
    // return artifact;
    // }
    // };
  }
}
