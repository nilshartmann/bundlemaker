package org.bundlemaker.core.mvn.content;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

  /** - */
  private static BundleContext _bundleContext;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {
    _bundleContext = context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    _bundleContext = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static BundleContext getBundleContext() {
    return _bundleContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IMvnRepositories getMvnRepositories() {

    //
    ServiceTracker<IMvnRepositories, IMvnRepositories> serviceTracker = new ServiceTracker<IMvnRepositories, IMvnRepositories>(
        _bundleContext, IMvnRepositories.class, null);
    serviceTracker.open();
    IMvnRepositories mvnRepositories = null;
    try {
      mvnRepositories = serviceTracker.waitForService(5000);
    } catch (InterruptedException e) {
    }
    serviceTracker.close();
    //
    return mvnRepositories;
  }
}
