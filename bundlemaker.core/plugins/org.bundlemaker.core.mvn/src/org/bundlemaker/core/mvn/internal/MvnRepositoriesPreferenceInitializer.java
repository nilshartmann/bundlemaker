package org.bundlemaker.core.mvn.internal;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.preferences.MvnConfigurationSettingEnum;
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

    // get the preferences
    IEclipsePreferences preferences = DefaultScope.INSTANCE.getNode(MvnCoreActivator.PLUGIN_ID);

    // default setting: configured repositories
    preferences.put(MvnCoreActivator.PREF_MVN_CURRENT_SETTING,
        MvnConfigurationSettingEnum.USE_CONFIGURED_RESPOSITORIES.name());

    // defaults for configured repositories
    preferences.put(MvnCoreActivator.PREF_MVN_LOCAL_REPO, MvnCoreActivator.DEFAULT_MVN_LOCAL_REPO);
    preferences.put(MvnCoreActivator.PREF_MVN_REMOTE_REPO, MvnCoreActivator.DEFAULT_MVN_REMOTE_REPO);

    // defaults for configured repositories
    preferences.put(MvnCoreActivator.PREF_MVN_SETTINGSXML, MvnCoreActivator.DEFAULT_MVN_SETTINGSXML);
  }
}