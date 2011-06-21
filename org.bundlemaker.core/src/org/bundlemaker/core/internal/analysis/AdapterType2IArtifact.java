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
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ITypeHolder;
import org.bundlemaker.core.internal.analysis.transformer.AbstractArtifactCache;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.DependencyKind;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.bundlemaker.dependencyanalysis.base.model.impl.Dependency;
import org.eclipse.core.runtime.Assert;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractArtifact implements IResourceHolder, IAdvancedArtifact, ITypeHolder {

  /** the bundle maker type */
  private IType                       _type;

  /** - */
  private AbstractArtifactCache       _artifactCache;

  /** - */
  private Map<IArtifact, IDependency> _cachedDependencies;

  /** - */
  private boolean                     _aggregateInnerTypes;

  /** - */
  private IResourceHolder             _resourceHolder;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public AdapterType2IArtifact(IType type, AbstractArtifactCache artifactCache, IArtifact parent,
      boolean aggregateInnerTypes) {

    super(ArtifactType.Type, type.getName());

    Assert.isNotNull(artifactCache);
    Assert.isNotNull(parent);

    _aggregateInnerTypes = aggregateInnerTypes;

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getChildren().add(this);

    _type = type;

    _artifactCache = artifactCache;

    //
    _resourceHolder = new ResourceHolder(type, _aggregateInnerTypes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResource> getAssociatedBinaryResources() {
    return _resourceHolder.getAssociatedBinaryResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResource> getAssociatedSourceResources() {
    return _resourceHolder.getAssociatedSourceResources();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isAggregateInnerTypes() {
    return _aggregateInnerTypes;
  }

  /**
   * <p>
   * </p>
   * 
   * @param aggregateInnerTypes
   */
  public void setAggregateInnerTypes(boolean aggregateInnerTypes) {
    _aggregateInnerTypes = aggregateInnerTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _type.getFullyQualifiedName();
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

    // TODO: RESOURCES!
    // STICKYTYPES!!

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

  @Override
  public IModularizedSystem getModularizedSystem() {
    return AdapterUtils.getModularizedSystem(this);
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

    initReferences(_type.getReferences());

    //
    if (_aggregateInnerTypes) {

      // TODO RESOURCES

      // TODO TOPLEVEL NON-MAIN TYPES
      if (_type.hasSourceResource()) {
        for (IType type : _type.getSourceResource().getContainedTypes()) {
          if (!type.isPrimaryType()) {
            initReferences(type.getReferences());
          }
        }
      }

      if (_type.hasBinaryResource()) {
        for (IResource stickyResource : _type.getBinaryResource().getStickyResources()) {
          for (IType type : stickyResource.getContainedTypes()) {
            initReferences(type.getReferences());
          }
        }
      }
    }
  }

  private void initReferences(Collection<? extends IReference> references) {

    // iterate over all references
    for (IReference reference : references) {

      //
      String referenceName = reference.getFullyQualifiedName();
      if (_aggregateInnerTypes && JavaTypeUtils.isInnerTypeName(referenceName)) {
        referenceName = JavaTypeUtils.getEnclosingNonInnerTypeName(referenceName);
      }

      //
      IArtifact artifact = _artifactCache.getTypeArtifact(referenceName);

      // TODO!!
      // STICKY-TYPES/AGGREGATED TYPES

      if (artifact != null) {

        // map to dependency
        Dependency dependency = new Dependency(this, artifact);

        DependencyKind dependencyKind = DependencyKind.USES;
        if (reference.isImplements()) {
          dependencyKind = DependencyKind.IMPLEMENTS;
        } else if (reference.isExtends()) {
          dependencyKind = DependencyKind.EXTENDS;
        } else if (reference.isClassAnnotation()) {
          dependencyKind = DependencyKind.ANNOTATES;
        }

        dependency.setDependencyKind(dependencyKind);

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

  @Override
  public String getIdentifier() {
    return getName();
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return false;
  }

  @Override
  public IArtifact getChildByIdentifier(String identifier) {
    return null;
  }

  @Override
  public IArtifact getChild(String path) {
    return null;
  }

  @Override
  public IType getAssociatedType() {
    return _type;
  }

}
