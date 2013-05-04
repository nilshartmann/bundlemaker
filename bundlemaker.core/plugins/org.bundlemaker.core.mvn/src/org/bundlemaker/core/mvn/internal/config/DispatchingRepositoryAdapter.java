package org.bundlemaker.core.mvn.internal.config;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.preferences.MvnConfigurationSettingEnum;
import org.bundlemaker.core.util.IBundleMakerPreferences;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.Bundle;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DispatchingRepositoryAdapter implements IAetherRepositoryAdapter {

  /** - */
  private IAetherRepositoryAdapter    _adapter;

  /** - */
  private IBundleMakerPreferences     _bundleMakerPreferences;

  /** - */
  private MvnConfigurationSettingEnum _currentSetting;

  /**
   * <p>
   * Creates a new instance of type {@link DispatchingRepositoryAdapter}.
   * </p>
   */
  public DispatchingRepositoryAdapter(IBundleMakerPreferences preferences) {

    //
    Assert.isNotNull(preferences);

    //
    _bundleMakerPreferences = preferences;
  }

  /**
   * {@inheritDoc}
   */
  public RepositorySystem getRepositorySystem() throws CoreException {

    //
    init();

    //
    return _adapter.getRepositorySystem();
  }

  /**
   * {@inheritDoc}
   */
  public RepositorySystemSession newSession() throws CoreException {

    //
    init();

    //
    return _adapter.newSession();
  }

  /**
   * {@inheritDoc}
   */
  public List<RemoteRepository> getRemoteRepositories() throws CoreException {

    //
    init();

    //
    return _adapter.getRemoteRepositories();
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   */
  private void init() throws CoreException {

    //
    if (_adapter != null) {
      return;
    }

    //
    _bundleMakerPreferences.reload();

    //
    MvnConfigurationSettingEnum newSetting = MvnConfigurationSettingEnum.valueOf(_bundleMakerPreferences.getString(
        MvnCoreActivator.PREF_MVN_CURRENT_SETTING, null));

    //
    switch (newSetting) {
    case USE_CONFIGURED_RESPOSITORIES:
      useConfiguredRepositories(_bundleMakerPreferences, newSetting);
      break;
    case USE_M2E_SETTINGS:
      useM2eSettings(newSetting);
      break;
    case USE_SETTINGS_XML:
      useSettingXml(_bundleMakerPreferences, newSetting);
      break;
    }

    //
    Assert.isNotNull(_adapter);
  }

  private void useSettingXml(IBundleMakerPreferences preferences, MvnConfigurationSettingEnum newSetting) {

    //
    String localRepoPath = preferences.getString(MvnCoreActivator.PREF_MVN_SETTINGSXML, null);

    File localRepo = new File(localRepoPath);

    //
    if (!localRepo.isFile()) {
      throw new RuntimeException(
          String
              .format(
                  "The specified settings file '%s' is invalid.\n Please specifiy a valid settings file in the BundleMaker preferences.",
                  localRepo.getAbsoluteFile()));
    }

    // create the adapter
    _adapter = new MvnSettingsBasedRepositoryAdapter(localRepo, null);

  }

  /**
   * <p>
   * </p>
   * 
   * @param newSetting
   */
  private void useM2eSettings(MvnConfigurationSettingEnum newSetting) {

    //
    boolean found = false;
    for (Bundle bundle : MvnCoreActivator.getDefault().getBundleContext().getBundles()) {
      if (bundle.getSymbolicName().equals(MvnCoreActivator.PLUGIN_ID_ORG_ECLIPSE_M2E_CORE)) {
        found = true;
      }
    }

    if (!found) {
      throw new RuntimeException("Missing '" + MvnCoreActivator.PLUGIN_ID_ORG_ECLIPSE_M2E_CORE
          + "'.\n Please specifiy a valid settings file in the BundleMaker preferences.");
    }

    // skip if nothing has changed
    if (newSetting.equals(_currentSetting)) {
      return;
    }

    //
    File userSettings = AetherUtils.readPreferenceAndConvertToFile(MvnCoreActivator.PREF_ECLIPSE_M2_USER_SETTINGS_FILE,
        InstanceScope.INSTANCE.getNode(MvnCoreActivator.PLUGIN_ID_ORG_ECLIPSE_M2E_CORE));

    //
    if (userSettings != null && !userSettings.isFile()) {
      throw new RuntimeException(
          String
              .format(
                  "The specified user settings settings file '%s' is invalid.\n Please specifiy a valid settings file in the BundleMaker preferences.",
                  userSettings.getAbsoluteFile()));
    }

    File globalSettings = AetherUtils.readPreferenceAndConvertToFile(
        MvnCoreActivator.PREF_ECLIPSE_M2_GLOBAL_SETTINGS_FILE,
        InstanceScope.INSTANCE.getNode(MvnCoreActivator.PLUGIN_ID_ORG_ECLIPSE_M2E_CORE));

    //
    if (globalSettings != null && !globalSettings.isFile()) {
      throw new RuntimeException(
          String
              .format(
                  "The specified global settings settings file '%s' is invalid.\n Please specifiy a valid settings file in the BundleMaker preferences.",
                  globalSettings.getAbsoluteFile()));
    }

    // skip if nothing has changed
    if (newSetting.equals(_currentSetting)
        && userSettings.equals(((MvnSettingsBasedRepositoryAdapter) _adapter).getUserSettingsFile())
        && globalSettings.equals(((MvnSettingsBasedRepositoryAdapter) _adapter).getGlobalSettingsFile())) {
      return;
    }

    // create the adapter
    _adapter = new MvnSettingsBasedRepositoryAdapter(userSettings, globalSettings);
  }

  /**
   * <p>
   * </p>
   * 
   * @param newSetting
   */
  private void useConfiguredRepositories(IBundleMakerPreferences preferences, MvnConfigurationSettingEnum newSetting) {

    //
    String localRepo = preferences.getString(MvnCoreActivator.PREF_MVN_LOCAL_REPO, null);
    String remoteRepo = preferences.getString(MvnCoreActivator.PREF_MVN_REMOTE_REPO, null);

    // skip if nothing has changed
    if (newSetting.equals(_currentSetting) && localRepo.equals(((SimpleRepositoryAdapter) _adapter).getLocalRepo())
        && remoteRepo.equals(((SimpleRepositoryAdapter) _adapter).getRemoteRepoUrl())) {
      return;
    }

    // create the adapter
    _adapter = new SimpleRepositoryAdapter(new File(localRepo), remoteRepo);
  }
}
