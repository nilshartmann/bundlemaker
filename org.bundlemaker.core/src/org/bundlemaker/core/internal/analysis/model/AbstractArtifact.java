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
import java.util.List;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;

/**
 * Abstrakte Oberklasse fuer die beiden unterschiedlichen Artefakte. Unterschieden wird zwischen gruppierenden
 * Artefakten und Primaerartefakten.
 * 
 * @author Kai Lehmann
 */
public abstract class AbstractArtifact implements IArtifact {

  // Ordnungs Eigenschaften
  private final ArtifactType type;

  private final String       name;

  private Integer            ordinal;

  private IArtifact          parent;

  public AbstractArtifact(ArtifactType type, String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public IArtifact getParent() {
    return parent;
  }

  public IArtifact getParent(ArtifactType type) {
    IArtifact parent = this.getParent();

    if (parent == null) {
      return null;
    } else if (type.equals(parent.getType())) {
      return this.getParent();
    } else {
      return parent.getParent(type);
    }
  }

  @Override
  public void setParent(IArtifact parent) {
    this.parent = parent;
  }

  public ArtifactType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Integer getOrdinal() {
    if (ordinal != null) {
      return ordinal;
    } else if (this.getParent() != null) {
      return this.getParent().getOrdinal();
    }
    return null;
  }

  public void setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
  }

  public String toString() {
    return this.getName();
  }

  @Override
  public List<IDependency> getDependencies(Collection<IArtifact> artifacts) {
    List<IDependency> dependencies = new ArrayList<IDependency>();

    for (IArtifact artifact : artifacts) {
      IDependency dependency = this.getDependency(artifact);
      if (dependency != null && dependency.getWeight() != 0) {
        dependencies.add(this.getDependency(artifact));
      }
    }
    return dependencies;
  }
}
