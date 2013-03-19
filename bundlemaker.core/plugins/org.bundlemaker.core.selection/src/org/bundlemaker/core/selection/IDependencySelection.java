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

import org.bundlemaker.core.analysis.IDependency;

/**
 * A selection of {@link IDependency} instances
 * 
 * @author Nils Hartmann
 * 
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencySelection  extends IProviderSelection {

  /**
   * The selected artifacts. Never null but might be empty.
   * 
   * @return an <b>unmodifiable</b> list of {@link IDependency IDependency-Objects}
   */
  public List<IDependency> getSelectedDependencies();

  /**
   * Returns the first element in this selection, or <code>null</code> if the selection is empty.
   * 
   * @return an element, or <code>null</code> if none
   */
  public IDependency getFirstDependency();

  /**
   * Returns true if this selection is not empty and contains at least one selected {@link IDependency}
   * 
   * @return
   */
  public boolean hasDependencies();

}
