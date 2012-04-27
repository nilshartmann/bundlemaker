package org.bundlemaker.core.projectdescription;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Abstract base class for all {@link IProjectContentEntry} implementations.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractContent implements IProjectContentEntry {

  /** the empty resource standin set */
  private static final Set<IResourceStandin> EMPTY_RESOURCE_STANDIN_SET = Collections
                                                                            .unmodifiableSet(new HashSet<IResourceStandin>());

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

  /** the set of binary resource standins */
  private Set<IResourceStandin>              _binaryResourceStandins;

  /** the set of source resource standins */
  private Set<IResourceStandin>              _sourceResourceStandins;

  /** the project description */
  private IProjectDescription                _projectDescription;

  /** the bundle maker project content provider */
  private IProjectContentProvider            _provider;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractContent}.
   * </p>
   */
  public AbstractContent(IProjectContentProvider provider) {
    Assert.isNotNull(provider);

    // set the provider
    _provider = provider;
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
  public Set<? extends IResource> getResources(ContentType type) {
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
   * Call back method.
   * </p>
   * 
   * @param projectDescription
   */
  protected abstract void onInitialize(IProjectDescription projectDescription) throws CoreException;

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
  public final IResourceStandin createNewResourceStandin(String contentId, String root, String path, ContentType type) {

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

    if (_provider instanceof AbstractContentProvider) {
      ((AbstractContentProvider) _provider).fireProjectDescriptionChangedEvent();
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
}