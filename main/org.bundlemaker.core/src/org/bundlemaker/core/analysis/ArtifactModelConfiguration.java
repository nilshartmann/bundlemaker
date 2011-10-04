package org.bundlemaker.core.analysis;

import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Configuration for an artifact model.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactModelConfiguration implements IArtifactModelConfiguration {

  /** the content type to show */
  private ContentType          _contentType                         = ContentType.SOURCE;

  /** the resource presentation style */
  private ResourcePresentation _resourcePresentation                = ResourcePresentation.ALL_RESOURCES;

  /** whether the packages should be hierarchical or flat */
  private boolean              _hierarchicalPackages                = false;

  /** whether to aggregate inner types or not */
  private boolean              _aggregateInnerTypes                 = false;

  /** whether to include missing types or not */
  private boolean              _includeVirtualModuleForMissingTypes = false;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelConfiguration}.
   * </p>
   * 
   */
  public ArtifactModelConfiguration() {
    super();
  }

  /**
   * <p>
   * Returns the resource presentation.
   * </p>
   * 
   * @return the resource presentation.
   */
  public final ResourcePresentation getResourcePresentation() {
    return _resourcePresentation;
  }

  /**
   * <p>
   * Sets the resource presentation.
   * </p>
   * 
   * @param resourcePresentation
   */
  public final void setResourcePresentation(ResourcePresentation resourcePresentation) {
    Assert.isNotNull(resourcePresentation);

    _resourcePresentation = resourcePresentation;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ContentType getContentType() {
    return _contentType;
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentType
   */
  public final void setContentType(ContentType contentType) {
    Assert.isNotNull(contentType);

    _contentType = contentType;
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
   * @param hierarchical
   */
  public final void setHierarchicalPackages(boolean hierarchical) {
    _hierarchicalPackages = hierarchical;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean isAggregateInnerTypes() {
    return _aggregateInnerTypes && !_resourcePresentation.equals(ResourcePresentation.ALL_RESOURCES);
  }

  /**
   * <p>
   * </p>
   * 
   * @param aggregateInnerTypes
   */
  public final void setAggregateInnerTypes(boolean aggregateInnerTypes) {
    _aggregateInnerTypes = aggregateInnerTypes;
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
   * <p>
   * </p>
   * 
   * @param includeVirtualModuleForMissingTypes
   */
  public final void setIncludeVirtualModuleForMissingTypes(boolean includeVirtualModuleForMissingTypes) {
    _includeVirtualModuleForMissingTypes = includeVirtualModuleForMissingTypes;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelConfiguration}.
   * </p>
   * 
   * @param hierarchical
   * @param resourcePresentation
   * @param contentType
   * @param aggregateInnerTypes
   */
  public ArtifactModelConfiguration(boolean hierarchical, ResourcePresentation resourcePresentation,
      ContentType contentType, boolean aggregateInnerTypes, boolean includeVirtualModuleForMissingTypes) {

    Assert.isNotNull(resourcePresentation);
    Assert.isNotNull(contentType);

    _hierarchicalPackages = hierarchical;
    _contentType = contentType;
    _resourcePresentation = resourcePresentation;
    _aggregateInnerTypes = aggregateInnerTypes;
    _includeVirtualModuleForMissingTypes = includeVirtualModuleForMissingTypes;
  }

  @Override
  public String toString() {
    return _contentType.getShortDescription() + "_" + _resourcePresentation.getShortDescription() + "_"
        + prefix(_hierarchicalPackages, "Hi") + "_" + prefix(_aggregateInnerTypes, "AgTy") + "_"
        + prefix(_includeVirtualModuleForMissingTypes, "MiTy");
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (_aggregateInnerTypes ? 1231 : 1237);
    result = prime * result + ((_contentType == null) ? 0 : _contentType.hashCode());
    result = prime * result + (_hierarchicalPackages ? 1231 : 1237);
    result = prime * result + (_includeVirtualModuleForMissingTypes ? 1231 : 1237);
    result = prime * result + ((_resourcePresentation == null) ? 0 : _resourcePresentation.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ArtifactModelConfiguration other = (ArtifactModelConfiguration) obj;
    if (_aggregateInnerTypes != other._aggregateInnerTypes)
      return false;
    if (_contentType != other._contentType)
      return false;
    if (_hierarchicalPackages != other._hierarchicalPackages)
      return false;
    if (_includeVirtualModuleForMissingTypes != other._includeVirtualModuleForMissingTypes)
      return false;
    if (_resourcePresentation != other._resourcePresentation)
      return false;
    return true;
  }

  private String prefix(boolean value, String string) {
    return value ? string : "Non" + string;
  }
}
