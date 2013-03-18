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
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ArtifactStageChangedEvent {

  /** - */
  private final ArtifactStageChangeReason _reason;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactStageChangedEvent}.
   * </p>
   * 
   * @param reason
   */
  ArtifactStageChangedEvent(ArtifactStageChangeReason reason) {
    this._reason = reason;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ArtifactStageChangeReason getReason() {
    return this._reason;
  }
}
