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
import java.util.Set;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.analysis.AbstractArtifact;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.bundlemaker.core.spi.analysis.Dependency;
import org.bundlemaker.core.spi.analysis.IReferencedArtifact;
import org.bundlemaker.core.spi.analysis.IReferencingArtifact;
import org.bundlemaker.core.spi.analysis.ReferencedArtifactTrait;
import org.bundlemaker.core.spi.analysis.ReferencingArtifactTrait;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 */
public class AdapterType2IArtifact extends AbstractArtifact implements IMovableUnit,
    ITypeArtifact, IReferencingArtifact, IReferencedArtifact, ITempTypeProvider {

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

  public Collection<IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {
    return _referencingArtifact.getDependenciesTo(artifacts);
  }

  public Collection<IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
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

  public Collection<IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {
    return _referencedArtifact.getDependenciesFrom(artifacts);
  }

  public Collection<IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
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

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.ITypeArtifact#getQualifiedTypeName()
   */
  @Override
  public String getQualifiedTypeName() {

    //
    IBundleMakerArtifact parent = getParent();

    String prefix = null;

    // Top-Level class
    ITypeArtifact typeArtifact = getParent(ITypeArtifact.class);
    if (typeArtifact != null) {
      prefix = typeArtifact.getQualifiedName() + "$";
    } else {
      IPackageArtifact packageArtifact = getParent(IPackageArtifact.class);
      if (packageArtifact == null) {
        throw new IllegalStateException("TypeArtifact '" + this
            + "' does not have IPackageArtifact or ITypeArtifact as Parent but " + parent.getClass());
      }
      prefix = packageArtifact.getPackageName();
      if (!prefix.isEmpty()) {
        prefix += ".";
      }
    }

    return prefix + getName();
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

  public IModule getAssoicatedModule() {
    return _movableUnit.getAssoicatedModule();
  }

  public boolean hasModule() {
    return _movableUnit.hasModule();
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
        && ((IModuleArtifact) artifact).getAssociatedModule().isResourceModule()) {
      return false;
    }

    //
    return true;
  }

  @Override
  public boolean hasAssociatedTypes() {
    return ((ITempTypeProvider) _movableUnit).hasAssociatedTypes();
  }

  @Override
  public boolean hasAssociatedBinaryResources() {
    return _movableUnit.hasAssociatedBinaryResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends IModuleResource> getAssociatedBinaryResources() {
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
  public IModuleResource getAssociatedSourceResource() {
    return _movableUnit.getAssociatedSourceResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IType> getAssociatedTypes() {
    return ((ITempTypeProvider) _movableUnit).getAssociatedTypes();
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

    // STEP 1: initialize all dependencies from this artifact
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
        if (!_referencingArtifact.coreDependenciesToMap().containsKey(referencedArtifact)) {

          // map to dependency
          Dependency dependency = new Dependency(this, referencedArtifact, true);

          if (reference.isExtends()) {
            dependency.setDependencyKind(DependencyKind.EXTENDS);
          } else if (reference.isImplements()) {
            dependency.setDependencyKind(DependencyKind.IMPLEMENTS);
          }

          //
          if (dependency.getFrom().getQualifiedName().equals(dependency.getTo().getQualifiedName())) {
            throw new RuntimeException(dependency.getFrom().getQualifiedName().toString());
          }

          //
          _referencingArtifact.coreDependenciesToMap().put(referencedArtifact, dependency);
        }
      }
    }

    // STEP 2: initialize all dependencies to this artifact
    // this is necessary to filter unwanted references to types that occur multiple times!
    if (_type.equals(((IModifiableModularizedSystem) getModularizedSystem()).adaptAs(ITypeModularizedSystem.class)
        .getType(_type.getFullyQualifiedName()))) {

      //
      Set<IType> referringTypes = ((AbstractCachingModularizedSystem) getModularizedSystem())
          .adaptAs(ITypeModularizedSystem.class)
          .getTypeNameToReferringCache().get(
              _type.getFullyQualifiedName());

      //
      if (referringTypes != null) {

        //
        for (IType referringType : referringTypes) {

          //
          if (referringType.equals(_type)) {
            continue;
          }

          //
          IBundleMakerArtifact referringArtifact = _artifactCache.getTypeArtifact(referringType, false);

          // does the artifact exist?
          if (referringArtifact != null) {

            // get the cached instance
            if (!_referencedArtifact.coreDependenciesFromMap().containsKey(referringArtifact)) {

              // map to dependency
              Dependency dependency = new Dependency(referringArtifact, this, true);

              //
              if (dependency.getFrom().getQualifiedName().equals(dependency.getTo().getQualifiedName())) {
                // throw new RuntimeException(dependency.getFrom().getQualifiedName().toString());
              } else {
                //
                _referencedArtifact.coreDependenciesFromMap().put(referringArtifact, dependency);
              }
            }
          }
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.spi.AbstractArtifact#addDefaultProperties(java.util.HashMap)
   */
  @Override
  protected void addDefaultProperties(HashMap<String, Object> properties) {
    super.addDefaultProperties(properties);

    IPackageArtifact packageArtifact = getParent(IPackageArtifact.class);
    String namespace = (packageArtifact == null ? "" : packageArtifact.getPackageName());
    properties.put("namespace", namespace);
    properties.put("abstract", getAssociatedType().isAbstractType());

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

  @Override
  protected String getArtifactType() {
    return "type";
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
