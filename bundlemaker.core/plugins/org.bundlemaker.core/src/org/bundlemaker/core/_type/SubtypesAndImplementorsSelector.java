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

package org.bundlemaker.core._type;

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
public class SubtypesAndImplementorsSelector implements IArtifactSelector {

  private final IBundleMakerArtifact _typeArtifact;

  public SubtypesAndImplementorsSelector(IBundleMakerArtifact typeArtifact) {
    _typeArtifact = checkNotNull(typeArtifact);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<IBundleMakerArtifact> getBundleMakerArtifacts() {

    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    findSubtypesAndImplementors(_typeArtifact, result);

    System.out.println("Subtypes and implementors of '" + _typeArtifact + "': " + result);

    return result;
  }

  protected void findSubtypesAndImplementors(IBundleMakerArtifact supertype, List<IBundleMakerArtifact> result) {

    Collection<IDependency> incomingReferencences = supertype.getDependenciesFrom();
    for (IDependency iDependency : incomingReferencences) {
      IBundleMakerArtifact candidate = iDependency.getFrom();

      Collection<IDependency> dependenciesTo = candidate.getDependenciesTo(supertype);
      for (IDependency candidateDependency : dependenciesTo) {
        if (candidateDependency.getDependencyKind().isInheritance()) {
          if (!result.contains(candidate)) {
            result.add(candidate);

            findSubtypesAndImplementors(candidate, result);
          }
        }
      }
    }

  }
}
