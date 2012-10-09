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

import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.impl.Dependency;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractBundleMakerArtifact implements IMovableUnit, IBundleMakerArtifact,
    ITypeArtifact {

  /** the bundle maker type */
  private IType                                  _type;

  /** - */
  private ArtifactCache                          _artifactCache;

  /** - */
  private Map<IBundleMakerArtifact, IDependency> _cachedDependencies;

  /** - */
  private IMovableUnit                           _movableUnit;

  private IRootArtifact                          _root;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public AdapterType2IArtifact(IType type, ArtifactCache defaultArtifactCache, IBundleMakerArtifact parent) {

    super(type.getName());

    Assert.isNotNull(type.isPrimaryType());
    Assert.isNotNull(defaultArtifactCache);
    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractBundleMakerArtifactContainer) parent).getModifiableChildren().add(this);

    _type = type;

    _artifactCache = defaultArtifactCache;

    //
    _movableUnit = MovableUnit.createFromType(type, defaultArtifactCache.getModularizedSystem());
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends IDependency> getDependencies(IBundleMakerArtifact... artifacts) {
    return Collections.emptyList();
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

  @Override
  public <T extends IBundleMakerArtifact> T getChildByPath(Class<T> clazz, IPath path) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getConfiguration() {
    return getRoot().getConfiguration();
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
  public List<IBundleMakerArtifact> invalidateDependencyCache() {
    _cachedDependencies = null;
    return Arrays.asList(new IBundleMakerArtifact[] { this });
  }

  @Override
  public Map<IBundleMakerArtifact, IDependency> getCachedDependencies() {
    return _cachedDependencies;
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
  public void setParent(IBundleMakerArtifact parent) {

    //
    super.setParent(parent);

    //
    getRoot();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (IRootArtifact) getParent(IRootArtifact.class);
    }

    //
    return _root;
  }

  public IBundleMakerArtifact getParent() {
    return (IBundleMakerArtifact) super.getParent();
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

  @Override
  public IDependency getDependency(IBundleMakerArtifact artifact) {

    //
    initDependencies();

    // TODO: RESOURCES!
    // STICKYTYPES!!

    //
    if (artifact.getLeafs() == null) {
      return _cachedDependencies.get(artifact);
    } else {
      Dependency dependencyContainer = new Dependency(this, artifact, false);
      for (IBundleMakerArtifact leaf : artifact.getLeafs()) {
        IDependency dependency = getDependency(leaf);
        if ((dependency != null) && (dependency.getTo().isInstanceOf(ITypeArtifact.class))) {
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
  public Collection<IBundleMakerArtifact> getLeafs() {

    // simply return null
    return null;
  }

  @Override
  public IModularizedSystem getModularizedSystem() {
    return getRoot().getModularizedSystem();
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
    _cachedDependencies = new HashMap<IBundleMakerArtifact, IDependency>();

    // TODO: WRONG!
    initReferences(_type.getReferences(), false);
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

      // skip self references
      if (referenceName.equals(this.getQualifiedName())) {
        continue;
      }

      //
      IBundleMakerArtifact artifact = _artifactCache.getTypeArtifact(referenceName, false);

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
          Dependency dependency = new Dependency(this, artifact, true);

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
  public void accept(IArtifactTreeVisitor visitor) {
    //
    visitor.visit(this);
  }

  public void accept(IArtifactTreeVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  @Override
  public void addArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addArtifacts(List<? extends IBundleMakerArtifact> artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addArtifacts(IArtifactSelector artifactSelector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeArtifacts(List<? extends IBundleMakerArtifact> artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeArtifacts(IArtifactSelector artifactSelector) {
    throw new UnsupportedOperationException();
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
