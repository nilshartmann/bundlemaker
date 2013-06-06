package org.bundlemaker.core.analysis;

import org.bundlemaker.core.common.ResourceType;

/**
 * <p>
 * Configuration for an artifact model.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAnalysisModelConfiguration {

  /** Default configuration SOURCE_RESOURCES_CONFIGURATION */
  public static final IAnalysisModelConfiguration SOURCE_RESOURCES_CONFIGURATION              = new AnalysisModelConfiguration(
                                                                                                  false,
                                                                                                  ResourceType.SOURCE,
                                                                                                  true);

  /** default configuration BINARY_RESOURCES_CONFIGURATION */
  public static final IAnalysisModelConfiguration BINARY_RESOURCES_CONFIGURATION              = new AnalysisModelConfiguration(
                                                                                                  false,
                                                                                                  ResourceType.BINARY,
                                                                                                  true);

  public static final IAnalysisModelConfiguration HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION = new AnalysisModelConfiguration(
                                                                                                  true,
                                                                                                  ResourceType.BINARY,
                                                                                                  true);

  public static final IAnalysisModelConfiguration HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION = new AnalysisModelConfiguration(
                                                                                                  true,
                                                                                                  ResourceType.SOURCE,
                                                                                                  true);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  ResourceType getContentType();

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
