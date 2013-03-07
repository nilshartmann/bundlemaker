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

}
