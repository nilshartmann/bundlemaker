package org.bundlemaker.core.transformations.internal;

import org.bundlemaker.core.transformations.script.ITransformationScriptLogger;
import org.bundlemaker.core.transformations.script.runner.SysoutTransformationScriptLogger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

  public static final String                                                       PLUGIN_ID = "org.bundlemaker.core.transformations";

  private static Activator                                                         theActivator;

  private BundleContext                                                            _bundleContext;

  private ServiceTracker<ITransformationScriptLogger, ITransformationScriptLogger> _scriptLoggerServiceTracker;

  public static Activator getDefault() {
    return theActivator;
  }

  @Override
  public void start(BundleContext context) throws Exception {
    theActivator = this;
    _bundleContext = context;

    _scriptLoggerServiceTracker = new ServiceTracker<ITransformationScriptLogger, ITransformationScriptLogger>(context,
        ITransformationScriptLogger.class, null);
    _scriptLoggerServiceTracker.open();

    // context.registerService(IBundleMakerProjectHook.class, new InitialTransformationRunnerHook(), null);
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

  public ITransformationScriptLogger getTransformationScriptLogger() {
    ITransformationScriptLogger logger = null;
    try {
      logger = _scriptLoggerServiceTracker.waitForService(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (logger == null) {

      // fall back
      logger = new SysoutTransformationScriptLogger();
    }

    return logger;
  }

  /**
   * @return
   */
  public static String getPrefsPath() {
    return ".settings/" + Activator.PLUGIN_ID + ".prefs";
  }
}
