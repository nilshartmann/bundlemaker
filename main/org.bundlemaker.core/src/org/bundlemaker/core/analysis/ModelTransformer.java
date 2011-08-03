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

import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.analysis.DependencyModel;
import org.bundlemaker.core.internal.analysis.transformer.AggregatedTypesArtifactCache;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 */
public class ModelTransformer {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modifiableModularizedSystem
   * @return
   */
  public static IDependencyModel getDependencyModel(IBundleMakerProject bundleMakerProject,
      IModifiableModularizedSystem modifiableModularizedSystem) {
    //
    return new DependencyModel(bundleMakerProject, modifiableModularizedSystem);
  }

  public static IAdvancedArtifact transform(IModifiableModularizedSystem modularizedSystem) throws CoreException {
    return transform(modularizedSystem, ContentType.BINARY, false);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws CoreException
   */
  public static IAdvancedArtifact transform(IModifiableModularizedSystem modularizedSystem, ContentType contentType,
      boolean hierarchical) throws CoreException {
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(contentType);

    DefaultArtifactCache artifactCache = new DefaultArtifactCache(modularizedSystem);
    artifactCache.setContentType(contentType);
    artifactCache.setHierarchical(hierarchical);
    IAdvancedArtifact root = artifactCache.transform();
    return root;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws CoreException
   */
  public static IAdvancedArtifact transformWithAggregatedTypes(IModifiableModularizedSystem modularizedSystem)
      throws CoreException {
    return new AggregatedTypesArtifactCache(modularizedSystem).transform();
  }
}
