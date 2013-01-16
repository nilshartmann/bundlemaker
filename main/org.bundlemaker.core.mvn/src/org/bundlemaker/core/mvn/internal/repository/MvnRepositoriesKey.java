package org.bundlemaker.core.mvn.internal.repository;

import java.io.File;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnRepositoriesKey {

  /** - */
  private File   _localRepo;

  /** - */
  private String _remoteRepoUrl;

  /**
   * <p>
   * Creates a new instance of type {@link MvnRepositoriesKey}.
   * </p>
   * 
   * @param localRepo
   * @param remoteRepoUrl
   */
  public MvnRepositoriesKey(File localRepo, String remoteRepoUrl) {
    Assert.isNotNull(localRepo);
    Assert.isNotNull(remoteRepoUrl);

    _localRepo = localRepo;
    _remoteRepoUrl = remoteRepoUrl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_localRepo == null) ? 0 : _localRepo.hashCode());
    result = prime * result + ((_remoteRepoUrl == null) ? 0 : _remoteRepoUrl.hashCode());
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
    MvnRepositoriesKey other = (MvnRepositoriesKey) obj;
    if (_localRepo == null) {
      if (other._localRepo != null)
        return false;
    } else if (!_localRepo.equals(other._localRepo))
      return false;
    if (_remoteRepoUrl == null) {
      if (other._remoteRepoUrl != null)
        return false;
    } else if (!_remoteRepoUrl.equals(other._remoteRepoUrl))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "MvnRepositoryConfigKey [_localRepo=" + _localRepo + ", _remoteRepoUrl=" + _remoteRepoUrl + "]";
  }
}
