package org.bundlemaker.core.mvn;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface BmMvnCoreConstants {

  /** PLUGIN_ID */
  public static final String PLUGIN_ID            = "org.bundlemaker.core.mvn"; //$NON-NLS-1$

  /** PREF_MVN_LOCAL_REPO */
  public static final String PREF_MVN_LOCAL_REPO  = BmMvnCoreConstants.PLUGIN_ID
                                                      + ".local_repository";   //$NON-NLS-1$

  /** PREF_MVN_REMOTE_REPO */
  public static final String PREF_MVN_REMOTE_REPO = BmMvnCoreConstants.PLUGIN_ID
                                                      + ".remote_repository";  //$NON-NLS-1$
}
