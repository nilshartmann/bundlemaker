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

import org.bundlemaker.core.analysis.internal.transformer.ArtifactCache;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
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
  public IAdvancedArtifact transform(IModularizedSystem modularizedSystem) throws CoreException {

    return transform(modularizedSystem, modularizedSystem.getAllModules().toArray(new IModule[0]));
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
   * @param artifact
   */
  public static void dumpArtifact(IArtifact artifact) {
    dumpArtifact(artifact, 0);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws Exception
   */
  private IAdvancedArtifact transform(IModularizedSystem modularizedSystem, IModule[] modules) {

    // the artifact cache
    ArtifactCache artifactCache = new ArtifactCache((IModifiableModularizedSystem) modularizedSystem);

    //
    for (IModule module : modules) {
      artifactCache.getModuleArtifact(module);
    }

    // iterate over all the type modules
    for (IModule typeModule : modules) {

      // iterate over all contained types
      for (IType type : typeModule.getContainedTypes()) {

        // filter local or anonymous type names
        if (!type.isLocalOrAnonymousType()) {

          // create the artifact
          artifactCache.getArtifact(type);
        }
      }

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
          // artifactCache.getResourceArtifact(resource);
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

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param level
   */
  private static void dumpArtifact(IArtifact artifact, int level) {

    //
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < level; i++) {
      builder.append("  ");
    }

    System.out.println(builder.toString() + artifact.getType() + " : " + artifact.getQualifiedName());

    for (IArtifact child : artifact.getChildren()) {
      dumpArtifact(child, level + 1);
    }
  }
}
