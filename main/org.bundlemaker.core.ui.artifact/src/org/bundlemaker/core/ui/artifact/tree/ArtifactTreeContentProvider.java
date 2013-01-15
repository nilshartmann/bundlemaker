package org.bundlemaker.core.ui.artifact.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.spi.IReferencedArtifact;
import org.bundlemaker.core.analysis.spi.IReferencingArtifact;
import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.internal.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * </p>
 * 
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeContentProvider implements ITreeContentProvider, IVirtualRootContentProvider {

  /** EMPTY_OBJECT_ARRAY */
  private static final Object[]                                 EMPTY_OBJECT_ARRAY                    = new Object[0];

  /**
   * Listener used to refresh navigator on artifact model changes
   */
  private final static RefreshArtifactTreeModelModifiedListener ARTIFACT_TREE_MODEL_MODIFIED_LISTENER = new RefreshArtifactTreeModelModifiedListener();

  /** - */
  private boolean                                               _showRoot;

  /** needed to show the root in the tree viewer, see [BM-165 Show Root-Node in Dependency Tree / XRef View] */
  private VirtualRoot                                           _virtualRoot;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeContentProvider}.
   * </p>
   */
  public ArtifactTreeContentProvider() {
    this(false);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeContentProvider}.
   * </p>
   * 
   * @param _showRoot
   */
  public ArtifactTreeContentProvider(boolean _showRoot) {
    this._showRoot = _showRoot;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getVirtualRoot() {
    return _virtualRoot;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getChildren(Object parent) {

    if (parent instanceof IProject) {

      IProject project = (IProject) parent;

      try {

        //
        if (project.hasNature(BundleMakerCore.NATURE_ID)) {

          //
          IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project, null);

          //
          Collection<IModularizedSystem> modularizedSystems = bundleMakerProject.getModularizedSystemWorkingCopies();

          List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();
          for (IModularizedSystem modularizedSystem : modularizedSystems) {

            //
            // modularizedSystem.applyTransformations();
            IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
                .getArtifactModelConfigurationProvider();

            IRootArtifact artifact = modularizedSystem.getAnalysisModel(artifactModelConfigurationProvider
                .getArtifactModelConfiguration());

            // // TODO!
            artifact.addAnalysisModelModifiedListener(ARTIFACT_TREE_MODEL_MODIFIED_LISTENER);

            result.add(artifact);
          }

          //
          return result.toArray();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }

      //
      return EMPTY_OBJECT_ARRAY;
    } else if (parent instanceof IBundleMakerArtifact) {

      IBundleMakerArtifact parentArtifact = (IBundleMakerArtifact) parent;

      List<IBundleMakerArtifact> artifacts = new ArrayList<IBundleMakerArtifact>();

      for (IBundleMakerArtifact iArtifact : parentArtifact.getChildren()) {
        if (iArtifact.isInstanceOf(IPackageArtifact.class)) {
          if (iArtifact.containsTypesOrResources()) {
            artifacts.add(iArtifact);
          } else {
            //
          }
        } else {
          artifacts.add(iArtifact);
        }
      }

      return artifacts.toArray();
    }
    return EMPTY_OBJECT_ARRAY;
  }

  @Override
  public Object getParent(Object input) {

    if (input instanceof IBundleMakerArtifact) {

      if (_showRoot && ((IBundleMakerArtifact) input).getParent() instanceof IRootArtifact) {
        return _virtualRoot;
      } else {
        return ((IBundleMakerArtifact) input).getParent();
      }
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasChildren(Object parent) {
    return getChildren(parent).length > 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getElements(Object parent) {

    if (_showRoot && parent instanceof IRootArtifact) {
      if (_virtualRoot == null) {
        _virtualRoot = new VirtualRoot((IRootArtifact) parent);
      }
      return new Object[] { _virtualRoot };
    } else {
      return getChildren(parent);
    }
  }

  @Override
  public void dispose() {
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    if (newInput != null && newInput instanceof IRootArtifact) {
      _virtualRoot = new VirtualRoot((IRootArtifact) newInput);
    } else {
      _virtualRoot = null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  // TODO: Implement as Proxy
  private class VirtualRoot implements IRootArtifact {

    /** the root artifact */
    private IRootArtifact _rootArtifact;

    /**
     * <p>
     * Creates a new instance of type {@link VirtualRoot}.
     * </p>
     * 
     * @param rootArtifact
     */
    public VirtualRoot(IRootArtifact rootArtifact) {
      Assert.isNotNull(rootArtifact);

      _rootArtifact = rootArtifact;
    }

    @Override
    public void resetTransformations() {
      _rootArtifact.resetTransformations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areCachesInitialized() {
      return _rootArtifact.areCachesInitialized();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeCaches(IProgressMonitor progressMonitor) {
      _rootArtifact.initializeCaches(progressMonitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableModelModifiedNotification(boolean isEnabled) {
      _rootArtifact.disableModelModifiedNotification(isEnabled);
    }

    @Override
    public boolean isModelModifiedNotificationDisabled() {
      return _rootArtifact.isModelModifiedNotificationDisabled();
    }

    @Override
    public List<IReferencingArtifact> getContainedReferencingArtifacts() {
      return _rootArtifact.getContainedReferencingArtifacts();
    }

    @Override
    public List<IReferencedArtifact> getContainedReferencedArtifacts() {
      return _rootArtifact.getContainedReferencedArtifacts();
    }

    @Override
    public boolean isInstanceOf(Class<? extends IBundleMakerArtifact> clazz) {
      return _rootArtifact.isInstanceOf(clazz);
    }

    @Override
    public <T extends IBundleMakerArtifact> T castTo(Class<T> clazz) {
      return _rootArtifact.castTo(clazz);
    }

    @Override
    public IGroupArtifact getGroupArtifact(IGroup group) {
      return _rootArtifact.getGroupArtifact(group);
    }

    @Override
    public boolean isAncestorOf(IBundleMakerArtifact artifact) {
      return _rootArtifact.isAncestorOf(artifact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRemove(IBundleMakerArtifact artifact) {
      return _rootArtifact.canRemove(artifact);
    }

    @Override
    public void addArtifacts(List<? extends IBundleMakerArtifact> artifact) {
      _rootArtifact.addArtifacts(artifact);
    }

    @Override
    public void addArtifacts(IArtifactSelector artifactSelector) {
      _rootArtifact.addArtifacts(artifactSelector);
    }

    @Override
    public void removeArtifacts(List<? extends IBundleMakerArtifact> artifact) {
      _rootArtifact.removeArtifacts(artifact);
    }

    @Override
    public void removeArtifacts(IArtifactSelector artifactSelector) {
      _rootArtifact.removeArtifacts(artifactSelector);
    }

    @Override
    public Collection<IDependency> getDependenciesFrom() {
      return _rootArtifact.getDependenciesFrom();
    }

    @Override
    public IDependency getDependencyFrom(IBundleMakerArtifact artifact) {
      return _rootArtifact.getDependencyFrom(artifact);
    }

    @Override
    public Collection<IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts) {
      return _rootArtifact.getDependenciesFrom(artifacts);
    }

    @Override
    public Collection<IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {
      return _rootArtifact.getDependenciesFrom(artifacts);
    }

    @Override
    public IDependency getDependencyTo(IBundleMakerArtifact artifact) {
      return _rootArtifact.getDependencyTo(artifact);
    }

    @Override
    public Collection<? extends IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {
      return _rootArtifact.getDependenciesTo(artifacts);
    }

    @Override
    public Collection<IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {
      return _rootArtifact.getDependenciesTo(artifacts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModuleArtifact getModuleArtifact(IModule module) {
      return _rootArtifact.getModuleArtifact(module);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IResourceArtifact getResourceArtifact(IResource resource) {
      return _rootArtifact.getResourceArtifact(resource);
    }

    @Override
    public <T extends IBundleMakerArtifact> Collection<T> getChildren(Class<T> clazz) {
      return _rootArtifact.getChildren(clazz);
    }

    @Override
    public IGroupArtifact getOrCreateGroup(IPath path) {
      return _rootArtifact.getOrCreateGroup(path);
    }

    @Override
    public IGroupArtifact getOrCreateGroup(String path) {
      return _rootArtifact.getOrCreateGroup(path);
    }

    @Override
    public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {
      return _rootArtifact.getOrCreateModule(qualifiedModuleName, moduleVersion);
    }

    @Override
    public void addAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener) {
      _rootArtifact.addAnalysisModelModifiedListener(listener);
    }

    @Override
    public void removeAnalysisModelModifiedListener(IAnalysisModelModifiedListener listener) {
      _rootArtifact.removeAnalysisModelModifiedListener(listener);
    }

    @Override
    public String getName() {
      return _rootArtifact.getName();
    }

    @Override
    public String getQualifiedName() {
      return _rootArtifact.getQualifiedName();
    }

    @Override
    public void setProperty(String key, Object value) {
      _rootArtifact.setProperty(key, value);
    }

    @Override
    public Object getProperty(String key) {
      return _rootArtifact.getProperty(key);
    }

    @Override
    public <T> T getProperty(Object key, Class<T> t) {
      return _rootArtifact.getProperty(key, t);
    }

    // @Override
    // public boolean hasChild(String path) {
    // return _rootArtifact.hasChild(path);
    // }

    @Override
    public void addArtifact(IBundleMakerArtifact artifact) {
      _rootArtifact.addArtifact(artifact);
    }

    @Override
    public boolean removeArtifact(IBundleMakerArtifact artifact) {
      return _rootArtifact.removeArtifact(artifact);
    }

    @Override
    public Collection<IDependency> getDependenciesTo() {
      return _rootArtifact.getDependenciesTo();
    }

    @Override
    public String getUniquePathIdentifier() {
      return _rootArtifact.getUniquePathIdentifier();
    }

    @Override
    public IPath getFullPath() {
      return _rootArtifact.getFullPath();
    }

    @Override
    public IBundleMakerArtifact getChild(String path) {
      return _rootArtifact.getChild(path);
    }

    @Override
    public Collection<IBundleMakerArtifact> getChildren() {
      return _rootArtifact.getChildren();
    }

    @Override
    public IBundleMakerArtifact getParent() {
      return _rootArtifact.getParent();
    }

    @Override
    public Collection<? extends IBundleMakerArtifact> getParents() {
      return _rootArtifact.getParents();
    }

    @Override
    public <T extends IBundleMakerArtifact> T getParent(Class<T> type) {
      return _rootArtifact.getParent(type);
    }

    @Override
    public IRootArtifact getRoot() {
      return _rootArtifact.getRoot();
    }

    @Override
    public boolean hasParent() {
      return _rootArtifact.hasParent();
    }

    @Override
    public IAnalysisModelConfiguration getConfiguration() {
      return _rootArtifact.getConfiguration();
    }

    @Override
    public IModularizedSystem getModularizedSystem() {
      return _rootArtifact.getModularizedSystem();
    }

    @Override
    public boolean isVirtual() {
      return _rootArtifact.isVirtual();
    }

    @Override
    public boolean isMovable() {
      return _rootArtifact.isMovable();
    }

    @Override
    public boolean canAdd(IBundleMakerArtifact artifact) {
      return _rootArtifact.canAdd(artifact);
    }

    @Override
    public void accept(IAnalysisModelVisitor visitor) {
      _rootArtifact.accept(visitor);
    }

    @Override
    public void accept(IAnalysisModelVisitor... visitors) {
      _rootArtifact.accept(visitors);
    }

    @Override
    public int compareTo(IBundleMakerArtifact arg0) {
      return _rootArtifact.compareTo(arg0);
    }

    @Override
    public boolean contains(IBundleMakerArtifact artifact) {
      return _rootArtifact.contains(artifact);
    }

    @Override
    public boolean containsTypesOrResources() {
      return _rootArtifact.containsTypesOrResources();
    }

    @Override
    public boolean containsTypes() {
      return _rootArtifact.containsTypes();
    }

    @Override
    public boolean containsResources() {
      return _rootArtifact.containsResources();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.analysis.IBundleMakerArtifact#getPropertyKeys()
     */
    @Override
    public Set<String> getPropertyKeys() {
      return _rootArtifact.getPropertyKeys();
    }
  }

  static class RefreshArtifactTreeModelModifiedListener implements IAnalysisModelModifiedListener {
    @Override
    public void analysisModelModified() {
      //
      CommonNavigatorUtils.update(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    }
  }

}
