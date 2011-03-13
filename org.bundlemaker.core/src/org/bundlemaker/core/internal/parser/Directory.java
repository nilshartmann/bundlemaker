package org.bundlemaker.core.internal.parser;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Directory implements IDirectory {

  /** - */
  private IFileBasedContent        _fileBasedContent;

  /** the package path (e.g. 'de/example/xy') */
  private IPath                    _path;

  /** the binary content */
  private List<IDirectoryFragment> _binaryDirectoryFragments;

  /** the source content */
  private List<IDirectoryFragment> _sourceDirectoryFragments;

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param path
   * @param binaryContent
   * @param sourceContent
   */
  public Directory(IFileBasedContent projectContent, IPath path, IDirectoryFragment binaryContent,
      IDirectoryFragment sourceContent) {
    Assert.isNotNull(projectContent);
    Assert.isNotNull(path);

    //
    _fileBasedContent = projectContent;

    // the package path
    _path = path;

    // create the content lists
    _binaryDirectoryFragments = new ArrayList<IDirectoryFragment>();
    _sourceDirectoryFragments = new ArrayList<IDirectoryFragment>();

    if (binaryContent != null) {
      _binaryDirectoryFragments.add(binaryContent);
    }

    if (sourceContent != null) {
      _sourceDirectoryFragments.add(sourceContent);
    }
  }

  @Override
  public IFileBasedContent getFileBasedContent() {
    return _fileBasedContent;
  }

  /**
   * {@inheritDoc}
   */
  public IPath getDirectoryName() {
    return _path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IDirectoryFragment> getBinaryDirectoryFragments() {
    return _binaryDirectoryFragments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IDirectoryFragment> getSourceDirectoryFragments() {
    return _sourceDirectoryFragments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSourceContent() {
    return !_sourceDirectoryFragments.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getBinaryContentCount() {

    int result = 0;

    for (IDirectoryFragment directoryContent : _binaryDirectoryFragments) {
      result = result + directoryContent.getResourceCount();
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSourceContentCount() {

    int result = 0;

    for (IDirectoryFragment directoryContent : _sourceDirectoryFragments) {
      result = result + directoryContent.getResourceCount();
    }

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param binaryContent
   */
  void addBinaryContent(IDirectoryFragment binaryContent) {
    Assert.isNotNull(binaryContent);

    _binaryDirectoryFragments.add(binaryContent);

    ((AbstractDirectoryFragment) binaryContent).setDirectory(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceContent
   */
  void addSourceContent(IDirectoryFragment sourceContent) {
    Assert.isNotNull(sourceContent);

    _sourceDirectoryFragments.add(sourceContent);

    ((AbstractDirectoryFragment) sourceContent).setDirectory(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isValid() {
    return !_binaryDirectoryFragments.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_path == null) ? 0 : _path.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Directory other = (Directory) obj;
    if (_path == null) {
      if (other._path != null)
        return false;
    } else if (!_path.equals(other._path))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Package [_packageName=" + _path + ", _binaryContent=" + _binaryDirectoryFragments + ", _sourceContent="
        + _sourceDirectoryFragments + "]";
  }
}