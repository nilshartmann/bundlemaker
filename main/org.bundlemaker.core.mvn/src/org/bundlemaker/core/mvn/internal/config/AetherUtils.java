package org.bundlemaker.core.mvn.internal.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.settings.Profile;
import org.apache.maven.settings.Repository;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * <ul>
 * <li>The Maven install: $M2_HOME/conf/settings.xml</li>
 * <li>A user's install: ${user.home}/.m2/settings.xml</li>
 * </ul>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AetherUtils {

  /** SETTINGS_XML */
  public static final String           SETTINGS_XML                    = "settings.xml";

  /** - */
  private static final String          ORG_ECLIPSE_M2E_CORE            = "org.eclipse.m2e.core";

  /** - */
  private static final String          ECLIPSE_M2_GLOBAL_SETTINGS_FILE = "eclipse.m2.globalSettingsFile";

  /** - */
  private static final String          ECLIPSE_M2_USER_SETTINGS_FILE   = "eclipse.m2.userSettingsFile";

  /** - */
  private static final SettingsBuilder settingsBuilder                 = new DefaultSettingsBuilderFactory()
                                                                           .newInstance();

  /**
   * <p>
   * </p>
   * 
   * @param settings
   * @return
   */
  public static List<RemoteRepository> getRemoteRepositories(Settings settings) {

    //
    List<RemoteRepository> result = new LinkedList<RemoteRepository>();

    //
    List<String> activeProfiles = settings.getActiveProfiles();

    //
    for (Profile profile : settings.getProfiles()) {
      if (activeProfiles.contains(profile.getId())) {
        for (Repository repository : profile.getRepositories()) {
          RemoteRepository remoteRepository = AetherUtils.getRemoteRepository(repository);
          result.add(remoteRepository);
        }
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param repository
   */
  public static RemoteRepository getRemoteRepository(Repository repository) {

    RemoteRepository remoteRepository;
    remoteRepository = new RemoteRepository();
    remoteRepository.setId(repository.getId());
    remoteRepository.setUrl(repository.getUrl());
    remoteRepository.setContentType(repository.getLayout());
    remoteRepository.setPolicy(true, new org.sonatype.aether.repository.RepositoryPolicy(repository
        .getSnapshots().isEnabled(), repository.getSnapshots().getUpdatePolicy(), repository.getSnapshots()
        .getChecksumPolicy()));
    remoteRepository.setPolicy(false, new org.sonatype.aether.repository.RepositoryPolicy(repository
        .getReleases().isEnabled(), repository.getReleases().getUpdatePolicy(), repository.getReleases()
        .getChecksumPolicy()));

    return remoteRepository;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static File getM2eUserSettings() {

    //
    return readPreferenceAndConvertToFile(ECLIPSE_M2_USER_SETTINGS_FILE,
        InstanceScope.INSTANCE.getNode(ORG_ECLIPSE_M2E_CORE));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static File getM2eGlobalSettings() {

    //
    return readPreferenceAndConvertToFile(ECLIPSE_M2_GLOBAL_SETTINGS_FILE,
        InstanceScope.INSTANCE.getNode(ORG_ECLIPSE_M2E_CORE));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static Settings getSettings(File userSettingsFile, File globalSettingsFile)
  {
    //
    Settings settings;

    //
    DefaultSettingsBuildingRequest request = new DefaultSettingsBuildingRequest();
    request.setUserSettingsFile(userSettingsFile);
    request.setGlobalSettingsFile(globalSettingsFile);

    // TODO:
    // request.setSystemProperties(getSystemProperties());
    // request.setUserProperties(getUserProperties());

    try
    {
      settings = settingsBuilder.build(request).getEffectiveSettings();

      // SettingsDecryptionResult result =
      // settingDecrypter.decrypt(new DefaultSettingsDecryptionRequest(settings));
      // settings.setServers(result.getServers());
      // settings.setProxies(result.getProxies());

      return settings;
    } catch (SettingsBuildingException e)
    {
      e.printStackTrace();
      // project.log("Could not process settings.xml: " + e.getMessage(), e, Project.MSG_WARN);

      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static File getGlobalSettings()
  {
    String mavenHome = getMavenHome();
    if (mavenHome != null)
    {
      return new File(new File(mavenHome, "conf"), SETTINGS_XML);
    }

    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static String getMavenHome()
  {
    return System.getenv("M2_HOME");
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static File getDefaultUserSettings()
  {
    // user home
    File userHome = new File(System.getProperty("user.home"));

    //
    File result = new File(new File(userHome, ".m2"), SETTINGS_XML);

    //
    return result.isFile() ? result : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  private static File readPreferenceAndConvertToFile(String key, IEclipsePreferences preferences) {

    //
    Assert.isNotNull(key);
    Assert.isNotNull(preferences);

    //
    if (preferences != null) {

      //
      String value = preferences.get(key, null);

      //
      return value != null ? new File(value) : null;
    }

    //
    return null;
  }
}