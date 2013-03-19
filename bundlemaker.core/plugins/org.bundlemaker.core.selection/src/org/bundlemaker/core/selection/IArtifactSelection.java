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
package org.bundlemaker.core.selection;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;

/**
 * A selection of {@link IBundleMakerArtifact} objects.
 * 
 * @author Nils Hartmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelection extends IProviderSelection {

  /**
   * The <b>effective</b> selected artifacts, that is, in case <tt>useChildrenOfSelectedArtifacts</tt> is set to true,
   * this method returns the children of the selected artifacts.
   * 
   * <p>
   * Clients will normally use this method
   * </p>
   */
  List<IBundleMakerArtifact> getEffectiveSelectedArtifacts();

  /**
   * The selected artifacts, regardless of <tt>useChildrenOfSelectedArtifacts</tt>.
   * 
   * <p>
   * Cients will normally use {@link #getEffectiveSelectedArtifacts()}
   * 
   * @return an <b>unmodifiable</b> list of {@link IBundleMakerArtifact IArtifacts}. Never null but might be empty.
   */
  List<IBundleMakerArtifact> getSelectedArtifacts();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasSelectedArtifacts();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IRootArtifact getRootArtifact();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean useChildrenOfSelectedArtifacts();
}
