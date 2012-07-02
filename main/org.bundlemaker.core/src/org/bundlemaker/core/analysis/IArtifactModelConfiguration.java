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

  /** default configuration SOURCE_RESOURCES_CONFIGURATION */
  public static final IArtifactModelConfiguration SOURCE_RESOURCES_CONFIGURATION              = new ArtifactModelConfiguration(
                                                                                                  false,
                                                                                                  ContentType.SOURCE,
                                                                                                  true);

  /** default configuration BINARY_RESOURCES_CONFIGURATION */
  public static final IArtifactModelConfiguration BINARY_RESOURCES_CONFIGURATION              = new ArtifactModelConfiguration(
                                                                                                  false,
                                                                                                  ContentType.BINARY,
                                                                                                  true);

  public static final IArtifactModelConfiguration HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION = new ArtifactModelConfiguration(
                                                                                                  true,
                                                                                                  ContentType.BINARY,
                                                                                                  true);

  public static final IArtifactModelConfiguration HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION = new ArtifactModelConfiguration(
                                                                                                  true,
                                                                                                  ContentType.SOURCE,
                                                                                                  true);

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
  boolean isSourceContent();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isBinaryContent();

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
  boolean isIncludeVirtualModuleForMissingTypes();
}
