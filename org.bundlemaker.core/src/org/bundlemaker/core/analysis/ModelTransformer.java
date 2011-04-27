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
package org.bundlemaker.core.analysis;

import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.internal.analysis.model.ArtifactContainer;
import org.bundlemaker.core.internal.analysis.transformer.ArtifactCache;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 */
public class ModelTransformer {

  /** - */
  private boolean _generateResourceArtifacts;

  /**
   * <p>
   * </p>
   * 
   * @param generateResourceArtifacts
   */
  public ModelTransformer(boolean generateResourceArtifacts) {
    Assert.isNotNull(generateResourceArtifacts);

    _generateResourceArtifacts = generateResourceArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws Exception
   */
  public IArtifact transform(IModularizedSystem modularizedSystem) throws CoreException {

    return transform(modularizedSystem, modularizedSystem.getAllModules().toArray(new IModule[0]), null);
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param modules
  // * @return
  // * @throws Exception
  // */
  // public IArtifact transform(IModularizedSystem modularizedSystem,
  // IModule module) throws Exception {
  //
  // //
  // return transform(modularizedSystem, new IModule[] { module });
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @return
  // * @throws Exception
  // */
  // public DependencyModel transformToDependencyModel(
  // IModularizedSystem modularizedSystem) throws Exception {
  //
  // //
  // return transformToDependencyModel(modularizedSystem, modularizedSystem
  // .getAllModules().toArray(new IModule[0]));
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param modules
  // * @return
  // * @throws Exception
  // */
  // public DependencyModel transformToDependencyModel(
  // IModularizedSystem modularizedSystem, IModule module)
  // throws Exception {
  //
  // //
  // return transformToDependencyModel(modularizedSystem,
  // new IModule[] { module });
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // * @throws Exception
  // */
  // public DependencyModel transformToDependencyModel(
  // IModularizedSystem modularizedSystem, IModule[] modules)
  // throws Exception {
  //
  // // create the result
  // DependencyModel dependencyModel = new DependencyModel("model");
  //
  // // transform
  // transform(modularizedSystem, modules,
  // (ArtifactContainer) dependencyModel.getRoot());
  //
  // // return the root artifact
  // return dependencyModel;
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param modules
  // * @return
  // * @throws Exception
  // */
  // public IArtifact transform(IModularizedSystem modularizedSystem,
  // IModule[] modules) throws Exception {
  //
  // //
  // return transform(modularizedSystem, modules, null);
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  private IArtifact transform(IModularizedSystem modularizedSystem, IModule[] modules,
      ArtifactContainer artifactContainer) {

    // the artifact cache
    ArtifactCache artifactCache = artifactContainer == null ? new ArtifactCache(modularizedSystem) : new ArtifactCache(
        modularizedSystem);

    //
    for (IModule module : modules) {
      artifactCache.getModuleArtifact(module);
    }

    // iterate over all the type modules
    for (IModule typeModule : modules) {

      // // iterate over all contained types
      // for (IType type : typeModule.getContainedTypes()) {
      //
      // // create the artifact
      // artifactCache.getArtifact(type);
      // }

      // generate resource artifacts
      if (_generateResourceArtifacts) {

        // cast to 'IResourceModule'
        if (typeModule instanceof IResourceModule) {

          // get the resource module
          IResourceModule resourceModule = (IResourceModule) typeModule;

          // // iterate over all contained source resources
          // for (IResource resource : resourceModule.getResources(ContentType.SOURCE)) {
          // if (!resource.containsTypes()) {
          // // create the artifact
          // artifactCache.getResourceArtifact(resource).setSourceResource(resource);
          // }
          // }

          // iterate over all contained binary resources
          for (IResource resource : resourceModule.getResources(ContentType.BINARY)) {
            if (!resource.containsTypes()) {
              // create the artifact
              artifactCache.getResourceArtifact(resource);
            }
          }
        }
      }
    }

    // return the root artifact
    return artifactCache.getRootArtifact();
  }
}
