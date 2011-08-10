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

/**
 * <p>
 * Provides access to the global {@link ArtifactModelConfiguration} that is used to configure the artifact tree view.
 * </p>
 * <p>
 * To retrieve the global instance of this class, you have to call <code><pre>
 * IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
        .getArtifactModelConfigurationProvider();
 * </pre></code>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @noimplement This interface must not be implemented by clients
 */
public interface IArtifactModelConfigurationProvider {

  /**
   * <p>
   * Returns the current {@link ArtifactModelConfiguration}.
   * </p>
   * 
   * @return the current {@link ArtifactModelConfiguration}.
   */
  public ArtifactModelConfiguration getArtifactModelConfiguration();
}