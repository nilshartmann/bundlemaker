package org.bundlemaker.dependencyanalysis.base.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;

/**
 * <p>
 * Implementiert eines gruppierenden Artefaktes
 * 
 * <p>
 * Gruppierende Artefakte koennen anderen gruppierenden Artefakte oder
 * Primarartefakte beinhalten
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public abstract class AbstractArtifactContainer extends AbstractArtifact {
  private Collection<IArtifact>                 children;

  private Collection<IDependency>               dependencies;

  private Collection<IArtifact>                 leafs;

  private transient Map<IArtifact, IDependency> cachedDependencies;

  public AbstractArtifactContainer(ArtifactType type, String name) {
    super(type, name);

    children = new ArrayList<IArtifact>();
    cachedDependencies = new HashMap<IArtifact, IDependency>();
  }

  @Override
  public IDependency getDependency(IArtifact to) {

    if (this.equals(to)) {
      return new Dependency(this, to, 0);
    }

    IDependency dependency = cachedDependencies.get(to);
    if (dependency != null) {
      return dependency;
    } else {
      dependency = new Dependency(this, to, 0);
      for (IDependency reference : getDependencies()) {
        if (to.contains(reference.getTo())) {
          ((Dependency) dependency).addDependency(reference);
          ((Dependency) dependency).addWeight();
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
  public void addArtifact(IArtifact artifact) {
    if (!children.contains(artifact)) {
      children.add(artifact);
    }
    IModifiableArtifact modifiableArtifact = (IModifiableArtifact) artifact;
    modifiableArtifact.setParent(this);
  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    return children.remove(artifact);
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
          if (child instanceof AbstractArtifactContainer) {
						leafs.addAll(((AbstractArtifactContainer) child)
								.getLeafs());
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
