package org.bundlemaker.core.ui.mvn;

import java.io.File;

import org.bundlemaker.core.mvn.content.IMvnRepositories;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String                                        PLUGIN_ID = "org.bundlemaker.core.ui.mvn"; //$NON-NLS-1$

  // The shared instance
  private static Activator                                          plugin;

  /** - */
  private static ServiceTracker<IMvnRepositories, IMvnRepositories> _mvnRepositoriesServiceTracker;

  /** - */
  private ScopedPreferenceStore                                     _scopedPreferenceStore;

  /**
   * The constructor
   */
  public Activator() {
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    //
    _mvnRepositoriesServiceTracker = new ServiceTracker<IMvnRepositories, IMvnRepositories>(context,
        IMvnRepositories.class, null) {

      @Override
      public IMvnRepositories addingService(ServiceReference<IMvnRepositories> reference) {

        //
        IMvnRepositories mvnRepositories = super.addingService(reference);

        reset(mvnRepositories);

        //
        return mvnRepositories;
      }
    };
    _mvnRepositoriesServiceTracker.open();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;

    //
    _mvnRepositoriesServiceTracker.close();

    super.stop(context);
  }

  /**
   * <p>
   * </p>
   */
  public void resetMvnRepositories() {

    //
    if (_mvnRepositoriesServiceTracker == null) {
      return;
    }

    // TODO
    reset(_mvnRepositoriesServiceTracker.getService());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPreferenceStore getPreferenceStore() {

    //
    if (_scopedPreferenceStore == null) {

      //
      _scopedPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE,
          org.bundlemaker.core.mvn.content.Activator.PLUGIN_ID);
    }

    //
    return _scopedPreferenceStore;
  }

  /**
   * <p>
   * </p>
   * 
   * @param mvnRepositories
   */
  private void reset(IMvnRepositories mvnRepositories) {
    String localRepo = getPreferenceStore().getString(org.bundlemaker.core.mvn.content.Activator.PREF_MVN_LOCAL_REPO);
    String remoteRepoUrl = getPreferenceStore().getString(
        org.bundlemaker.core.mvn.content.Activator.PREF_MVN_REMOTE_REPO);
    mvnRepositories.setMvnRepositories(new File(localRepo), remoteRepoUrl);
  }

}
