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
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.internal.analysis.transformer.caches.GroupCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.MissingTypeCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.MissingTypePackageCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.PackageCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.ResourceCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.TypeCache;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Implements an cache for the base artifacts.
 * </p>
 */
public class DefaultArtifactCache extends AbstractConfigurableArtifactCache {

  /**
   * <p>
   * Creates a new instance of type {@link DefaultArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param modelConfiguration
   */
  public DefaultArtifactCache(IModifiableModularizedSystem modularizedSystem,
      ArtifactModelConfiguration modelConfiguration) {
    super(modularizedSystem, modelConfiguration);
  }

  /**
   * <p>
   * Creates a new instance of type {@link DefaultArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param rootArtifact
   * @param modelConfiguration
   */
  public DefaultArtifactCache(IModularizedSystem modularizedSystem, IAdvancedArtifact rootArtifact,
      ArtifactModelConfiguration modelConfiguration) {
    super(modularizedSystem, rootArtifact, modelConfiguration);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  protected IAdvancedArtifact transform(IModule[] modules) {

    // missing types
    for (IModule module : modules) {
      if (module instanceof IResourceModule) {
        for (IReference iReference : getModularizedSystem().getUnsatisfiedReferences((IResourceModule) module)) {
          getMissingTypeCache().getOrCreate(iReference.getFullyQualifiedName());
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
            || (getConfiguration().isAggregateInnerTypes() && !type.isInnerType() && handleAsPrimaryType(type))) {

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

          // iterate over all contained binary resources
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<ModulePackageKey, AbstractArtifactContainer> createPackageCache() {
    return new PackageCache(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<ModuleResourceKey, AbstractArtifactContainer> createResourceCache() {
    return new ResourceCache(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<IType, IArtifact> createTypeCache() {
    return new TypeCache(this);
  }

  /**
   * {@inheritDoc}
   */
  protected GenericCache<IPath, AbstractArtifactContainer> createGroupCache() {
    return new GroupCache(this);
  }

  /**
   * {@inheritDoc}
   */
  protected GenericCache<IModule, AbstractArtifactContainer> createModuleCache() {
    return new ModuleCache(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<String, AbstractArtifactContainer> createMissingTypePackageCache() {
    return new MissingTypePackageCache(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericCache<String, AbstractArtifactContainer> createMissingTypeCache() {
    return new MissingTypeCache(this);
  }
}
