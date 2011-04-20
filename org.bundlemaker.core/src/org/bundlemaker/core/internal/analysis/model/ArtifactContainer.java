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
package org.bundlemaker.core.internal.analysis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;

/**
 * <p>
 * Implementiert eines gruppierenden Artefaktes
 * 
 * <p>
 * Gruppierende Artefakte koennen anderen gruppierenden Artefakte oder Primarartefakte beinhalten
 * 
 * @author Kai Lehmann
 * 
 */
public class ArtifactContainer extends AbstractArtifact {

  private Collection<IArtifact>       children;

  private Collection<IDependency>     dependencies;

  private Collection<IArtifact>       leafs;

  private String                      qualifiedName = null;

  private Map<IArtifact, IDependency> cachedDependencies;

  public ArtifactContainer(ArtifactType type, String name) {
    super(type, name);

    children = new ArrayList<IArtifact>();
    cachedDependencies = new HashMap<IArtifact, IDependency>();
    qualifiedName = name;
  }

  @Override
  public String getQualifiedName() {
    return qualifiedName;
  }

  public void setQualifiedName(String qualifiedName) {
    this.qualifiedName = qualifiedName;
  }

  @Override
  public IDependency getDependency(IArtifact to) {

    if (this.equals(to)) {
      return new DependencyAlt(this, to, 0);
    }

    IDependency dependency = cachedDependencies.get(to);
    if (dependency != null) {
      return dependency;
    } else {
      dependency = new DependencyAlt(this, to, 0);
      for (IDependency reference : getDependencies()) {
        if (to.contains(reference.getTo())) {
          dependency.addDependency(reference);
          ((DependencyAlt) dependency).addWeight();
        }
      }
      cachedDependencies.put(to, dependency);
      return dependency;
    }

  }

  public Collection<IDependency> getDependencies() {
    if (dependencies == null) {
      aggregateDependencies();
    }
    return dependencies;
  }

  private void aggregateDependencies() {
    dependencies = new ArrayList<IDependency>();
    for (IArtifact child : children) {
      dependencies.addAll(child.getDependencies());
    }

  }

  @Override
  public void addArtifact(IArtifact baseArtifact) {
    IArtifact artifact = (IArtifact) baseArtifact;
    if (!children.contains(artifact)) {
      children.add(artifact);
    }

  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    return children.remove(artifact);
  }

  @Override
  public DependencyAlt addDependency(IArtifact artifact) {
    throw new UnsupportedOperationException("Nur Blätter können Abhängigkeiten besitzen");
  }

  @Override
  public boolean contains(IArtifact artifact) {
    if (leafs == null || leafs.isEmpty()) {
      leafs = getLeafs();
    }
    return leafs.contains(artifact);
  }

  public Collection<IArtifact> getLeafs() {
    if (leafs == null || leafs.isEmpty()) {
      leafs = new HashSet<IArtifact>();
      for (IArtifact child : children) {
        if (child.getChildren().isEmpty()) {
          leafs.add(child);
        } else {
          if (child instanceof ArtifactContainer) {
            leafs.addAll(((ArtifactContainer) child).getLeafs());
          }
        }
      }

    }
    return leafs;
  }

  @Override
  public Collection<IArtifact> getChildren() {
    return children;
  }

  @Override
  public Integer size() {
    return getLeafs().size();
  }
}
