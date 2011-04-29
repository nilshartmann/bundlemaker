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
package org.bundlemaker.core.analysis.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bundlemaker.core.analysis.internal.model.Dependency;
import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactContainer extends AbstractArtifact {

  private Collection<IArtifact>       children;

  private Collection<IDependency>     dependencies;

  private Collection<IArtifact>       leafs;

  /** - */
  private Map<IArtifact, IDependency> cachedDependencies;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactContainer}.
   * </p>
   * 
   * @param type
   */
  public AbstractArtifactContainer(ArtifactType type, IArtifact parent) {
    super(type, parent);

    children = new ArrayList<IArtifact>();
    cachedDependencies = new HashMap<IArtifact, IDependency>();

    if (parent != null) {
      parent.getChildren().add(this);
    }
  }

  @Override
  public final IDependency getDependency(IArtifact to) {

    // if (this.equals(to)) {
    // return new DependencyAlt(this, to, 0);
    // }
    //
    // IDependency dependency = cachedDependencies.get(to);
    // if (dependency != null) {
    // return dependency;
    // } else {
    // dependency = new DependencyAlt(this, to, 0);
    // for (IDependency reference : getDependencies()) {
    // if (to.contains(reference.getTo())) {
    // dependency.addDependency(reference);
    // ((DependencyAlt) dependency).addWeight();
    // }
    // }
    // cachedDependencies.put(to, dependency);
    // return dependency;
    // }

    return null;
  }

  public final Collection<IDependency> getDependencies() {
    if (dependencies == null) {
      aggregateDependencies();
    }
    return dependencies;
  }

  private final void aggregateDependencies() {
    dependencies = new ArrayList<IDependency>();
    for (IArtifact child : children) {
      dependencies.addAll(child.getDependencies());
    }

  }

  @Override
  public final void addArtifact(IArtifact baseArtifact) {
    IArtifact artifact = (IArtifact) baseArtifact;
    if (!children.contains(artifact)) {
      children.add(artifact);
    }

  }

  @Override
  public final boolean removeArtifact(IArtifact artifact) {
    return children.remove(artifact);
  }

  @Override
  public final Dependency addDependency(IArtifact artifact) {
    throw new UnsupportedOperationException("Nur Blätter können Abhängigkeiten besitzen");
  }

  @Override
  public final boolean contains(IArtifact artifact) {
    if (leafs == null || leafs.isEmpty()) {
      leafs = getLeafs();
    }
    return leafs.contains(artifact);
  }

  public final Collection<IArtifact> getLeafs() {
    if (leafs == null || leafs.isEmpty()) {
      leafs = new HashSet<IArtifact>();
      for (IArtifact child : children) {
        if (child.getChildren().isEmpty()) {
          leafs.add(child);
        } else {
          if (child instanceof AbstractArtifactContainer) {
            leafs.addAll(((AbstractArtifactContainer) child).getLeafs());
          }
        }
      }

    }
    return leafs;
  }

  @Override
  public final Collection<IArtifact> getChildren() {
    return children;
  }

  @Override
  public final Integer size() {
    return getLeafs().size();
  }
}
