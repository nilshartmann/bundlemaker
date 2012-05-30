package org.bundlemaker.core.internal.analysis;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelModifiedListener;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.TypeKey;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.TypeSubCache;
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
public class AdapterRoot2IArtifact extends AbstractBundleMakerArtifactContainer implements IRootArtifact,
    IModularizedSystemChangedListener {

  /** - */
  private IModifiableModularizedSystem                               _modularizedSystem;

  /** - */
  private ArtifactCache                                              _artifactCache;

  /** - */
  private final GroupAndModuleContainerDelegate                      _groupAndModuleContainerDelegate;

  /** - */
  private final IArtifactModelConfiguration                          _artifactModelConfiguration;

  /** - */
  private CurrentAction                                              _currentAction = null;

  private final CopyOnWriteArrayList<IArtifactModelModifiedListener> _artifactModelChangedListeners;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifactCache
   */
  public AdapterRoot2IArtifact(IModifiableModularizedSystem modularizedSystem,
      IArtifactModelConfiguration modelConfiguration, ArtifactCache artifactCache) {
    super(ArtifactType.Root, name(modularizedSystem));

    //
    Assert.isNotNull(modelConfiguration);

    // set the resource module
    _modularizedSystem = modularizedSystem;
    _modularizedSystem.addModularizedSystemChangedListener(this);

    //
    _artifactCache = artifactCache;

    //
    _artifactModelConfiguration = modelConfiguration;

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);

    //
    _artifactModelChangedListeners = new CopyOnWriteArrayList<IArtifactModelModifiedListener>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleArtifact getModuleArtifact(IModule module) {
    return _artifactCache.getModuleCache().get(new ModuleKey(module));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceArtifact getResourceArtifact(IResource resource) {
    return _artifactCache.getResourceCache().get(resource);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ArtifactCache getArtifactCache() {
    return _artifactCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getConfiguration() {
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
  public IGroupArtifact getOrCreateGroup(IPath path) {
    return _groupAndModuleContainerDelegate.getOrCreateGroup(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IBundleMakerArtifact> invalidateDependencyCache() {
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
  public String handleCanAdd(IBundleMakerArtifact artifact) {
    //
    if (!(artifact.getType().equals(ArtifactType.Group) || artifact instanceof AdapterModule2IArtifact)) {
      return "Only groups and modules are addable to root";
    }

    // prevent entries with duplicate names entries
    if (getChild(artifact.getName()) != null) {
      return String.format("The group '%s' already contains a child with the name '%s'.", this.getQualifiedName(),
          artifact.getName());
    }

    //
    return null;
  }

  @Override
  public IRootArtifact getRoot() {
    return this;
  }

  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {
      if (!AdapterUtils.addModuleToModularizedSystem(artifact, null)) {
        internalAddArtifact(artifact);
      }
    }
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.removeResourceModuleFromModularizedSystem(artifact)) {
      internalRemoveArtifact(artifact);
    }
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public IDependencyModel getDependencyModel() {
  // return _dependencyModel;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {

    //
    if (visitor.visit(this)) {

      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  public void accept(IArtifactTreeVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitAdded(MovableUnitMovedEvent event) {

    // get the movable unit
    IMovableUnit movableUnit = event.getMovableUnit();
    IArtifactModelConfiguration configuration = getConfiguration();

    // Step 1: Handle resources
    if (!configuration.containsNoResources()) {

      // Step 1a: Handle BinaryContent
      if (configuration.isBinaryContent() && movableUnit.hasAssociatedBinaryResources()) {

        // iterate over the associated binary resources
        for (IResource resource : movableUnit.getAssociatedBinaryResources()) {
          _addResource(resource);
        }
      }
      // Step 1b: Handle SourceContent
      else if (configuration.isSourceContent() && movableUnit.hasAssociatedSourceResource()) {

        // iterate over the associated binary resources
        _addResource(movableUnit.getAssociatedSourceResource());
      }
      // TODO: BUGFIX!!
      else if (configuration.isSourceContent() && movableUnit.hasAssociatedBinaryResources()) {

        // iterate over the associated binary resources
        for (IResource resource : movableUnit.getAssociatedBinaryResources()) {
          _addResource(resource);
        }
      }
    }

    // TODO
    if (movableUnit.hasAssociatedTypes()) {

      for (IType type : movableUnit.getAssociatedTypes()) {

        // filter local or anonymous type names
        if ((!getConfiguration().isAggregateInnerTypes() && !type.isLocalOrAnonymousType())
            || (getConfiguration().isAggregateInnerTypes() && !type.isInnerType() && type.handleAsPrimaryType())) {
          TypeKey typeKey = new TypeKey(type);
          IBundleMakerArtifact artifact = _artifactCache.getTypeCache().getOrCreate(typeKey);
          AbstractBundleMakerArtifactContainer parentArtifact = _artifactCache.getTypeCache().getTypeParent(
              typeKey.getType());
          parentArtifact.internalAddArtifact(artifact);
        }
      }
    }
  }

  private void _addResource(IResource resource) {

    // skip type resources if necessary
    if (resource.containsTypes() && getConfiguration().containsOnlyNonTypeResources()) {
      return;
    }

    // skip local or anonymous types (no 'Bla$1.class' resources)
    if (resource.hasPrimaryType() && resource.getPrimaryType().isLocalOrAnonymousType()) {
      return;
    }

    // add the resource
    IBundleMakerArtifact artifact = _artifactCache.getResourceCache().getOrCreate(resource);
    AbstractBundleMakerArtifactContainer parentArtifact = _artifactCache.getResourceCache().getOrCreateParent(resource);
    parentArtifact.internalAddArtifact(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitRemoved(MovableUnitMovedEvent event) {

    //
    if (hasCurrentAction()) {

      if (!getCurrentAction().getChild().hasParent()) {
        return;
      }

      if (getCurrentAction().getChild().getParent().equals(getCurrentAction().getParent())
          && getCurrentAction().getChangeAction().equals(ChangeAction.REMOVED)) {

        //
        ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent())
            .internalRemoveArtifact(getCurrentAction().getChild());

      } else {
        removeMovableUnitArtifacts(event);
      }

    } else {
      removeMovableUnitArtifacts(event);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  private void removeMovableUnitArtifacts(MovableUnitMovedEvent event) {

    //
    IMovableUnit movableUnit = event.getMovableUnit();
    IArtifactModelConfiguration configuration = getConfiguration();

    if (configuration.isBinaryContent() && movableUnit.hasAssociatedBinaryResources()
        && (configuration.containsAllResources() || !movableUnit.hasAssociatedTypes())) {
      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {
        IBundleMakerArtifact artifact = _artifactCache.getResourceCache().get(resource);

        if (artifact != null && artifact.getParent() != null) {
          ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
    } else if (configuration.isSourceContent() && movableUnit.hasAssociatedSourceResource()
        && (configuration.containsAllResources() || !movableUnit.hasAssociatedTypes())) {
      IResource resource = movableUnit.getAssociatedSourceResource();
      IBundleMakerArtifact artifact = _artifactCache.getResourceCache().get(resource);

      if (artifact != null && artifact.getParent() != null) {
        ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
      }
    } else if (movableUnit.hasAssociatedTypes()) {
      for (IType type : movableUnit.getAssociatedTypes()) {

        //
        TypeSubCache typeCache = _artifactCache.getTypeCache();

        //
        IBundleMakerArtifact artifact = typeCache.get(new TypeKey(type));
        if (artifact != null && artifact.getParent() != null) {
          ((AbstractBundleMakerArtifactContainer) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleAdded(ModuleMovedEvent event) {

    Assert.isTrue(hasCurrentAction());

    // initiated by an artifact tree action?
    if (hasCurrentAction()) {

      //
      ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent()).internalAddArtifact(getCurrentAction()
          .getChild());

    } else {

      //
      ModuleSubCache moduleCache = _artifactCache.getModuleCache();

      //
      AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) moduleCache.getOrCreate(new ModuleKey(event
          .getModule()));

      //
      if (moduleArtifact.getParent() == null) {
        IGroupAndModuleContainer parent = moduleCache.getModuleParent(event.getModule());
        ((AbstractBundleMakerArtifactContainer) parent).internalAddArtifact(moduleArtifact);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleRemoved(ModuleMovedEvent event) {

    if (hasCurrentAction()) {

      ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent()).internalRemoveArtifact(getCurrentAction()
          .getChild());

    } else {

      //
      AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) _artifactCache.getModuleCache().get(
          new ModuleKey(event.getModule()));

      //
      if (moduleArtifact != null) {
        ((AbstractBundleMakerArtifactContainer) moduleArtifact.getParent()).setParent(null);
      }
    }

    // //
    // if (hasCurrentAction() && getCurrentAction().getChild().getParent().equals(getCurrentAction().getParent())
    // && getCurrentAction().getChangeAction().equals(ChangeAction.REMOVED)) {
    //
    // //
    // ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent()).internalRemoveArtifact(getCurrentAction()
    // .getChild());
    //
    // }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleClassificationChanged(ModuleClassificationChangedEvent event) {

    if (hasCurrentAction()) {

      //
      if (getCurrentAction().getChangeAction().equals(ChangeAction.ADDED)) {

        //
        ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent()).internalAddArtifact(getCurrentAction()
            .getChild());
      }

      //
      else if (getCurrentAction().getChangeAction().equals(ChangeAction.REMOVED)) {

        //
        ((AbstractBundleMakerArtifactContainer) getCurrentAction().getParent())
            .internalRemoveArtifact(getCurrentAction().getChild());
      }

    } else {

      //
      IModule module = event.getModule();
      IModuleArtifact moduleArtifact = _artifactCache.getModuleCache().getOrCreate(new ModuleKey(module));

      //
      IPath classification = module.getClassification();

      if (classification != null) {

        //
        IGroupAndModuleContainer groupArtifact = _artifactCache.getGroupCache().getOrCreate(classification);
        //
        ((AbstractBundleMakerArtifactContainer) groupArtifact).internalAddArtifact(moduleArtifact);

      } else {
        internalAddArtifact(moduleArtifact);
      }
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
   * @param modularizedSystem
   * @return
   */
  private static String name(IModifiableModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    return modularizedSystem.getName();
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void addArtifactModelChangedListener(IArtifactModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.addIfAbsent(listener);
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void removeArtifactModelChangedListener(IArtifactModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.remove(listener);
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void fireArtifactModelChanged() {
    for (IArtifactModelModifiedListener artifactModelChangedListener : _artifactModelChangedListeners) {
      artifactModelChangedListener.artifactModelModified();
    }
  }
}
