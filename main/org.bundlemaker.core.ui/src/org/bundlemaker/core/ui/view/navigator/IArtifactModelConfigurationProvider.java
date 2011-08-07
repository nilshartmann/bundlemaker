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
 * Provides access to the global {@link ArtifactModelConfiguration} that is used to configure the artifact tree display
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @noimplement This interface must not be implemented by clients
 */
public interface IArtifactModelConfigurationProvider {

  public ArtifactModelConfiguration getArtifactModelConfiguration();

}
