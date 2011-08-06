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
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
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
      IModifiableModularizedSystem modifiableModularizedSystem, ArtifactModelConfiguration configuration) {

    Assert.isNotNull(bundleMakerProject);
    Assert.isNotNull(modifiableModularizedSystem);

    // default
    configuration = configuration == null ? new ArtifactModelConfiguration() : configuration;

    try {

      //
      Assert.isNotNull(modifiableModularizedSystem);
      Assert.isNotNull(configuration);

      //
      DefaultArtifactCache artifactCache = new DefaultArtifactCache(modifiableModularizedSystem, configuration);
      //
      return new DependencyModel(modifiableModularizedSystem, artifactCache.transform());

    } catch (CoreException e) {
      System.out.println(" --> Error in ModelTransformer.transformWithAggregatedTypes: " + e);
      e.printStackTrace();
      throw new RuntimeException("Error in ModelTransformer.transformWithAggregatedTypes: " + e.getMessage(), e);
    }
  }
}
