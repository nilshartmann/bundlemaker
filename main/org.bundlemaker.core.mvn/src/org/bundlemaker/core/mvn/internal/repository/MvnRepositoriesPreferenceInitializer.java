package org.bundlemaker.core.mvn.internal.repository;

import java.io.File;

import org.bundlemaker.core.mvn.BmMvnCoreConstants;
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
    IEclipsePreferences node = DefaultScope.INSTANCE.getNode(BmMvnCoreConstants.PLUGIN_ID);
    node.put(BmMvnCoreConstants.PREF_MVN_LOCAL_REPO, System.getProperty("user.home") + File.separatorChar + ".m2");
    node.put(BmMvnCoreConstants.PREF_MVN_REMOTE_REPO, "http://repo1.maven.org/maven2/");
  }
}