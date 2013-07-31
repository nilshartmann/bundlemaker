package org.bundlemaker.core.project.filecontent;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
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

  /** the name of this entry */
  @Expose
  @SerializedName("name")
  private String              _name;

  /** the version of this entry */
  @Expose
  @SerializedName("version")
  private String              _version;

  /** the binary paths */
  @Expose
  @SerializedName("binary-paths")
  protected Set<VariablePath> _binaryPaths;

  /** the source paths */
  @Expose
  @SerializedName("source-paths")
  private Set<VariablePath>   _sourcePaths;

  /** the analyze mode of this entry */
  @Expose
  @SerializedName("analyse")
  private AnalyzeMode         _analyzeMode;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedProjectContentProvider}.
   * </p>
   */
  public FileBasedProjectContentProvider() {
    _binaryPaths = new HashSet<VariablePath>();
    _sourcePaths = new HashSet<VariablePath>();
  }

  /**
   * <p>
   * Set the name of the module that is represented by this {@link FileBasedProjectContentProvider} to the given value.
   * </p>
   * 
   * @param name
   */
  public void setName(String name) {
    _name = name;

    providerChanged();
  }

  /**
   * <p>
   * Returns the name of this {@link FileBasedProjectContentProvider}.
   * </p>
   * 
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * <p>
   * Set the Version of this IFileBasedContent to the given value
   * </p>
   * 
   * @param version
   */
  public void setVersion(String version) {
    _version = version;

    providerChanged();
  }

  /**
   * <p>
   * Returns the version of this {@link FileBasedProjectContentProvider}.
   * </p>
   * 
   * @return the version of this {@link FileBasedProjectContentProvider}.
   */
  public String getVersion() {
    return _version;
  }

  /**
   * <p>
   * Sets the {@link AnalyzeMode} of this {@link FileBasedProjectContentProvider}.
   * </p>
   * 
   * @param analyze
   *          the {@link AnalyzeMode} of this {@link FileBasedProjectContentProvider}.
   */
  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    _analyzeMode = analyzeMode;

    providerChanged();
  }

  /**
   * <p>
   * Returns the {@link AnalyzeMode} of this {@link FileBasedProjectContentProvider}.
   * </p>
   * 
   * @return the {@link AnalyzeMode} of this {@link FileBasedProjectContentProvider}.
   */
  public AnalyzeMode getAnalyzeMode() {
    return _analyzeMode;
  }

  /**
   * <p>
   * Sets the given binary root paths.
   * </p>
   * 
   * @param binaryRootPaths
   */
  public void setBinaryPaths(String[] binaryRootPaths) {

    //
    _binaryPaths.clear();
    for (String path : binaryRootPaths) {
      _binaryPaths.add(new VariablePath(path));
    }

    providerChanged();
  }

  /**
   * <p>
   * Returns the binary paths.
   * </p>
   * 
   * @return the binary paths.
   */
  public Set<VariablePath> getBinaryPaths() {
    return _binaryPaths;
  }

  /**
   * <p>
   * Sets the given source root paths.
   * </p>
   * 
   * @param sourceRootPaths
   */
  public void setSourcePaths(String[] sourceRootPaths) {

    //
    _sourcePaths.clear();
    for (String path : sourceRootPaths) {
      _sourcePaths.add(new VariablePath(path));
    }

    //
    providerChanged();
  }

  /**
   * <p>
   * Returns the source paths.
   * </p>
   * 
   * @return
   */
  public Set<VariablePath> getSourcePaths() {
    return _sourcePaths;
  }

  /**
   * <p>
   * Adds the given path as a root path.
   * </p>
   * 
   * @param path
   * @param type
   */
  public void addRootPath(VariablePath path, ResourceType type) {

    //
    if (ResourceType.BINARY.equals(type)) {
      _binaryPaths.add(path);
    }

    //
    else if (ResourceType.SOURCE.equals(type)) {
      _sourcePaths.add(path);
    }

    providerChanged();
  }

  /**
   * <p>
   * Removes the given root path.
   * </p>
   * 
   * @param path
   * @param contentType
   */
  public void removeRootPath(VariablePath path, ResourceType type) {

    //
    if (ResourceType.BINARY.equals(type)) {
      _binaryPaths.add(path);
    }

    //
    else if (ResourceType.SOURCE.equals(type)) {
      _sourcePaths.add(path);
    }

    // fire provider changed
    providerChanged();
  }

  /**
   * <p>
   * Returns <code>true</code> if the analyze mode is either <code>AnalyzeMode.BINARIES_ONLY</code> or
   * <code>AnalyzeMode.BINARIES_AND_SOURCES</code>.
   * </p>
   * 
   * @return <code>true</code> if the analyze mode is either <code>AnalyzeMode.BINARIES_ONLY</code> or
   *         <code>AnalyzeMode.BINARIES_AND_SOURCES</code>.
   */
  public boolean isAnalyze() {
    return _analyzeMode == AnalyzeMode.BINARIES_ONLY || _analyzeMode == AnalyzeMode.BINARIES_AND_SOURCES;
  }

  /**
   * <p>
   * Returns the (one and only) {@link IProjectContentEntry}.
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public IProjectContentEntry getFileBasedContent() {
    return getBundleMakerProjectContent().get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((_analyzeMode == null) ? 0 : _analyzeMode.hashCode());
    result = prime * result + ((_binaryPaths == null) ? 0 : _binaryPaths.hashCode());
    result = prime * result + ((_name == null) ? 0 : _name.hashCode());
    result = prime * result + ((_sourcePaths == null) ? 0 : _sourcePaths.hashCode());
    result = prime * result + ((_version == null) ? 0 : _version.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    FileBasedProjectContentProvider other = (FileBasedProjectContentProvider) obj;
    if (_analyzeMode != other._analyzeMode)
      return false;
    if (_binaryPaths == null) {
      if (other._binaryPaths != null)
        return false;
    } else if (!_binaryPaths.equals(other._binaryPaths))
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
   */
  protected void providerChanged() {

    //
    if (isProjectSet()) {

      // clear the file based content
      clearFileBasedContents();

      // fire project description changed event
      fireProjectDescriptionChangedEvent();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeProjectContent(IProgressMonitor progressMonitor)
  {

    //
    try {
      createFileBasedContent(_name, _version, convert(_binaryPaths), convert(_sourcePaths),
          _analyzeMode);
    } catch (CoreException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param variablePaths
   * @return
   * @throws CoreException
   */
  private File[] convert(Set<VariablePath> variablePaths) throws CoreException {

    //
    List<File> result = new LinkedList<File>();

    //
    for (VariablePath variablePath : variablePaths) {
      result.add(variablePath.getAsFile());
    }

    //
    return result.toArray(new File[0]);
  }
}
