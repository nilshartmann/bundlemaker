package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.TypeKey;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.IModule;
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
public class AdapterModularizedSystem2IArtifact extends AbstractBundleMakerArtifactContainer implements IRootArtifact,
    IModularizedSystemChangedListener {

  /** - */
  private IModifiableModularizedSystem          _modularizedSystem;

  /** - */
  private DependencyModel                       _dependencyModel;

  /** - */
  private final GroupAndModuleContainerDelegate _groupAndModuleContainerDelegate;

  /** - */
  private final IArtifactModelConfiguration     _artifactModelConfiguration;

  /** - */
  private CurrentAction                         _currentAction = null;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModularizedSystem2IArtifact(IModifiableModularizedSystem modularizedSystem,
      IArtifactModelConfiguration modelConfiguration) {
    super(ArtifactType.Root, name(modularizedSystem));

    //
    Assert.isNotNull(modelConfiguration);

    // set the resource module
    _modularizedSystem = modularizedSystem;
    _modularizedSystem.addModularizedSystemChangedListener(this);

    //
    _artifactModelConfiguration = modelConfiguration;

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getArtifactModelConfiguration() {
    return _artifactModelConfiguration;
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

  /**
   * {@inheritDoc}
   */
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

    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(new CurrentAction(this,
        (IBundleMakerArtifact) artifact, ChangeAction.REMOVED));

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.removeResourceModuleFromModularizedSystem(artifact)) {
      internalRemoveArtifact(artifact);
    }

    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(null);

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

    System.out.println("------movableUnitAdded------------");
    System.out.println(event.getMovableUnit());

    // TODO: RICHTIGE BEHANDLUNG _ SOURCE/BINARY/TYPES etc. abhängig von ModelConfiguration
    IMovableUnit movableUnit = event.getMovableUnit();

    if (movableUnit.hasAssociatedBinaryResources()) {

      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {

        //
        if (resource.hasPrimaryType() && resource.getPrimaryType().isLocalOrAnonymousType()) {
          continue;
        }

        // get the resource
        IBundleMakerArtifact artifact = _dependencyModel.getArtifactCache().getResourceCache().getOrCreate(resource);
        AbstractBundleMakerArtifactContainer parentArtifact = _dependencyModel.getArtifactCache().getResourceCache()
            .getOrCreateParent(resource);

        parentArtifact.internalAddArtifact(artifact);
      }
    }

    //
    if (movableUnit.hasAssociatedTypes()) {

      for (IType type : movableUnit.getAssociatedTypes()) {

        TypeKey typeKey = new TypeKey(type);

        IArtifact artifact = _dependencyModel.getArtifactCache().getTypeCache().getOrCreate(typeKey);
        AbstractBundleMakerArtifactContainer parentArtifact = _dependencyModel.getArtifactCache().getTypeCache()
            .getTypeParent(typeKey.getType());

        parentArtifact.internalAddArtifact(artifact);
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

    System.out.println("*******movableUnitRemoved*********");
    System.out.println(event.getMovableUnit());

    IMovableUnit movableUnit = event.getMovableUnit();

    if (movableUnit.hasAssociatedBinaryResources()) {

      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {

        IArtifact artifact = _dependencyModel.getArtifactCache().getResourceCache().get(resource);

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

    System.out.println("moduleAdded" + event.getModule());

    //
    ModuleSubCache moduleCache = _dependencyModel.getArtifactCache().getModuleCache();

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) moduleCache.getOrCreate(new ModuleKey(event
        .getModule()));

    //
    if (moduleArtifact.getParent() == null) {
      AbstractBundleMakerArtifactContainer parent = moduleCache.getModuleParent(event.getModule());
      parent.internalAddArtifact(moduleArtifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleRemoved(ModuleMovedEvent event) {

    //
    if (hasCurrentAction() && getCurrentAction().getChild().getParent().equals(getCurrentAction().getParent())
        && getCurrentAction().getChangeAction().equals(ChangeAction.REMOVED)) {

      //
      ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent()).internalRemoveArtifact(getCurrentAction()
          .getChild());

    } else {

      //
      AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) _dependencyModel.getArtifactCache()
          .getModuleCache().get(new ModuleKey(event.getModule()));

      //
      if (moduleArtifact != null) {
        ((AbstractBundleMakerArtifactContainer) moduleArtifact.getParent()).setParent(null);
      }
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
      AbstractBundleMakerArtifactContainer groupArtifact = _dependencyModel.getArtifactCache().getGroupCache()
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
   * @return
   */
  public CurrentAction getCurrentAction() {
    return _currentAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @param currentAction
   */
  public void setCurrentAction(CurrentAction currentAction) {
    _currentAction = currentAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @param currentAction
   */
  public boolean hasCurrentAction() {
    return _currentAction != null;
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
