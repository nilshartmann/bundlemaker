package org.bundlemaker.core.projectdescription.file;

import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.content.file.xml.XmlFileBasedContentType;
import org.bundlemaker.core.content.file.xml.XmlResourceContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedContentProvider extends AbstractContentProvider implements IProjectContentProvider {

  /** the file based content */
  private FileBasedContent _fileBasedContent;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedContentProvider}.
   * </p>
   */
  public FileBasedContentProvider() {

    // create a new instance of type FileBasedContent
    _fileBasedContent = new FileBasedContent(this, true);
  }

  /**
   * <p>
   * Returns the contained {@link FileBasedContent}.
   * </p>
   * 
   * @return the contained {@link FileBasedContent}.
   */
  public FileBasedContent getFileBasedContent() {
    return _fileBasedContent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    Assert.isNotNull(bundleMakerProject);

    // initialize the file based content
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

  public void addRootPath(VariablePath rootPath, ContentType type) {
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

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getConfiguration() {

    // create the configuration object
    XmlFileBasedContentType contentType = new XmlFileBasedContentType();

    // add the content
    contentType.setName(_fileBasedContent.getName());
    contentType.setVersion(_fileBasedContent.getVersion());
    contentType.setAnalyzeMode(_fileBasedContent.getAnalyzeMode().toString());
    for (VariablePath path : _fileBasedContent.getBinaryRootPaths()) {
      contentType.getBinaryPathNames().add(path.getUnresolvedPath().toString());
    }

    XmlResourceContentType xmlResourceContent = new XmlResourceContentType();
    contentType.setResourceContent(xmlResourceContent);
    for (VariablePath path : _fileBasedContent.getSourceRootPaths()) {
      xmlResourceContent.getSourcePathNames().add(path.getUnresolvedPath().toString());
    }

    // return the result
    return contentType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(Object configuration) {

    //
    Assert.isNotNull(configuration);
    Assert.isTrue(configuration instanceof XmlFileBasedContentType);

    // cast down
    XmlFileBasedContentType config = (XmlFileBasedContentType) configuration;

    _fileBasedContent.setId(getId());
    _fileBasedContent.setName(config.getName());
    _fileBasedContent.setVersion(config.getVersion());
    _fileBasedContent.setAnalyzeMode(AnalyzeMode.valueOf(config.getAnalyzeMode()));

    // set the binary path names
    for (String path : config.getBinaryPathNames()) {
      _fileBasedContent.addRootPath(new VariablePath(path), ContentType.BINARY);
    }

    // set the source path name
    if (config.getResourceContent() != null) {
      for (String path : config.getResourceContent().getSourcePathNames()) {
        _fileBasedContent.addRootPath(new VariablePath(path), ContentType.SOURCE);
      }
    }
  }
}
