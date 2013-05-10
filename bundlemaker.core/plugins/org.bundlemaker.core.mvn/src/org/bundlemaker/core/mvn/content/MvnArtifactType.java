package org.bundlemaker.core.mvn.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MvnArtifactType {

  @Expose
  @SerializedName("groupId")
  protected String groupId;

  @Expose
  @SerializedName("artifactId")
  protected String artifactId;

  @Expose
  @SerializedName("version")
  protected String version;

  /**
   * Gets the value of the groupId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * Sets the value of the groupId property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setGroupId(String value) {
    this.groupId = value;
  }

  /**
   * Gets the value of the artifactId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getArtifactId() {
    return artifactId;
  }

  /**
   * Sets the value of the artifactId property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setArtifactId(String value) {
    this.artifactId = value;
  }

  /**
   * Gets the value of the version property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the value of the version property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setVersion(String value) {
    this.version = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "MvnArtifactType [groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + "]";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
    result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
    result = prime * result + ((version == null) ? 0 : version.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MvnArtifactType other = (MvnArtifactType) obj;
    if (artifactId == null) {
      if (other.artifactId != null) {
        return false;
      }
    } else if (!artifactId.equals(other.artifactId)) {
      return false;
    }
    if (groupId == null) {
      if (other.groupId != null) {
        return false;
      }
    } else if (!groupId.equals(other.groupId)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    return true;
  }
}
