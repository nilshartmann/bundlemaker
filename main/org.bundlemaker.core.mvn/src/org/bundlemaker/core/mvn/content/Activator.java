package org.bundlemaker.core.mvn.content;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

  // The plug-in ID
  public static final String PLUGIN_ID            = "org.bundlemaker.core.mvn"; //$NON-NLS-1$

  /** - */
  public static final String PREF_MVN_LOCAL_REPO  = Activator.PLUGIN_ID
                                                      + ".local_repository";

  /** - */
  public static final String PREF_MVN_REMOTE_REPO = Activator.PLUGIN_ID
                                                      + ".remote_repository";

  /** - */
  private static Activator   _instance;

  /** - */
  private BundleContext      _bundleContext;

  /** - */
  private MvnRepositories    _mvnRepositories;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    _instance = this;

    //
    _bundleContext = context;

    //
    _mvnRepositories = new MvnRepositories();

    //
    IPreferencesService service = Platform.getPreferencesService();
    String localRepo = service.getString(PLUGIN_ID, PREF_MVN_LOCAL_REPO, System.getProperty("user.home")
        + File.separatorChar + ".m2", null);
    String remoteRepo = service.getString(PLUGIN_ID, PREF_MVN_REMOTE_REPO, "http://repo1.maven.org/maven2/", null);

    // // TODO
    _mvnRepositories
        .setMvnRepositories(new File(localRepo), remoteRepo);

    //
    _bundleContext.registerService(IMvnRepositories.class, _mvnRepositories, null);
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
  public IMvnRepositories getMvnRepositories() {

    //
    if (_bundleContext == null || _mvnRepositories == null) {
      throw new RuntimeException("Not started yet!");
    }

    //
    return _mvnRepositories;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static Activator getDefault() {
    return _instance;
  }
}
