package org.bundlemaker.core.projectdescription.file;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContentProvider;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.eclipse.core.runtime.CoreException;

public class FileBasedContentProvider implements IBundleMakerProjectContentProvider {

  /** - */
  private FileBasedContent _fileBasedContent;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedContentProvider}.
   * </p>
   */
  public FileBasedContentProvider() {
    //
    _fileBasedContent = new FileBasedContent();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public FileBasedContent getFileBasedContent() {
    return _fileBasedContent;
  }

  @Override
  public String getId() {
    return _fileBasedContent.getId();
  }

  @Override
  public List<IBundleMakerProjectContent> getBundleMakerProjectContent(IBundleMakerProject bundleMakerProject)
      throws CoreException {
    _fileBasedContent.initialize(bundleMakerProject.getProjectDescription());
    return Arrays.asList(new IBundleMakerProjectContent[] { _fileBasedContent });
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
   * @param analyze
   */
  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    _fileBasedContent.setAnalyzeMode(analyzeMode);
  }

  /**
   * @return
   */
  public Set<IRootPath> getModifiableBinaryPaths() {
    return _fileBasedContent.getModifiableBinaryPaths();
  }

  /**
   * @return
   */
  public Set<IRootPath> getModifiableSourcePaths() {
    return _fileBasedContent.getModifiableSourcePaths();
  }
}
