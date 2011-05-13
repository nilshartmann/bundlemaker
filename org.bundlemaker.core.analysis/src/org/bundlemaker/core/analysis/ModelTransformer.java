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

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.internal.DependencyModel;
import org.bundlemaker.core.analysis.internal.transformer.AggregatedTypesArtifactCache;
import org.bundlemaker.core.analysis.internal.transformer.DefaultArtifactCache;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
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

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws CoreException
   */
  public static IArtifact transform(IModifiableModularizedSystem modularizedSystem) throws CoreException {
    return new DefaultArtifactCache(modularizedSystem).transform();
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   * @throws CoreException
   */
  public static IArtifact transformWithAggregatedTypes(IModifiableModularizedSystem modularizedSystem)
      throws CoreException {
    return new AggregatedTypesArtifactCache(modularizedSystem).transform();
  }
}
