/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.event.stage;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public enum ArtifactStageAddMode {

  autoAddSelectedArtifacts("Automatically add selected Artifacts"), //
  autoAddChildrenOfSelectedArtifacts("Automatically add children of selected Artifacts"), //
  doNotAutomaticallyAddArtifacts("Manually add Artifacts");

  private final String _label;

  private ArtifactStageAddMode(String label) {
    this._label = label;
  }

  @Override
  public String toString() {
    return this._label;
  }

  /**
   * Returns true if this instance is one of the "auto add" modes (auto add artifacts or auto add children)
   * 
   * @return
   */
  public boolean isAutoAddMode() {
    return (this == ArtifactStageAddMode.autoAddChildrenOfSelectedArtifacts || this == ArtifactStageAddMode.autoAddSelectedArtifacts);
  }

}
