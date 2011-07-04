package org.bundlemaker.dependencyanalysis.base.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.DependencyKind;
import org.bundlemaker.dependencyanalysis.base.model.DependencyVisitor;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.rules.Violation;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public class Dependency implements IDependency {
  private static transient Set<Violation>          violations             = null;

  private static transient final String            NAME                   = "name";

  private static transient final String            CLAZZ                  = "class";

  private static transient List<DependencyVisitor> dependencyVisitors     = new ArrayList<DependencyVisitor>();

  private static transient List<IDependency>       allIgnoredDependencies = new ArrayList<IDependency>();

  private IArtifact                                to;

  private IArtifact                                from;

  private int                                      weight;

  private boolean                                  taggedIgnore;

  private DependencyKind                           dependencyKind         = DependencyKind.USES;

  private boolean                                  isLeaf                 = false;

  private Collection<IDependency>                  dependencies;

  static {
    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = extensionRegistry
        .getExtensionPoint("org.bundlemaker.dependencyanalysis.dependencyVisitors");
    if (extensionPoint != null) {
      for (IExtension extension : extensionPoint.getExtensions()) {
        for (IConfigurationElement element : extension.getConfigurationElements()) {
          try {
            DependencyVisitor dependencyVisitor = (DependencyVisitor) element.createExecutableExtension(CLAZZ);
            dependencyVisitors.add(dependencyVisitor);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public Dependency(IArtifact from, IArtifact to) {
    this(from, to, 1);
    if (from.getType().equals(ArtifactType.Type)) {
      isLeaf = true;
    }
  }

  public Dependency(IArtifact from, IArtifact to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
    this.taggedIgnore = false;
    visitVisitors();
  }

  public void visitVisitors() {
    for (DependencyVisitor dependencyVisitor : dependencyVisitors) {
      dependencyVisitor.visit(this);
    }
  }

  protected void ignoreDependencyNotification() {
    for (DependencyVisitor dependencyVisitor : dependencyVisitors) {
    	try {
      dependencyVisitor.addIgnoreDependency(this);
    	} catch (RuntimeException ex) {
    		// TODO
    		ex.printStackTrace();
    	}
    }
  }

  public void setDependencyKind(DependencyKind dependencyKind) {
    this.dependencyKind = dependencyKind;
  }

  public DependencyKind getDependencyKind() {
    return dependencyKind;
  }

  public void addWeight() {
    this.weight++;
  }

  // public boolean isConnectableTo(Dependency dependency) {
  // return this.getTo().equals(dependency.getFrom());
  // }

  /**
   * Fuegt der Abhaengigkeit eine andere Abhaengigkeit hinzu. Diese Methode wird verwendet, um aggregierte
   * Abhaengigkeiten zu erstellen
   * 
   * @param dependency
   *          Abhaengigkeit, die hinzugefuegt werden soll
   */
  public void addDependency(IDependency dependency) {
    if (dependencies == null) {
      dependencies = new ArrayList<IDependency>();
    }
    dependencies.add(dependency);
  }

  /**
   * @return the weight
   */
  public int getWeight() {
    if (dependencies != null && !dependencies.isEmpty()) {
      return dependencies.size();
    }
    return weight;
  }

  /**
   * @return the from
   */
  public IArtifact getFrom() {
    return from;
  }

  public IArtifact getTo() {
    return to;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Dependency( ");
    sb.append(this.getFrom().getName());
    sb.append(" ");
    sb.append(dependencyKind);
    sb.append(" ");
    sb.append(this.getTo().getName());
    sb.append(" )");

    return sb.toString();
  }

  /**
   * @return the tagged
   */
  public boolean isTaggedIgnore() {
    if (taggedIgnore) {
      return taggedIgnore;
    } else {
      Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
      getLeafDependencies(leafDependencies);
      for (IDependency iDependency : leafDependencies) {
        Dependency dependency = (Dependency) iDependency;
        if (!dependency.taggedIgnore) {
          return false;
        }
      }
      return true;
    }
  }

	
  public static void resetIgnored() {
    for (DependencyVisitor dependencyVisitor : dependencyVisitors) {
      dependencyVisitor.reload();
    }

    for (IDependency iDependency : allIgnoredDependencies) {
      Dependency dependency = (Dependency) iDependency;
      dependency.taggedIgnore = false;
    }
    allIgnoredDependencies.clear();
  }

  public int getTaggedIgnoreCount() {
    if (taggedIgnore) {
      Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
      getLeafDependencies(leafDependencies);
      return leafDependencies.size();
    } else {
      return 0;
    }
  }

  /**
   * @param tagged
   *          the tagged to set
   */
  public void setTaggedIgnore(boolean taggedIgnore) {
    setTaggedIgnore(taggedIgnore, true);
  }

  /**
   * @param tagged
   *          the tagged to set
   */
  public void setTaggedIgnore(boolean taggedIgnore, boolean withNotifyDSL) {
    this.taggedIgnore = taggedIgnore;
    if (taggedIgnore) {
      allIgnoredDependencies.add(this);
    }
    if (withNotifyDSL) {
      ignoreDependencyNotification();
    }
    if (!isLeaf) {
      Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
      getLeafDependencies(leafDependencies);
      for (IDependency iDependency : leafDependencies) {
        Dependency dependency = (Dependency) iDependency;
        dependency.taggedIgnore = taggedIgnore;
        if (taggedIgnore) {
          allIgnoredDependencies.add(dependency);
        }
      }
    }
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

  public int getViolationWeight() {
    if ((violations != null) && (violations.size() > 0)) {
      return getWeight();
    } else {
      return 0;
    }
  }

  /**
   * Fuegt der Abhaengigkeit einen Regelverstoﬂ hinzu
   * 
   * @param violation
   *          Die Violation
   */
  public void addViolation(Violation violation) {
    if (violations == null) {
      this.violations = new HashSet<Violation>();
    }
    violations.add(violation);
  }

  /**
   * Gibt eine <code>Collection</code> der Violations zurueck
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   */
  public Collection<Violation> getViolations() {
    return violations;
  }

  /**
   * Entfernt die bislang in der Abh‰ngigkeit enthalten Regelverstˆﬂe
   */
  public void clearViolations() {
    violations.clear();
  }

  /**
   * Gibt <code>true</code> zurueck, falls die Abhaengigkeit Regeln verletzt hat.
   * 
   * @return <code>true</code>, bei Regelverstoﬂ, ansonsten <code>false</code>
   */
  public boolean hasViolations() {
    return violations != null && !violations.isEmpty();
  }
}
