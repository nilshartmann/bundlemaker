package org.bundlemaker.core.projectdescription.file;

import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectContentEntry;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedProjectContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  /** the file based content */
  @Expose
  @SerializedName("content")
  private ProjectContentEntry _fileBasedContent;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedProjectContentProvider}.
   * </p>
   */
  public FileBasedProjectContentProvider() {

    // create a new instance of type FileBasedContent
    _fileBasedContent = new ProjectContentEntry(this, true);
  }

  /**
   * <p>
   * Returns the contained {@link IModifiableProjectContentEntry}.
   * </p>
   * 
   * @return the contained {@link IModifiableProjectContentEntry}.
   */
  public IModifiableProjectContentEntry getFileBasedContent() {
    return _fileBasedContent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(
      IProgressMonitor progressMonitor,
      IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    Assert.isNotNull(bundleMakerProject);

    // initialize the file based content
    _fileBasedContent.setId(getId());
    _fileBasedContent.initialize(bundleMakerProject.getProjectDescription());

    // return the file based content
    return Arrays.asList(new IProjectContentEntry[] { _fileBasedContent });
  }

  /**
   * <p>
   * Set the name of this IFileBasedContent to the given value
   * </p>
   * 
   * @param name
   */
  public void setName(String name) {
    _fileBasedContent.setName(name);
  }

  /**
   * <p>
   * Set the Version of this IFileBasedContent to the given value
   * </p>
   * 
   * @param version
   */
  public void setVersion(String version) {
    _fileBasedContent.setVersion(version);
  }

  /**
   * <p>
   * </p>
   * 
   * @param binaryRootPaths
   */
  public void setBinaryPaths(String[] binaryRootPaths) {
    _fileBasedContent.setBinaryPaths(binaryRootPaths);
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceRootPaths
   */
  public void setSourcePaths(String[] sourceRootPaths) {
    _fileBasedContent.setSourcePaths(sourceRootPaths);
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootPath
   * @param type
   */
  public void addRootPath(VariablePath rootPath, ProjectContentType type) {
    _fileBasedContent.addRootPath(rootPath, type);
  }

  /**
   * <p>
   * </p>
   * 
   * @param analyze
   */
  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    _fileBasedContent.setAnalyzeMode(analyzeMode);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((_fileBasedContent == null) ? 0 : _fileBasedContent.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    FileBasedProjectContentProvider other = (FileBasedProjectContentProvider) obj;
    if (_fileBasedContent == null) {
      if (other._fileBasedContent != null)
        return false;
    } else if (!_fileBasedContent.equals(other._fileBasedContent))
      return false;
    return true;
  }
}
