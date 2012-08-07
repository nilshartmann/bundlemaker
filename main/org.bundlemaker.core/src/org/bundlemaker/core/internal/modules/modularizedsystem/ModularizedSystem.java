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

import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.ModelTransformerCache;
import org.bundlemaker.core.projectdescription.IProjectDescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractQueryableModularizedSystem {

  /** - */
  private ModelTransformerCache _transformerCache = null;

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IProjectDescription projectDescription) {
    super(name, projectDescription);

    _transformerCache = new ModelTransformerCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getArtifactModel(IArtifactModelConfiguration configuration) {

    IRootArtifact result = (IRootArtifact) _transformerCache.getArtifactModel(this, configuration);

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void afterApplyTransformations() {
    super.afterApplyTransformations();

    //
    for (IRootArtifact rootArtifact : _transformerCache.getAllArtifactModels()) {
      ((AdapterRoot2IArtifact) rootArtifact).fireArtifactModelChanged();
    }
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected void postApplyTransformations() {
  //
  // // validate the resource modules
  // for (IModifiableResourceModule module : getModifiableResourceModules()) {
  // ((ResourceModule) module).validate();
  // }
  // }
}
