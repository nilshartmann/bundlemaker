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
public class ArtifactModelConfiguration {

  /**
   * <p>
   * The resource presentation style.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum ResourcePresentation {

    /** show all resources */
    ALL_RESOURCES,

    /** show only resources that contain no types */
    ONLY_NON_TYPE_RESOURCES,

    /** show no resources */
    NO_RESOURCE;
  }

  /** default configuration AGGREGATE_INNER_TYPES_CONFIGURATION */
  public static final ArtifactModelConfiguration AGGREGATE_INNER_TYPES_NO_RESOURCES_CONFIGURATION = new ArtifactModelConfiguration(
                                                                                                      false,
                                                                                                      ResourcePresentation.NO_RESOURCE,
                                                                                                      ContentType.BINARY,
                                                                                                      true, false);

  /** default configuration AGGREGATE_INNER_TYPES_CONFIGURATION */
  public static final ArtifactModelConfiguration AGGREGATE_INNER_TYPES_CONFIGURATION              = new ArtifactModelConfiguration(
                                                                                                      false,
                                                                                                      ResourcePresentation.ONLY_NON_TYPE_RESOURCES,
                                                                                                      ContentType.BINARY,
                                                                                                      true, true);

  /** default configuration SOURCE_RESOURCES_CONFIGURATION */
  public static final ArtifactModelConfiguration SOURCE_RESOURCES_CONFIGURATION                   = new ArtifactModelConfiguration(
                                                                                                      false,
                                                                                                      ResourcePresentation.ALL_RESOURCES,
                                                                                                      ContentType.SOURCE,
                                                                                                      false, true);

  /** default configuration BINARY_RESOURCES_CONFIGURATION */
  public static final ArtifactModelConfiguration BINARY_RESOURCES_CONFIGURATION                   = new ArtifactModelConfiguration(
                                                                                                      false,
                                                                                                      ResourcePresentation.ALL_RESOURCES,
                                                                                                      ContentType.BINARY,
                                                                                                      false, true);

  public static final ArtifactModelConfiguration HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION      = new ArtifactModelConfiguration(
                                                                                                      true,
                                                                                                      ResourcePresentation.ALL_RESOURCES,
                                                                                                      ContentType.BINARY,
                                                                                                      false, true);

  public static final ArtifactModelConfiguration HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION      = new ArtifactModelConfiguration(
                                                                                                      true,
                                                                                                      ResourcePresentation.ALL_RESOURCES,
                                                                                                      ContentType.SOURCE,
                                                                                                      false, true);

  /** the content type to show */
  private ContentType                            _contentType                                     = ContentType.SOURCE;

  /** the resource presentation style */
  private ResourcePresentation                   _resourcePresentation                            = ResourcePresentation.ALL_RESOURCES;

  /** whether the packages should be hierarchical or flat */
  private boolean                                _hierarchicalPackages                            = false;

  /** whether to aggregate inner types or not */
  private boolean                                _aggregateInnerTypes                             = false;

  /** whether to include missing types or not */
  private boolean                                _includeVirtualModuleForMissingTypes             = false;

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
  private ArtifactModelConfiguration(boolean hierarchical, ResourcePresentation resourcePresentation,
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
    return _contentType + "_" + _resourcePresentation + "_" + prefix(_hierarchicalPackages, "Hierarchical") + "_"
        + prefix(_aggregateInnerTypes, "AggregateTypes") + "_"
        + prefix(_includeVirtualModuleForMissingTypes, "MissingTypes");
  }

  private String prefix(boolean value, String string) {
    return value ? string : "Non" + string;
  }
}
