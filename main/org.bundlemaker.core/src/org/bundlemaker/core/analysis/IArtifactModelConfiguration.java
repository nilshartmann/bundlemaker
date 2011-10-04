package org.bundlemaker.core.analysis;

import org.bundlemaker.core.projectdescription.ContentType;

/**
 * <p>
 * Configuration for an artifact model.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactModelConfiguration {

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
  public static final IArtifactModelConfiguration AGGREGATE_INNER_TYPES_NO_RESOURCES_CONFIGURATION = new ArtifactModelConfiguration(
                                                                                                       false,
                                                                                                       ResourcePresentation.NO_RESOURCE,
                                                                                                       ContentType.BINARY,
                                                                                                       true, false);

  /** default configuration AGGREGATE_INNER_TYPES_CONFIGURATION */
  public static final IArtifactModelConfiguration AGGREGATE_INNER_TYPES_CONFIGURATION              = new ArtifactModelConfiguration(
                                                                                                       false,
                                                                                                       ResourcePresentation.ONLY_NON_TYPE_RESOURCES,
                                                                                                       ContentType.BINARY,
                                                                                                       true, true);

  /** default configuration SOURCE_RESOURCES_CONFIGURATION */
  public static final IArtifactModelConfiguration SOURCE_RESOURCES_CONFIGURATION                   = new ArtifactModelConfiguration(
                                                                                                       false,
                                                                                                       ResourcePresentation.ALL_RESOURCES,
                                                                                                       ContentType.SOURCE,
                                                                                                       false, true);

  /** default configuration BINARY_RESOURCES_CONFIGURATION */
  public static final IArtifactModelConfiguration BINARY_RESOURCES_CONFIGURATION                   = new ArtifactModelConfiguration(
                                                                                                       false,
                                                                                                       ResourcePresentation.ALL_RESOURCES,
                                                                                                       ContentType.BINARY,
                                                                                                       false, true);

  public static final IArtifactModelConfiguration HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION      = new ArtifactModelConfiguration(
                                                                                                       true,
                                                                                                       ResourcePresentation.ALL_RESOURCES,
                                                                                                       ContentType.BINARY,
                                                                                                       false, true);

  public static final IArtifactModelConfiguration HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION      = new ArtifactModelConfiguration(
                                                                                                       true,
                                                                                                       ResourcePresentation.ALL_RESOURCES,
                                                                                                       ContentType.SOURCE,
                                                                                                       false, true);

  /**
   * <p>
   * Returns the resource presentation.
   * </p>
   * 
   * @return the resource presentation.
   */
  ResourcePresentation getResourcePresentation();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  ContentType getContentType();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isHierarchicalPackages();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isAggregateInnerTypes();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isIncludeVirtualModuleForMissingTypes();
}
