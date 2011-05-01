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
package org.bundlemaker.core.analysis.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.DependencyKind;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.bundlemaker.core.analysis.rules.Violation;

public class DependencyAlt implements IDependency 
{
  
  
  // private static final String NAME = "name";
  // private static final String CLAZZ = "class";
  // private static List<IDependency> allIgnoredDependencies = new ArrayList<IDependency>();
  // private static List<DependencyVisitor> dependencyVisitors = new ArrayList<DependencyVisitor>();
  //
  // private static Set<Violation> violations = null;

  @Override
  public Collection<IDependency> getDependencies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void getLeafDependencies(Collection<IDependency> leafDependencies) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setDependencyKind(DependencyKind dependencyKind) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isTaggedIgnore() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setTaggedIgnore(boolean taggedIgnore) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setTaggedIgnore(boolean taggedIgnore, boolean withNotifyDSL) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getTaggedIgnoreCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void getNewDependencies(Collection<IDependency> newDependencies) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public DependencyKind getDependencyKind() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getViolationWeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void addViolation(Violation violation) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Collection<Violation> getViolations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void visitVisitors() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean hasViolations() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void clearViolations() {
    // TODO Auto-generated method stub
    
  }

  private final IArtifact         to;

  private final IArtifact         from;

  private int                     weight;

  // private boolean taggedIgnore;
  // private DependencyKind dependencyKind = DependencyKind.USES;

  private boolean                 isLeaf = false;

  private Collection<IDependency> dependencies;

  // static {
  // IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
  // IExtensionPoint extensionPoint =
  // extensionRegistry.getExtensionPoint("org.bundlemaker.dependencyanalysis.dependencyVisitors");
  // if( extensionPoint != null ) {
  // for(IExtension extension : extensionPoint.getExtensions()){
  // for(IConfigurationElement element : extension.getConfigurationElements()){
  // try {
  // DependencyVisitor dependencyVisitor = (DependencyVisitor)element.createExecutableExtension(CLAZZ);
  // dependencyVisitors.add(dependencyVisitor);
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
  // }
  // }
  // }

  // public static void resetIgnored() {
  // for( DependencyVisitor dependencyVisitor: dependencyVisitors) {
  // dependencyVisitor.reload();
  // }
  //
  // for( IDependency iDependency: allIgnoredDependencies ) {
  // Dependency dependency = (Dependency)iDependency;
  // dependency.taggedIgnore = false;
  // }
  // allIgnoredDependencies.clear();
  // }

  public DependencyAlt(IArtifact from, IArtifact to) {
    this(from, to, 1);
    if (from.getType().equals(ArtifactType.Type)) {
      isLeaf = true;
    }
  }

  public DependencyAlt(IArtifact from, IArtifact to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
    // this.taggedIgnore = false;
    // visitVisitors();
  }

  // public void visitVisitors() {
  // for( DependencyVisitor dependencyVisitor: dependencyVisitors) {
  // dependencyVisitor.visit(this);
  // }
  // }
  //
  // protected void ignoreDependencyNotification() {
  // for( DependencyVisitor dependencyVisitor: dependencyVisitors) {
  // dependencyVisitor.addIgnoreDependency(this);
  // }
  // }
  //
  // public void setDependencyKind(DependencyKind dependencyKind) {
  // this.dependencyKind = dependencyKind;
  // }
  //
  // public DependencyKind getDependencyKind() {
  // return dependencyKind;
  // }

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

    sb.append("[ Von: " + this.getFrom().getName());
    sb.append(", Zu: " + this.getTo().getName());
    sb.append(", Gewichtung: " + this.getWeight() + " ]");

    return sb.toString();
  }

  //
  // /**
  // * @return the tagged
  // */
  // public boolean isTaggedIgnore() {
  // if( taggedIgnore ) {
  // return taggedIgnore;
  // } else {
  // Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
  // getLeafDependencies(leafDependencies);
  // for( IDependency iDependency: leafDependencies) {
  // Dependency dependency = (Dependency)iDependency;
  // if( !dependency.taggedIgnore ) {
  // return false;
  // }
  // }
  // return true;
  // }
  // }
  //
  // public int getTaggedIgnoreCount() {
  // if( taggedIgnore ) {
  // Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
  // getLeafDependencies(leafDependencies);
  // return leafDependencies.size();
  // } else {
  // return 0;
  // }
  // }

  // /**
  // * @param tagged the tagged to set
  // */
  // public void setTaggedIgnore(boolean taggedIgnore) {
  // setTaggedIgnore(taggedIgnore, true);
  // }
  //
  // /**
  // * @param tagged the tagged to set
  // */
  // public void setTaggedIgnore(boolean taggedIgnore, boolean withNotifyDSL ) {
  // this.taggedIgnore = taggedIgnore;
  // if( taggedIgnore ) {
  // allIgnoredDependencies.add(this);
  // }
  // if( withNotifyDSL ) {
  // ignoreDependencyNotification();
  // }
  // if( !isLeaf ) {
  // Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
  // getLeafDependencies(leafDependencies);
  // for( IDependency iDependency: leafDependencies) {
  // Dependency dependency = (Dependency)iDependency;
  // dependency.taggedIgnore = taggedIgnore;
  // if( taggedIgnore ) {
  // allIgnoredDependencies.add(dependency);
  // }
  // }
  // }
  // }

  /**
   * Gibt eine <code>Collection</code> von Abhaengigkeiten zurueck, falls die
   * Abhaengigkeit aus aggegrierten Abhaengigkeiten besteht.
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   * 
   */
//  public Collection<IDependency> getDependencies() {
//    return dependencies;
//  }

  // @Override
  // public IBaseArtifact getBaseFrom() {
  // return getFrom();
  // }
//  public void getNewDependencies(Collection<IDependency> newDependencies) {
//    Collection<IDependency> leafDependencies = new ArrayList<IDependency>();
//    getLeafDependencies(leafDependencies);
//    for (IDependency leafDependency : leafDependencies) {
//      String transformed = leafDependency.getFrom().getProperty("transformed");
//      String fromBundleName = leafDependency.getFrom().getParent(ArtifactType.Module).getName();
//      String toBundleName = leafDependency.getTo().getParent(ArtifactType.Module).getName();
//      if (transformed != null) {
//        if (fromBundleName.equals(toBundleName)) {
//          newDependencies.add(leafDependency);
//        }
//      }
//    }
//  }
//
//  public void getLeafDependencies(Collection<IDependency> leafDependencies) {
//    if (isLeaf) {
//      leafDependencies.add(this);
//    } else {
//      if (dependencies != null) {
//        for (IDependency dependency : dependencies) {
//          dependency.getLeafDependencies(leafDependencies);
//        }
//      }
//    }
//  }

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
    DependencyAlt other = (DependencyAlt) obj;
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

  // public int getViolationWeight() {
  // if( (violations != null) && (violations.size()>0) ) {
  // return getWeight();
  // } else {
  // return 0;
  // }
  // }
  //
  //
  // /**
  // * Fuegt der Abhaengigkeit einen Regelverstoﬂ hinzu
  // *
  // * @param violation
  // * Die Violation
  // */
  // public void addViolation(Violation violation){
  // if(violations == null){
  // this.violations = new HashSet<Violation>();
  // }
  // violations.add(violation);
  // }
  //
  // /**
  // * Gibt eine <code>Collection</code> der Violations zurueck
  // *
  // * @return <code>Collection</code> von Abhaengigkeiten
  // */
  // public Collection<Violation> getViolations(){
  // return violations;
  // }
  //
  // /**
  // * Entfernt die bislang in der Abh‰ngigkeit enthalten Regelverstˆﬂe
  // */
  // public void clearViolations() {
  // violations.clear();
  // }
  //
  // /**
  // * Gibt <code>true</code> zurueck, falls die Abhaengigkeit
  // * Regeln verletzt hat.
  // *
  // * @return <code>true</code>, bei Regelverstoﬂ, ansonsten <code>false</code>
  // */
  // public boolean hasViolations(){
  // return violations != null && !violations.isEmpty();
  // }
}
