package org.bundlemaker.core.internal.projectdescription;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.common.utils.FileUtils;
import org.bundlemaker.core.internal.api.project.IInternalProjectDescription;
import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescription;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Abstract base class for all {@link IProjectContentEntry} implementations.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentEntry implements IProjectContentEntry {

  /** the empty resource standin set */
  private static final Set<IResourceStandin> EMPTY_RESOURCE_STANDIN_SET = Collections
                                                                            .unmodifiableSet(new HashSet<IResourceStandin>());

  /** - */
  private static final Set<VariablePath>     EMPTY_ROOTPATH_SET         = Collections
                                                                            .unmodifiableSet(new HashSet<VariablePath>());

  /** the internal identifier of this content entry */
  private String                             _id;

  /** the name of this entry */
  private String                             _name;

  /** the version of this entry */
  private String                             _version;

  /** the analyze mode of this entry */
  private AnalyzeMode                        _analyze;

  /** the binary pathes */
  protected Set<VariablePath>                _binaryPaths;

  /** the source pathes */
  private Set<VariablePath>                  _sourcePaths;

  /** indicates that the content has been initialized */
  private boolean                            _isInitialized;

  /** the set of binary resource standins */
  private Map<String, IResourceStandin>      _binaryResourceStandins;

  /** the set of source resource standins */
  private Map<String, IResourceStandin>      _sourceResourceStandins;

  /** the project description */
  private IInternalProjectDescription        _projectDescription;

  /** the bundle maker project content provider */
  private IProjectContentProvider            _provider;

  /** the user attributes */
  private Map<String, Object>                _userAttributes;

  /**
   * indicates wether changes to this instance should be notified.
   * 
   * <p>
   * This flag may be set to 'false' to prevent change notification while initializing this Content instance.
   * 
   */
  private boolean                            _notifyChanges             = true;

  /**
   * <p>
   * Creates a new instance of type {@link ProjectContentEntry}.
   * </p>
   */
  public ProjectContentEntry(IProjectContentProvider provider) {
    this(provider, false);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ProjectContentEntry}.
   * </p>
   */
  public ProjectContentEntry(IProjectContentProvider provider, boolean notifyChanges) {
    Assert.isNotNull(provider);

    // set notify flag
    _notifyChanges = notifyChanges;

    // set the provider
    _provider = provider;

    //
    setAnalyzeMode(AnalyzeMode.BINARIES_ONLY);

    //
    _binaryPaths = new HashSet<VariablePath>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getUserAttributes() {

    //
    if (_userAttributes == null) {
      _userAttributes = new HashMap<String, Object>();
    }

    //
    return _userAttributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectContentProvider getProvider() {
    return _provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAnalyze() {
    return _analyze.isAnalyze();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AnalyzeMode getAnalyzeMode() {
    return _analyze;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<? extends IModuleResource> getBinaryResources() {
    return Collections.unmodifiableCollection(getBinaryResourceStandins());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<? extends IModuleResource> getSourceResources() {
    return Collections.unmodifiableCollection(getSourceResourceStandins());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends IModuleResource> getResources(ResourceType type) {
    switch (type) {
    case BINARY: {
      return getBinaryResources();
    }
    case SOURCE: {
      return getSourceResources();
    }
    default: {
      return null;
    }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleResource getResource(String path, ResourceType type) {
    switch (type) {
    case BINARY: {
      return binaryResourceStandins().get(path);
    }
    case SOURCE: {
      return sourceResourceStandins().get(path);
    }
    default: {
      return null;
    }
    }
  }

  /**
   * <p>
   * Returns <code>true</code> if the content has been initialized yet.
   * </p>
   * 
   * @return <code>true</code> if the content has been initialized yet.
   */
  protected boolean isInitialized() {
    return _isInitialized;
  }

  /**
   * <p>
   * Sets the id of this content entry.
   * </p>
   * 
   * @param id
   *          the id of this content entry.
   */
  public void setId(String id) {
    Assert.isNotNull(id);
    _id = id;
  }

  /**
   * <p>
   * Sets the name of this content entry.
   * </p>
   * 
   * @param name
   *          the name of this content entry.
   */
  public void setName(String name) {
    Assert.isNotNull(name);
    _name = name;

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * Sets the version of this content entry.
   * </p>
   * 
   * @param name
   *          the version of this content entry.
   */
  public void setVersion(String version) {
    Assert.isNotNull(version);
    _version = version;

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * Sets the {@link AnalyzeMode} of this content entry.
   * </p>
   * 
   * @param name
   *          the {@link AnalyzeMode} of this content entry.
   */
  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    Assert.isNotNull(analyzeMode, "Paramter 'analyzeMode' must not be null");

    //
    _analyze = analyzeMode;

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * Returns the set of all contained binary {@link IResourceStandin}.
   * </p>
   * 
   * @return the set of all contained binary {@link IResourceStandin}.
   */
  public final Collection<IResourceStandin> getBinaryResourceStandins() {
    return _binaryResourceStandins != null ? _binaryResourceStandins.values() : EMPTY_RESOURCE_STANDIN_SET;
  }

  /**
   * <p>
   * Returns the set of all contained source {@link IResourceStandin}.
   * </p>
   * 
   * @return the set of all contained source {@link IResourceStandin}.
   */
  public final Collection<IResourceStandin> getSourceResourceStandins() {
    return _sourceResourceStandins != null ? _sourceResourceStandins.values() : EMPTY_RESOURCE_STANDIN_SET;
  }

  /**
   * This method can be used to switch on/off the notification of changes that are made on this Content instance.
   * 
   * @param notifyChanges
   */
  public void setNotifyChanges(boolean notifyChanges) {
    _notifyChanges = notifyChanges;
  }

  /**
   * <p>
   * Initializes this content entry.
   * </p>
   * 
   * @param projectDescription
   *          the project description.
   * @throws CoreException
   */
  public final void initialize(IProjectDescription projectDescription) throws CoreException {

    //
    Assert.isNotNull(projectDescription);
    Assert.isTrue(projectDescription instanceof IInternalProjectDescription,
        String.format("Project description must be instance of %s.", IInternalProjectDescription.class.getName()));

    // return if content already is initialized
    if (isInitialized()) {
      return;
    }

    // the project description
    _projectDescription = (IInternalProjectDescription) projectDescription;

    //
    onInitialize(projectDescription);

    // set initialized
    _isInitialized = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @param type
   * @return
   */
  protected IResourceStandin createNewResourceStandin(String contentId, String root, String path,
      ResourceType type, boolean analyzeReferences) {

    Assert.isNotNull(contentId);
    Assert.isNotNull(root);
    Assert.isNotNull(path);
    Assert.isNotNull(type);

    //
    ResourceStandin resourceStandin = new ResourceStandin(contentId, root, path);
    resourceStandin.setAnalyzeReferences(analyzeReferences);

    // add the resource
    switch (type) {
    case BINARY: {
      _projectDescription.addBinaryResource(resourceStandin);
      binaryResourceStandins().put(path, resourceStandin);
      break;
    }
    case SOURCE: {
      _projectDescription.addSourceResource(resourceStandin);
      sourceResourceStandins().put(path, resourceStandin);
      break;
    }
    default:
      break;
    }

    //
    return resourceStandin;
  }

  protected void fireProjectDescriptionChangeEvent() {

    if (_notifyChanges && _provider instanceof AbstractProjectContentProvider) {
      ((AbstractProjectContentProvider) _provider).fireProjectDescriptionChangedEvent();
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Map<String, IResourceStandin> binaryResourceStandins() {

    //
    if (_binaryResourceStandins == null) {
      _binaryResourceStandins = new HashMap<String, IResourceStandin>();
    }

    //
    return _binaryResourceStandins;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Map<String, IResourceStandin> sourceResourceStandins() {

    //
    if (_sourceResourceStandins == null) {
      _sourceResourceStandins = new HashMap<String, IResourceStandin>();
    }

    //
    return _sourceResourceStandins;
  }

  /**
   * {@inheritDoc}
   */
  public Set<VariablePath> getBinaryRootPaths() {
    return Collections.unmodifiableSet(_binaryPaths);
  }

  /**
   * {@inheritDoc}
   */
  public Set<VariablePath> getSourceRootPaths() {
    return _sourcePaths != null ? _sourcePaths : EMPTY_ROOTPATH_SET;
  }

  /**
   * {@inheritDoc}
   */
  protected void onInitialize(IProjectDescription projectDescription) throws CoreException {

    // add the binary resources
    for (VariablePath root : _binaryPaths) {
      for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {

        //
        if (!binaryResourceStandins().containsKey(filePath)) {

          // create the resource standin
          createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ResourceType.BINARY,
              isAnalyze());

        } else {

          //
          System.out.println(String.format("DUPLICATE RESOURCE IN ENTRY '%s': '%s'", getId(), filePath));
        }
      }
    }

    // add the source resources
    if (getAnalyzeMode().equals(AnalyzeMode.BINARIES_AND_SOURCES)) {
      if (_sourcePaths != null) {
        for (VariablePath root : _sourcePaths) {
          for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {

            //
            if (!sourceResourceStandins().containsKey(filePath)) {

              // create the resource standin
              createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ResourceType.SOURCE,
                  isAnalyze());

            } else {

              //
              System.out.println(String.format("DUPLICATE RESOURCE IN ENTRY '%s': '%s'", getId(), filePath));
            }
          }
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootPath
   * @param type
   */
  public void addRootPath(VariablePath rootPath, ResourceType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ResourceType.BINARY)) {
      _binaryPaths.add(rootPath);
    } else if (type.equals(ResourceType.SOURCE)) {
      sourcePaths().add(rootPath);
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootPath
   * @param type
   */
  public void removeRootPath(VariablePath rootPath, ResourceType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ResourceType.BINARY)) {
      _binaryPaths.remove(rootPath);
    } else if (type.equals(ResourceType.SOURCE)) {
      _sourcePaths.remove(rootPath);
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * <p>
   * </p>
   * 
   * @param binaryRootPaths
   */
  public void setBinaryPaths(String[] binaryRootPaths) {
    Assert.isNotNull(binaryRootPaths);

    _binaryPaths.clear();

    for (String path : binaryRootPaths) {
      _binaryPaths.add(new VariablePath(path));
    }

    fireProjectDescriptionChangeEvent();

  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceRootPaths
   */
  public void setSourcePaths(String[] sourceRootPaths) {
    Assert.isNotNull(sourceRootPaths);

    sourcePaths().clear();

    for (String path : sourceRootPaths) {
      sourcePaths().add(new VariablePath(path));
    }

    fireProjectDescriptionChangeEvent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FileBasedContent [_binaryPaths=");
    builder.append(_binaryPaths);
    builder.append(", _sourcePaths=");
    builder.append(_sourcePaths);
    builder.append(", getId()=");
    builder.append(getId());
    builder.append(", getName()=");
    builder.append(getName());
    builder.append(", getVersion()=");
    builder.append(getVersion());
    builder.append(", isAnalyze()=");
    builder.append(isAnalyze());
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_analyze == null) ? 0 : _analyze.hashCode());
    result = prime * result + ((_binaryPaths == null) ? 0 : _binaryPaths.hashCode());
    result = prime * result + ((_id == null) ? 0 : _id.hashCode());
    result = prime * result + ((_name == null) ? 0 : _name.hashCode());
    result = prime * result + ((_sourcePaths == null) ? 0 : _sourcePaths.hashCode());
    result = prime * result + ((_userAttributes == null) ? 0 : _userAttributes.hashCode());
    result = prime * result + ((_version == null) ? 0 : _version.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProjectContentEntry other = (ProjectContentEntry) obj;
    if (_analyze != other._analyze)
      return false;
    if (_binaryPaths == null) {
      if (other._binaryPaths != null)
        return false;
    } else if (!_binaryPaths.equals(other._binaryPaths))
      return false;
    if (_id == null) {
      if (other._id != null)
        return false;
    } else if (!_id.equals(other._id))
      return false;
    if (_name == null) {
      if (other._name != null)
        return false;
    } else if (!_name.equals(other._name))
      return false;
    if (_sourcePaths == null) {
      if (other._sourcePaths != null)
        return false;
    } else if (!_sourcePaths.equals(other._sourcePaths))
      return false;
    if (_userAttributes == null) {
      if (other._userAttributes != null)
        return false;
    } else if (!_userAttributes.equals(other._userAttributes))
      return false;
    if (_version == null) {
      if (other._version != null)
        return false;
    } else if (!_version.equals(other._version))
      return false;
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<VariablePath> sourcePaths() {

    // lazy initialization
    if (_sourcePaths == null) {
      _sourcePaths = new HashSet<VariablePath>();
    }

    // return the source paths
    return _sourcePaths;
  }
}