/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.analysis.selectors;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransitiveClosureSelector implements IArtifactSelector {

  private final IBundleMakerArtifact _artifact;

  public TransitiveClosureSelector(IBundleMakerArtifact artifact) {
    _artifact = checkNotNull(artifact);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {

    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    findTransitiveClosure(_artifact, result);

    return result;
  }

  protected void findTransitiveClosure(IBundleMakerArtifact artifact, List<IBundleMakerArtifact> result) {

    Collection<IDependency> dependencies = artifact.getDependenciesTo();

    for (IDependency dependency : dependencies) {
      IBundleMakerArtifact referencedObject = dependency.getTo();

      if (!result.contains(referencedObject)) {
        result.add(referencedObject);

        findTransitiveClosure(referencedObject, result);
      }

    }

  }

}
