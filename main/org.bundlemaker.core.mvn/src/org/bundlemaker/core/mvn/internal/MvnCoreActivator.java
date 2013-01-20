package org.bundlemaker.core.mvn.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnCoreActivator implements BundleActivator {

  /** - */
  private static MvnCoreActivator _instance;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _instance = this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    _instance = null;
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
