package org.bundlemaker.core.transformations.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  private static Activator theActivator;

  private BundleContext    _bundleContext;

  public static Activator getDefault() {
    return theActivator;
  }

  @Override
  public void start(BundleContext context) throws Exception {
    theActivator = this;
    _bundleContext = context;
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    theActivator = null;
    _bundleContext = null;
  }

  /**
   * @return the bundleContext
   */
  public BundleContext getBundleContext() {
    return _bundleContext;
  }

}
