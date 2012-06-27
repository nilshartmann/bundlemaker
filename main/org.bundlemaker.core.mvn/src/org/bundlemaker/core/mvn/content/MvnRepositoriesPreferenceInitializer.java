package org.bundlemaker.core.mvn.content;

import java.io.File;

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
    IEclipsePreferences node = DefaultScope.INSTANCE.getNode(Activator.PLUGIN_ID);
    node.put(Activator.PREF_MVN_LOCAL_REPO, System.getProperty("user.home") + File.separatorChar + ".m2");
    node.put(org.bundlemaker.core.mvn.content.Activator.PREF_MVN_REMOTE_REPO, "http://repo1.maven.org/maven2/");
  }
}