package org.bundlemaker.core.mvn.internal.config;

import org.bundlemaker.core.mvn.preferences.MvnPreferencesUtils;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnRepositoriesPreferenceInitializer extends AbstractPreferenceInitializer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeDefaultPreferences() {

    //
    IEclipsePreferences node = DefaultScope.INSTANCE.getNode(MvnPreferencesUtils.PLUGIN_ID);
    node.put(MvnPreferencesUtils.PREF_MVN_LOCAL_REPO, MvnPreferencesUtils.DEFAULT_MVN_LOCAL_REPO);
    node.put(MvnPreferencesUtils.PREF_MVN_REMOTE_REPO, MvnPreferencesUtils.DEFAULT_MVN_REMOTE_REPO);
  }
}