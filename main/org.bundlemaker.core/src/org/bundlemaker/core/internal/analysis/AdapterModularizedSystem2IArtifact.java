package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.transformer.AbstractCacheAwareArtifactCache.TypeKey;
import org.bundlemaker.core.internal.analysis.transformer.ModuleResourceKey;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache.ModuleKey;
import org.bundlemaker.core.modules.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleClassificationChangedEvent;
import org.bundlemaker.core.modules.ModuleMovedEvent;
import org.bundlemaker.core.modules.MovableUnitMovedEvent;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModularizedSystem2IArtifact extends AbstractAdvancedContainer implements IRootArtifact,
    IModularizedSystemChangedListener {

  /** - */
  private IModifiableModularizedSystem          _modularizedSystem;

  /** - */
  private DependencyModel                       _dependencyModel;

  /** - */
  private final GroupAndModuleContainerDelegate _groupAndModuleContainerDelegate;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModularizedSystem2IArtifact(IModifiableModularizedSystem modularizedSystem) {
    super(ArtifactType.Root, name(modularizedSystem));

    // set the resource module
    _modularizedSystem = modularizedSystem;
    _modularizedSystem.addModularizedSystemChangedListener(this);

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);
  }

  /**
   * {@inheritDoc}
   */
  public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {
    return _groupAndModuleContainerDelegate.getOrCreateModule(qualifiedModuleName, moduleVersion);
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(String path) {
    return _groupAndModuleContainerDelegate.getOrCreateGroup(path);
  }

  @Override
  public List<IArtifact> invalidateDependencyCache() {
    super.invalidateDependencyCache();
    accept(new InvalidateAggregatedDependencies());
    return null;
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
    return false;
  }

  @Override
  public final String getName() {
    return _modularizedSystem.getName();
  }

  @Override
  public final String getQualifiedName() {
    return getName();
  }

  @Override
  public IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  @Override
  public String handleCanAdd(IArtifact artifact) {

    //
    if (!(artifact.getType().equals(ArtifactType.Group) || artifact instanceof AdapterModule2IArtifact)) {
      return "Only groups and modules are addable to root";
    }

    //
    return null;
  }

  @Override
  public IRootArtifact getRoot() {
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    // // call the super method
    // super.addArtifact(artifact);

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {
      if (!AdapterUtils.addModuleToModularizedSystem(artifact, null)) {
        internalAddArtifact(artifact);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    Assert.isNotNull(artifact);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.removeResourceModuleFromModularizedSystem(artifact);

    // return super.removeArtifact(artifact);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependencyModel getDependencyModel() {
    return _dependencyModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {

    //
    if (visitor.visit(this)) {

      //
      for (IArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitAdded(MovableUnitMovedEvent event) {

    System.out.println("------------------------------------");
    System.out.println(event.getMovableUnit());
    // TODO: RICHTIGE BEHANDLUNG _ SOURCE/BINARY/TYPES etc. abhängig von ModelConfiguration
    IMovableUnit movableUnit = event.getMovableUnit();

    if (movableUnit.hasAssociatedBinaryResources()) {

      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {

        ModuleResourceKey moduleResourceKey = new ModuleResourceKey((IResourceModule) event.getModule(), resource);

        IArtifact artifact = _dependencyModel.getArtifactCache().getResourceCache().getOrCreate(moduleResourceKey);

        if (artifact != null && artifact.getParent() != null) {
          ((AdapterPackage2IArtifact) artifact.getParent()).internalAddArtifact(artifact);
        }
      }
    }

    //
    if (movableUnit.hasAssociatedTypes()) {

      for (IType type : movableUnit.getAssociatedTypes()) {

        TypeKey typeKey = new TypeKey(type);

        IArtifact artifact = _dependencyModel.getArtifactCache().getTypeCache().getOrCreate(typeKey);

        if (artifact != null && artifact.getParent() != null) {
          ((AbstractAdvancedContainer) artifact.getParent()).internalAddArtifact(artifact);
        }
      }
    }

    // for (IType type : movableUnit.getAssociatedTypes()) {
    // _dependencyModel.getArtifactCache().getTypeArtifact(type, false);
    // }

    System.out.println("------------------------------------");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitRemoved(MovableUnitMovedEvent event) {

    System.out.println("***********************************");
    System.out.println(event.getMovableUnit());

    IMovableUnit movableUnit = event.getMovableUnit();

    if (movableUnit.hasAssociatedBinaryResources()) {

      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {

        ModuleResourceKey moduleResourceKey = new ModuleResourceKey((IResourceModule) event.getModule(), resource);

        IArtifact artifact = _dependencyModel.getArtifactCache().getResourceCache().get(moduleResourceKey);

        if (artifact != null && artifact.getParent() != null) {
          ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
    }

    // for (IType type : movableUnit.getAssociatedTypes()) {
    // _dependencyModel.getArtifactCache().getTypeArtifact(type, false);
    // }

    System.out.println("***********************************");
    // _dependencyModel.getArtifactCache().getResourceArtifact(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleAdded(ModuleMovedEvent event) {

    //
    ModuleCache moduleCache = _dependencyModel.getArtifactCache().getModuleCache();

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) moduleCache.getOrCreate(new ModuleKey(event
        .getModule()));

    //
    if (moduleArtifact.getParent() == null) {
      AbstractAdvancedContainer parent = moduleCache.getParent(event.getModule());
      parent.internalAddArtifact(moduleArtifact);
    }
  }

  @Override
  public void moduleRemoved(ModuleMovedEvent event) {

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) _dependencyModel.getArtifactCache()
        .getModuleCache().get(new ModuleKey(event.getModule()));

    //
    if (moduleArtifact != null) {
      ((AbstractAdvancedContainer) moduleArtifact.getParent()).setParent(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleClassificationChanged(ModuleClassificationChangedEvent event) {

    //
    IModule module = event.getModule();
    AbstractArtifactContainer moduleArtifact = _dependencyModel.getArtifactCache().getModuleCache()
        .get(new ModuleKey(module));

    //
    IPath classification = module.getClassification();

    if (classification != null) {

      //
      AbstractAdvancedContainer groupArtifact = _dependencyModel.getArtifactCache().getGroupCache()
          .getOrCreate(classification);
      //
      groupArtifact.internalAddArtifact(moduleArtifact);

    } else {
      internalAddArtifact(moduleArtifact);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencyModel
   */
  void setDependencyModel(DependencyModel dependencyModel) {
    _dependencyModel = dependencyModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  private static String name(IModifiableModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    return modularizedSystem.getName();
  }
}
