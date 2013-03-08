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
package org.bundlemaker.core.ui.event.selection;

import java.util.Collection;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * A central selection service for selected {@link IBundleMakerArtifact artifacts}.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelectionService {

  /**
   * <p>
   * </p>
   * 
   * @param useChildrenOfSelectedArtifacts
   */
  public void setUseChildrenOfSelectedArtifacts(boolean useChildrenOfSelectedArtifacts);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean getUseChildrenOfSelectedArtifacts();

  /**
   * Return the current selection of the specified artifactSelectionId.
   * 
   * @param artifactSelectionId
   *          the artifactSelectionId. must not be null
   * @return the selection, or null if there is no selection for the specified artifactSelectionId
   */
  public IArtifactSelection getSelection(String artifactSelectionId);

  /**
   * <p>
   * </p>
   *
   * @param artifactSelectionId
   * @param selectionProviderId
   * @param selectedArtifacts
   */
  public void setSelection(String artifactSelectionId, String selectionProviderId,
      Collection<IBundleMakerArtifact> selectedArtifacts);
  
  /**
   * <p>
   * </p>
   * 
   * @param artifactSelectionId
   * @param selectionProviderId
   * @param selectedArtifacts
   *          the (newly) selected artifacts. might be null, resulting in an empty selection
   * @param useChildrenOfSelectedArtifacts
   */
  public void setSelection(String artifactSelectionId, String selectionProviderId,
      Collection<IBundleMakerArtifact> selectedArtifacts, boolean useChildrenOfSelectedArtifacts);

  /**
   * Registers an {@link IArtifactSelectionListener} for the specified artifactSelectionId.
   * 
   * <p>
   * The listener is invoked if the selection with the given artifactSelectionId changes.
   * 
   * @param artifactSelectionId
   *          the artifactSelectionId
   * @param listener
   *          the listener. Must not be null
   */
  public void addArtifactSelectionListener(String artifactSelectionId, IArtifactSelectionListener listener);

  /**
   * Removes the specified listener
   * 
   * @param listener
   */
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener);
}
