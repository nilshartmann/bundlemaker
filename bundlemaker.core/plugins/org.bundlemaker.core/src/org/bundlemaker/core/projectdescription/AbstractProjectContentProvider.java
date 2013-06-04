package org.bundlemaker.core.projectdescription;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProvider;
import org.bundlemaker.core.resource.ResourceType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
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
  private String                        _id;

  /** - */
  private IBundleMakerProject           _bundleMakerProject;

  /** - */
  private BundleMakerProjectDescription _projectDescription;

  /** cached list of all file based contents */
  private List<IProjectContentEntry>    _fileBasedContents;

  /** the internal counter */
  private int                           _counter = 0;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractProjectContentProvider}.
   * </p>
   */
  public AbstractProjectContentProvider() {

    //
    _fileBasedContents = new LinkedList<IProjectContentEntry>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected List<IProjectContentEntry> getFileBasedContents() {
    return Collections.unmodifiableList(_fileBasedContents);
  }

  /**
   * <p>
   * </p>
   */
  public void clearFileBasedContents() {
    _fileBasedContents.clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IBundleMakerProject getBundleMakerProject() {
    Assert.isNotNull(_bundleMakerProject, "BundleMaker project has not been set.");
    return _bundleMakerProject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  public final void setId(String id) {
    _id = id;
  }

  /**
   * <b>Internal method</b> This method is not intended to be called from outside bundlemaker.core
   * 
   * @param description
   */
  public void setProjectDescription(BundleMakerProjectDescription description) {
    _projectDescription = description;
    _bundleMakerProject = _projectDescription.getBundleMakerProject();

    init(description);
  }

  /**
   * <p>
   * </p>
   * 
   * @param description
   */
  protected void init(IProjectDescription description) {
    // empty implementation
  }

  /**
   * This method should be called after this provider has been changed.
   */
  public void fireProjectDescriptionChangedEvent() {
    if (_projectDescription != null) {
      _projectDescription.fireProjectDescriptionChangedEvent();
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireProjectDescriptionRecomputedEvent() {
    if (_projectDescription != null) {
      _projectDescription.fireProjectDescriptionRecomputedEvent();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
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
  public final List<IProjectContentEntry> getBundleMakerProjectContent(IProgressMonitor progressMonitor,
      IBundleMakerProject bundleMakerProject) throws CoreException {

    //
    Assert.isNotNull(bundleMakerProject);

    //
    _bundleMakerProject = bundleMakerProject;

    //
    onGetBundleMakerProjectContent(progressMonitor);

    //
    return getFileBasedContents();
  }

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   * @return
   */
  protected abstract void onGetBundleMakerProjectContent(IProgressMonitor progressMonitor)
      throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String toJson() {
    return GsonProjectDescriptionHelper.gson().toJson(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @param gsonString
   * @param type
   * @return
   */
  public static <T extends FileBasedProjectContentProvider> T fromJson(String gsonString,
      Class<T> type) {
    return GsonProjectDescriptionHelper.gson().fromJson(gsonString, type);
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
    _fileBasedContents.add(result);

    //
    return result;
  }
}
