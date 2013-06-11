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

package org.bundlemaker.core._type.analysis.selectors;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * Selects all supertypes (classes and interfaces) of a specified type.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class SuperTypeSelector implements IArtifactSelector {

  private final IBundleMakerArtifact _typeArtifact;

  public SuperTypeSelector(IBundleMakerArtifact typeArtifact) {
    _typeArtifact = checkNotNull(typeArtifact);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {

    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    // recursively find all super types
    findSupertype(_typeArtifact, result);

    System.out.println("Supertype of " + _typeArtifact.getQualifiedName() + " -> " + result);

    return result;
  }

  protected void findSupertype(IBundleMakerArtifact baseType, List<IBundleMakerArtifact> result) {

    Collection<IDependency> dependencies = baseType.getDependenciesTo();

    for (IDependency iDependency : dependencies) {

      if (DependencyKind.EXTENDS.equals(iDependency.getDependencyKind())
          || DependencyKind.IMPLEMENTS.equals(iDependency.getDependencyKind())) {
        IBundleMakerArtifact supertype = iDependency.getTo();

        if (!result.contains(supertype)) {
          result.add(supertype);

          findSupertype(supertype, result);
        }
      }
    }
  }
}
