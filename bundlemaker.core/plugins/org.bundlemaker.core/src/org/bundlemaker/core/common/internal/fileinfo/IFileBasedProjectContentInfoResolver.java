package org.bundlemaker.core.common.internal.fileinfo;

import java.io.File;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IFileBasedProjectContentInfoResolver {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean resolve(File file);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  String getVersion();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isSource();
}
