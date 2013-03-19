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

/**
 * An event describing a changed dependency
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencySelectionChangedEvent extends IDependencySelection {

  /**
   * Not null but might be empty
   */
  public IDependencySelection getSelection();

}
