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

/**
 * A listener that is invoked when a selection changed.
 * 
 * <p>
 * This method must be implemented by clients
 * 
 * @author Nils Hartmann
 */
public interface IArtifactSelectionListener {

  /**
   * Is invoked when a selection has been changed
   * 
   * @param event
   *          the event describing the selection. Never null.
   */
  public void artifactSelectionChanged(IArtifactSelectionChangedEvent event);

}
