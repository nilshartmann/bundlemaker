package org.bundlemaker.core.internal.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.impl.ModuleSubCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.modelext.ModelExtFactory;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.Module;
import org.bundlemaker.core.internal.modules.event.ClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.GroupChangedEvent;
import org.bundlemaker.core.internal.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.internal.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleIdentifierChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleMovedEvent;
import org.bundlemaker.core.internal.modules.event.MovableUnitMovedEvent;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.analysis.AbstractArtifact;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

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

  /** - */
  private boolean                                                    _cachesInitialized;

  private boolean                                                    _isInInvalidationCaches;

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
    _modularizedSystem.getListenerList().addModularizedSystemChangedListener(this);

    //
    _artifactCache = artifactCache;

    //
    _artifactModelConfiguration = modelConfiguration;

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);

    //
    _artifactModelChangedListeners = new CopyOnWriteArrayList<IAnalysisModelModifiedListener>();
  }

  @Override
  public void resetTransformations() {
    getModularizedSystem().undoTransformations(null);
  }

  @Override
  public final void disableModelModifiedNotification(boolean isDisabled) {
    getModularizedSystem().getListenerList().disableModelModifiedNotification(isDisabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void modelModifiedNotificationDisabled(boolean isDisabled) {
    //
    if (!isDisabled) {
      for (IAnalysisModelModifiedListener artifactModelChangedListener : _artifactModelChangedListeners) {
        artifactModelChangedListener.analysisModelModified();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isModelModifiedNotificationDisabled() {
    return getModularizedSystem().getListenerList().isModelModifiedNotificationDisabled();
  }

  @Override
  public final boolean areCachesInitialized() {
    return _cachesInitialized;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void initializeCaches(IProgressMonitor progressMonitor) {

    //
    this.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        ((AbstractArtifact) artifact).initializeCaches();
        return true;
      }
    });

    //
    _cachesInitialized = true;
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
  public IGroupArtifact getGroupArtifact(Group group) {
    Assert.isNotNull(group);
    return (IGroupArtifact) _artifactCache.getGroupCache().get(group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceArtifact getResourceArtifact(IModuleResource resource) {
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitChanged(MovableUnitMovedEvent event, ChangeAction action) {

    switch (action) {
    case ADDED: {
      // get the movable unit
      IModuleAwareMovableUnit movableUnit = event.getMovableUnit();
      IAnalysisModelConfiguration configuration = getConfiguration();

      // Step 1: Handle resources
      // Step 1a: Handle BinaryContent
      if (configuration.isBinaryContent() && movableUnit.hasAssociatedBinaryResources()) {

        // iterate over the associated binary resources
        for (IProjectContentResource resource : movableUnit.getAssociatedBinaryResources()) {
          _addResource(resource.adaptAs(IModuleResource.class));
        }
      }
      // Step 1b: Handle SourceContent
      else if (configuration.isSourceContent() && movableUnit.hasAssociatedSourceResource()) {

        // iterate over the associated binary resources
        _addResource(movableUnit.getAssociatedSourceResource().adaptAs(IModuleResource.class));
      }
      // TODO: BUGFIX!!
      else if (configuration.isSourceContent() && movableUnit.hasAssociatedBinaryResources()) {

        // iterate over the associated binary resources
        for (IProjectContentResource resource : movableUnit.getAssociatedBinaryResources()) {
          _addResource(resource.adaptAs(IModuleResource.class));
        }
      }

      break;
    }
    case REMOVED: {
      //
      IModuleAwareMovableUnit movableUnit = event.getMovableUnit();
      IAnalysisModelConfiguration configuration = getConfiguration();

      if (configuration.isBinaryContent() && movableUnit.hasAssociatedBinaryResources()
      /* && ( configuration.containsAllResources() || !movableUnit.hasAssociatedTypes()) */) {
        for (IProjectContentResource resource : movableUnit.getAssociatedBinaryResources()) {
          IBundleMakerArtifact artifact = _artifactCache.getResourceCache().get(resource);

          if (artifact != null && artifact.getParent() != null) {
            ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
          }
        }
      } else if (configuration.isSourceContent() && movableUnit.hasAssociatedSourceResource()
      /* && ( configuration.containsAllResources() || !movableUnit.hasAssociatedTypes()) */) {
        IProjectContentResource resource = movableUnit.getAssociatedSourceResource();
        IBundleMakerArtifact artifact = _artifactCache.getResourceCache().get(resource);

        if (artifact != null && artifact.getParent() != null) {
          ((AdapterPackage2IArtifact) artifact.getParent()).internalRemoveArtifact(artifact);
        }
      }
      break;
    }
    }

    //
    handleModelModification();
  }

  private void _addResource(IModuleResource resource) {

    //
    if (!ModelExtFactory.getModelExtensionFactory().shouldAddResourceArtifact(resource)) {
      return;
    }

    // add the resource
    IBundleMakerArtifact artifact = _artifactCache.getResourceCache().getOrCreate(resource);
    AbstractArtifactContainer parentArtifact = _artifactCache.getResourceCache().getOrCreateParent(resource);
    parentArtifact.internalAddArtifact(artifact);
  }

  @Override
  public void moduleIdentifierChanged(ModuleIdentifierChangedEvent event) {

    //
    ModuleSubCache moduleCache = _artifactCache.getModuleCache();

    //
    AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) moduleCache.getOrCreate(new ModuleKey(event
        .getModule()));

    moduleArtifact.setName(event.getModule().getModuleIdentifier().toString());

    //
    handleModelModification();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void moduleChanged(ModuleMovedEvent event, ChangeAction changeAction) {

    switch (changeAction) {
    case ADDED: {

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
      break;
    }
    case REMOVED: {
      AdapterModule2IArtifact moduleArtifact = (AdapterModule2IArtifact) _artifactCache.getModuleCache().get(
          new ModuleKey(event.getModule()));
      if (moduleArtifact != null) {
        ((AbstractArtifactContainer) moduleArtifact.getParent()).internalRemoveArtifact(moduleArtifact);
      }
      break;
    }
    }

    //
    handleModelModification();
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
    Group classification = ((Module) module).getClassificationGroup();

    if (classification != null) {
      IGroupAndModuleContainer groupArtifact = _artifactCache.getGroupCache().getOrCreate(classification);
      ((AbstractArtifactContainer) groupArtifact).internalAddArtifact(moduleArtifact);
    } else {
      internalAddArtifact(moduleArtifact);
    }

    //
    handleModelModification();
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
      if (((Group) event.getMovedGroup()).getParentGroup() == null) {

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

    //
    handleModelModification();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void groupChanged(GroupChangedEvent event, ChangeAction changeAction) {

    switch (changeAction) {
    case ADDED: {
      getArtifactCache().getGroupCache().getOrCreate((Group) event.getGroup());
      break;
    }
    case REMOVED: {
      IGroupArtifact groupArtifact = getRoot().getGroupArtifact(event.getGroup());
      AbstractArtifactContainer parent = (AbstractArtifactContainer) groupArtifact.getParent();
      parent.internalRemoveArtifact(groupArtifact);
      break;
    }
    }

    //
    handleModelModification();
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
  public void addAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.addIfAbsent(listener);
  }

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  public void removeAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener) {

    Assert.isNotNull(listener);

    _artifactModelChangedListeners.remove(listener);
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void handleModelModification() {

    //
    if (!getModularizedSystem().getListenerList().isHandleModelModification()) {
      return;
    }

    //
    _cachesInitialized = false;

    //
    _isInInvalidationCaches = true;
    this.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        ((AbstractArtifact) artifact).invalidateCaches();
        return true;
      }
    });
    _isInInvalidationCaches = false;

    //
    if (!getModularizedSystem().getListenerList().isModelModifiedNotificationDisabled()) {
      for (IAnalysisModelModifiedListener artifactModelChangedListener : _artifactModelChangedListeners) {
        artifactModelChangedListener.analysisModelModified();
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isInInvalidationCaches() {
    return _isInInvalidationCaches;
  }

  // --- Blueprint API -------------------
  private final Function<IDependency, Edge> _dependencyToEdgeConverterFunction = new Function<IDependency, Edge>() {
                                                                                 @Override
                                                                                 public Edge apply(
                                                                                     IDependency dependency) {
                                                                                   return dependency;
                                                                                 }
                                                                               };

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getFeatures()
   */
  @Override
  public Features getFeatures() {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#addVertex(java.lang.Object)
   */
  @Override
  public Vertex addVertex(Object id) {
    throw new UnsupportedOperationException("addVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertex(java.lang.Object)
   */
  @Override
  public Vertex getVertex(Object id) {
    throw new UnsupportedOperationException("getVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeVertex(com.tinkerpop.blueprints.Vertex)
   */
  @Override
  public void removeVertex(Vertex vertex) {
    throw new UnsupportedOperationException("removeVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertices()
   */
  @Override
  public Iterable<Vertex> getVertices() {

    final List<Vertex> result = new LinkedList<Vertex>();

    accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#onVisit(org.bundlemaker.core.analysis.
       * IBundleMakerArtifact)
       */
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        result.add(artifact);
        return true;
      }
    });

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertices(java.lang.String, java.lang.Object)
   */
  @Override
  public Iterable<Vertex> getVertices(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#addEdge(java.lang.Object, com.tinkerpop.blueprints.Vertex,
   * com.tinkerpop.blueprints.Vertex, java.lang.String)
   */
  @Override
  public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
    throw new UnsupportedOperationException("addEdge");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdge(java.lang.Object)
   */
  @Override
  public Edge getEdge(Object id) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeEdge(com.tinkerpop.blueprints.Edge)
   */
  @Override
  public void removeEdge(Edge edge) {
    throw new UnsupportedOperationException("removeEdge");

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges()
   */
  @Override
  public Iterable<Edge> getEdges() {
    return toEdgeIterable(getDependenciesTo());
  }

  private Iterable<Edge> toEdgeIterable(Iterable<IDependency> iterable) {
    return Iterables.transform(iterable, _dependencyToEdgeConverterFunction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges(java.lang.String, java.lang.Object)
   */
  @Override
  public Iterable<Edge> getEdges(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#shutdown()
   */
  @Override
  public void shutdown() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IRootArtifact#getBlueprintGraph()
   */
  @Override
  public Graph getBlueprintGraph() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.spi.AbstractArtifact#getArtifactType()
   */
  @Override
  protected String getArtifactType() {
    return "root";
  }

}
