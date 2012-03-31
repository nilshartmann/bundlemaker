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
package org.bundlemaker.core.ui.selection;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * A selection of {@link IBundleMakerArtifact} objects.
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelection extends IProviderSelection {

  /**
   * The selected artifacts. Never null but might be empty.
   * 
   * @return an <b>unmodifiable</b> list of {@link IBundleMakerArtifact IArtifacts}
   */
  public List<IBundleMakerArtifact> getSelectedArtifacts();

}
