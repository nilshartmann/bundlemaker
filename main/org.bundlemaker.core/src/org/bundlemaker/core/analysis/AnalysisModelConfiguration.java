package org.bundlemaker.core.analysis;

import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.eclipse.core.runtime.Assert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * Defines the configuration of an analysis model.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AnalysisModelConfiguration implements IAnalysisModelConfiguration {

  /** the content type to show */
  @Expose
  @SerializedName("contentType")
  private ProjectContentType _contentType                         = ProjectContentType.SOURCE;

  /** whether the packages should be hierarchical or flat */
  @Expose
  @SerializedName("hierarchicalPackages")
  private boolean     _hierarchicalPackages                = true;

  /** whether to include missing types or not */
  @Expose
  @SerializedName("includeVirtualModuleForMissingTypes")
  private boolean     _includeVirtualModuleForMissingTypes = false;

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelConfiguration}.
   * </p>
   * 
   */
  public AnalysisModelConfiguration() {
    super();
  }

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelConfiguration}.
   * </p>
   * 
   * @param hierarchical
   * @param contentType
   * @param resourcePresentation
   */
  public AnalysisModelConfiguration(boolean hierarchical,
      ProjectContentType contentType, boolean includeVirtualModuleForMissingTypes) {
  
    Assert.isNotNull(contentType);
  
    _hierarchicalPackages = hierarchical;
    _contentType = contentType;
    _includeVirtualModuleForMissingTypes = includeVirtualModuleForMissingTypes;
  }

  /**
   * <p>
   * Returns the {@link ProjectContentType} of this configuration.
   * </p>
   * 
   * @return
   */
  public final ProjectContentType getContentType() {
    return _contentType;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean isHierarchicalPackages() {
    return _hierarchicalPackages;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean isIncludeVirtualModuleForMissingTypes() {
    return _includeVirtualModuleForMissingTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isSourceContent() {
    return _contentType.equals(ProjectContentType.SOURCE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isBinaryContent() {
    return _contentType.equals(ProjectContentType.BINARY);
  }

  /**
   * <p>
   * </p>
   * 
   * @param hierarchical
   */
  public final void setHierarchicalPackages(boolean hierarchical) {
    _hierarchicalPackages = hierarchical;
  }

  /**
   * <p>
   * Sets the content type of this configuration.
   * </p>
   * 
   * @param contentType
   */
  public final void setContentType(ProjectContentType contentType) {
    Assert.isNotNull(contentType);

    _contentType = contentType;
  }

  /**
   * <p>
   * </p>
   * 
   * @param includeVirtualModuleForMissingTypes
   */
  public final void setIncludeVirtualModuleForMissingTypes(boolean includeVirtualModuleForMissingTypes) {
    _includeVirtualModuleForMissingTypes = includeVirtualModuleForMissingTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return _contentType.getShortDescription() + "_"
        + prefix(_hierarchicalPackages, "Hi") + "_"
        + prefix(_includeVirtualModuleForMissingTypes, "MiTy");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_contentType == null) ? 0 : _contentType.hashCode());
    result = prime * result + (_hierarchicalPackages ? 1231 : 1237);
    result = prime * result + (_includeVirtualModuleForMissingTypes ? 1231 : 1237);
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
    AnalysisModelConfiguration other = (AnalysisModelConfiguration) obj;
    if (_contentType != other._contentType)
      return false;
    if (_hierarchicalPackages != other._hierarchicalPackages)
      return false;
    if (_includeVirtualModuleForMissingTypes != other._includeVirtualModuleForMissingTypes)
      return false;
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param value
   * @param string
   * @return
   */
  private String prefix(boolean value, String string) {
    return value ? string : "Non" + string;
  }
}
