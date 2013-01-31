package org.bundlemaker.core.ui.mvn.internal.preference;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.preferences.MvnPreferencesUtils;
import org.bundlemaker.core.ui.mvn.Activator;
import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.eclipse.swt.widgets.Composite;

public class MvnPropertyAndPreferencePage extends AbstractPropertyAndPreferencesPage {

  /** PREF_MVN_LOCAL_REPO */
  public static final String PREF_MVN_LOCAL_REPO            = Activator.PLUGIN_ID
                                                                + ".local_repository";

  /** PREF_MVN_REMOTE_REPO */
  public static final String PREF_MVN_REMOTE_REPO           = Activator.PLUGIN_ID
                                                                + ".remote_repository";

  /** PREF_MVN_REMOTE_REPO */
  public static final String PREF_MVN_CONFIGURATION_SETTING = Activator.PLUGIN_ID
                                                                + ".configurationsetting";

  @Override
  protected String getPreferencePageID() {
    return "org.bundlemaker.core.ui.mvn.preferences";
  }

  @Override
  protected String getPropertyPageID() {
    return "org.bundlemaker.core.ui.mvn.properties";
  }

  @Override
  protected ConfigurationBlock createPreferenceContent(Composite composite) {
    return new MvnPropertyAndPreferenceConfigurationBlock(composite, this);
  }

  @Override
  public String getStoreIdentifier() {
    return MvnCoreActivator.PLUGIN_ID;
  }
}
