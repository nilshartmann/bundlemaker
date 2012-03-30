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

import java.util.Collection;

import org.bundlemaker.analysis.model.IArtifact;

/**
 * A central selection service for selected {@link IArtifact artifacts}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IArtifactSelectionService {

  /**
   * Return the current selection of the specified provider.
   * 
   * @param selectionProviderId
   *          the provider id. must not be null
   * @return the selection, or null if there is no selection for the specified provider
   */
  public IArtifactSelection getSelection(String selectionProviderId);

  /**
   * @param selectionProviderId
   * @param selectedArtifacts
   *          the (newly) selected artifacts. might be null, resulting in an empty selection
   */
  public void setSelection(String selectionProviderId, Collection<IArtifact> selectedArtifacts);

  /**
   * Registers an {@link IArtifactSelectionListener} for the specified providerId.
   * 
   * <p>
   * The listener is invoked if the provider's selection changes. If the providerId is null, the listener is invoked for
   * selection change on any provider
   * 
   * @param providerId
   *          the providerId or null
   * @param listener
   *          the listener. Must not be null
   */
  public void addArtifactSelectionListener(String providerId, IArtifactSelectionListener listener);

  // /**
  // * Registers an {@link IArtifactSelectionListener} for all providers.
  // *
  // * <p>
  // * Same as calling <tt>addArtifactSelectionListener(null, listener)</tt>
  // *
  // * @param listener
  // * the listener. Must not be null
  // */
  //
  // public void addArtifactSelectionListener(IArtifactSelectionListener listener);

  /**
   * Removes the specified listener
   * 
   * @param listener
   */
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener);

}
