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
package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.internal.analysis.model.ArtifactContainer;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
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
  private GenericCache<IPath, ArtifactContainer>            _classificationCache;

  /** - */
  private GenericCache<IModule, ArtifactContainer>          _moduleCache;

  /** - */
  private GenericCache<ModulePackageKey, ArtifactContainer> _packageCache;

  /** - */
  private GenericCache<ModuleResourceKey, IArtifact>        _resourceCache;

  /** - */
  private GenericCache<IType, IArtifact>                    _typeCache;

  /** - */
  private IModularizedSystem                                _modularizedSystem;

  /** - */
  private ArtifactContainer                                 _rootArtifact;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ArtifactCache(IModularizedSystem modularizedSystem) {
    this(modularizedSystem, new ArtifactContainer(ArtifactType.Package, "root"));
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifact
   */
  public ArtifactCache(IModularizedSystem modularizedSystem, ArtifactContainer artifact) {

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
  public ArtifactContainer getRootArtifact() {
    return _rootArtifact;
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
  public Resource2IArtifactAdapter getResourceArtifact(IResource resource) {

    Assert.isNotNull(resource);

    //
    ModuleResourceKey key = new ModuleResourceKey(resource.getAssociatedResourceModule(_modularizedSystem),
        resource.getPath());

    //
    try {

      //
      return (Resource2IArtifactAdapter) _resourceCache.getOrCreate(key);

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

    // STEP 2: CLASSIFICATION CACHE
    _classificationCache = new GenericCache<IPath, ArtifactContainer>() {

      @Override
      protected ArtifactContainer create(IPath classification) {

        //
        if (classification == null || classification.isEmpty()) {
          return (ArtifactContainer) ArtifactCache.this._rootArtifact;
        }

        //
        IArtifact baseArtifact = _classificationCache.getOrCreate(classification.removeLastSegments(1));

        //
        ArtifactContainer result = new ArtifactContainer(ArtifactType.Group, classification.lastSegment());

        //
        result.setParent(baseArtifact);
        baseArtifact.getChildren().add(result);

        //
        return result;
      }
    };

    // STEP 3: MODULE CACHE
    _moduleCache = new GenericCache<IModule, ArtifactContainer>() {

      @Override
      protected ArtifactContainer create(IModule typeModule) {

        // get the parent
        IArtifact parent = typeModule.hasClassification() ? _classificationCache.getOrCreate(typeModule
            .getClassification()) : ArtifactCache.this._rootArtifact;

        //
        ArtifactContainer artifactContainer = new ArtifactContainer(ArtifactType.Module, typeModule
            .getModuleIdentifier().getName());

        //
        artifactContainer.setParent(parent);
        parent.getChildren().add(artifactContainer);

        //
        return artifactContainer;
      }
    };

    // STEP 4: PACKAGE CACHE
    _packageCache = new GenericCache<ModulePackageKey, ArtifactContainer>() {

      @Override
      protected ArtifactContainer create(ModulePackageKey modulePackageKey) {

        // get the parent
        IArtifact parent = _moduleCache.getOrCreate(modulePackageKey.getModule());

        //
        ArtifactContainer artifactContainer = new ArtifactContainer(ArtifactType.Package,
            modulePackageKey.getPackageName());

        //
        artifactContainer.setParent(parent);
        parent.getChildren().add(artifactContainer);

        //
        return artifactContainer;
      }
    };

    // SETP 5: RESOURCE CACHE
    _resourceCache = new GenericCache<ModuleResourceKey, IArtifact>() {

      @Override
      protected IArtifact create(ModuleResourceKey key) {

        // compute the package name
        int lastIndex = key.getResourcePath().lastIndexOf('/');
        String packageName = lastIndex != -1 ? key.getResourcePath().substring(0, lastIndex) : "";
        packageName = packageName.replace('/', '.');

        // get the module package
        ModulePackageKey modulePackageKey = new ModulePackageKey(key.getResourceModule(), packageName);

        // get the parent
        ArtifactContainer parent = _packageCache.getOrCreate(modulePackageKey);

        //
        IArtifact artifact = new Resource2IArtifactAdapter(key.getResourcePath(), ArtifactCache.this, parent);
        parent.getChildren().add(artifact);

        //
        return artifact;
      }
    };

    // STEP 6: TYPE CACHE
    _typeCache = new GenericCache<IType, IArtifact>() {

      @Override
      protected IArtifact create(IType type) {

        //
        IResource resource = type.hasBinaryResource() ? type.getBinaryResource() : type.getSourceResource();

        //
        IModule module = resource != null ? resource.getAssociatedResourceModule(_modularizedSystem) : type
            .getModule(_modularizedSystem);

        // get the module package
        ModulePackageKey modulePackageKey = new ModulePackageKey(module, type.getPackageName());

        // get the parent
        ArtifactContainer parent = _packageCache.getOrCreate(modulePackageKey);

        //
        IArtifact artifact = new Type2IArtifactAdapter(type, ArtifactCache.this, parent);
        parent.getChildren().add(artifact);

        //
        return artifact;
      }
    };
  }
}
