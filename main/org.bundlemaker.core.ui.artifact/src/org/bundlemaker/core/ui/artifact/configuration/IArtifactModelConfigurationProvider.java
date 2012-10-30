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
package org.bundlemaker.core.ui.artifact.configuration;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;

/**
 * <p>
 * Provides access to the global {@link AnalysisModelConfiguration} that is used to configure the artifact tree view.
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
   * Returns the current {@link AnalysisModelConfiguration}.
   * </p>
   * 
   * @return the current {@link AnalysisModelConfiguration}.
   */
  public AnalysisModelConfiguration getArtifactModelConfiguration();
}
