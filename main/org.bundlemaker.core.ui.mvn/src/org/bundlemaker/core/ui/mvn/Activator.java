package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String    PLUGIN_ID = "org.bundlemaker.core.ui.mvn"; //$NON-NLS-1$

  // The shared instance
  private static Activator      plugin;

  /** - */
  private ScopedPreferenceStore _scopedPreferenceStore;

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
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPreferenceStore getPreferenceStore() {

    //
    if (_scopedPreferenceStore == null) {
      _scopedPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE,
          MvnCoreActivator.PLUGIN_ID);
    }

    //
    return _scopedPreferenceStore;
  }
}
