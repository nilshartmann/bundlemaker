package org.bundlemaker.core.ui.mvn.internal.preference;

import org.bundlemaker.core.mvn.preferences.MvnPreferencesUtils;
import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.eclipse.swt.widgets.Composite;

public class MvnPropertyAndPreferencePage extends AbstractPropertyAndPreferencesPage {

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
    return MvnPreferencesUtils.PLUGIN_ID;
  }
}
