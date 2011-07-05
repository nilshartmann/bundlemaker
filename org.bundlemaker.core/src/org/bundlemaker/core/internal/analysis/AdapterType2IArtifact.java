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

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.impl.AbstractArtifact;
import org.bundlemaker.analysis.model.impl.Dependency;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.transformer.AbstractArtifactCache;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractArtifact implements IMovableUnit, IAdvancedArtifact, ITypeArtifact {

  /** the bundle maker type */
  private IType                       _type;

  /** - */
  private AbstractArtifactCache       _artifactCache;

  /** - */
  private Map<IArtifact, IDependency> _cachedDependencies;

  /** - */
  private boolean                     _aggregateInnerTypes;

  /** - */
  private IMovableUnit                _resourceHolder;

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
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);

    _type = type;

    _artifactCache = artifactCache;

    //
    _resourceHolder = MovableUnit.createFromType(type);
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
  public IResource getAssociatedSourceResource() {
    return _resourceHolder.getAssociatedSourceResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IType> getAssociatedTypes() {
    return _resourceHolder.getAssociatedTypes();
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

    //
    if (_cachedDependencies != null) {
      return;
    }

    //
    _cachedDependencies = new HashMap<IArtifact, IDependency>();

    //
    initReferences(_type.getReferences(), _type);

    //
    if (_aggregateInnerTypes) {

      if (_type.hasSourceResource()) {
        for (IType type : _type.getSourceResource().getContainedTypes()) {
          if (!type.isPrimaryType()) {
            initReferences(type.getReferences(), type);
          }
        }
      }

      if (_type.hasBinaryResource()) {
        for (IResource stickyResource : _type.getBinaryResource().getStickyResources()) {
          for (IType type : stickyResource.getContainedTypes()) {
            initReferences(type.getReferences(), type);
          }
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param references
   */
  private void initReferences(Collection<? extends IReference> references, IType type) {

    // iterate over all references
    for (IReference reference : references) {

      //
      String referenceName = reference.getFullyQualifiedName();

      if (_aggregateInnerTypes) {
        referenceName = resolvePrimaryType(referenceName, (IResourceModule) type.getModule(getModularizedSystem()));
      }

      // skip self references
      if (referenceName.equals(type.getFullyQualifiedName())) {
        continue;
      }

      //
      IArtifact artifact = _artifactCache.getTypeArtifact(referenceName, false);

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
      } else {
        System.out.println("MISSING TYPE: " + referenceName);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param referenceName
   * @param resourceModule
   * @return
   */
  private String resolvePrimaryType(String referenceName, IResourceModule resourceModule) {

    // step 1: inner type (e.g. 'de.example.Test$InnerClass')
    if (JavaTypeUtils.isInnerTypeName(referenceName)) {

      // get the reference name
      referenceName = JavaTypeUtils.getEnclosingNonInnerTypeName(referenceName);

      // recurse
      return resolvePrimaryType(referenceName, resourceModule);
    }

    // step 2:
    try {

      //
      IType iType = getModularizedSystem().getType(referenceName, resourceModule);

      //
      if (!iType.isPrimaryType()) {

        //
        IType primaryType = iType.getSourceResource().getPrimaryType();

        //
        referenceName = primaryType.getFullyQualifiedName();

        // recurse
        return resolvePrimaryType(referenceName, resourceModule);
      }
    }

    //
    catch (AmbiguousElementException e) {
      throw new RuntimeException(e);
    }

    // return the reference name
    return referenceName;
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return false;
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
