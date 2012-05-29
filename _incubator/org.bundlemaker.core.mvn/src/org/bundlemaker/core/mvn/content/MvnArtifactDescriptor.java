package org.bundlemaker.core.mvn.content;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnArtifactDescriptor {

  /** - */
  private String _groupId;

  /** - */
  private String _artifactId;

  /** - */
  private String _version;

  /**
   * <p>
   * Creates a new instance of type {@link MvnArtifactDescriptor}.
   * </p>
   * 
   * @param groupId
   * @param artifactId
   * @param version
   */
  public MvnArtifactDescriptor(String groupId, String artifactId, String version) {

    Assert.isNotNull(groupId);
    Assert.isNotNull(artifactId);
    Assert.isNotNull(version);

    _groupId = groupId;
    _artifactId = artifactId;
    _version = version;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getGroupId() {
    return _groupId;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getArtifactId() {
    return _artifactId;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getVersion() {
    return _version;
  }
}
