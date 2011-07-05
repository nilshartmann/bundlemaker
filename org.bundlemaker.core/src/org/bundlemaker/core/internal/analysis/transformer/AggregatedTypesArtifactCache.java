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
import org.bundlemaker.core.internal.analysis.AdapterPackage2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterType2IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;

/**
 * <p>
 * Implements an cache for the base artifacts.
 * </p>
 */
public class AggregatedTypesArtifactCache extends AbstractBaseArtifactCache {

  /**
   * <p>
   * Creates a new instance of type {@link AggregatedTypesArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AggregatedTypesArtifactCache(IModifiableModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * Creates a new instance of type {@link AggregatedTypesArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifact
   */
  protected AggregatedTypesArtifactCache(IModularizedSystem modularizedSystem, IAdvancedArtifact artifact) {
    super(modularizedSystem, artifact);
  }

  @Override
  protected IAdvancedArtifact transform(IModularizedSystem modularizedSystem, IModule[] modules) {

    //
    for (IModule module : modules) {
      this.getModuleArtifact(module);
    }

    // iterate over all the type modules
    for (IModule typeModule : modules) {

      // iterate over all contained types
      for (IType type : typeModule.getContainedTypes()) {

        // filter local or anonymous type names
        if (!type.isInnerType() && isPrimaryType(type)) {

          // create the artifact
          this.getTypeArtifact(type, true);
        }
      }

      // cast to 'IResourceModule'
      if (typeModule instanceof IResourceModule) {

        // get the resource module
        IResourceModule resourceModule = (IResourceModule) typeModule;

        // // iterate over all contained source resources
        // for (IResource resource : resourceModule.getResources(ContentType.SOURCE)) {
        // if (!resource.containsTypes()) {
        // // create the artifact
        // artifactCache.getResourceArtifact(resource);
        // }
        // }

        // iterate over all contained binary resources
        for (IResource resource : resourceModule.getResources(ContentType.BINARY)) {
          if (!resource.containsTypes()) {

            // create the artifact
            this.getResourceArtifact(resource);
          }
        }
      }
    }

    // return the root artifact
    return this.getRootArtifact();
  }

  private boolean isPrimaryType(IType type) {

    if (!type.hasSourceResource()) {
      return true;
    }
    IResource sourceResource = type.getSourceResource();

    return sourceResource.isPrimaryType(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<ModulePackageKey, AbstractArtifactContainer> createPackageCache() {

    return new GenericCache<ModulePackageKey, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(ModulePackageKey modulePackageKey) {

        // get the parent
        IArtifact parent = getModuleCache().getOrCreate(modulePackageKey.getModule());

        //
        return new AdapterPackage2IArtifact(modulePackageKey.getPackageName(), parent);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<ModuleResourceKey, AbstractArtifactContainer> createResourceCache() {

    return new GenericCache<ModuleResourceKey, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(ModuleResourceKey key) {

        // compute the package name
        String packageName = key.getResource().getPackageName();

        // get the module package
        ModulePackageKey modulePackageKey = new ModulePackageKey(key.getResourceModule(), packageName);

        // get the parent
        AbstractArtifactContainer parent = getPackageCache().getOrCreate(modulePackageKey);

        //
        return new AdapterResource2IArtifact(key.getResource(), false, parent);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<IType, IArtifact> createTypeCache() {

    return new GenericCache<IType, IArtifact>() {

      @Override
      protected IArtifact create(IType type) {

        // get the associated resources
        IResource binaryResource = type.getBinaryResource();

        // get the associated module
        IModule module = binaryResource != null ? binaryResource.getAssociatedResourceModule(getModularizedSystem())
            : type.getModule(getModularizedSystem());

        // get the module package
        ModulePackageKey modulePackageKey = new ModulePackageKey(module, type.getPackageName());

        // get the parent
        AbstractArtifactContainer parent = getPackageCache().getOrCreate(modulePackageKey);

        //
        AdapterType2IArtifact artifact = new AdapterType2IArtifact(type, AggregatedTypesArtifactCache.this, parent,
            true);
        artifact.setAggregateInnerTypes(true);

        //
        return artifact;
      }
    };
  }
}
