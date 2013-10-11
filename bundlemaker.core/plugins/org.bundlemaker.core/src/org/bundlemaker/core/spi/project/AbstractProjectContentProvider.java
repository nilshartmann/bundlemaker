package org.bundlemaker.core.spi.project;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.internal.parser.IParserService;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProblem;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * Superclass for all implementations of IProjectContentProvider
 * </p>
 * 
 * <p>
 * <b>Implementation note:</b> Implementators should call {@link #fireProjectDescriptionChangedEvent()} after the
 * provider changed.
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractProjectContentProvider implements IProjectContentProvider {

  /** - */
  @Expose
  @SerializedName("id")
  private String                                     _id;

  /** the associated bundlemaker project */
  private IProjectDescriptionAwareBundleMakerProject _bundleMakerProject;

  /** cached list of all file based contents */
  private List<IProjectContentEntry>                 _projectContentEntries;

  /** the internal counter */
  private int                                        _counter = 0;

  /** - */
  private boolean                                    _isInitialized;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractProjectContentProvider}.
   * </p>
   */
  public AbstractProjectContentProvider() {

    //
    _projectContentEntries = new LinkedList<IProjectContentEntry>();
  }

  /**
   * {@inheritDoc}
   */
  public void setProject(IProjectDescriptionAwareBundleMakerProject project) {

    //
    Assert.isNotNull(project);

    //
    if (_bundleMakerProject != null) {
      throw new RuntimeException("Project already has been set!");
    }

    //
    _bundleMakerProject = project;

    //
    prepare();
  }

  /**
   * {@inheritDoc}
   */
  public final void setId(String id) {
    _id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectDescriptionAwareBundleMakerProject getBundleMakerProject() {
    checkProjectSet();
    return _bundleMakerProject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    checkProjectSet();
    return _id;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public List<IProjectContentProblem> getProblems() {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasProblems() {
    return !getProblems().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final List<IProjectContentEntry> getBundleMakerProjectContent() {

    //
    try {
      initializeProjectContent(null);
    } catch (CoreException e) {
      //
    }

    //
    checkProjectSet();
    return Collections.unmodifiableList(_projectContentEntries);
  }

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   * @return
   */
  public final void initializeProjectContent(IProgressMonitor progressMonitor)
      throws CoreException {

    if (!_isInitialized) {

      onInitializeProjectContent(progressMonitor);

      _isInitialized = true;
    }
  }

  /**
   * @param progressMonitor
   */
  protected abstract void onInitializeProjectContent(IProgressMonitor progressMonitor) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String toJson() {
    return GsonProjectDescriptionHelper.gson().toJson(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
    AbstractProjectContentProvider other = (AbstractProjectContentProvider) obj;
    if (_id == null) {
      if (other._id != null)
        return false;
    } else if (!_id.equals(other._id))
      return false;
    return true;
  }

  /**
   * <p>
   * </p>
   */
  protected void fireProjectContentChangedEvent(BundleMakerProjectContentChangedEvent changedEvent) {
    if (_bundleMakerProject != null
        && _bundleMakerProject.getModifiableProjectDescription() instanceof BundleMakerProjectDescription) {
      ((BundleMakerProjectDescription) _bundleMakerProject.getModifiableProjectDescription())
          .fireProjectContentChangedEvent(changedEvent);
    }
  }

  /**
   * This method should be called after this provider has been changed.
   */
  public void fireProjectDescriptionChangedEvent() {
    if (_bundleMakerProject != null && _bundleMakerProject.getModifiableProjectDescription() != null) {
      ((BundleMakerProjectDescription) _bundleMakerProject.getModifiableProjectDescription())
          .fireProjectDescriptionChangedEvent();
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireProjectDescriptionRecomputedEvent() {
    if (_bundleMakerProject != null && _bundleMakerProject.getModifiableProjectDescription() != null) {
      ((BundleMakerProjectDescription) _bundleMakerProject.getModifiableProjectDescription())
          .fireProjectDescriptionRecomputedEvent();
    }
  }

  /**
   * <p>
   * </p>
   */
  protected void prepare() {
    // empty implementation
  }

  /**
   * <p>
   * </p>
   */
  protected void clearFileBasedContents() {

    //
    checkProjectSet();

    _isInitialized = false;
    _projectContentEntries.clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentName
   * @param contentVersion
   * @param binaryPaths
   * @param sourcePaths
   * @param analyzeMode
   * @return
   * @throws CoreException
   */
  protected IProjectContentEntry createFileBasedContent(String contentName, String contentVersion,
      File[] binaryPaths, File[] sourcePaths, AnalyzeMode analyzeMode)
      throws CoreException {

    //
    checkProjectSet();

    // asserts
    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPaths);
    Assert.isNotNull(analyzeMode);

    ProjectContentEntry result = new ProjectContentEntry(this);

    result.setAnalyzeMode(analyzeMode);

    result.setId(getId() + _counter++);
    result.setName(contentName);
    result.setVersion(contentVersion);

    for (File binaryPath : binaryPaths) {
      result.addRootPath(new VariablePath(binaryPath.getAbsolutePath()), ResourceType.BINARY);
    }

    if (sourcePaths != null) {
      for (File sourcePath : sourcePaths) {
        result.addRootPath(new VariablePath(sourcePath.getAbsolutePath()), ResourceType.SOURCE);
      }
    }

    // initialize the result
    result.initialize(getBundleMakerProject().getProjectDescription());

    //
    _projectContentEntries.add(result);

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected boolean isProjectSet() {
    return _bundleMakerProject != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentEntry
   * @param rootPath
   * @param filePath
   * @param type
   */
  protected void handleResourceAdded(IProjectContentEntry contentEntry, IPath rootPath, IPath filePath,
      ResourceType type) {

    //
    IProjectContentResource contentResource = null;

    //
    if (type.equals(ResourceType.BINARY)) {
      contentResource = ((ProjectContentEntry) contentEntry).createNewProjectContentResource(rootPath.toString(),
          filePath.toString(),
          ResourceType.BINARY);
    } else {
      contentResource = ((ProjectContentEntry) contentEntry).createNewProjectContentResource(rootPath.toString(),
          filePath.toString(),
          ResourceType.SOURCE);
    }

    //
    try {
      parse(contentEntry, contentResource.adaptAs(IParsableResource.class));
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //
    fireProjectContentChangedEvent(new BundleMakerProjectContentChangedEvent(getBundleMakerProject(),
        BundleMakerProjectContentChangedEvent.Type.ADDED, contentResource));
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentEntry
   * @param resource
   * @param type
   */
  protected void handleResourceRemoved(IProjectContentEntry contentEntry, IProjectContentResource resource,
      ResourceType type) {

    //
    ((ProjectContentEntry) contentEntry).removeProjectContentResource(resource,
        type);

    //
    fireProjectContentChangedEvent(new BundleMakerProjectContentChangedEvent(getBundleMakerProject(),
        BundleMakerProjectContentChangedEvent.Type.REMOVED, resource));
  }

  protected void handleResourceModified(IProjectContentEntry contentEntry, IProjectContentResource contentResource) {

    //
    try {
      parse(contentEntry, contentResource.adaptAs(IParsableResource.class));
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //
    fireProjectContentChangedEvent(new BundleMakerProjectContentChangedEvent(getBundleMakerProject(),
        BundleMakerProjectContentChangedEvent.Type.MODIFIED, contentResource));
  }

  /**
   * @param contentEntry
   * @param contentResource
   * @throws CoreException
   */
  private void parse(IProjectContentEntry contentEntry, IParsableResource contentResource) throws CoreException {
    IParserService.Factory.getParserService().parseResource(contentEntry, contentResource, true);
  }

  /**
   * <p>
   * </p>
   */
  private void checkProjectSet() {
    Assert.isNotNull(_bundleMakerProject, "BundleMaker project has not been set.");
  }
}
