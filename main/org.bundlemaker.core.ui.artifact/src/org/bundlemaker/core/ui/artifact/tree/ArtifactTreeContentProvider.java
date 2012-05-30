package org.bundlemaker.core.ui.artifact.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelModifiedListener;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.artifact.internal.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
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
  private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /** - */
  private boolean               _showRoot;

  /** needed to show the root in the tree viewer, see [BM-165 Show Root-Node in Dependency Tree / XRef View] */
  private VirtualRoot           _virtualRoot;

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

            IBundleMakerArtifact artifact = modularizedSystem.getArtifactModel(artifactModelConfigurationProvider
                .getArtifactModelConfiguration());

            // ModelTransformer.dumpArtifact(artifact);

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
        if (iArtifact.getType().equals(ArtifactType.Package)) {
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
    public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {
      return _rootArtifact.getOrCreateModule(qualifiedModuleName, moduleVersion);
    }

    @Override
    public void addArtifactModelChangedListener(IArtifactModelModifiedListener listener) {
      _rootArtifact.addArtifactModelChangedListener(listener);
    }

    @Override
    public ArtifactType getType() {
      return _rootArtifact.getType();
    }

    @Override
    public void removeArtifactModelChangedListener(IArtifactModelModifiedListener listener) {
      _rootArtifact.removeArtifactModelChangedListener(listener);
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
    public void setProperty(Object key, Object value) {
      _rootArtifact.setProperty(key, value);
    }

    @Override
    public String getProperty(Object key) {
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
    public Collection<IDependency> getDependencies() {
      return _rootArtifact.getDependencies();
    }

    @Override
    public IDependency getDependency(IBundleMakerArtifact artifact) {
      return _rootArtifact.getDependency(artifact);
    }

    @Override
    public Collection<? extends IDependency> getDependencies(Collection<? extends IBundleMakerArtifact> artifacts) {
      return _rootArtifact.getDependencies(artifacts);
    }

    @Override
    public Collection<IBundleMakerArtifact> getLeafs() {
      return _rootArtifact.getLeafs();
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
    public <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz) {
      return _rootArtifact.findChildren(clazz);
    }

    @Override
    public <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz, String filter) {
      return _rootArtifact.findChildren(clazz, filter);
    }

    @Override
    public <T extends IBundleMakerArtifact> T getChildByPath(Class<T> clazz, IPath path) {
      return _rootArtifact.getChildByPath(clazz, path);
    }

    @Override
    public IBundleMakerArtifact getParent() {
      return _rootArtifact.getParent();
    }

    @Override
    public <T extends IBundleMakerArtifact> T getParent(Class<T> type) {
      return _rootArtifact.getParent(type);
    }

    @Override
    public IBundleMakerArtifact getParent(ArtifactType type) {
      return _rootArtifact.getParent(type);
    }

    @Override
    public IRootArtifact getRoot() {
      return _rootArtifact.getRoot();
    }

    @Override
    public Collection<? extends IDependency> getDependencies(IBundleMakerArtifact... artifacts) {
      return _rootArtifact.getDependencies(artifacts);
    }

    @Override
    public boolean hasParent() {
      return _rootArtifact.hasParent();
    }

    @Override
    public IArtifactModelConfiguration getConfiguration() {
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
    public void accept(IArtifactTreeVisitor visitor) {
      _rootArtifact.accept(visitor);
    }

    @Override
    public void accept(IArtifactTreeVisitor... visitors) {
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
    public <T extends IBundleMakerArtifact> T findChild(Class<T> clazz, String filter) {
      return _rootArtifact.findChild(clazz, filter);
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

    @Override
    public List<IBundleMakerArtifact> invalidateDependencyCache() {
      return _rootArtifact.invalidateDependencyCache();
    }

    @Override
    public Map<IBundleMakerArtifact, IDependency> getCachedDependencies() {
      return _rootArtifact.getCachedDependencies();
    }

  }
}
