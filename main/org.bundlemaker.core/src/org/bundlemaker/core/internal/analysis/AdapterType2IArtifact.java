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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifact;
import org.bundlemaker.analysis.model.impl.Dependency;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
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
  private DefaultArtifactCache        _artifactCache;

  /** - */
  private Map<IArtifact, IDependency> _cachedDependencies;

  // /** - */
  // private boolean _aggregateNonPrimaryTypes;

  /** - */
  private IMovableUnit                _movableUnit;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public AdapterType2IArtifact(IType type, DefaultArtifactCache defaultArtifactCache, IArtifact parent) {

    super(ArtifactType.Type, type.getName());

    Assert.isNotNull(type.isPrimaryType());
    Assert.isNotNull(defaultArtifactCache);
    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);

    _type = type;

    _artifactCache = defaultArtifactCache;

    //
    _movableUnit = MovableUnit.createFromType(type);
  }

  @Override
  public boolean containsTypesOrResources() {
    return true;
  }

  @Override
  public boolean isVirtual() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    //
    IArtifact artifact = getParent(ArtifactType.Module);

    //
    if (!(artifact instanceof IModuleArtifact)
        && ((IModuleArtifact) artifact).getAssociatedModule() instanceof IResourceModule) {
      return false;
    }

    //
    return true;
  }

  @Override
  public List<IArtifact> invalidateDependencyCache() {
    _cachedDependencies = null;
    return Arrays.asList(new IArtifact[] { this });
  }

  @Override
  public Map<IArtifact, IDependency> getCachedDependencies() {
    return _cachedDependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResource> getAssociatedBinaryResources() {
    return _movableUnit.getAssociatedBinaryResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedSourceResource() {
    return _movableUnit.hasAssociatedSourceResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getAssociatedSourceResource() {
    return _movableUnit.getAssociatedSourceResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IType> getAssociatedTypes() {
    return _movableUnit.getAssociatedTypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _type.getFullyQualifiedName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {
    return (IRootArtifact) getParent(ArtifactType.Root);
  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
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

  @Override
  public IDependencyModel getDependencyModel() {
    return ((AbstractAdvancedContainer) getParent(ArtifactType.Root)).getDependencyModel();
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

    // TODO: WRONG!
    initReferences(_type.getReferences(), _artifactCache.getConfiguration().isAggregateInnerTypes());

    //
    if (_artifactCache.getConfiguration().isAggregateInnerTypes()) {

      if (_type.hasSourceResource()) {
        for (IType type : _type.getSourceResource().getContainedTypes()) {
          if (!treatAsPrimaryType(type)) {
            initReferences(type.getReferences(), false);
          }
        }
      }

      if (_type.hasBinaryResource()) {
        for (IResource stickyResource : _type.getBinaryResource().getStickyResources()) {
          for (IType type : stickyResource.getContainedTypes()) {
            initReferences(type.getReferences(), false);
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
  private void initReferences(Collection<? extends IReference> references, boolean isPrimaryType) {

    // iterate over all references
    for (IReference reference : references) {

      // get the reference name
      String referenceName = reference.getFullyQualifiedName();

      // resolve the top level type
      if (_artifactCache.getConfiguration().isAggregateInnerTypes()) {
        referenceName = resolvePrimaryType(referenceName, (IResourceModule) _type.getModule(getModularizedSystem()));
      }

      // skip self references
      if (referenceName.equals(this.getQualifiedName())) {
        continue;
      }

      //
      IArtifact artifact = _artifactCache.getTypeArtifact(referenceName, false);

      // does the artifact exist?
      if (artifact != null) {

        // get the cached instance
        if (_cachedDependencies.containsKey(artifact)) {

          //
          if (referenceName.equals(reference.getFullyQualifiedName()) && isPrimaryType) {

            // set the dependency kind
            ((Dependency) _cachedDependencies.get(artifact)).setDependencyKind(getDependencyKind(reference));
          }

        } else {

          // map to dependency
          Dependency dependency = new Dependency(this, artifact);

          // top level primary types only
          if (referenceName.equals(reference.getFullyQualifiedName()) && isPrimaryType) {
            dependency.setDependencyKind(getDependencyKind(reference));
          }

          //
          if (dependency.getFrom().getQualifiedName().equals(dependency.getTo().getQualifiedName())) {
            throw new RuntimeException(dependency.getFrom().getQualifiedName().toString());
          }

          //
          _cachedDependencies.put(artifact, dependency);
        }
      }

      //
      else {
        System.out.println("MISSING TYPE: " + referenceName);
      }
    }
  }

  private DependencyKind getDependencyKind(IReference reference) {
    DependencyKind dependencyKind = DependencyKind.USES;
    if (reference.isImplements()) {
      dependencyKind = DependencyKind.IMPLEMENTS;
    } else if (reference.isExtends()) {
      dependencyKind = DependencyKind.EXTENDS;
    } else if (reference.isClassAnnotation()) {
      dependencyKind = DependencyKind.ANNOTATES;
    }
    return dependencyKind;
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
    Assert.isNotNull(referenceName);

    // TODO replace with containsType(referenceName)
    if (getModularizedSystem().getTypes(referenceName, resourceModule).isEmpty()) {
      return referenceName;
    }

    // step 1: inner type (e.g. 'de.example.Test$InnerClass')
    if (JavaTypeUtils.isInnerTypeName(referenceName)) {

      // get the reference name
      referenceName = JavaTypeUtils.getEnclosingNonInnerTypeName(referenceName);

      // recurse
      return resolvePrimaryType(referenceName, resourceModule);
    }

    // step 2:
    try {

      // the referenced type
      IType iType = getModularizedSystem().getType(referenceName, resourceModule);

      //
      if (!treatAsPrimaryType(iType)) {

        //
        IType primaryType = iType.getSourceResource().getPrimaryType();

        //
        String primaryTypeName = primaryType.getFullyQualifiedName();

        // recurse
        return resolvePrimaryType(primaryTypeName, resourceModule);
      }
    }

    //
    catch (AmbiguousElementException e) {
      throw new RuntimeException(e);
    }

    // return the reference name
    return referenceName;
  }

  /**
   * <p>
   * </p>
   * 
   * @param iType
   * @return
   */
  private boolean treatAsPrimaryType(IType iType) {
    return iType.isPrimaryType() || !iType.hasSourceResource() || !iType.getSourceResource().hasPrimaryType();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {
    //
    visitor.visit(this);
  }

  @Override
  public void addArtifact(IArtifact artifact) {
    throw new UnsupportedOperationException();
  }
}
