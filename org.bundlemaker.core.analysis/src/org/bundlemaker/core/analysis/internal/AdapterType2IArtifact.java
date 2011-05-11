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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.internal.transformer.ArtifactCache;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.bundlemaker.dependencyanalysis.base.model.impl.Dependency;
import org.eclipse.core.runtime.Assert;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractArtifact {

  /** the bundle maker type */
  private IType                       _type;

  /** - */
  private ArtifactCache               _artifactCache;

  /** - */
  private Map<IArtifact, IDependency> _cachedDependencies;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public AdapterType2IArtifact(IType type, ArtifactCache artifactCache, IArtifact parent) {

    super(ArtifactType.Type, type.getName());

    Assert.isNotNull(artifactCache);
    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getChildren().add(this);

    _type = type;

    _artifactCache = artifactCache;
  }

  @Override
  public String getQualifiedName() {
    return _type.getFullyQualifiedName();
  }

  public IType getBundleMakerType() {
    return _type;
  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    // throw new unsupported operation exception
    throw new UnsupportedOperationException();
  }

  @Override
  public void addArtifact(IArtifact artifact) {
    // throw new unsupported operation exception
    throw new UnsupportedOperationException();
  }

  @Override
  @Deprecated
  public IDependency addDependency(IArtifact artifact) {
    // throw new unsupported operation exception
    throw new UnsupportedOperationException();
  }

  @Override
  public Integer size() {
    return 1;
  }

  @Override
  public Collection<IArtifact> getChildren() {
    return Collections.emptySet();
  }

  @Override
  public boolean contains(IArtifact artifact) {
    return this.equals(artifact);
  }

  @Override
  public IDependency getDependency(IArtifact artifact) {

    //
    initDependencies();

    //
    if (artifact.getLeafs() == null) {
      return _cachedDependencies.get(artifact);
    } else {
      Dependency dependencyContainer = new Dependency(this, artifact, 0);
      for (IArtifact leaf : artifact.getLeafs()) {
        IDependency dependency = getDependency(leaf);
        if ((dependency != null) && (dependency.getTo().getType() == ArtifactType.Type)) {
          dependencyContainer.addDependency(dependency);
        }
      }
      return dependencyContainer;
    }
  }

  @Override
  public Collection<IDependency> getDependencies() {

    //
    initDependencies();

    return _cachedDependencies.values();
  }

  @Override
  public Collection<IArtifact> getLeafs() {

    // simply return null
    return null;
  }

  /**
	 * 
	 */
  private void initDependencies() {

    if (_cachedDependencies != null) {
      return;
    }

    //
    _cachedDependencies = new HashMap<IArtifact, IDependency>();

    // iterate over all references
    for (IReference reference : _type.getReferences()) {

      //
      IArtifact artifact = _artifactCache.getArtifact(reference.getFullyQualifiedName());

      // TODO!!

      if (artifact != null) {

        // map to dependency
        Dependency dependency = new Dependency(this, artifact);

        // DependencyKind dependencyKind = DependencyKind.USES;
        // if (reference.isImplements()) {
        // dependencyKind = DependencyKind.IMPLEMENTS;
        // } else if (reference.isExtends()) {
        // dependencyKind = DependencyKind.EXTENDS;
        // } else if (reference.isClassAnnotation()) {
        // dependencyKind = DependencyKind.ANNOTATES;
        // }
        //
        // dependency.setDependencyKind(dependencyKind);

        _cachedDependencies.put(artifact, dependency);
      }
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  private static final String getNullSafeFullyQualifiedName(IType type) {
    Assert.isNotNull(type, "Parameter 'type' has to be set!");

    return type.getFullyQualifiedName();
  }
}
