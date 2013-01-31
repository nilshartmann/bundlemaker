package org.bundlemaker.core.mvn;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnCoreActivator implements BundleActivator {

  /** PLUGIN_ID */
  public static final String      PLUGIN_ID                            = "org.bundlemaker.core.mvn";             //$NON-NLS-1$

  /** - */
  public static final String      PLUGIN_ID_ORG_ECLIPSE_M2E_CORE       = "org.eclipse.m2e.core";

  /** PREF_MVN_CURRENT_SETTING */
  public static final String      PREF_MVN_CURRENT_SETTING             = PLUGIN_ID + ".setting";

  /** PREF_MVN_LOCAL_REPO */
  public static final String      PREF_MVN_LOCAL_REPO                  = PLUGIN_ID
                                                                           + ".local_repository";                //$NON-NLS-1$

  /** PREF_MVN_REMOTE_REPO */
  public static final String      PREF_MVN_REMOTE_REPO                 = PLUGIN_ID
                                                                           + ".remote_repository";               //$NON-NLS-1$

  /** PREF_MVN_SETTINGSXML */
  public static final String      PREF_MVN_SETTINGSXML                 = PLUGIN_ID
                                                                           + ".settingsxml";                     //$NON-NLS-1$

  /** DEFAULT_MVN_LOCAL_REPO */
  public static final String      DEFAULT_MVN_LOCAL_REPO               = System.getProperty("user.home")
                                                                           + File.separatorChar + ".m2";         //$NON-NLS-1$

  /** DEFAULT_MVN_REMOTE_REPO */
  public static final String      DEFAULT_MVN_REMOTE_REPO              = "http://repo1.maven.org/maven2/";       //$NON-NLS-1$

  /** DEFAULT_MVN_SETTINGSXML */
  public static final String      DEFAULT_MVN_SETTINGSXML              = System.getProperty("user.home")
                                                                           + File.separatorChar + ".m2"
                                                                           + File.separatorChar + "settings.xml";

  /** - */
  public static final String      PREF_ECLIPSE_M2_GLOBAL_SETTINGS_FILE = "eclipse.m2.globalSettingsFile";

  /** - */
  public static final String      PREF_ECLIPSE_M2_USER_SETTINGS_FILE   = "eclipse.m2.userSettingsFile";

  /** - */
  private static MvnCoreActivator _instance;

  /** - */
  private BundleContext           _bundleContext;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _bundleContext = context;

    //
    _instance = this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {

    _bundleContext = null;
    _instance = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public BundleContext getBundleContext() {
    return _bundleContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static MvnCoreActivator getDefault() {
    return _instance;
  }
}
