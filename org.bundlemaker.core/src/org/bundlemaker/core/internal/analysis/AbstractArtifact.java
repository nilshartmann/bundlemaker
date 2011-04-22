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
package org.bundlemaker.core.internal.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract base class for all artifact adapter.
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifact implements IArtifact {

  /** the artifact type */
  private final ArtifactType _type;

  /** - */
  private IArtifact          _parent;

  /** - */
  private Integer            ordinal;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifact}.
   * </p>
   * 
   * @param type
   */
  public AbstractArtifact(ArtifactType type) {
    Assert.isNotNull(type);

    // set the type
    this._type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IArtifact getParent() {
    return _parent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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

  /**
   * {@inheritDoc}
   */
  @Override
  public ArtifactType getType() {
    return _type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getOrdinal() {
    if (ordinal != null) {
      return ordinal;
    } else if (this.getParent() != null) {
      return this.getParent().getOrdinal();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
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

  public void setParent(IArtifact parent) {
    this._parent = parent;
  }

  public void setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
  }

  public String toString() {
    return this.getName();
  }

}
