package org.bundlemaker.core.common;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface Constants {

  /** the plug-in id */
  public static final String BUNDLE_ID_BUNDLEMAKER_CORE                 = "org.bundlemaker.core";

  /** the nature id */
  public static final String NATURE_ID                                  = "org.bundlemaker.core.bundlemakernature";

  /** bundlemaker classpath container */
  public static final IPath  BUNDLEMAKER_CONTAINER_PATH                 = new Path("org.bundlemaker.core.classpath");              //$NON-NLS-1$

  /** the bundle make directory name */
  public static final String BUNDLEMAKER_DIRECTORY_NAME                 = ".bundlemaker";

  /** the project description file name */
  public static final String PROJECT_DESCRIPTION_NAME                   = "bundlemaker.json";

  /** the project description path */
  public static final IPath  PROJECT_DESCRIPTION_PATH                   = new Path(PROJECT_DESCRIPTION_NAME);

  /** - */
  public static final String BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER =
                                                                            "#####BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER#####";
}
