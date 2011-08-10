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
package org.bundlemaker.analysis.ui.selection;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;

/**
 * A selection of {@link IArtifact} objects
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelection {

  /**
   * @return the selection provider's Id
   */
  public String getProviderId();

  /**
   * The selected artifacts. Never null but might be empty.
   * 
   * @return an <b>unmodifiable</b> list of {@link IArtifact IArtifacts}
   */
  public List<IArtifact> getSelectedArtifacts();

}