package org.bundlemaker.core.mvn.preferences;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnPreferencesUtils {

  /** PLUGIN_ID */
  public static final String PLUGIN_ID               = "org.bundlemaker.core.mvn";      //$NON-NLS-1$

  /** PREF_MVN_LOCAL_REPO */
  public static final String PREF_MVN_LOCAL_REPO     = PLUGIN_ID
                                                         + ".local_repository";         //$NON-NLS-1$

  /** DEFAULT_MVN_LOCAL_REPO */
  public static final String DEFAULT_MVN_LOCAL_REPO  = System.getProperty("user.home")
                                                         + File.separatorChar + ".m2";  //$NON-NLS-1$

  /** PREF_MVN_REMOTE_REPO */
  public static final String PREF_MVN_REMOTE_REPO    = PLUGIN_ID
                                                         + ".remote_repository";        //$NON-NLS-1$

  /** DEFAULT_MVN_REMOTE_REPO */
  public static final String DEFAULT_MVN_REMOTE_REPO = "http://repo1.maven.org/maven2/"; //$NON-NLS-1$

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  public String getLocalRepo(IProject bundleMakerProject) {
    return Platform.getPreferencesService().getString(PLUGIN_ID,
        PREF_MVN_LOCAL_REPO,
        System.getProperty("user.home")
            + File.separatorChar + ".m2", getScopeContexts(bundleMakerProject.getProject()));
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  public String getRemoteRepo(IProject bundleMakerProject) {
    return Platform.getPreferencesService().getString(PLUGIN_ID,
        PREF_MVN_REMOTE_REPO,
        "http://repo1.maven.org/maven2/", getScopeContexts(bundleMakerProject.getProject()));
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  private IScopeContext[] getScopeContexts(IProject project) {
    return new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE };
  }
}
