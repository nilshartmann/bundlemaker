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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.bundlemaker.core.internal.analysis.model.DependencyAlt;
import org.bundlemaker.core.internal.analysis.transformer.ArtifactCache;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResource2IArtifact extends AbstractArtifact {

  /** - */
  private String                      _path;

  /** the bundle maker resource */
  private IResource                   _binaryResource;

  /** the bundle maker resource */
  private IResource                   _sourceResource;

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
  public AdapterResource2IArtifact(String path, ArtifactCache artifactCache, IArtifact parent) {

    super(ArtifactType.Type, parent);

    Assert.isNotNull(artifactCache);
    Assert.isNotNull(parent);

    setParent(parent);

    Assert.isNotNull(artifactCache);

    _path = path;

    _artifactCache = artifactCache;
  }

  public void setBinaryResource(IResource binaryResource) {
    _binaryResource = binaryResource;
  }

  public void setSourceResource(IResource sourceResource) {
    _sourceResource = sourceResource;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasBinaryResource() {
    return _binaryResource != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasSourceResource() {
    return _sourceResource != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IResource getBinaryResource() {
    return _binaryResource;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IResource getSourceResource() {
    return _sourceResource;
  }

  /**
   * @see org.bundlemaker.dependencyanalysis.base.model.IBaseArtifact#getName()
   */
  @Override
  public String getName() {
    int lastIndex = _path.lastIndexOf('/');
    return lastIndex != -1 ? _path.substring(lastIndex + 1) : _path;
  }

  @Override
  public String getQualifiedName() {
    return _path;
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
      DependencyAlt dependencyContainer = new DependencyAlt(this, artifact, 0);
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

    // TODO: to handle resource dependencies, uncomment the following lines
    // initDependencies();
    // return _cachedDependencies.values();

    return Collections.emptyList();
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
    for (IReference reference : _binaryResource.getReferences()) {

      //
      IArtifact artifact = _artifactCache.getArtifact(reference.getFullyQualifiedName());

      // TODO!!
      if (artifact != null) {

        // map to dependency
        DependencyAlt dependency = new DependencyAlt(this, artifact);

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
}
