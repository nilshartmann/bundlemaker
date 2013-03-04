package org.bundlemaker.core.internal.projectdescription;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectContentEntry;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Abstract base class for all {@link IProjectContentEntry} implementations.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContent implements IModifiableProjectContentEntry {

  /** the empty resource standin set */
  private static final Set<IResourceStandin> EMPTY_RESOURCE_STANDIN_SET = Collections
                                                                            .unmodifiableSet(new HashSet<IResourceStandin>());

  /** - */
  private static final Set<VariablePath>     EMPTY_ROOTPATH_SET         = Collections
                                                                            .unmodifiableSet(new HashSet<VariablePath>());

  /** indicates that the content has been initialized */
  private boolean                            _isInitialized;

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

  /** the set of binary resource standins */
  private Set<IResourceStandin>              _binaryResourceStandins;

  /** the set of source resource standins */
  private Set<IResourceStandin>              _sourceResourceStandins;

  /** the project description */
  private IProjectDescription                _projectDescription;

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
   * Creates a new instance of type {@link ProjectContent}.
   * </p>
   */
  public ProjectContent(IProjectContentProvider provider) {
    this(provider, false);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ProjectContent}.
   * </p>
   */
  public ProjectContent(IProjectContentProvider provider, boolean notifyChanges) {
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
  public final Set<? extends IResource> getBinaryResources() {
    return Collections.unmodifiableSet(getBinaryResourceStandins());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Set<? extends IResource> getSourceResources() {
    return Collections.unmodifiableSet(getSourceResourceStandins());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends IResource> getResources(ProjectContentType type) {
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
  public final Set<IResourceStandin> getBinaryResourceStandins() {
    return _binaryResourceStandins != null ? _binaryResourceStandins : EMPTY_RESOURCE_STANDIN_SET;
  }

  /**
   * <p>
   * Returns the set of all contained source {@link IResourceStandin}.
   * </p>
   * 
   * @return the set of all contained source {@link IResourceStandin}.
   */
  public final Set<IResourceStandin> getSourceResourceStandins() {
    return _sourceResourceStandins != null ? _sourceResourceStandins : EMPTY_RESOURCE_STANDIN_SET;
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

    // return if content already is initialized
    if (isInitialized()) {
      return;
    }

    // the project description
    _projectDescription = projectDescription;

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
      ProjectContentType type) {

    Assert.isNotNull(contentId);
    Assert.isNotNull(root);
    Assert.isNotNull(path);
    Assert.isNotNull(type);

    //
    ResourceStandin resourceStandin = new ResourceStandin(contentId, root, path);

    // add the resource
    switch (type) {
    case BINARY: {
      ((BundleMakerProjectDescription) _projectDescription).addBinaryResource(resourceStandin);
      binaryResourceStandins().add(resourceStandin);
      break;
    }
    case SOURCE: {
      ((BundleMakerProjectDescription) _projectDescription).addSourceResource(resourceStandin);
      sourceResourceStandins().add(resourceStandin);
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
  private Set<IResourceStandin> binaryResourceStandins() {

    //
    if (_binaryResourceStandins == null) {
      _binaryResourceStandins = new HashSet<IResourceStandin>();
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
  private Set<IResourceStandin> sourceResourceStandins() {

    //
    if (_sourceResourceStandins == null) {
      _sourceResourceStandins = new HashSet<IResourceStandin>();
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

    if (isAnalyze()) {

      // add the binary resources
      for (VariablePath root : _binaryPaths) {
        for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {
          // create the resource standin
          createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ProjectContentType.BINARY);
        }
      }

      // add the source resources
      if (getAnalyzeMode().equals(AnalyzeMode.BINARIES_AND_SOURCES)) {
        if (_sourcePaths != null) {
          for (VariablePath root : _sourcePaths) {
            for (String filePath : FileUtils.getAllChildren(root.getAsFile())) {
              // create the resource standin
              createNewResourceStandin(getId(), root.getResolvedPath().toString(), filePath, ProjectContentType.SOURCE);
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
  public void addRootPath(VariablePath rootPath, ProjectContentType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ProjectContentType.BINARY)) {
      _binaryPaths.add(rootPath);
    } else if (type.equals(ProjectContentType.SOURCE)) {
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
  public void removeRootPath(VariablePath rootPath, ProjectContentType type) {
    Assert.isNotNull(rootPath);
    Assert.isNotNull(type);

    //
    if (type.equals(ProjectContentType.BINARY)) {
      _binaryPaths.remove(rootPath);
    } else if (type.equals(ProjectContentType.SOURCE)) {
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