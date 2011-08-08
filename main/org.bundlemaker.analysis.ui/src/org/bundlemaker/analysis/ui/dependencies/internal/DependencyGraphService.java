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

package org.bundlemaker.analysis.ui.dependencies.internal;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.dependencies.IDependencyGraphService;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * <p>
 * This implementation caches the last calculated DependencyGraph for better performance
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyGraphService implements IDependencyGraphService {

  // --- "Mini-Cache" ----------------------
  private List<IArtifact> _lastRecentlyUsedArtifacts;

  private DependencyGraph _lastRecentlyUsedGraph;

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized DependencyGraph getDependencyGraph(List<IArtifact> artifacts) {
    Assert.isNotNull(artifacts, "Parameter 'artifacts' must not be null");

    // Check if Graph can returned from 'cache'
    if (artifacts.equals(_lastRecentlyUsedArtifacts)) {
      return _lastRecentlyUsedGraph;
    }

    // Calcuate new Graph
    _lastRecentlyUsedArtifacts = artifacts;
    _lastRecentlyUsedGraph = DependencyGraph.calculateDependencyGraph(artifacts);

    // Return the graph
    return _lastRecentlyUsedGraph;

  }

}
