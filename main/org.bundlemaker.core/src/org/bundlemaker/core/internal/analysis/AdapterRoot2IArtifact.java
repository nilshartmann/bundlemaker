package org.bundlemaker.core.internal.analysis;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.TypeKey;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.analysis.cache.impl.TypeSubCache;
import org.bundlemaker.core.internal.modules.AbstractModule;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.event.ClassificationChangedEvent;
import org.bundlemaker.core.modules.event.GroupChangedEvent;
import org.bundlemaker.core.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.modules.event.ModuleMovedEvent;
import org.bundlemaker.core.modules.event.MovableUnitMovedEvent;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterRoot2IArtifact extends AbstractArtifactContainer implements IRootArtifact,
    IModularizedSystemChangedListener {

  /** - */
  private IModifiableModularizedSystem                               _modularizedSystem;

  /** - */
  private ArtifactCache                                              _artifactCache;

  /** - */
  private final GroupAndModuleContainerDelegate                      _groupAndModuleContainerDelegate;

  /** - */
  private final IAnalysisModelConfiguration                          _artifactModelConfiguration;

  /** - */
  private final CopyOnWriteArrayList<IAnalysisModelModifiedListener> _artifactModelChangedListeners;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifactCache
   */
  public AdapterRoot2IArtifact(IModifiableModularizedSystem modularizedSystem,
      IAnalysisModelConfiguration modelConfiguration, ArtifactCache artifactCache) {
    super(name(modularizedSystem));

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
    _artifactModelChangedListeners = new CopyOnWriteArrayList<IAnalysisModelModifiedListener>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleArtifact getModuleArtifact(IModule module) {
    Assert.isNotNull(module);
    return _artifactCache.getModuleCache().get(new ModuleKey(module));
  }

  @Override
  public IGroupArtifact getGroupArtifact(IGroup group) {
    Assert.isNotNull(group);
    return (IGroupArtifact) _artifactCache.getGroupCache().get(group);
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
  public IAnalysisModelConfiguration getConfiguration() {
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
  public IGroupArtifact getOrCreateGroup(String path) {
    return _groupAndModuleContainerDelegate.getOrCreateGroup(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onInvalidateCaches() {
    super.onInvalidateCaches();
    accept(new InvalidateAggregatedDependencies());
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
    if (!(artifact.isInstanceOf(IGroupArtifact.class) || artifact instanceof AdapterModule2IArtifact)) {
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
  public IPath getFullPath() {
    return new Path(".");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {

    //
    Assert.isNotNull(artifact);

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact) {
      if (!AdapterUtils.addModulesIfNecessaryAndResetClassification((IModuleArtifact) artifact, null)) {
        internalAddArtifact(artifact);
      }
    } else if (artifact instanceof IGroupArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IGroupArtifact) artifact, this);
    }
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.removeResourceModuleFromModularizedSystem(artifact)) {
      internalRemoveArtifact(artifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IAnalysisModelVisitor visitor) {

    //
    if (visitor.visit(this)) {

      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  public void accept(IAnalysisModelVisitor... visitors) {
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
    IAnalysisModelConfiguration configuration = getConfiguration();

    // Step 1: Handle resources
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

    // TODO
    if (movableUnit.hasAssociatedTypes()) {

      for (IType type : movableUnit.getAssociatedTypes()) {

        // filter local or anonymous type names
        if (!type.isLocalOrAnonymousType()) {
          TypeKey typeKey = new TypeKey(type);
          IBundleMakerArtifact artifact = _artifactCache.getTypeCache().getOrCreate(typeKey);
          AbstractArtifactContainer parentArtifact = _artifactCache.getTypeCache().getTypeParent(
              typeKey.getType());
          parentArtifact.internalAddArtifact(artifact);
        }
      }
    }
  }

  private void _addResource(IResource resource) {

    // skip local or anonymous types (no 'Bla$1.class' resources)
    if (resource.hasPrimaryType() && resource.getPrimaryType().isLocalOrAnonymousType()) {
      return;
    }

    // add the resource
    IBundleMakerArtifact artifact = _artifactCache.getResourceCache().getOrCreate(resource);
    AbstractArtifactContainer parentArtifact = _artifactCache.getResourceCache().getOrCreateParent(resource);
    parentArtifact.internalAddArtifact(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitRemoved(MovableUnitMovedEvent event) {
    removeMovableUnitArtifacts(event);
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
    IAnalysisModelConfiguration configuration = getConfiguration();

    if (configuration.isBinaryContent() && movableUnit.hasAssociatedBinaryResources()
    /* && ( configuration.containsAllResources() || !movableUnit.hasAssociatedTypes()) */) {
      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {
        IBundleMakerArtifact artifact = _artifactCache.getResourceCache().get(resource);

        if (artifact != null && artifact.getParent() != null) {
          ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
    } else if (configuration.isSourceContent() && movableUnit.hasAssociatedSourceResource()
    /* && ( configuration.containsAllResources() || !movableUnit.hasAssociatedTypes()) */) {
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
          ((AbstractArtifactContainer) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleAdded(ModuleMovedEvent event) {

    //
    ModuleSubCache moduleCache = _artifactCache.getModuleCache();

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) moduleCache.getOrCreate(new ModuleKey(event
        .getModule()));

    //
    if (moduleArtifact.getParent() == null) {
      IGroupAndModuleContainer parent = moduleCache.getModuleParent(event.getModule());
      ((AbstractArtifactContainer) parent).internalAddArtifact(moduleArtifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleRemoved(ModuleMovedEvent event) {

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) _artifactCache.getModuleCache().get(
        new ModuleKey(event.getModule()));

    //
    if (moduleArtifact != null) {
      ((AbstractArtifactContainer) moduleArtifact.getParent()).internalRemoveArtifact(moduleArtifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleClassificationChanged(ModuleClassificationChangedEvent event) {

    //
    IModule module = event.getModule();

    //
    IBundleMakerArtifact moduleArtifact = _artifactCache.getModuleCache().getOrCreate(
        new ModuleKey(
            module));

    //
    Group classification = ((AbstractModule<?, ?>) module).getClassificationGroup();

    if (classification != null) {
      IGroupAndModuleContainer groupArtifact = _artifactCache.getGroupCache().getOrCreate(classification);
      ((AbstractArtifactContainer) groupArtifact).internalAddArtifact(moduleArtifact);
    } else {
      internalAddArtifact(moduleArtifact);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void classificationChanged(ClassificationChangedEvent event) {

    //
    if (event.isMovedGroup()) {

      //
      IBundleMakerArtifact movedGroupArtifact = _artifactCache.getGroupCache().getOrCreate(
          event.getMovedGroup());

      //
      if (((Group) event.getMovedGroup()).getParent() == null) {

        //
        if (((Group) event.getMovedGroup()).hasRootParent()) {
          this.internalAddArtifact(movedGroupArtifact);
        }

        //
        else {
          AbstractArtifactContainer currentParent = (AbstractArtifactContainer) movedGroupArtifact
              .getParent();
          currentParent.internalRemoveArtifact(movedGroupArtifact);
        }
      }

      //
      else {
        IBundleMakerArtifact parentArtifact = _artifactCache.getGroupCache()
            .getOrCreate(
                event.getNewParentGroup());

        ((AbstractArtifactContainer) parentArtifact).internalAddArtifact(movedGroupArtifact);
      }
    }

    //
    else if (event.isGroupRenamed()) {
      // nothing to do here...
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void groupAdded(GroupChangedEvent event) {
    getArtifactCache().getGroupCache().getOrCreate((Group) event.getGroup());
  }

  @Override
  public void groupRemoved(GroupChangedEvent event) {
    // TODO Auto-generated method stub

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
  public void addArtifactModelChangedListener(IAnalysisModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.addIfAbsent(listener);
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void removeArtifactModelChangedListener(IAnalysisModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.remove(listener);
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void fireArtifactModelChanged() {
    for (IAnalysisModelModifiedListener artifactModelChangedListener : _artifactModelChangedListeners) {
      artifactModelChangedListener.artifactModelModified();
    }
  }
}
