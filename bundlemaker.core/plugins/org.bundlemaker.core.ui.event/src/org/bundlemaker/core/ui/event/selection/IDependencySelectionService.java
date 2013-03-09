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

import org.bundlemaker.core.analysis.IDependency;

/**
 * A central selection service for selected {@link IDependency dependencies}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencySelectionService {

  /**
   * Return the current selection with the specified selectionId.
   * 
   * @param selectionId
   *          the selection id. must not be null
   * @return the selection, or null if there is no selection for the specified selectionId
   */
  public IDependencySelection getSelection(String selectionId);

  /**
   * <p>
   * </p>
   * 
   * @param artifactSelectionId
   * @param selectionProviderId
   * @param selectedArtifacts
   *          the (newly) selected dependencies. might be null, resulting in an empty selection
   */
  public void setSelection(String artifactSelectionId, String selectionProviderId,
      Collection<IDependency> selectedArtifacts);

  /**
   * <p>
   * </p>
   * 
   * @param artifactSelectionId
   * @param selectionProviderId
   * @param dependency
   */
  public void setSelection(String artifactSelectionId, String selectionProviderId, IDependency dependency);

  /**
   * Registers an {@link IDependencySelectionListener} for the specified providerId.
   * 
   * <p>
   * The listener is invoked if the provider's selection changes. If the providerId is null, the listener is invoked for
   * selection change on any provider
   * 
   * @param selectionId
   *          the selectionId or null
   * @param listener
   *          the listener. Must not be null
   */
  public void addDependencySelectionListener(String selectionId, IDependencySelectionListener listener);

  /**
   * Removes the specified listener
   * 
   * @param listener
   */
  public void removeDependencySelectionListener(IDependencySelectionListener listener);

}
