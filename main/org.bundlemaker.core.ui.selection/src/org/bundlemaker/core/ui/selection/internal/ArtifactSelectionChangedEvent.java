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
package org.bundlemaker.core.ui.selection.internal;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.selection.IArtifactSelection;
import org.bundlemaker.core.ui.selection.IArtifactSelectionChangedEvent;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionChangedEvent implements IArtifactSelectionChangedEvent {

  /**
   * the new selection
   */
  private final IArtifactSelection _selection;

  public ArtifactSelectionChangedEvent(IArtifactSelection selection) {
    Assert.isNotNull(selection, "The parameter 'selection' must not be null");

    _selection = selection;
  }

  @Override
  public IArtifactSelection getSelection() {
    return _selection;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IProviderSelection#getSelectionId()
   */
  public String getSelectionId() {
    return _selection.getSelectionId();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IProviderSelection#getProviderId()
   */
  public String getProviderId() {
    return _selection.getProviderId();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IArtifactSelection#getSelectedArtifacts()
   */
  public List<IBundleMakerArtifact> getSelectedArtifacts() {
    return _selection.getSelectedArtifacts();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @see org.bundlemaker.core.ui.selection.IArtifactSelection#useChildrenOfSelectedArtifacts()
   */
  public boolean useChildrenOfSelectedArtifacts() {
    return _selection.useChildrenOfSelectedArtifacts();
  }
}
