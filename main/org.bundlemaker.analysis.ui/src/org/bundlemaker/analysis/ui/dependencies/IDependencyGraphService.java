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

package org.bundlemaker.analysis.ui.dependencies;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.dependencies.IDependencyGraph;

/**
 * <p>
 * Provides access to {@link IDependencyGraph} instances for sets of {@link IArtifact artifacts}
 * </p>
 * <p>
 * The IDependencyGraphService implementation provides a cache for last recently used dependency graphs.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @noimplement This interface should not be implemented by clients
 */
public interface IDependencyGraphService {

  /**
   * <p>
   * Returns the calculated {@link IDependencyGraph} for the specified {@link IArtifact artifacts}
   * </p>
   * 
   * @param artifacts
   * @return The DependencyGraph. Never null
   */
  public IDependencyGraph getDependencyGraph(List<IArtifact> artifacts);

}
