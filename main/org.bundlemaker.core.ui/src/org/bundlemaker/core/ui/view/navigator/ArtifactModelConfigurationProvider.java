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
package org.bundlemaker.core.ui.view.navigator;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration.ResourcePresentation;

/**
 * TODO: persist settings and reload on Eclipse startup
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactModelConfigurationProvider implements IArtifactModelConfigurationProvider {

  /**
   * TODO
   */
  private final static ArtifactModelConfigurationProvider _instance = new ArtifactModelConfigurationProvider();

  /**
   * TODO
   * 
   * @return
   */
  public static IArtifactModelConfigurationProvider instance() {
    return _instance;
  }

  private ArtifactModelConfiguration _configuration;

  public ArtifactModelConfigurationProvider() {
    ArtifactModelConfiguration configuration = new ArtifactModelConfiguration();
    configuration.setAggregateInnerTypes(true);
    configuration.setResourcePresentation(ResourcePresentation.ONLY_NON_TYPE_RESOURCES);

    _configuration = configuration;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.view.navigator.IArtifactModelConfigurationProvider#getCurrentArtifactModelConfiguration()
   */
  @Override
  public ArtifactModelConfiguration getCurrentArtifactModelConfiguration() {
    return _configuration;
  }

}
