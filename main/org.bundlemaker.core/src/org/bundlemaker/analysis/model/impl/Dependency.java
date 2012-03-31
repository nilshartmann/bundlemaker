package org.bundlemaker.analysis.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public class Dependency implements IDependency {

  private IBundleMakerArtifact    to;

  private IBundleMakerArtifact    from;

  private int                     weight;

  private DependencyKind          dependencyKind = DependencyKind.USES;

  private boolean                 isLeaf         = false;

  private Collection<IDependency> dependencies;

  public Dependency(IBundleMakerArtifact from, IBundleMakerArtifact to) {
    this(from, to, 1);
    if (from.getType().equals(ArtifactType.Type)) {
      isLeaf = true;
    }
  }

  public Dependency(IBundleMakerArtifact from, IBundleMakerArtifact to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public void setDependencyKind(DependencyKind dependencyKind) {
    this.dependencyKind = dependencyKind;
  }

  @Override
  public DependencyKind getDependencyKind() {
    return dependencyKind;
  }

  public void addWeight() {
    this.weight++;
  }

  /**
   * Fuegt der Abhaengigkeit eine andere Abhaengigkeit hinzu. Diese Methode wird verwendet, um aggregierte
   * Abhaengigkeiten zu erstellen
   * 
   * @param dependency
   *          Abhaengigkeit, die hinzugefuegt werden soll
   */
  @Override
  public void addDependency(IDependency dependency) {
    if (dependencies == null) {
      dependencies = new ArrayList<IDependency>();
    }
    dependencies.add(dependency);
  }

  /**
   * @return the weight
   */
  @Override
  public int getWeight() {
    if (dependencies != null && !dependencies.isEmpty()) {
      return dependencies.size();
    }
    return weight;
  }

  /**
   * @return the from
   */
  @Override
  public IBundleMakerArtifact getFrom() {
    return from;
  }

  @Override
  public IBundleMakerArtifact getTo() {
    return to;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Dependency( ");
    sb.append(this.getFrom().getQualifiedName());
    sb.append(" ");
    sb.append(dependencyKind);
    sb.append(" ");
    sb.append(this.getTo().getQualifiedName());
    sb.append(" )");

    return sb.toString();
  }

  @Override
  /**
   * Gibt eine <code>Collection</code> von Abhaengigkeiten zurueck, falls die
   * Abhaengigkeit aus aggegrierten Abhaengigkeiten besteht.
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   * 
   */
  public Collection<IDependency> getDependencies() {
    return dependencies;
  }

  // @Override
  // public IBaseArtifact getBaseFrom() {
  // return getFrom();
  // }
  @Override
  public void getNewDependencies(Collection<IDependency> newDependencies) {
    Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
    getLeafDependencies(leafDependencies);
    for (IDependency leafDependency : leafDependencies) {
      String transformed = leafDependency.getFrom().getProperty("transformed");
      String fromBundleName = leafDependency.getFrom().getParent(ArtifactType.Module).getName();
      String toBundleName = leafDependency.getTo().getParent(ArtifactType.Module).getName();
      if (transformed != null) {
        if (fromBundleName.equals(toBundleName)) {
          newDependencies.add(leafDependency);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getLeafDependencies() {
    Collection<IDependency> result = new LinkedList<IDependency>();
    getLeafDependencies(result);
    return result;
  }

  @Override
  public void getLeafDependencies(Collection<IDependency> leafDependencies) {
    if (isLeaf) {
      leafDependencies.add(this);
    } else {
      if (dependencies != null) {
        for (IDependency dependency : dependencies) {
          dependency.getLeafDependencies(leafDependencies);
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Dependency other = (Dependency) obj;
    if (from == null) {
      if (other.from != null) {
        return false;
      }
    } else if (!from.equals(other.from)) {
      return false;
    }
    if (to == null) {
      if (other.to != null) {
        return false;
      }
    } else if (!to.equals(other.to)) {
      return false;
    }
    return true;
  }
}
