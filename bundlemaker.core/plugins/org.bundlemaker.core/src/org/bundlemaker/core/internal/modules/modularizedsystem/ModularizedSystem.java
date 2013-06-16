/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.modules.modularizedsystem;

import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.algorithms.AdjacencyList;
import org.bundlemaker.core.internal.analysis.ModelTransformerCache;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractCachingModularizedSystem {

  /** - */
  private ModelTransformerCache _transformerCache = null;

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IModuleAwareBundleMakerProject project) {
    super(name, project);

    _transformerCache = new ModelTransformerCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    if (ITypeModularizedSystem.class.equals(clazz)) {
      return (T) getTypeModularizedSystem();
    }

    //
    if (clazz.isAssignableFrom(this.getClass())) {
      return (T) this;
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(Class adapter) {

    return adaptAs(adapter);
  }

  /**
   * {@inheritDoc}
   */
  public IRootArtifact getAnalysisModel(IAnalysisModelConfiguration configuration) {
    return (IRootArtifact) _transformerCache.getArtifactModel(this, configuration, null);
  }

  /**
   * {@inheritDoc}
   */
  public IRootArtifact getAnalysisModel(IAnalysisModelConfiguration configuration, IProgressMonitor progressMonitor) {

    //
    if (progressMonitor == null) {
      progressMonitor = new NullProgressMonitor();
    }

    //
    try {

      //
      progressMonitor.beginTask("Creating analysis model...", 201);
      progressMonitor.subTask("Transforming...");
      progressMonitor.worked(1);

      //
      IRootArtifact root = (IRootArtifact) _transformerCache.getArtifactModel(this, configuration,
          new SubProgressMonitor(progressMonitor, 100));

      // pre initialize
      progressMonitor.subTask("Initializing...");

      AdjacencyList.computeAdjacencyList(root.getChildren(), new SubProgressMonitor(progressMonitor, 100));

      //
      return root;

    } finally {
      progressMonitor.done();
    }
  }

}
