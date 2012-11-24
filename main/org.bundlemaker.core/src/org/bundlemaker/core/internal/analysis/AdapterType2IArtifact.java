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
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.spi.Dependency;
import org.bundlemaker.core.analysis.spi.IReferencedArtifact;
import org.bundlemaker.core.analysis.spi.IReferencingArtifact;
import org.bundlemaker.core.analysis.spi.ReferencedArtifactTrait;
import org.bundlemaker.core.analysis.spi.ReferencingArtifactTrait;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractArtifact implements IMovableUnit,
    ITypeArtifact, IReferencingArtifact, IReferencedArtifact {

  /** the bundle maker type */
  private IType                    _type;

  /** - */
  private ArtifactCache            _artifactCache;

  /** - */
  private IMovableUnit             _movableUnit;

  /** - */
  private ReferencingArtifactTrait _referencingArtifact;

  /** - */
  private ReferencedArtifactTrait  _referencedArtifact;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public AdapterType2IArtifact(IType type, ArtifactCache defaultArtifactCache, IBundleMakerArtifact parent) {

    //
    super(type.getName());

    //
    Assert.isNotNull(type.isPrimaryType());
    Assert.isNotNull(defaultArtifactCache);
    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);

    //
    _type = type;
    _artifactCache = defaultArtifactCache;

    //
    _movableUnit = MovableUnit.createFromType(type, defaultArtifactCache.getModularizedSystem());
    _referencingArtifact = new ReferencingArtifactTrait() {
      @Override
      protected void initialize() {
        initReferences();
      }
    };
    _referencedArtifact = new ReferencedArtifactTrait() {
      @Override
      protected void initialize() {
        initReferences();
      }
    };
  }

  public Collection<IDependency> getDependenciesTo() {
    return _referencingArtifact.getDependenciesTo();
  }

  public boolean hasDependencyTo(IBundleMakerArtifact artifact) {
    return _referencingArtifact.hasDependencyTo(artifact);
  }

  public IDependency getDependencyTo(IBundleMakerArtifact artifact) {
    return _referencingArtifact.getDependencyTo(artifact);
  }

  public Collection<? extends IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {
    return _referencingArtifact.getDependenciesTo(artifacts);
  }

  public Collection<? extends IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
    return _referencingArtifact.getDependenciesTo(artifacts);
  }

  public Collection<IDependency> getDependenciesFrom() {
    return _referencedArtifact.getDependenciesFrom();
  }

  public boolean hasDependencyFrom(IBundleMakerArtifact artifact) {
    return _referencedArtifact.hasDependencyFrom(artifact);
  }

  public IDependency getDependencyFrom(IBundleMakerArtifact artifact) {
    return _referencedArtifact.getDependencyFrom(artifact);
  }

  public Collection<? extends IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {
    return _referencedArtifact.getDependenciesFrom(artifacts);
  }

  public Collection<? extends IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
    return _referencedArtifact.getDependenciesFrom(artifacts);
  }

  public Map<IBundleMakerArtifact, IDependency> coreDependenciesToMap() {
    return _referencingArtifact.coreDependenciesToMap();
  }

  public Map<IBundleMakerArtifact, IDependency> coreDependenciesFromMap() {
    return _referencedArtifact.coreDependenciesFromMap();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUniquePathIdentifier() {
    return getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getFullPath() {

    //
    if (hasParent()) {
      IPath path = getParent().getFullPath();
      return path.append(getUniquePathIdentifier());

    } else {

      //
      return new Path(getUniquePathIdentifier());
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeFromParent() {
    if (this.getParent() != null) {
      this.getParent().removeArtifact(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasParent() {
    return getParent() != null;
  }

  @Override
  public boolean containsTypesOrResources() {
    return true;
  }

  @Override
  public boolean containsTypes() {
    return true;
  }

  @Override
  public boolean containsResources() {
    return false;
  }

  @Override
  public boolean isVirtual() {
    return false;
  }

  public IResourceModule getContainingResourceModule() {
    return _movableUnit.getContainingResourceModule();
  }

  public boolean hasContainingResourceModule() {
    return _movableUnit.hasContainingResourceModule();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    //
    IBundleMakerArtifact artifact = getParent(IModuleArtifact.class);

    //
    if (!(artifact instanceof IModuleArtifact)
        && ((IModuleArtifact) artifact).getAssociatedModule() instanceof IResourceModule) {
      return false;
    }

    //
    return true;
  }

  @Override
  public boolean hasAssociatedTypes() {
    return _movableUnit.hasAssociatedTypes();
  }

  @Override
  public boolean hasAssociatedBinaryResources() {
    return _movableUnit.hasAssociatedBinaryResources();
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

  @Override
  public Collection<IBundleMakerArtifact> getChildren() {
    return Collections.emptySet();
  }

  @Override
  public <T extends IBundleMakerArtifact> Collection<T> getChildren(Class<T> clazz) {
    return Collections.emptySet();
  }

  @Override
  public boolean contains(IBundleMakerArtifact artifact) {
    return this.equals(artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @param references
   */
  private void initReferences() {

    boolean isPrimaryType = false;

    // iterate over all references
    for (IReference reference : _type.getReferences()) {

      // get the reference name
      String referenceName = reference.getFullyQualifiedName();

      // skip self references
      if (referenceName.equals(this.getQualifiedName())) {
        continue;
      }

      //
      IBundleMakerArtifact referencedArtifact = _artifactCache.getTypeArtifact(referenceName, false);

      // does the artifact exist?
      if (referencedArtifact != null) {

        // get the cached instance
        if (_referencingArtifact.coreDependenciesToMap().containsKey(referencedArtifact)) {

          //
          if (referenceName.equals(reference.getFullyQualifiedName()) && isPrimaryType) {

            // set the dependency kind
            ((Dependency) _referencingArtifact.coreDependenciesToMap().get(referencedArtifact))
                .setDependencyKind(getDependencyKind(reference));
          }

        } else {

          // map to dependency
          Dependency dependency = new Dependency(this, referencedArtifact, true);

          // top level primary types only
          if (referenceName.equals(reference.getFullyQualifiedName()) && isPrimaryType) {
            dependency.setDependencyKind(getDependencyKind(reference));
          }

          //
          if (dependency.getFrom().getQualifiedName().equals(dependency.getTo().getQualifiedName())) {
            throw new RuntimeException(dependency.getFrom().getQualifiedName().toString());
          }

          //
          _referencingArtifact.coreDependenciesToMap().put(referencedArtifact, dependency);

          //
          if (referencedArtifact instanceof IReferencedArtifact) {
            ((IReferencedArtifact) referencedArtifact).coreDependenciesFromMap().put(this, dependency);
          }
        }
      }

      //
      else {
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
  public boolean canAdd(IBundleMakerArtifact artifact) {
    return false;
  }

  @Override
  public boolean canRemove(IBundleMakerArtifact artifact) {
    return false;
  }

  @Override
  public IBundleMakerArtifact getChild(String path) {
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
  public void accept(IAnalysisModelVisitor visitor) {
    //
    visitor.visit(this);
  }

  public void accept(IAnalysisModelVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(IBundleMakerArtifact o) {

    //
    if (o == null) {
      return Integer.MIN_VALUE;
    }

    // compare the qualified name
    return this.getQualifiedName().compareTo(o.getQualifiedName());
  }
}
