package org.bundlemaker.core.mvn.content;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.preferences.MvnConfigurationSettingEnum;
import org.bundlemaker.core.util.prefs.IBundleMakerPreferences;

public class MockHelper {

  // //
  // IProjectDescription projectDescription = mock(IProjectDescription.class);
  // IBundleMakerProject bundleMakerProject = mock(IBundleMakerProject.class);

  /**
   * <p>
   * </p>
   * 
   * @param localRepository
   * @param remoteRepository
   * @return
   */
  public static final IBundleMakerPreferences mockBundleMakerPreferences_configuredRepositories(String localRepository,
      String remoteRepository) {

    assertNotNull(localRepository);
    assertNotNull(remoteRepository);

    return mockBundleMakerPreferences(new String[][] {
        { MvnCoreActivator.PREF_MVN_CURRENT_SETTING, MvnConfigurationSettingEnum.USE_CONFIGURED_RESPOSITORIES.name() },
        { MvnCoreActivator.PREF_MVN_LOCAL_REPO, localRepository },
        { MvnCoreActivator.PREF_MVN_REMOTE_REPO, remoteRepository } });
  }

  /**
   * <p>
   * </p>
   * 
   * @param values
   * @return
   */
  public static final IBundleMakerPreferences mockBundleMakerPreferences(String[][] values) {

    //
    final IBundleMakerPreferences preferences = mock(IBundleMakerPreferences.class);

    //
    for (String[] value : values) {

      //
      assertThat(value.length, is(2));
      when(preferences.getString(value[0], null)).thenReturn(value[1]);
    }

    //
    return preferences;
  }
}
